package com.example.android.politicalpreparedness.repository

import android.util.Log
import androidx.lifecycle.LiveData
import com.example.android.politicalpreparedness.database.ElectionDatabase
import com.example.android.politicalpreparedness.network.CivicsApi
import com.example.android.politicalpreparedness.network.models.Election
import com.example.android.politicalpreparedness.network.models.FollowedElection
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class AppRepository(private val database: ElectionDatabase) {

    val electionList: LiveData<List<Election>>
        get() = database.electionDao.getAllElections()

    val followedElectionList: LiveData<List<Election>>
        get() = database.electionDao.getAllFollowedElections()

    suspend fun refreshElectionData() {
        withContext(Dispatchers.IO) {
            try {
                val civicListResponse = CivicsApi.retrofitService.getElections()
                val electionData = civicListResponse.elections
                database.electionDao.insertAll(electionData!!)
                Log.d("ExceptionInRepo", "e.toString()")

            } catch (e: Exception) {
                Log.d("ExceptionInRepo", e.toString())
            }
        }
    }

    suspend fun isFollowed(id: Int): Boolean {
        return database.electionDao.getFollowedElectionByID(id) != null && database.electionDao.getFollowedElectionByID(
            id
        ) == id

    }

    suspend fun unfollowElection(id: Int) {
        database.electionDao.unfollowElection(id)

    }

    suspend fun followElection(id: Int) {
        database.electionDao.followElection(FollowedElection(id))

    }
}