package project.wgtech.imoge.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import project.wgtech.imoge.R
import project.wgtech.imoge.databinding.SettingsFragmentBinding
import project.wgtech.imoge.util.ResourceProviderImpl
import project.wgtech.imoge.viewmodel.ExploreViewModel
import project.wgtech.imoge.viewmodel.SettingsViewModel

class SettingsFragment : Fragment() {

    private lateinit var binding: SettingsFragmentBinding

    companion object {
        fun newInstance() = SettingsFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.settings_fragment, container, false)
        binding.lifecycleOwner = this
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.viewmodel = SettingsViewModel(ResourceProviderImpl(requireContext()))

        binding.executePendingBindings()
    }

}
