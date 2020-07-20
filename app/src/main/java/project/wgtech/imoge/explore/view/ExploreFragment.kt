package project.wgtech.imoge.explore.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CompoundButton
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DividerItemDecoration
import com.google.android.material.chip.Chip
import project.wgtech.imoge.BuildConfig
import project.wgtech.imoge.R
import project.wgtech.imoge.databinding.ExploreFragmentBinding
import project.wgtech.imoge.explore.model.ExploreModel
import project.wgtech.imoge.explore.viewmodel.ExploreViewModel
import project.wgtech.imoge.util.*

class ExploreFragment() : Fragment() {

    private lateinit var binding: ExploreFragmentBinding
    private lateinit var provider: ResourceProviderImpl

    companion object {
       val instance = ExploreFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        LogUtil.d(Types.COMMON,"ExploreFragment onCreateView")
        provider = ResourceProviderImpl(requireContext())
        binding = DataBindingUtil.inflate(inflater, R.layout.explore_fragment, container, false)
        binding.viewModel = ExploreViewModel(provider)
        binding.rvExplore.adapter = ExploreRecyclerViewAdapter(provider.stringArray(R.array.test_array).toMutableList())
        binding.rvExplore.addItemDecoration(DividerItemDecoration(requireContext(), DividerItemDecoration.VERTICAL))
        binding.rvExplore.addItemDecoration(GridItemDecoration(15, 2))
        binding.chipGroupExplore.apply {
            val chipStrings = binding.viewModel?.selectedChipTextList

            if (chipStrings != null) {
                for (element in chipStrings) {
                    val chip = Chip(requireContext()).apply {
                        text = element
                        isCloseIconVisible = false
                        isClickable = true
                        isCheckable = true
                        setTextColor(provider.color(R.color.colorAccent))
                        chipBackgroundColor = provider.color(R.color.colorPrimaryLight)
                        chipStrokeColor = provider.color(R.color.colorAccent)
                        chipStrokeWidth = 1.0f

                        setOnCheckedChangeListener { buttonView: CompoundButton?, isChecked: Boolean ->
                            LogUtil.d(Types.COMMON,"chip= ${buttonView?.text}, $isChecked")
                        }
                    }
                    this.addView(chip)
                }
            }

            Thread() {
                binding.viewModel?.client?.getPhotosByKeyword(BuildConfig.api_unsplash_access, "joyful") // TODO
                    ?.subscribe(
                        { t: PhotosByKeywordEntity? ->
                            LogUtil.d(Types.COMMON, "t: ${t.toString()}")
                        },
                        { t: Throwable -> t.printStackTrace() }
                    )

            }.start()
        }
        binding.lifecycleOwner = this
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.viewModel?.exploreLiveData?.observe(viewLifecycleOwner, Observer<ExploreModel> {
            _: ExploreModel? ->
        })
    }

}
