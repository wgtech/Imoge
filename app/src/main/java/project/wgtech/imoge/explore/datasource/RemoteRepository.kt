package project.wgtech.imoge.explore.datasource

import android.os.Looper
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Flowable
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import project.wgtech.imoge.explore.model.PhotoStatistics
import project.wgtech.imoge.explore.model.UnsplashJsonObject
import project.wgtech.imoge.util.ApiClient
import retrofit2.Response

class RemoteRepository {

    suspend fun photosByKeyword(
        api_unsplash_access : String,
        keyword: String,
        page: Int
    ): Flowable<Response<UnsplashJsonObject>> {
        return withContext(Dispatchers.IO) {
            ApiClient.unsplashClient.create(ApiClient.IUnsplashService::class.java)
                .photosByKeyword(api_unsplash_access, keyword, page)
                .onBackpressureBuffer()
                .observeOn(AndroidSchedulers.from(Looper.getMainLooper()))
        }
    }

    suspend fun statisticsByPhotoId(
        photoId: String
    ): Flowable<PhotoStatistics> {
        return withContext(Dispatchers.IO) {
            ApiClient.unsplashClient.create(ApiClient.IUnsplashService::class.java)
                .statisticsByPhotoId(photoId)
                .observeOn(AndroidSchedulers.from(Looper.getMainLooper()))
        }
    }
}