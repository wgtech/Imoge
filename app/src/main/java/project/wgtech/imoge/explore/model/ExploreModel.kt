package project.wgtech.imoge.explore.model

import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import project.wgtech.imoge.BR

class ExploreModel(
    _selectedChipTextList: MutableList<String>
) : BaseObservable() {

    var selectedChipTextList: MutableList<String> = _selectedChipTextList
        @Bindable get() = field
        set(value) {
            field = value
            notifyPropertyChanged(BR.selectedChipTextList)
        }



}