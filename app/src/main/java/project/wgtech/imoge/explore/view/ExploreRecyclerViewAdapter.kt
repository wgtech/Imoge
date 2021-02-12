package project.wgtech.imoge.explore.view

import android.animation.ValueAnimator
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.widget.AppCompatImageView
import androidx.core.widget.ContentLoadingProgressBar
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.RequestOptions
import com.facebook.shimmer.Shimmer
import com.facebook.shimmer.ShimmerDrawable
import project.wgtech.imoge.R
import project.wgtech.imoge.databinding.ItemRecyclerExploreBinding
import project.wgtech.imoge.databinding.ItemRecyclerLoadingBinding
import project.wgtech.imoge.explore.model.UnsplashJsonObject
import project.wgtech.imoge.explore.model.Results

class ExploreRecyclerViewAdapter(private var obj: UnsplashJsonObject?) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private lateinit var context: Context
    private var items: ArrayList<Results?> = ArrayList()

    private val viewTypeItem = 0
    private val viewTypeLoading = 1

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        context = parent.context

        obj?.results()?.forEach { items.add(it) }

        if (viewType == viewTypeItem) {
            return ItemViewHolder(
                ItemRecyclerExploreBinding.inflate(LayoutInflater.from(parent.context), parent, false))

        } else {
            val loadingViewHolder = LoadingViewHolder(
                ItemRecyclerLoadingBinding.inflate(LayoutInflater.from(parent.context), parent, false))
            loadingViewHolder.bindView()
            return loadingViewHolder
        }

    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val results: Results? = items[position]

        if (holder is ItemViewHolder) {
            val shimmerDrawable = ShimmerDrawable().apply {
                setShimmer(Shimmer.AlphaHighlightBuilder()
                    .setAutoStart(true)
                    .setDirection(Shimmer.Direction.TOP_TO_BOTTOM)
                    .setRepeatCount(ValueAnimator.INFINITE)
                    .setDuration(3000L)
                    .setShape(Shimmer.Shape.RADIAL)
                    .setBaseAlpha(.8f)
                    .build())
                setVisible(true, true)
            }

            Glide.with(context)
                .asDrawable()
                .placeholder(shimmerDrawable)
                .load(results?.urls?.thumb)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .apply(RequestOptions().optionalCenterCrop())
                .transition(DrawableTransitionOptions.withCrossFade())
                .error(R.drawable.ic_round_error)
                .into(holder.imageView)

            holder.bindView(results)

        } else if (holder is LoadingViewHolder) {
            holder.contentLoadingProgressBar.apply {

            }.show()

        }

    }

    override fun getItemCount(): Int = items.size

    override fun getItemViewType(position: Int): Int {
        return super.getItemViewType(position)
    }

    fun addItems(obj: UnsplashJsonObject?) {
        if (obj != null) items.addAll(obj.results()) else items.addAll(emptyList())
        notifyDataSetChanged()
    }

    fun removeItems(keyword: String) {
        val iter = items.iterator()

        while (iter.hasNext()) {
            if (iter.next()?.keyword == keyword) iter.remove()
        }

        notifyDataSetChanged()
    }
}

class ItemViewHolder(
    private val viewBinding: ItemRecyclerExploreBinding
) : RecyclerView.ViewHolder(viewBinding.root) {

    var imageView: AppCompatImageView = viewBinding.imageViewExplore

    fun bindView(position: Int) {
        var context = viewBinding.root.context
        imageView.setOnClickListener { _ ->
            Toast.makeText(context, "Image $position clicked", Toast.LENGTH_SHORT).show()
        }
    }

    fun bindView(results: Results?) {
        imageView.setOnClickListener { _ ->
            val context = viewBinding.root.context
            context.startActivity(Intent(context.applicationContext, ExploreDetailActivity::class.java).apply {
                putExtra("url", results?.urls?.raw)
                putExtra("description", results?.description)
                putExtra("createdAt", results?.createdAt)
            })
        }
    }
}

class LoadingViewHolder(
    private val viewBinding: ItemRecyclerLoadingBinding
) : RecyclerView.ViewHolder(viewBinding.root) {

    var contentLoadingProgressBar: ContentLoadingProgressBar = viewBinding.progressBarLoading

    fun bindView() {
    }
}