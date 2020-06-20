package project.wgtech.imoge.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import androidx.core.view.get
import androidx.core.view.iterator
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import project.wgtech.imoge.R
import project.wgtech.imoge.databinding.MainActivityBinding
import kotlin.math.exp

class MainActivity : AppCompatActivity() {

    private lateinit var binding: MainActivityBinding
    private val exploreFragment = ExploreFragment.newInstance()
    private val settingsFragment = SettingsFragment.newInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.main_activity)
        binding.activity = this

        binding.bottomNavigationViewMain.menu.iterator().forEach {
            menuItem -> menuItem.setOnMenuItemClickListener {
                when (it.itemId) {
                    R.id.menu_explore -> {
                        transactFragment(exploreFragment)
                        true
                    }
                    R.id.menu_settings -> {
                        transactFragment(settingsFragment)
                        true
                    }
                    else -> false
                }
            }
        }


        // initialize
        if (savedInstanceState == null) {
            transactFragment(exploreFragment)
        }
    }

    private fun transactFragment(choosenFragment: Fragment) = supportFragmentManager.beginTransaction().replace(R.id.containerMain, choosenFragment).commitNow()
}
