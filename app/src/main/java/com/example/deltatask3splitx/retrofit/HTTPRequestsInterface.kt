package com.example.deltatask3splitx.retrofit

import com.example.deltatask3splitx.retrofit.dataclasses.GetIndividualSplitDetailsDataClass
import com.example.deltatask3splitx.retrofit.dataclasses.IndividualSplitDetailsDataClass
import com.example.deltatask3splitx.retrofit.dataclasses.LoginUserIDDataClass
import com.example.deltatask3splitx.retrofit.dataclasses.PatchRequestDataClass
import com.example.deltatask3splitx.retrofit.dataclasses.RegLogDataClass
import com.example.deltatask3splitx.retrofit.dataclasses.SplitRequestDataClass
import com.example.deltatask3splitx.retrofit.dataclasses.UserIdDataClassForNetSplitDetails
import com.example.deltatask3splitx.retrofit.dataclasses.UserNetSplitsDataClass
import com.example.deltatask3splitx.retrofit.dataclasses.UsernameUserIDDataClass
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.PATCH
import retrofit2.http.POST

interface HTTPRequestsInterface
{
    @POST("/createUser")
    suspend fun createUser(@Body credentials: RegLogDataClass): Response<LoginUserIDDataClass>

    @POST("/loginUser")
    suspend fun loginUser(@Body credentials: RegLogDataClass): Response<LoginUserIDDataClass>

    @POST("/newSplit")
    suspend fun newSplit(@Body details: SplitRequestDataClass)

    @POST("/UserNetSplitDetails")
    suspend fun userNetSplitDetails(@Body userID: UserIdDataClassForNetSplitDetails): Response<UserNetSplitsDataClass>

    @GET("/fetchUsers")
    suspend fun getUsers(): Response<MutableList<UsernameUserIDDataClass>>

    @POST("/fetchIndividualSplitDetails")
    suspend fun getIndividualSplitDetails(@Body splitID: GetIndividualSplitDetailsDataClass): Response<IndividualSplitDetailsDataClass>

    @PATCH("/updateSplitSettle")
    suspend fun setSplitSettle(@Body details: PatchRequestDataClass)
}