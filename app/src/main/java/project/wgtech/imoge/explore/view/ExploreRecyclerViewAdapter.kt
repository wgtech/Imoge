package project.wgtech.imoge.explore.view

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatImageView
import androidx.core.widget.ContentLoadingProgressBar
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
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
            val itemViewHolder = ItemViewHolder(
                ItemRecyclerExploreBinding.inflate(LayoutInflater.from(parent.context), parent, false))
            itemViewHolder.bindView()
            return itemViewHolder

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
            Glide.with(context)
                .asBitmap()
                .load(results?.urls?.thumb)
                .optionalCenterCrop()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(holder.imageView)

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

    fun bindView() {
    }
}

class LoadingViewHolder(
    private val viewBinding: ItemRecyclerLoadingBinding
) : RecyclerView.ViewHolder(viewBinding.root) {

    var contentLoadingProgressBar: ContentLoadingProgressBar = viewBinding.progressBarLoading

    fun bindView() {
    }
}