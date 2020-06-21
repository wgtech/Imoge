package project.wgtech.imoge.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import project.wgtech.imoge.R
import project.wgtech.imoge.databinding.ExploreFragmentBinding
import project.wgtech.imoge.util.ResourceProviderImpl
import project.wgtech.imoge.viewmodel.ExploreViewModel

class ExploreFragment : Fragment() {

    private lateinit var binding: ExploreFragmentBinding

    companion object {
        fun newInstance() = ExploreFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.explore_fragment, container, false)
        binding.lifecycleOwner = this
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.viewmodel = ExploreViewModel(ResourceProviderImpl(requireContext()))

        binding.executePendingBindings()
    }

}
