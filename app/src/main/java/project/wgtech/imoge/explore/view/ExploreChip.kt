package project.wgtech.imoge.explore.view

import com.google.android.material.chip.Chip
import project.wgtech.imoge.R
import project.wgtech.imoge.util.ResourceProvider

class ExploreChip(provider: ResourceProvider, chipText: String) : Chip(provider.context) {

    init {
        text = chipText
        isCloseIconVisible = false
        isClickable = true
        isCheckable = true
        setTextColor(provider.color(R.color.colorAccent))
        chipBackgroundColor = provider.color(R.color.colorPrimaryLight)
        chipStrokeColor = provider.color(R.color.colorAccent)
        chipStrokeWidth = 1.0f
    }


}

