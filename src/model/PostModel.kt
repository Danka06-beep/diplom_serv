package com.kuzmin.model

data class PostModel (
    val id: Long = 0,
    val author: String? = null,
    val data: Long = 0,
    val txt: String? = null,
    var like: Boolean = false,
    var dislike: Boolean = false,
    val share: Boolean = false,
    var likeTxt: Int = 0,
    val dislikeTxt: Int = 0,
    val shareTxt: Int = 0,
    val adress: String? = null,
    val coordinates: Pair<Double, Double>? = null,
    val type: PostType = PostType.Reposts,
    val url: String = "",
    val dateRepost: Long? = null,
    val autorRepost: String? = null,
    var hidePost: Boolean = false,
    var viewPost: Long = 0,
    val autorId: Long = -1,
    val postLike: ArrayList<LikeAndDislikeModel> = ArrayList(),
    val repost:PostModel?=null,
    val attachment: AttachmentModel?= null
)

enum class PostType {
    Reposts,
    YoutubeVideo,
    SponsoredPosts
}
enum class AttachmentType {
    IMAGE
}
data class AttachmentModel(val id: String, val mediaType: AttachmentType = AttachmentType.IMAGE)