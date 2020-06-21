package project.wgtech.imoge.explore.model

import androidx.databinding.Observable
import androidx.lifecycle.MutableLiveData

class ExploreMutableLiveData : MutableLiveData<ExploreModel>() {

    companion object {
        val instance = ExploreMutableLiveData()
    }

    override fun setValue(value: ExploreModel?) {
        super.setValue(value)
        value?.addOnPropertyChangedCallback(callback)
    }

    override fun postValue(value: ExploreModel?) {
        super.postValue(value)
        value?.addOnPropertyChangedCallback(callback)
    }

    private val callback = object : Observable.OnPropertyChangedCallback() {
        override fun onPropertyChanged(sender: Observable?, propertyId: Int) = postValue(value)
    }
}