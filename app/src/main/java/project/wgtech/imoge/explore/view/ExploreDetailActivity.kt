package project.wgtech.imoge.explore.view

import android.content.DialogInterface
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.view.View
import android.view.WindowInsetsController
import android.view.WindowManager
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.view.ContextThemeWrapper
import androidx.databinding.DataBindingUtil
import project.wgtech.imoge.R
import project.wgtech.imoge.databinding.ActivityDetailBinding

class ExploreDetailActivity : AppCompatActivity() {
    private val TAG = ExploreDetailActivity::class.java.simpleName

    private lateinit var binding : ActivityDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        window.apply {
            clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
            addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            when {
                Build.VERSION.SDK_INT >= Build.VERSION_CODES.R -> {
                    setDecorFitsSystemWindows(true)
                }
                Build.VERSION.SDK_INT >= Build.VERSION_CODES.M -> {
                    decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
                }
                else -> {
                    decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                }
            }
            statusBarColor = Color.TRANSPARENT
        }

        binding = DataBindingUtil.setContentView(this, R.layout.activity_detail)
        binding.imageUrl = intent?.getStringExtra("url")

        binding.imageButtonInfo.setOnClickListener {
            val dialogOnClickListener = DialogInterface.OnClickListener { dialog, which ->
                when (which) {
                    DialogInterface.BUTTON_POSITIVE -> dialog.dismiss()
                    DialogInterface.BUTTON_NEGATIVE -> dialog.dismiss()
                }
            }

            AlertDialog
                    .Builder(ContextThemeWrapper(this@ExploreDetailActivity, R.style.Theme_Imoge_AlertDialog))
                    .setTitle(intent?.getStringExtra("description"))
                    .setMessage("${binding.imageUrl}\n")
                    .setPositiveButton(R.string.okay, dialogOnClickListener)
                    .show()
        }
    }
}