package com.example.benefit.remote.models

import com.google.gson.annotations.SerializedName

data class FriendDTO(@SerializedName("name") var name: String,
                     @SerializedName("lastName") var lastName: String)
