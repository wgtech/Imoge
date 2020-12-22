package project.wgtech.imoge.explore.view

import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.WindowManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import project.wgtech.imoge.BuildConfig
import project.wgtech.imoge.R
import project.wgtech.imoge.databinding.ActivityDetailBinding

class ExploreDetailActivity : AppCompatActivity() {
    private val TAG = ExploreDetailActivity::class.java.simpleName

    private lateinit var binding : ActivityDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (BuildConfig.DEBUG) {
            Log.d(TAG, "url: ${intent?.getStringExtra("url")}")
            Toast.makeText(baseContext, intent?.getStringExtra("url"), Toast.LENGTH_SHORT).show()
        }


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            window.apply {
                clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
                addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
                setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
                    WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS)
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
                } else {
                    decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                }
                statusBarColor = Color.TRANSPARENT
            }

        }

        binding = DataBindingUtil.setContentView(this, R.layout.activity_detail)

        Glide.with(baseContext)
            .asDrawable()
            .load(intent?.getStringExtra("url"))
            .apply(RequestOptions().fitCenter())
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .into(binding.pinchableImageViewExplore)
    }
}