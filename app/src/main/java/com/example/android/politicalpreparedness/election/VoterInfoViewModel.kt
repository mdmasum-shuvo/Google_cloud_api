package com.example.android.politicalpreparedness.election

import android.app.Application
import android.util.Log
import androidx.lifecycle.*
import com.example.android.politicalpreparedness.database.ElectionDao
import com.example.android.politicalpreparedness.database.ElectionDatabase
import com.example.android.politicalpreparedness.network.CivicsApi
import com.example.android.politicalpreparedness.network.models.Election
import com.example.android.politicalpreparedness.network.models.VoterInfoResponse
import com.example.android.politicalpreparedness.repository.AppRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class VoterInfoViewModel(private val election: Election, private val application: Application) :
    ViewModel() {
    private val database = ElectionDatabase.getInstance(application)
    private val appRepository: AppRepository = AppRepository(database)

    //TODO: Add live data to hold voter info
    private val _selectedElection = MutableLiveData<Election>()

    private val _followButtonText = MutableLiveData<String>()
    val followButtonText: LiveData<String> = _followButtonText

    val selectedElection: LiveData<Election>
        get() = _selectedElection

    //TODO: Add var and methods to populate voter info
    private val voterInfo = MutableLiveData<VoterInfoResponse>()
    val votingLocation: LiveData<String> = Transformations.map(voterInfo) {
        it?.state!![0]?.electionAdministrationBody?.votingLocationFinderUrl
            ?: ""
    }
    val ballotInfoUrl: LiveData<String> = Transformations.map(voterInfo) {
        it?.state!![0]?.electionAdministrationBody?.ballotInfoUrl
            ?: ""
    }

    //TODO: Add var and methods to support loading URLs

    //TODO: Add var and methods to save and remove elections to local database
    //TODO: cont'd -- Populate initial state of save button to reflect proper action based on election saved status
    private suspend fun updateFollowButtonText() = withContext(Dispatchers.IO) {
        val text = if (appRepository.isFollowed(election.id)) {
            "Unfollow"
        } else {
            "Follow"
        }

        _followButtonText.postValue(text)
    }

    fun followOrUnfollow() = viewModelScope.launch(Dispatchers.IO) {
        if (appRepository.isFollowed(election.id)) {
            appRepository.unfollowElection(election.id)
            updateFollowButtonText()
        } else {
            appRepository.followElection(election.id)
            updateFollowButtonText()
        }

    }

    init {
        _selectedElection.value = election

        viewModelScope.launch {

            updateFollowButtonText()
            selectedElection.value?.let {

                refreshVoterInfo(it.id, it.division.country + "/" + it.division.state)
            }
        }
    }

    private suspend fun refreshVoterInfo(id: Int, address: String) {
        withContext(Dispatchers.IO) {
            try {
               val voterInfoResponse= CivicsApi.retrofitService.getVoterInfo(id, address)
                voterInfo.postValue(voterInfoResponse)
                Log.e("", "")
            } catch (e: Exception) {
                Log.d("ExceptionInRepo", e.toString())
            }

        }
    }
    /**
     * Hint: The saved state can be accomplished in multiple ways. It is directly related to how elections are saved/removed from the database.
     */

}