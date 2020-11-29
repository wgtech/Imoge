package project.wgtech.imoge.explore.view

import android.content.Context
import android.os.Bundle
import android.util.AttributeSet
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import project.wgtech.imoge.R
import project.wgtech.imoge.databinding.ActivityDetailBinding

class ExploreDetailActivity : AppCompatActivity() {

    private lateinit var binding : ActivityDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        Toast.makeText(baseContext, intent?.getStringExtra("url"), Toast.LENGTH_SHORT).show()
        binding = DataBindingUtil.setContentView(this, R.layout.activity_detail)
        Glide.with(baseContext)
            .asDrawable()
            .load(intent?.getStringExtra("url"))
            .optionalCenterCrop()
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .into(binding.imageViewDetailExplore)
    }
}