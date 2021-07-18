package com.example.android.politicalpreparedness.repository

import android.util.Log
import androidx.lifecycle.LiveData
import com.example.android.politicalpreparedness.database.ElectionDatabase
import com.example.android.politicalpreparedness.network.CivicsApi
import com.example.android.politicalpreparedness.network.models.Election
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class AppRepository(private val database: ElectionDatabase) {

    val electionList: LiveData<List<Election>>
        get() = database.electionDao.getAllElections()

    suspend fun refreshElectionData() {
        withContext(Dispatchers.IO) {
            try {
                val civicListResponse = CivicsApi.retrofitService.getElections()
                val electionData=civicListResponse.elections
              val insert= database.electionDao.insertAll(electionData!!)
                Log.d("ExceptionInRepo", "e.toString()")

            } catch (e: Exception) {
                Log.d("ExceptionInRepo", e.toString())
            }
        }
    }
}