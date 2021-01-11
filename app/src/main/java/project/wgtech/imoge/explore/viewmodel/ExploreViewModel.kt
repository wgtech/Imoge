package project.wgtech.imoge.explore.viewmodel

import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import project.wgtech.imoge.BuildConfig
import project.wgtech.imoge.util.ExceptionHandleUtil
import project.wgtech.imoge.explore.datasource.LocalRepository
import project.wgtech.imoge.explore.datasource.RemoteRepository
import project.wgtech.imoge.explore.model.UnsplashJsonObject
import project.wgtech.imoge.util.*
import java.lang.Exception

class ExploreViewModel(provider: ResourceProviderImpl) : ViewModel() {

    private val remoteRepo: RemoteRepository = RemoteRepository()
    private val localRepo: LocalRepository = LocalRepository()

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
                    ExceptionHandleUtil(t as Exception, provider.context())
                        .showDialog(
                            { loadPhotos(provider, keyword, page) }, { (provider.context() as AppCompatActivity).finish() })
                }
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