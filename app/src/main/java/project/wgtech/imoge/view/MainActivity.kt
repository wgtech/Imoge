package project.wgtech.imoge.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import project.wgtech.imoge.R
import project.wgtech.imoge.databinding.MainActivityBinding
import project.wgtech.imoge.explore.view.ExploreFragment
import project.wgtech.imoge.settings.view.SettingsFragment

class MainActivity : AppCompatActivity() {

    private lateinit var binding: MainActivityBinding
    private val exploreFragment = ExploreFragment.instance
    private val settingsFragment = SettingsFragment.instance

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.main_activity)
        binding.activity = this

        binding.bottomNavigationViewMain.setOnNavigationItemSelectedListener {
            item -> when (item.itemId) {
                R.id.menu_explore -> {
                    replaceFragment(exploreFragment)
                    true
                }
                R.id.menu_settings -> {
                    replaceFragment(settingsFragment)
                    true
                }
                else -> false
            }
        }

        // initialize
        if (savedInstanceState == null) {
            initFragment()
        }
    }

    private fun initFragment() = supportFragmentManager.beginTransaction().add(R.id.containerMain, exploreFragment).commit()
    private fun replaceFragment(chosenFragment: Fragment) = supportFragmentManager.beginTransaction().replace(R.id.containerMain, chosenFragment).commit()
}
