package com.example.github.remote.responses

import com.google.gson.annotations.SerializedName

data class UserRepositoriesResponse(
    @SerializedName("id") val id : Int?,
    @SerializedName("name") val name : String?,
    @SerializedName("full_name") val full_name : String?,
    @SerializedName("private") val private : Boolean?,
    @SerializedName("owner") val owner : Owner?,
    @SerializedName("html_url") val html_url : String?,
    @SerializedName("description") val description : String?,
    @SerializedName("url") val url : String?
) : java.io.Serializable
