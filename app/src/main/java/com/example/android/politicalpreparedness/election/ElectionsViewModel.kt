package com.example.android.politicalpreparedness.election

import android.app.Application
import androidx.lifecycle.*
import com.example.android.politicalpreparedness.database.ElectionDatabase
import com.example.android.politicalpreparedness.network.models.Election
import com.example.android.politicalpreparedness.repository.AppRepository
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

//TODO: Construct ViewModel and provide election datasource
class ElectionsViewModel(application: Application): AndroidViewModel(application)  {

    //TODO: Create live data val for upcoming elections
    private val database = ElectionDatabase.getInstance(application)

    private val appRepository: AppRepository = AppRepository(database)
    val electionList=appRepository.electionList
    val followedElectionList=appRepository.followedElectionList
    private val _navigateToElectionDetailScreen = MutableLiveData<Election>()

    val navigateToElectionDetailScreen: LiveData<Election>
        get() = _navigateToElectionDetailScreen

    init {
        viewModelScope.launch {
            appRepository.refreshElectionData()
        }
    }

    fun displayElectionDetail(election: Election){
        _navigateToElectionDetailScreen.value= election
    }

    fun navigationCompleted() {
        _navigateToElectionDetailScreen.value=null
    }

    //TODO: Create live data val for saved elections

    //TODO: Create val and functions to populate live data for upcoming elections from the API and saved elections from local database

    //TODO: Create functions to navigate to saved or upcoming election voter info
}

