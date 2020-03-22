package models

import com.google.gson.annotations.SerializedName

data class TaskItems(
        @SerializedName("id")
        val id: String,
        @SerializedName("items")
        val items: List<Task>,
        @SerializedName("kind")
        val kind: String,
        @SerializedName("selfLink")
        val selfLink: String,
        @SerializedName("title")
        val title: String,
        @SerializedName("updated")
        val updated: String
)