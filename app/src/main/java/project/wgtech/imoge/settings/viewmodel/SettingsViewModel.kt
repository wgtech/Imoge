package project.wgtech.imoge.settings.viewmodel

import androidx.lifecycle.ViewModel
import project.wgtech.imoge.util.ResourceProviderImpl

class SettingsViewModel(private val provider: ResourceProviderImpl) : ViewModel() {

    override fun onCleared() {
        super.onCleared()
    }
}