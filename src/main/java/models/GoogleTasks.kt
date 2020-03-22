package models

import com.google.gson.annotations.SerializedName

data class GoogleTasks(
        @SerializedName("items")
        val tasks: List<TaskItems>,
        @SerializedName("kind")
        val kind: String
)