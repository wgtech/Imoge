package project.wgtech.imoge.explore.view

import android.content.Context
import android.os.Bundle
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.ScaleGestureDetector
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatImageView
import androidx.databinding.DataBindingUtil
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import project.wgtech.imoge.R
import project.wgtech.imoge.databinding.ActivityDetailBinding

class ExploreDetailActivity : AppCompatActivity() {

    private lateinit var binding : ActivityDetailBinding
    private lateinit var scaleGestureDetector: ScaleGestureDetector
    var scaleFactor: Float = 1.0f
    private lateinit var appCompatImageView: AppCompatImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        Toast.makeText(baseContext, intent?.getStringExtra("url"), Toast.LENGTH_SHORT).show()
        binding = DataBindingUtil.setContentView(this, R.layout.activity_detail)
        appCompatImageView = binding.imageViewDetailExplore
        scaleGestureDetector = ScaleGestureDetector(this, ScaleListener())

        Glide.with(baseContext)
            .asDrawable()
            .load(intent?.getStringExtra("url"))
            .centerInside()
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