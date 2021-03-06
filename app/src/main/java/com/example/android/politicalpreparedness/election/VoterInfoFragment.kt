package com.example.android.politicalpreparedness.election

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.android.politicalpreparedness.databinding.FragmentVoterInfoBinding


class VoterInfoFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        //TODO: Add ViewModel values and create ViewModel

        //TODO: Add binding values
        val binding = FragmentVoterInfoBinding.inflate(inflater)

        val selectedElection = VoterInfoFragmentArgs.fromBundle(requireArguments()).election
        val viewModelFactory = VoterInfoViewModelFactory(selectedElection, activity!!.application)
        val voterViewModel =
            ViewModelProvider(this, viewModelFactory).get(VoterInfoViewModel::class.java)

        binding.viewmodel = voterViewModel
        binding.lifecycleOwner = this

        //TODO: Populate voter info -- hide views without provided data.


        /**
        Hint: You will need to ensure proper data is provided from previous fragment.
         */


        //TODO: Handle loading of URLs

        //TODO: Handle save button UI state
        //TODO: cont'd Handle save button clicks

        return binding.root
    }

    //TODO: Create method to load URL intents

}

