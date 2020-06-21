package project.wgtech.imoge.explore.viewmodel

import androidx.databinding.Bindable
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import project.wgtech.imoge.R
import project.wgtech.imoge.explore.model.ExploreModel
import project.wgtech.imoge.explore.model.ExploreMutableLiveData
import project.wgtech.imoge.util.ResourceProviderImpl

class ExploreViewModel(private val provider: ResourceProviderImpl) : ViewModel() {

    val exploreLiveData: LiveData<ExploreModel> = ExploreMutableLiveData.instance.apply {
        value = ExploreModel(provider.stringArray(R.array.test_array).toMutableList())
    }

    var selectedChipTextList: MutableList<String>
        get() = exploreLiveData.value!!.selectedChipTextList
        set(value) {
            exploreLiveData.value!!.selectedChipTextList = value
        }

    override fun onCleared() {
        super.onCleared()
    }
}