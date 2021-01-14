package project.wgtech.imoge.explore.view

import android.graphics.Rect
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import project.wgtech.imoge.BuildConfig
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
                            if (BuildConfig.DEBUG) Toast.makeText(context, "${buttonView.text.toString()}, $isChecked", Toast.LENGTH_SHORT).show()
                            binding.viewModel!!.loadPhotos(
                                provider,
                                buttonView.text.toString(),
                                page
                            )
                        } else {
                            (binding.rvExplore.adapter as ExploreRecyclerViewAdapter).removeItems(
                                buttonView.text.toString()
                            )
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
                        binding.viewModel!!.nextPhotos(provider, keyword, page)
                    }
                }
            })
        }

        binding.viewModel!!.status.observe(viewLifecycleOwner, {
            if (it.code >= 400) {
                ExceptionHandleUtil(provider.context, provider.drawable(it.drawableId),
                    provider.context.getString(it.titleId), provider.context.getString(it.descriptionId))
                    .showDialog({ binding.viewModel!!.loadPhotos(provider, keyword, page) }, { requireActivity().finish() })
            }
        })

        binding.viewModel!!.photos.observe(viewLifecycleOwner, Observer {
            binding.rvExplore.recycledViewPool.clear()
            (binding.rvExplore.adapter as ExploreRecyclerViewAdapter).addItems(it)
        })


        binding.viewModel!!.loadPhotos(provider, keyword, 1)
        binding.executePendingBindings()
    }

}
