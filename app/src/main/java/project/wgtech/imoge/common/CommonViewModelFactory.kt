package project.wgtech.imoge.common

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import project.wgtech.imoge.explore.viewmodel.ExploreViewModel
import project.wgtech.imoge.util.ResourceProviderImpl

class CommonViewModelFactory(var context: Context) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return if (modelClass.isAssignableFrom(ExploreViewModel::class.java)) {
            ExploreViewModel(ResourceProviderImpl(context)) as T

        } else throw IllegalAccessException("Not defined modelClass!")
    }
}