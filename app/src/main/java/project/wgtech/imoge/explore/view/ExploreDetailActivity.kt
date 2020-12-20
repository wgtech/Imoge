package project.wgtech.imoge.explore.view

import android.os.Bundle
import android.util.Log
import android.view.MotionEvent
import android.view.ScaleGestureDetector
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatImageView
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
    private lateinit var scaleGestureDetector: ScaleGestureDetector
    var scaleFactor: Float = 1.0f
    private lateinit var appCompatImageView: AppCompatImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (BuildConfig.DEBUG) {
            Log.d(TAG, "url: ${intent?.getStringExtra("url")}")
            Toast.makeText(baseContext, intent?.getStringExtra("url"), Toast.LENGTH_SHORT).show()
        }

        binding = DataBindingUtil.setContentView(this, R.layout.activity_detail)
        appCompatImageView = binding.imageViewDetailExplore
        scaleGestureDetector = ScaleGestureDetector(this, ScaleListener())

        Glide.with(baseContext)
            .asDrawable()
            .load(intent?.getStringExtra("url"))
            .apply(RequestOptions().centerCrop())
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .into(binding.imageViewDetailExplore)
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        scaleGestureDetector.onTouchEvent(event)
        return true
    }

    inner class ScaleListener : ScaleGestureDetector.SimpleOnScaleGestureListener() {
        override fun onScale(detector: ScaleGestureDetector?): Boolean {
            scaleFactor *= detector?.scaleFactor!!
            scaleFactor = 0.1f.coerceAtLeast(scaleFactor.coerceAtMost(10.0f))
            appCompatImageView.scaleX = scaleFactor
            appCompatImageView.scaleY = scaleFactor
            return true;
        }
    }
}