package com.yogendra.imgurmediamvvm.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


data class ImgurResponseData(
    val id: String,
    val title: String,
    val description: String? = null,
    val datetime: String,//In seconds
    val cover: String,
    val cover_width: Int,
    val cover_height: Int,
    val account_url: String,
    val account_id: String,
    val privacy: String,
    val layout: String,
    val views: Long = 0,
    val link: String,
    val ups: Long = 0,
    val downs: Long = 0,
    val points: Long = 0,
    val score: Long = 0,
    val is_album: Boolean,
    val vote: String? = null,
    val favorite: Boolean,
    val nsfw: Boolean,
    val section: String,
    val comment_count: Long = 0,
    val favorite_count: Long = 0,
    val topic: String,
    val topic_id: Long,
    val images_count: Long,
    val in_gallery: Boolean,
    val is_ad: Boolean,
    val ad_type: Long,
    val ad_url: String,
    val in_most_viral: Boolean,
    val include_album_ads: Boolean,
    val images: List<BlogImages>,
    val ad_config: AdConfig,
    val tags: List<ImgurTags> = emptyList()

) {
    override fun toString() = "Blog title: $title,Image count: $images_count"
}

data class AdConfig(
    val safeFlags: List<String>?,
    val highRiskFlags: List<String> = emptyList(),
    val unsafeFlags: List<String> = emptyList(),
    val wallUnsafeFlags: List<String> = emptyList(),
    val showsAds: Boolean
)


data class ImgurTags(
    val name: String,
    val display_name: String,
    val followers: Long,
    val total_items: Long,
    val following: Boolean,
    val is_whitelisted: Boolean,
    val background_hash: String,
    val thumbnail_hash: String? = null,
    val accent: String,
    val background_is_animated: Boolean,
    val thumbnail_is_animated: Boolean,
    val is_promoted: Boolean,
    val description: String,
    val logo_hash: String? = null,
    val logo_destination_url: String? = null,
    val description_annotations: Any? = null
)

@Entity(tableName = "blog_images")
data class BlogImages(
    @PrimaryKey
    @ColumnInfo(name = "id")
    val id: String,
    val title: String? = null,
    val description: String,
    val datetime: String,//In Seconds
    val type: String,
    val animated: Boolean,
    val width: Int,
    val height: Int,
    val size: Long,
    val views: Long,
    val bandwidth: Long,
    val vote: String? = null,
    val favorite: Boolean,
    val nsfw: String? = null,
    val section: String? = null,
    val account_url: String? = null,
    val account_id: String? = null,
    val is_ad: Boolean,
    val in_most_viral: Boolean,
    val has_sound: Boolean,
    val tags: List<ImgurTags>? = null,
    val ad_type: String,
    val ad_url: String,
    val edited: String,
    val in_gallery: Boolean,
    val link: String,
    val comment_count: String? = null,
    val favorite_count: String? = null,
    val ups: String? = null,
    val downs: String? = null,
    val points: String? = null,
    val score: String? = null,
    val local_comment: String? = null //To handle Local comment
) {
    override fun toString() = "image url= $link"
}
