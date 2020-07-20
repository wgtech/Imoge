package project.wgtech.imoge.explore.view

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.explore_recycler_view_item.view.*
import project.wgtech.imoge.R
import kotlin.random.Random

class ExploreRecyclerViewAdapter(private var _dummyData: MutableList<String>) : RecyclerView.Adapter<ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val viewHolder = ViewHolder(LayoutInflater
            .from(parent.context)
            .inflate(R.layout.explore_recycler_view_item, parent, false))
        viewHolder.bindView()
        return viewHolder
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        //holder.bindView()
    }

    override fun getItemCount(): Int = _dummyData.size
}

class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    private fun getRandomIntInRange(max: Int, min: Int) = Random.nextInt((max - min) + min) * min

    fun bindView(/* exploreModel: ExploreModel */) {
        //itemView.cardViewExplore.layoutParams.height = getRandomIntInRange(50,20)
        itemView.cardViewExplore.setBackgroundColor(Color.argb(Random.nextInt(256), Random.nextInt(256), Random.nextInt(256), Random.nextInt(256)))
    }

}