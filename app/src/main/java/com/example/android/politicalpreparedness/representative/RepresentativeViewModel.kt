package com.example.android.politicalpreparedness.representative

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.android.politicalpreparedness.network.CivicsApi
import com.example.android.politicalpreparedness.network.models.Address
import com.example.android.politicalpreparedness.network.models.RepresentativeResponse
import com.example.android.politicalpreparedness.network.models.representatives
import com.example.android.politicalpreparedness.representative.model.Representative
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.lang.Exception

class RepresentativeViewModel : ViewModel() {

    //TODO: Establish live data for representatives and address
    private val _representatives = MutableLiveData<List<Representative>>()
    val representatives: LiveData<List<Representative>>
        get() = _representatives
    //TODO: Create function to fetch representatives from API from a provided address

    /**
     *  The following code will prove helpful in constructing a representative from the API. This code combines the two nodes of the RepresentativeResponse into a single official :

    val (offices, officials) = getRepresentativesDeferred.await()
    _representatives.value = offices.flatMap { office -> office.getRepresentatives(officials) }

    Note: getRepresentatives in the above code represents the method used to fetch data from the API
    Note: _representatives in the above code represents the established mutable live data housing representatives

     */

    //TODO: Create function get address from geo location
    fun searchRepresentetive(address: Address) {
        viewModelScope.launch(Dispatchers.IO) {


            try {
                val networkRepresentativeResponse =
                    CivicsApi.retrofitService.getRepresentative(address.toFormattedString())

               val representatives = networkRepresentativeResponse.representatives

                _representatives.postValue(representatives)
                Log.d("ExceptionInGetRepresentatives", "")


            } catch (e: Exception) {

                Log.d("ExceptionInGetRepresentatives", e.toString())
            }
        }
    }
    //TODO: Create function to get address from individual fields

}
