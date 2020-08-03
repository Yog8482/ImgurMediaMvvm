package com.yogendra.imgurmediamvvm.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import com.google.gson.annotations.SerializedName
import com.yogendra.imgurmediamvvm.utils.DATE_FORMAT_12
import com.yogendra.imgurmediamvvm.utils.DATE_FORMAT_20
import com.yogendra.imgurmediamvvm.utils.formatDateFromDateString
import java.math.RoundingMode
import java.text.DecimalFormat
import java.text.SimpleDateFormat
import java.util.*

@Entity(tableName = "Posts", primaryKeys = ["id"])
data class PostsData(
    val id: String,
    @ColumnInfo(collate = ColumnInfo.NOCASE)
    val title: String,
    val description: String? = null,
    val datetime: String,//In seconds
    val account_url: String?,
    val views: Long = 0,
    val points: Long = 0,
    val images_count: Long,
    val images: List<PostImages>?,
    var searchquery: String?
) {


    fun getDateFormatted(): String {
        return formatDateFromDateString(DATE_FORMAT_12, DATE_FORMAT_20, getDateTime(datetime))
    }

    private fun getDateTime(s: String): String {
        try {
            val sdf = SimpleDateFormat(DATE_FORMAT_12)
            val netDate = Date(s.toLong() * 1000) //Seconds to milliseconds
            return sdf.format(netDate)
        } catch (e: Exception) {
            return e.toString()
        }
    }

    fun getFormattedPoints(): String {

        val df = DecimalFormat("#.##")
        df.roundingMode = RoundingMode.CEILING

        if (points > 1000)
            return "${df.format(points.toDouble() / 1000)}K"
        else
            return "${points}"
    }

    fun getFormattedViews(): String {
        val df = DecimalFormat("#.##")
        df.roundingMode = RoundingMode.CEILING

        if (views > 1000)
            return "${df.format(views.toDouble() / 1000)}K"
        else
            return "${views}"

    }

    override fun toString() = "Blog title: $title,Image count: $images_count"
}

@Entity(tableName = "Post_Images", primaryKeys = ["image_id"])
data class PostImages(
    @field:SerializedName("id")
    val image_id: String,
    val title: String? = null,
    val description: String?,
    val datetime: String,//In Seconds
    val type: String,
    val animated: Boolean,
    val width: Int,
    val height: Int,
    val size: Long,
    val views: Long,
    val link: String,
    var local_comment: String? = null //To handle Local comment
) {
    override fun toString() = "image url= $link"
}




