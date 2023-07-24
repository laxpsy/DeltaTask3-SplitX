package com.example.deltatask3splitx

import com.example.deltatask3splitx.retrofit.HTTPRequestsInterface
import com.example.deltatask3splitx.retrofit.RetrofitInstance
import com.example.deltatask3splitx.retrofit.dataclasses.GetIndividualSplitDetailsDataClass
import com.example.deltatask3splitx.retrofit.dataclasses.IndividualSplitDetailsDataClass
import com.example.deltatask3splitx.retrofit.dataclasses.PatchRequestDataClass
import com.example.deltatask3splitx.retrofit.dataclasses.RegLogDataClass
import com.example.deltatask3splitx.retrofit.dataclasses.SplitRequestDataClass
import com.example.deltatask3splitx.retrofit.dataclasses.UserIdDataClassForNetSplitDetails
import com.example.deltatask3splitx.retrofit.dataclasses.UserIdDataClassForSplitIDArray
import com.example.deltatask3splitx.retrofit.dataclasses.UserNetSplitsDataClass
import com.example.deltatask3splitx.retrofit.dataclasses.UsernameUserIDDataClass
import retrofit2.Response
import kotlin.random.Random
import kotlin.random.nextInt

class RetrofitImplementation
{
    val Retrofit = RetrofitInstance.getInstance().create(HTTPRequestsInterface::class.java)

    suspend fun createAccount(username: String, password: String)
    {
        Retrofit.createUser(RegLogDataClass(name = username, password = password))
    }

    suspend fun loginValidation(username: String, password: String): Int?
    {
        return Retrofit.loginUser(RegLogDataClass(name = username, password = password)).body()?.userID
    }

    suspend fun fetchUserNetSplitData(userID: Int): Response<UserNetSplitsDataClass>
    {
       return Retrofit.userNetSplitDetails(UserIdDataClassForNetSplitDetails(userID))
    }

    suspend fun newSplit(listIDs: MutableList<UserIdDataClassForSplitIDArray>, splitAmount: Int, split_origin_id: Int, reason: String)
    {
        Retrofit.newSplit(SplitRequestDataClass(listIDs, splitAmount = splitAmount, Random.nextInt(1..10000), split_origin_id, reason))
    }

    suspend fun fetchUsers(): MutableList<UsernameUserIDDataClass>
    {
        return Retrofit.getUsers().body()!!
    }

    suspend fun getIndividualSplitDetails(splitID: Int): Response<IndividualSplitDetailsDataClass>
    {
       return Retrofit.getIndividualSplitDetails(GetIndividualSplitDetailsDataClass(splitID))

    }

    fun findOrigin(list: List<UsernameUserIDDataClass>, Response: Response<IndividualSplitDetailsDataClass>): String?
    {
        val originID = Response.body()?.userOriginId
        var username: String? = null

       username = list.filter { it -> it.userID == originID }[0].username
        return username
    }

    fun findAmount(Response: Response<IndividualSplitDetailsDataClass>): Int?
    {
        return Response.body()?.splitAmonut
    }

    fun findReason(Response: Response<IndividualSplitDetailsDataClass>): String?
    {
        return Response.body()?.reason
    }

    fun findSettled(Response: Response<IndividualSplitDetailsDataClass>): List<Int>
    {
        return Response.body()!!.settled
    }

    fun findIDs(Response: Response<IndividualSplitDetailsDataClass>): List<Int>
    {
        return Response.body()!!.listOfIDs
    }

    fun findNames(list: List<UsernameUserIDDataClass>, Response: Response<IndividualSplitDetailsDataClass>): List<String>
    {
        val listOfUserIDs = Response.body()?.listOfIDs
        println(listOfUserIDs)
        val listOfUserNames = mutableListOf<String>()
        println(list)

        listOfUserIDs?.forEach{
            list.forEach{ item ->
                if(item.userID ==  it)
                {
                    listOfUserNames.add(item.username)
                    println("Done boss! @ ${item.username}")
                }
            }
        }
        println(listOfUserNames)
        return listOfUserNames
    }

    suspend fun splitPatchRequest(splitID: Int, userID: Int)
    {
        Retrofit.setSplitSettle(PatchRequestDataClass(splitID, userID))
    }


}