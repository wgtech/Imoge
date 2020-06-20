package project.wgtech.imoge.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import project.wgtech.imoge.R
import project.wgtech.imoge.databinding.MainActivityBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: MainActivityBinding
    private val exploreFragment = ExploreFragment.newInstance()
    private val settingsFragment = SettingsFragment.newInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.main_activity)
        binding.activity = this

        binding.bottomNavigationViewMain.setOnNavigationItemSelectedListener {
            item -> when (item.itemId) {
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

        // initialize
        if (savedInstanceState == null) {
            transactFragment(exploreFragment)
        }
    }

    private fun transactFragment(chosenFragment: Fragment) = supportFragmentManager.beginTransaction().replace(R.id.containerMain, chosenFragment).commitNow()
}
