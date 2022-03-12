package project.wgtech.imoge.explore.viewmodel

import android.content.Context
import android.content.res.Configuration
import android.graphics.Color
import android.graphics.drawable.Drawable
import android.util.Log
import android.widget.Toast
import androidx.appcompat.widget.AppCompatImageView
import androidx.databinding.BindingAdapter
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.Target
import project.wgtech.imoge.BuildConfig
import project.wgtech.imoge.common.CommonEnumeration
import project.wgtech.imoge.explore.view.PinchZoomableImageView

object ExploreBindingAdapter {

    private fun isDarkMode(context: Context) : Boolean
        = (context.resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK) == Configuration.UI_MODE_NIGHT_YES

    @JvmStatic
    @BindingAdapter("imageUrl")
    fun AppCompatImageView.setImageUrl(
        imageUrl: String
    ) {
        bindUrlWithGlide(this, imageUrl)
    }

    private fun bindUrlWithGlide(view: AppCompatImageView, stringifyUrl: String?) {
        Log.d(CommonEnumeration.TAG.stringify, "bindUrlWithGlide: ")
        view.context?.let {
            val circularProgressDrawable = CircularProgressDrawable(view.context)
            if (isDarkMode(it)) circularProgressDrawable.setColorSchemeColors(Color.WHITE)
            circularProgressDrawable.strokeWidth = 15f
            circularProgressDrawable.centerRadius = 45f
            circularProgressDrawable.start()

            Glide.with(it)
                .asDrawable()
                .placeholder(circularProgressDrawable)
                .load(stringifyUrl)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .apply(RequestOptions().fitCenter())
                .transition(DrawableTransitionOptions.withCrossFade())
                .listener(object : RequestListener<Drawable> {
                    override fun onLoadFailed(e: GlideException?, model: Any?, target: Target<Drawable>?, isFirstResource: Boolean): Boolean {
                        if (BuildConfig.DEBUG) Toast.makeText(view.context, "onLoadFailed", Toast.LENGTH_SHORT).show()
                        return false
                    }

                    override fun onResourceReady(resource: Drawable?, model: Any?, target: Target<Drawable>?, dataSource: DataSource?, isFirstResource: Boolean): Boolean {
                        if (BuildConfig.DEBUG) Toast.makeText(view.context, "onResourceReady", Toast.LENGTH_SHORT).show()
                        return false
                    }

                })
                .into(view)
        }
    }
}