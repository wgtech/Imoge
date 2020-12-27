package project.wgtech.imoge.explore.viewmodel

import android.util.Log
import androidx.appcompat.widget.AppCompatImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.RequestOptions
import project.wgtech.imoge.common.CommonEnumeration
import project.wgtech.imoge.explore.view.PinchZoomableImageView

object ExploreBindingAdapter {

    @JvmStatic
    @BindingAdapter("thumbImageUrl")
    fun bindUrlThumbWithGlide(view: AppCompatImageView?, stringifyUrl: String?) {
        Log.d(CommonEnumeration.TAG.stringify, "bindUrlWithGlide: ")
        view?.context?.let {
            Glide.with(it)
                .asDrawable()
                .load(stringifyUrl)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .apply(RequestOptions().optionalCenterCrop())
                .transition(DrawableTransitionOptions.withCrossFade())
                .into(view)
        }
    }

    @JvmStatic
    @BindingAdapter("imageUrl")
    fun bindUrlWithGlide(view: PinchZoomableImageView?, stringifyUrl: String?) {
        Log.d(CommonEnumeration.TAG.stringify, "bindUrlWithGlide: ")
        view?.context?.let {
            Glide.with(it)
                .asDrawable()
                .load(stringifyUrl)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .apply(RequestOptions().fitCenter())
                .transition(DrawableTransitionOptions.withCrossFade())
                .into(view)
        }
    }
}