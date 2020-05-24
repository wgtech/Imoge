package project.wgtech.imoge.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import project.wgtech.imoge.R
import project.wgtech.imoge.databinding.MainIncludeFragmentBinding
import project.wgtech.imoge.viewmodel.MainViewModel

class MainIncludeFragment : Fragment() {

    private lateinit var binding: MainIncludeFragmentBinding

    companion object {
        fun newInstance() = MainIncludeFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.main_include_fragment, container, false)
        binding.lifecycleOwner = this
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.viewmodel = MainViewModel(requireActivity().application)
        binding.executePendingBindings()
    }
}