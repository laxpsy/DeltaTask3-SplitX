package com.example.deltatask3splitx.retrofit.dataclasses

data class SplitRequestDataClass(
    val userIDs: MutableList<UserIdDataClassForSplitIDArray>,
    val splitAmount: Int,
    val splitID: Int,
    val split_origin_id: Int,
    val reason: String
)
