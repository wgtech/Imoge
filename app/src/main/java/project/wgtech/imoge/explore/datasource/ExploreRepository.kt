package project.wgtech.imoge.explore.datasource

import android.os.Looper
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Flowable
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import project.wgtech.imoge.explore.model.UnsplashJsonObject
import project.wgtech.imoge.util.ApiClient

class ExploreRepository {

    suspend fun photosByKeyword(
        api_unsplash_access : String,
        keyword: String,
        page: Int
    ): Flowable<UnsplashJsonObject> {
        return withContext(Dispatchers.IO) {
            ApiClient.unsplashClient.create(ApiClient.IUnsplashService::class.java)
                .photosByKeyword(api_unsplash_access, keyword, page)
                .onBackpressureBuffer()
                .observeOn(AndroidSchedulers.from(Looper.getMainLooper()))
        }

    }
}