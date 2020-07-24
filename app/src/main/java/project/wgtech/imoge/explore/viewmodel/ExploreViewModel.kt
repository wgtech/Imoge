package project.wgtech.imoge.explore.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import project.wgtech.imoge.BuildConfig
import project.wgtech.imoge.R
import project.wgtech.imoge.explore.model.ExploreRepository
import project.wgtech.imoge.explore.model.PhotosByKeywordEntity
import project.wgtech.imoge.util.*

class ExploreViewModel(private val provider: ResourceProviderImpl) : ViewModel() {

    private val _chips = MutableLiveData<MutableList<String>>()
    val chips: LiveData<MutableList<String>>
        get() = _chips


    private val _photosByKeyword = MutableLiveData<PhotosByKeywordEntity?>()
    val photosByKeyword: LiveData<PhotosByKeywordEntity?>
        get() = _photosByKeyword

    init {
        _chips.postValue(provider.stringArray(R.array.test_array).toMutableList())
        setUpPhotosByKeywordEntity()
    }

    private fun setUpPhotosByKeywordEntity() {
        viewModelScope.launch(Dispatchers.IO) {
            val repo = ExploreRepository().getPhotosByKeywordEntity(BuildConfig.api_unsplash_access, "joyful")

            repo.subscribe(
                { v: PhotosByKeywordEntity? -> _photosByKeyword.postValue(v) },
                { t: Throwable -> t.printStackTrace() }
            )
        }
    }

    override fun onCleared() {
        super.onCleared()
    }
}