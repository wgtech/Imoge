package project.wgtech.imoge.util

import android.content.Context
import android.graphics.drawable.Drawable
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import org.jetbrains.annotations.NotNull

class ResourceProviderImpl(@NotNull private val context: Context) : ResourceProvider {

    override fun context(): Context = context

    override fun string(resId: Int): String = context.getString(resId)
    override fun drawable(resId: Int): Drawable? = context.getDrawable(resId)
}

interface ResourceProvider {
    fun context() : Context
    fun string(@StringRes resId: Int) : String
    fun drawable(@DrawableRes resId: Int) : Drawable?

}