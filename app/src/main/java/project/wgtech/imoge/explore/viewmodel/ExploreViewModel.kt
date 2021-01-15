package project.wgtech.imoge.explore.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import project.wgtech.imoge.BuildConfig
import project.wgtech.imoge.explore.datasource.LocalRepository
import project.wgtech.imoge.explore.datasource.RemoteRepository
import project.wgtech.imoge.explore.model.Status
import project.wgtech.imoge.explore.model.UnsplashJsonObject
import project.wgtech.imoge.util.*
import java.lang.Exception
import java.net.SocketTimeoutException
import java.net.UnknownHostException

class ExploreViewModel(provider: ResourceProviderImpl) : ViewModel() {

    private val remoteRepo: RemoteRepository = RemoteRepository()
    private val localRepo: LocalRepository = LocalRepository()

    private val _status = MutableLiveData<Status>()
    val status: LiveData<Status>
        get() = _status

    private val _chips = MutableLiveData<MutableList<String>>()
    val chips: LiveData<MutableList<String>>
        get() = _chips

    private val _photos = MutableLiveData<UnsplashJsonObject>()
    val photos: LiveData<UnsplashJsonObject>
        get() = _photos

    init {
        _chips.postValue(localRepo.getChips(provider))
    }

    fun nextPhotos(provider: ResourceProviderImpl, keyword: String, page: Int) = loadPhotos(provider, keyword, page)
    fun loadPhotos(provider: ResourceProviderImpl, keyword: String, page: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            remoteRepo.photosByKeyword(BuildConfig.api_unsplash_access, keyword, page)
                .doOnError { t ->
                    t.printStackTrace()
                    when (t) {
                        is UnknownHostException -> _status.value = Status.ERROR_404
                        is SocketTimeoutException -> _status.value = Status.ERROR_408
                        is Exception -> _status.value = Status.ERROR_ETC
                    }
                }
                //.retryWhen { } // TODO 재시도하는 방법 구현하기
                .subscribe(
                    // onNext
                    { it ->
                        if (it.isSuccessful) {
                            it.body().let {
                                it?.results()?.forEach { element -> element.keyword = keyword }
                                _photos.postValue(it)
                            }
                        }
                    },
                    // onError
                    {

                    })
        }
    }

    override fun onCleared() {
        super.onCleared()
    }
}