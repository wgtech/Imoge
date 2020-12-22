package project.wgtech.imoge.settings.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import project.wgtech.imoge.R
import project.wgtech.imoge.databinding.FragmentSettingsBinding
import project.wgtech.imoge.util.ResourceProviderImpl
import project.wgtech.imoge.settings.viewmodel.SettingsViewModel

class SettingsFragment : Fragment() {

    private lateinit var binding: FragmentSettingsBinding

    companion object {
        val instance = SettingsFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_settings, container, false)
        binding.lifecycleOwner = this
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.viewModel = SettingsViewModel(ResourceProviderImpl(requireContext()))

        binding.executePendingBindings()
    }

}
