package project.wgtech.imoge.explore.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.lang.StringBuilder

data class PhotosByKeywordEntity(
    @SerializedName("total")
    @Expose
    private var total: Int?,
    @SerializedName("total_pages")
    @Expose
    private var totalPages: Int?,
    @SerializedName("results")
    @Expose
    private var results: List<Results>
){
    override fun toString(): String {
        var stringBuilder = StringBuilder()
        for (i in 0..results.size) {
            stringBuilder.append("$results[i]\n")
        }
        return "total= $total, totalPages= $totalPages\n$stringBuilder"
    }

    fun total() = total
    fun totalPages() = totalPages
    fun results() = results
}

data class Results(
    @SerializedName("id")
    @Expose
    private var id: String?,
    @SerializedName("created_at")
    @Expose
    private var createdAt: String?,
    @SerializedName("updated_at")
    @Expose
    private var updatedAt: String?,
    @SerializedName("promoted_at")
    @Expose
    private var promotedAt: Any?,
    @SerializedName("width")
    @Expose
    private var width: Int?,
    @SerializedName("height")
    @Expose
    private var height: Int?,
    @SerializedName("color")
    @Expose
    private var color: String?,
    @SerializedName("description")
    @Expose
    private var description: String?,
    @SerializedName("alt_description")
    @Expose
    var altDescription: String?,
    @SerializedName("urls")
    @Expose
    var urls: Urls?,
    @SerializedName("links")
    @Expose
    var linksUser: LinksUser?,
    @SerializedName("categories")
    @Expose
    var categories: List<Any>?,
    @SerializedName("likes")
    @Expose
    var likes: Int?,
    @SerializedName("liked_by_user")
    @Expose
    var likedByUser: Boolean?,
    @SerializedName("current_user_collections")
    @Expose
    var currentUserCollections: List<Any>?,
    @SerializedName("sponsorship")
    @Expose
    var sponsorship: Any?,
    @SerializedName("user")
    @Expose
    var user: User?,
    @SerializedName("tags")
    @Expose
    var tags: List<Tag>?
) {
    override fun toString(): String {
        return "$id, $width, $height, raw=${urls?.raw}, thumb=${urls?.thumb}"
    }
}

data class Urls(
    @SerializedName("raw")
    @Expose
    var raw: String?,
    @SerializedName("full")
    @Expose
    var full: String?,
    @SerializedName("regular")
    @Expose
    var regular: String?,
    @SerializedName("small")
    @Expose
    var small: String?,
    @SerializedName("thumb")
    @Expose
    var thumb: String?
)

data class User(
    @SerializedName("self")
    @Expose
    var self: String?,
    @SerializedName("html")
    @Expose
    var html: String?,
    @SerializedName("download")
    @Expose
    var download: String?,
    @SerializedName("download_location")
    @Expose
    var downloadLocation: String?
)

data class LinksUser(
    @SerializedName("self")
    @Expose
    var self: String?,
    @SerializedName("html")
    @Expose
    var html: String?,
    @SerializedName("photos")
    @Expose
    var photos: String?,
    @SerializedName("likes")
    @Expose
    var likes: String?,
    @SerializedName("portfolio")
    @Expose
    var portfolio: String?,
    @SerializedName("following")
    @Expose
    var following: String?,
    @SerializedName("followers")
    @Expose
    var followers: String?
)

data class Tag(
    @SerializedName("type")
    @Expose
    var type: String?,
    @SerializedName("title")
    @Expose
    var title: String?,
    @SerializedName("source")
    @Expose
    var source: Source?
)

data class Source(
    @SerializedName("ancestry")
    @Expose
    var ancestry: Ancestry?,
    @SerializedName("title")
    @Expose
    var title: String?,
    @SerializedName("subtitle")
    @Expose
    var subtitle: String?,
    @SerializedName("description")
    @Expose
    var description: String?,
    @SerializedName("meta_title")
    @Expose
    var metaTitle: String?,
    @SerializedName("meta_description")
    @Expose
    var metaDescription: String?,
    @SerializedName("cover_photo")
    @Expose
    var coverPhoto: CoverPhoto?
)

data class Ancestry(
    @SerializedName("type")
    @Expose
    var type: Type?,
    @SerializedName("category")
    @Expose
    var category: Category?
)

