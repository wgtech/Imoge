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

class ExploreRecyclerViewAdapter(private var obj: UnsplashJsonObject?) : RecyclerView.Adapter<ViewHolder>() {

    private lateinit var context: Context
    private var items: ArrayList<Results> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        context = parent.context

        obj?.results()?.forEach { items.add(it) }

        val viewHolder = ViewHolder(LayoutInflater.from(parent.context)
            .inflate(R.layout.item_recycler_explore, parent, false))
        viewHolder.bindView()
        return viewHolder
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val results: Results? = items[position]

        Glide.with(context)
            .asBitmap()
            .load(results?.urls?.thumb)
            .optionalCenterCrop()
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .into(holder.imageView)
    }

    override fun getItemCount(): Int = items.size

    fun addItems(obj: UnsplashJsonObject?) {
        if (obj != null) items.addAll(obj.results())
        notifyDataSetChanged()
    }

    fun removeItems(keyword: String) {
        val iter = items.iterator()

        while (iter.hasNext()) {
            if (iter.next().keyword == keyword) iter.remove()
        }

        notifyDataSetChanged()
    }
}

class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    var imageView: AppCompatImageView = itemView.imageViewExplore

    fun bindView() {
    }

}