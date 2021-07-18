package com.example.android.politicalpreparedness.election

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.android.politicalpreparedness.R
import com.example.android.politicalpreparedness.databinding.FragmentElectionBinding
import com.example.android.politicalpreparedness.election.adapter.ElectionListAdapter

class ElectionsFragment: Fragment() {

    //TODO: Declare ViewModel
    private lateinit var binding: FragmentElectionBinding

    private val viewModel: ElectionsViewModel by lazy {
        val activity = requireNotNull(this.activity) {
            "You can only access the viewModel after onViewCreated()"
        }
        ViewModelProvider(this, ElectionsViewModelFactory(activity.application)).get(
            ElectionsViewModel::class.java
        )
    }
    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        //TODO: Add ViewModel values and create ViewModel

        //TODO: Add binding values
        //Data binding
        binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_election, container, false)
        binding.lifecycleOwner = this

        binding.viewmodel = viewModel
        //TODO: Link elections to voter info
        viewModel.navigateToElectionDetailScreen.observe(viewLifecycleOwner, Observer {
            if (null != it) {
                // Must find the NavController from the Fragment
                this.findNavController()
                    .navigate(ElectionsFragmentDirections.actionElectionsFragmentToVoterInfoFragment(it))
                // Tell the ViewModel we've made the navigate call to prevent multiple navigation
               viewModel.navigationCompleted()
            }
        })
        //TODO: Initiate recycler adapters
        binding.upcomingElectionRecycler.adapter = ElectionListAdapter(ElectionListAdapter.OnClickListener{
            viewModel.displayElectionDetail(it)
        })

        //TODO: Populate recycler adapters
        binding.savedElectionRecycler.adapter = ElectionListAdapter(ElectionListAdapter.OnClickListener{
           viewModel.displayElectionDetail(it)
        })
       return binding.root
    }

    //TODO: Refresh adapters when fragment loads

}