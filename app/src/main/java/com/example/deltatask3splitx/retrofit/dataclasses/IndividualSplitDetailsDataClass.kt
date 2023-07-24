package com.example.deltatask3splitx.retrofit.dataclasses

data class IndividualSplitDetailsDataClass(
    val listOfIDs: List<Int>,
    val settled: List<Int>,
    val userOriginId: Int,
    val splitAmonut: Int,
    val reason: String
)