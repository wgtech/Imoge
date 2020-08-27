package project.wgtech.imoge.explore.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import project.wgtech.imoge.BuildConfig
import project.wgtech.imoge.R
import project.wgtech.imoge.explore.datasource.ExploreRepository
import project.wgtech.imoge.explore.model.UnsplashJsonObject
import project.wgtech.imoge.util.*

class ExploreViewModel(provider: ResourceProviderImpl) : ViewModel() {

    private val repo: ExploreRepository = ExploreRepository()

    private val _chips = MutableLiveData<MutableList<String>>()
    val chips: LiveData<MutableList<String>>
        get() = _chips

    private val _photos = MutableLiveData<UnsplashJsonObject>()
    val photos: LiveData<UnsplashJsonObject>
        get() = _photos

    init {
        _chips.postValue(provider.stringArray(R.array.test_array).toMutableList())
    }

    fun nextPhotos(keyword: String, page: Int) = loadPhotos(keyword, page)
    fun loadPhotos(keyword: String, page: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            repo.photosByKeyword(BuildConfig.api_unsplash_access, keyword, page)
                .subscribe { response ->
                    if (response.isSuccessful) {
                        response.body().let {
                            it?.results()?.forEach { element -> element.keyword = keyword }
                            _photos.postValue(it)
                        }
                    }
                }
        }
    }

    override fun onCleared() {
        super.onCleared()
    }
}