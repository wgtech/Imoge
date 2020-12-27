package project.wgtech.imoge.explore.viewmodel

import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import io.reactivex.rxjava3.functions.Consumer
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import project.wgtech.imoge.BuildConfig
import project.wgtech.imoge.R
import project.wgtech.imoge.explore.datasource.LocalRepository
import project.wgtech.imoge.explore.datasource.RemoteRepository
import project.wgtech.imoge.explore.model.UnsplashJsonObject
import project.wgtech.imoge.util.*
import java.net.UnknownHostException

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
                .doOnError { t -> t.printStackTrace() }
                .subscribe({ it ->
                    if (it.isSuccessful) {
                        it.body().let {
                            it?.results()?.forEach { element -> element.keyword = keyword }
                            _photos.postValue(it)
                        }
                    }
                }, {
                    val builder = AlertDialog.Builder(provider.context())
                    if (it is UnknownHostException) {
                        builder.setTitle(R.string.error_404)
                    } else {
                        builder.setTitle(R.string.error_common)
                    }

                    builder
                        .setIcon(R.drawable.ic_round_error)
                        .setMessage(it.message)
                        .setPositiveButton(R.string.okay) { dialog, _ -> dialog.dismiss() }
                        .show()
                })
        }
    }

    override fun onCleared() {
        super.onCleared()
    }
}