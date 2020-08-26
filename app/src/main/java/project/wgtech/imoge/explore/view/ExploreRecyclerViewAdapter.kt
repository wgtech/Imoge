package project.wgtech.imoge.explore.view

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import kotlinx.android.synthetic.main.item_recycler_explore.view.*
import project.wgtech.imoge.R
import project.wgtech.imoge.explore.model.UnsplashJsonObject
import project.wgtech.imoge.explore.model.Results
import project.wgtech.imoge.util.LogUtil
import project.wgtech.imoge.util.Types

class ExploreRecyclerViewAdapter(private var obj: UnsplashJsonObject?) : RecyclerView.Adapter<ViewHolder>() {

    private lateinit var context: Context
    private var items: ArrayList<Results> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        context = parent.context
        if (obj != null) items.addAll(obj!!.results())

        val viewHolder = ViewHolder(LayoutInflater.from(parent.context)
            .inflate(R.layout.item_recycler_explore, parent, false))
        viewHolder.bindView()
        return viewHolder
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val results: Results? = items[position]
        LogUtil.d(Types.COMMON, "onBindViewHolder: $position")
        results?.tags?.forEach {
            LogUtil.d(Types.COMMON, it.title?.toString()!!)
        }

        Glide.with(context)
            .asBitmap()
            .load(results?.urls?.thumb)
            .optionalCenterCrop()
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .into(holder.imageView)
    }

    override fun getItemCount(): Int = items.size

    fun addItems(obj: UnsplashJsonObject?) {
        if (obj?.results()?.size!! > 0) items.addAll(obj.results())
        notifyDataSetChanged()
    }

    fun removeItems() {
        // TODO
    }

}

class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    var imageView: AppCompatImageView = itemView.imageViewExplore

    fun bindView() {
    }

}