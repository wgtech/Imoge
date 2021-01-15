package project.wgtech.imoge.util

import android.content.Context
import android.content.res.ColorStateList
import android.graphics.Color
import android.graphics.drawable.Drawable
import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import org.jetbrains.annotations.NotNull

class ResourceProviderImpl(@NotNull override val context: Context) : ResourceProvider {

    override fun string(resId: Int): String = context.getString(resId)
    override fun stringArray(resId: Int): Array<String> = context.resources.getStringArray(resId)
    override fun drawable(resId: Int): Drawable? = context.getDrawable(resId)
    override fun color(resId: Int): ColorStateList = context.getColorStateList(resId)
}

interface ResourceProvider {
    val context : Context
    fun string(@StringRes resId: Int) : String
    fun stringArray(resId: Int) : Array<String>
    fun drawable(@DrawableRes resId: Int) : Drawable?
    fun color(@ColorRes resId: Int): ColorStateList

}