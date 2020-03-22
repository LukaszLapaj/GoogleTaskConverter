package models

import com.google.gson.annotations.SerializedName

data class Task(
        @SerializedName("completed")
        val completed: String,
        @SerializedName("delete_time")
        val deleteTime: String,
        @SerializedName("deleted")
        val deleted: Boolean,
        @SerializedName("due")
        val due: String,
        @SerializedName("hidden")
        val hidden: Boolean,
        @SerializedName("id")
        val id: String,
        @SerializedName("kind")
        val kind: String,
        @SerializedName("notes")
        val notes: String,
        @SerializedName("selfLink")
        val selfLink: String,
        @SerializedName("status")
        val status: String,
        @SerializedName("title")
        val title: String,
        @SerializedName("updated")
        val updated: String
)