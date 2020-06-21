package project.wgtech.imoge.viewmodel

import androidx.lifecycle.ViewModel
import project.wgtech.imoge.util.ResourceProviderImpl

class ExploreViewModel(private val provider: ResourceProviderImpl) : ViewModel() {


    override fun onCleared() {
        super.onCleared()
    }
}