data class CoverPhoto(
    @SerializedName("id")
    @Expose
    var id: String?,
    @SerializedName("created_at")
    @Expose
    var createdAt: String?,
    @SerializedName("updated_at")
    @Expose
    var updatedAt: String?,
    @SerializedName("promoted_at")
    @Expose
    var promotedAt: String?,
    @SerializedName("width")
    @Expose
    var width: Int?,
    @SerializedName("height")
    @Expose
    var height: Int?,
    @SerializedName("color")
    @Expose
    var color: String?,
    @SerializedName("description")
    @Expose
    var description: String?,
    @SerializedName("alt_description")
    @Expose
    var altDescription: String?,
    @SerializedName("urls")
    @Expose
    var coverPhotosUrls: CoverPhotosUrls?,
    @SerializedName("links")
    @Expose
    var resultsLinks: ResultsLinks?,
    @SerializedName("categories")
    @Expose
    var categories: List<Any>?,
    @SerializedName("likes")
    @Expose
    var likes: Int?,
    @SerializedName("liked_by_user")
    @Expose
    var likedByUser: Boolean?,
    @SerializedName("current_user_collections")
    @Expose
    var currentUserCollections: List<Any>?,
    @SerializedName("sponsorship")
    @Expose
    var sponsorship: Any?,
    @SerializedName("user")
    @Expose
    var resultsUser: ResultsUser?
)

data class Type(
    @SerializedName("slug")
    @Expose
    var slug: String?,
    @SerializedName("pretty_slug")
    @Expose
    var prettySlug: String?
)

data class Category(
    @SerializedName("slug")
    @Expose
    var slug: String?,
    @SerializedName("pretty_slug")
    @Expose
    var prettySlug: String?
)

data class CoverPhotosUrls(
    @SerializedName("raw")
    @Expose
    var raw: String?,
    @SerializedName("full")
    @Expose
    var full: String?,
    @SerializedName("regular")
    @Expose
    var regular: String?,
    @SerializedName("small")
    @Expose
    var small: String?,
    @SerializedName("thumb")
    @Expose
    var thumb: String
)

data class ResultsLinks(
    @SerializedName("self")
    @Expose
    var self: String?,
    @SerializedName("html")
    @Expose
    var html: String?,
    @SerializedName("download")
    @Expose
    var download: String?,
    @SerializedName("download_location")
    @Expose
    var downloadLocation: String?
)

data class ResultsUser(
    @SerializedName("id")
    @Expose
    var id: String?,
    @SerializedName("updated_at")
    @Expose
    var updatedAt: String?,
    @SerializedName("username")
    @Expose
    var username: String?,
    @SerializedName("name")
    @Expose
    var name: String?,
    @SerializedName("first_name")
    @Expose
    var firstName: String?,
    @SerializedName("last_name")
    @Expose
    var lastName: String?,
    @SerializedName("twitter_username")
    @Expose
    var twitterUsername: String?,
    @SerializedName("portfolio_url")
    @Expose
    var portfolioUrl: String?,
    @SerializedName("bio")
    @Expose
    var bio: String?,
    @SerializedName("location")
    @Expose
    var location: String?,
    @SerializedName("links")
    @Expose
    var userLinks: UserLinks?,
    @SerializedName("profile_image")
    @Expose
    var userProfileImage: UserProfileImage?,
    @SerializedName("instagram_username")
    @Expose
    var instagramUsername: String?,
    @SerializedName("total_collections")
    @Expose
    var totalCollections: Int?,
    @SerializedName("total_likes")
    @Expose
    var totalLikes: Int?,
    @SerializedName("total_photos")
    @Expose
    var totalPhotos: Int?,
    @SerializedName("accepted_tos")
    @Expose
    var acceptedTos: Boolean
)

data class UserLinks(
    @SerializedName("self")
    @Expose
    var self: String?,
    @SerializedName("html")
    @Expose
    var html: String?,
    @SerializedName("photos")
    @Expose
    var photos: String?,
    @SerializedName("likes")
    @Expose
    var likes: String?,
    @SerializedName("portfolio")
    @Expose
    var portfolio: String?,
    @SerializedName("following")
    @Expose
    var following: String?,
    @SerializedName("followers")
    @Expose
    var followers: String?
)

data class UserProfileImage(
    @SerializedName("small")
    @Expose
    var small: String?,
    @SerializedName("medium")
    @Expose
    var medium: String?,
    @SerializedName("large")
    @Expose
    var large: String?
)