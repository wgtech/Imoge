package project.wgtech.imoge.explore.view

import android.graphics.Rect
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import project.wgtech.imoge.R
import project.wgtech.imoge.databinding.FragmentExploreBinding
import project.wgtech.imoge.explore.viewmodel.ExploreViewModel
import project.wgtech.imoge.util.*

class ExploreFragment() : Fragment() {

    private lateinit var binding: FragmentExploreBinding
    private lateinit var provider: ResourceProviderImpl

    companion object {
       val instance = ExploreFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        provider = ResourceProviderImpl(requireContext())

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_explore, container, false)
        binding.lifecycleOwner = this
        binding.viewModel = ExploreViewModel(provider)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        var keyword = "happy"
        var page = 1
        var isLoaded = false

        binding.chipGroupExplore.apply {

            binding.viewModel?.chips?.observe(viewLifecycleOwner, Observer {
                it.forEach {
                    val chip = ExploreChip(provider, it)
                    chip.isChecked = (chip.text == keyword)
                    chip.setOnCheckedChangeListener { buttonView, isChecked ->
                        if (isChecked) {
                            binding.viewModel!!.loadPhotos(buttonView.text.toString(), page)
                        } else {
                            (binding.rvExplore.adapter as ExploreRecyclerViewAdapter).removeItems(buttonView.text.toString())
                        }
                    }
                    addView(chip)
                }
            })

        }

        binding.rvExplore.apply {
            val spacing = resources.getDimensionPixelSize(R.dimen.photos_spacing) / 2
            itemAnimator = DefaultItemAnimator()
            layoutManager = GridLayoutManager(requireContext(), resources.getInteger(R.integer.photos_columns))
            setPadding(spacing, spacing, spacing, spacing)
            clipToPadding = false
            clipChildren = false
            addItemDecoration(object: RecyclerView.ItemDecoration() {
                override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
                    outRect.set(spacing, spacing, spacing, spacing)
                }
            })

            adapter = ExploreRecyclerViewAdapter(binding.viewModel!!.photos.value)

            addOnScrollListener(object : RecyclerView.OnScrollListener() {

                override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                    if (!isLoaded && adapter!!.itemCount > 0 && !recyclerView.canScrollHorizontally(1)) { // 1 : end of view, -1 : start of view
                        page += 1
                        (recyclerView.adapter!! as ExploreRecyclerViewAdapter).addItems(null)
                        recyclerView.adapter!!.notifyItemInserted(recyclerView.adapter!!.itemCount + 1)
                        binding.viewModel!!.nextPhotos(keyword, page)
                        recyclerView.adapter
                    }
                }
            })
        }

        binding.viewModel!!.photos.observe(viewLifecycleOwner, Observer {
            binding.rvExplore.recycledViewPool.clear()
            (binding.rvExplore.adapter as ExploreRecyclerViewAdapter).addItems(it)
        })


        binding.viewModel!!.loadPhotos(keyword, 1)
        binding.executePendingBindings()
    }

}
