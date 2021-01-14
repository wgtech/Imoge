package project.wgtech.imoge.explore.model

import project.wgtech.imoge.R

enum class Status {

    ERROR_404 {
        override val code: Int = 404
        override val tag: String = "error_404"
        override val drawableId: Int = R.drawable.ic_round_error
        override val titleId: Int = R.string.error_404
        override val descriptionId: Int = R.string.error_404_text
    },

    ERROR_408 {
        override val code: Int = 408
        override val tag: String = "error_408"
        override val drawableId: Int = R.drawable.ic_round_error
        override val titleId: Int = R.string.error_408
        override val descriptionId: Int = R.string.error_408_text
    },

    ERROR_ETC {
        override val code: Int = 499
        override val tag: String = "error_etc"
        override val drawableId: Int = R.drawable.ic_round_error
        override val titleId: Int = R.string.error_common
        override val descriptionId: Int = R.string.error_common
    };

    // abstract functions
    abstract val code : Int
    abstract val tag : String
    abstract val drawableId : Int
    abstract val titleId: Int
    abstract val descriptionId : Int
}