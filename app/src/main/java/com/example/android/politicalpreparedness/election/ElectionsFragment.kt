package com.example.android.politicalpreparedness.election

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
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
        ViewModelProvider(this, ElectionsViewModel.Factory(activity.application)).get(
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

        //TODO: Initiate recycler adapters
        binding.upcomingElectionRecycler.adapter = ElectionListAdapter(ElectionListAdapter.OnClickListener{
            //viewModel.displayElectionDetails(it)
        })

        //TODO: Populate recycler adapters

       return binding.root
    }

    //TODO: Refresh adapters when fragment loads

}