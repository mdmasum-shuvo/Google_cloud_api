package com.example.android.politicalpreparedness.database

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.android.politicalpreparedness.network.models.Election
import com.example.android.politicalpreparedness.network.models.FollowedElection

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


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun followElection(id: FollowedElection)

    @Query("DELETE FROM followed_elections WHERE id = :id")
    suspend fun unfollowElection(id: Int)

    @Query("SELECT * FROM election_table INNER JOIN followed_elections ON election_table.id = followed_elections.id ")
    fun getAllFollowedElections():LiveData<List<Election>>

    @Query("SELECT election_table.id FROM election_table WHERE id =:id AND  id IN followed_elections ")
    suspend fun getFollowedElectionByID(id: Int): Int
}