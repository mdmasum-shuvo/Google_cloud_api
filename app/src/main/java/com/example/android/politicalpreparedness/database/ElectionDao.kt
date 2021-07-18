package com.example.android.politicalpreparedness.database

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.android.politicalpreparedness.network.models.Election

@Dao
interface ElectionDao {

    //TODO: Add insert query
    @Transaction
    fun updateData(election: List<Election>): List<Long> {
        deleteAll()
        return insertAll(election)
    }

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(election: List<Election>): List<Long>



    //TODO: Add select all election query

    @Query("SELECT * FROM election_table")
    fun getAllElections(): LiveData<List<Election>>
    //TODO: Add select single election query

    //TODO: Add delete query
    @Query("DELETE FROM election_table")
    fun deleteAll()
    //TODO: Add clear query

}