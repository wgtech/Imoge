package project.wgtech.imoge.explore.view

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import kotlinx.android.synthetic.main.explore_recycler_view_item.view.*
import project.wgtech.imoge.R
import project.wgtech.imoge.explore.model.PhotosByKeywordEntity
import project.wgtech.imoge.explore.model.Results
import kotlin.random.Random

class ExploreRecyclerViewAdapter(private var entity: PhotosByKeywordEntity?) : RecyclerView.Adapter<ViewHolder>() {

    private lateinit var context: Context

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        context = parent.context
        val viewHolder = ViewHolder(LayoutInflater
            .from(parent.context)
            .inflate(R.layout.explore_recycler_view_item, parent, false))
        viewHolder.bindView()
        return viewHolder
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val results: Results? = entity?.results()?.get(position)
        Glide.with(context)
            .asBitmap()
            .load(results?.urls?.thumb)
            .fitCenter()
            .centerCrop()
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .into(holder.imageView)
    }

    override fun getItemCount(): Int = if (entity?.results() == null) 0 else entity?.results()?.size!!
}

class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    var imageView: AppCompatImageView = itemView.imageViewExplore
    private fun getRandomIntInRange(max: Int, min: Int) = Random.nextInt((max - min) + min) * min

    fun bindView(/* exploreModel: ExploreModel */) {
        //cardView.layoutParams.height = getRandomIntInRange(50,20)
        imageView.setBackgroundColor(Color.argb(Random.nextInt(256), Random.nextInt(256), Random.nextInt(256), Random.nextInt(256)))
    }

}