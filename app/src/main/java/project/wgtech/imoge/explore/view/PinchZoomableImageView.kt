package project.wgtech.imoge.explore.view

import android.content.Context
import android.graphics.Matrix
import android.graphics.PointF
import android.util.AttributeSet
import android.view.GestureDetector
import android.view.MotionEvent
import android.view.ScaleGestureDetector
import android.view.View
import androidx.annotation.Nullable
import androidx.appcompat.widget.AppCompatImageView

class PinchZoomableImageView: AppCompatImageView, View.OnTouchListener,
GestureDetector.OnGestureListener, GestureDetector.OnDoubleTapListener {

    // Shared constructing
    private var mContext: Context? = null
    private var scaleDetector: ScaleGestureDetector? = null
    private var gestureDetector: GestureDetector? = null
    var mMatrix: Matrix? = null
    private var matrixValues: FloatArray? = null
    var mode = NONE

    // Scales
    var saveScale = 1f
    var minScale = 1f
    var maxScale = 4f

    // View dimensions
    var origWidth = 0f
    var origHeight = 0f
    var viewWidth = 0
    var viewHeight = 0
    private var mLast = PointF()
    private var mStart = PointF()

    constructor(context: Context) : super(context) {
        sharedConstructing(context)
    }

    constructor(context: Context, @Nullable attrs: AttributeSet?) : super(context, attrs) {
        sharedConstructing(context)
    }

    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context!!, attrs, defStyleAttr)

    private fun sharedConstructing(context: Context) {
        super.setClickable(true)
        this.mContext = context
        scaleDetector = ScaleGestureDetector(context, ScaleListener())
        mMatrix = Matrix()
        matrixValues = FloatArray(9)
        imageMatrix = mMatrix
        scaleType = ScaleType.MATRIX
        gestureDetector = GestureDetector(context, this)
        setOnTouchListener(this)
    }

    private inner class ScaleListener : ScaleGestureDetector.SimpleOnScaleGestureListener() {
        override fun onScaleBegin(detector: ScaleGestureDetector): Boolean {
            mode = ZOOM
            return true
        }

        override fun onScale(detector: ScaleGestureDetector): Boolean {
            var scaleFactor = detector.scaleFactor
            val prevScale = saveScale
            saveScale *= scaleFactor
            if (saveScale > maxScale) {
                saveScale = maxScale
                scaleFactor = maxScale / prevScale
            } else if (saveScale < minScale) {
                saveScale = minScale
                scaleFactor = minScale / prevScale
            }
            if (origWidth * saveScale <= viewWidth
                || origHeight * saveScale <= viewHeight) {
                mMatrix!!.postScale(scaleFactor, scaleFactor, viewWidth / 2.toFloat(),
                    viewHeight / 2.toFloat())
            } else {
                mMatrix!!.postScale(scaleFactor, scaleFactor,
                    detector.focusX, detector.focusY)
            }
            fixTranslation()
            return true
        }
    }

    private  fun fitToScreen() {
        saveScale = 1f
        val scale: Float
        val drawable = drawable
        if (drawable == null || drawable.intrinsicWidth == 0 || drawable.intrinsicHeight == 0) return
        val imageWidth = drawable.intrinsicWidth
        val imageHeight = drawable.intrinsicHeight
        val scaleX = viewWidth.toFloat() / imageWidth.toFloat()
        val scaleY = viewHeight.toFloat() / imageHeight.toFloat()
        scale = scaleX.coerceAtMost(scaleY)
        mMatrix!!.setScale(scale, scale)

        // Center the image
        var redundantYSpace = (viewHeight.toFloat()
                - scale * imageHeight.toFloat())
        var redundantXSpace = (viewWidth.toFloat()
                - scale * imageWidth.toFloat())
        redundantYSpace /= 2.toFloat()
        redundantXSpace /= 2.toFloat()
        mMatrix!!.postTranslate(redundantXSpace, redundantYSpace)
        origWidth = viewWidth - 2 * redundantXSpace
        origHeight = viewHeight - 2 * redundantYSpace
        imageMatrix = mMatrix
    }

    fun fixTranslation() {
        mMatrix!!.getValues(matrixValues) //put matrix values into a float array so we can analyze
        val transX = matrixValues!![Matrix.MTRANS_X] //get the most recent translation in x direction
        val transY = matrixValues!![Matrix.MTRANS_Y] //get the most recent translation in y direction
        val fixTransX = getFixTranslation(transX, viewWidth.toFloat(), origWidth * saveScale)
        val fixTransY = getFixTranslation(transY, viewHeight.toFloat(), origHeight * saveScale)
        if (fixTransX != 0f || fixTransY != 0f) mMatrix!!.postTranslate(fixTransX, fixTransY)
    }

    private fun getFixTranslation(trans: Float, viewSize: Float, contentSize: Float): Float {
        val minTrans: Float
        val maxTrans: Float
        if (contentSize <= viewSize) { // case: NOT ZOOMED
            minTrans = 0f
            maxTrans = viewSize - contentSize
        } else { //CASE: ZOOMED
            minTrans = viewSize - contentSize
            maxTrans = 0f
        }
        if (trans < minTrans) { // negative x or y translation (down or to the right)
            return -trans + minTrans
        }
        if (trans > maxTrans) { // positive x or y translation (up or to the left)
            return -trans + maxTrans
        }
        return 0F
    }

    private fun getFixDragTrans(delta: Float, viewSize: Float, contentSize: Float): Float {
        return if (contentSize <= viewSize) {
            0F
        } else delta
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        viewWidth = MeasureSpec.getSize(widthMeasureSpec)
        viewHeight = MeasureSpec.getSize(heightMeasureSpec)
        if (saveScale == 1f) {
            // Fit to screen.
            fitToScreen()
        }
    }

    /**
     * onTouch
     */
    override fun onTouch(view: View?, event: MotionEvent): Boolean {
        scaleDetector!!.onTouchEvent(event)
        gestureDetector!!.onTouchEvent(event)
        val currentPoint = PointF(event.x, event.y)
        when (event.action) {
            MotionEvent.ACTION_DOWN -> {
                mLast.set(currentPoint)
                mStart.set(mLast)
                mode = DRAG
            }
            MotionEvent.ACTION_MOVE -> if (mode == DRAG) {
                val dx = currentPoint.x - mLast.x
                val dy = currentPoint.y - mLast.y
                val fixTransX = getFixDragTrans(dx, viewWidth.toFloat(), origWidth * saveScale)
                val fixTransY = getFixDragTrans(dy, viewHeight.toFloat(), origHeight * saveScale)
                mMatrix!!.postTranslate(fixTransX, fixTransY)
                fixTranslation()
                mLast[currentPoint.x] = currentPoint.y
            }
            MotionEvent.ACTION_POINTER_UP -> mode = NONE
        }
        imageMatrix = mMatrix
        return false
    }

    /**
     * onGestureListener
     */
    override fun onDown(motionEvent: MotionEvent): Boolean {
        return false
    }

    override fun onShowPress(motionEvent: MotionEvent) {}
    override fun onSingleTapUp(motionEvent: MotionEvent): Boolean {
        return false
    }

    override fun onScroll(motionEvent: MotionEvent, motionEvent1: MotionEvent, v: Float, v1: Float): Boolean {
        return false
    }

    override fun onLongPress(motionEvent: MotionEvent) {}
    override fun onFling(motionEvent: MotionEvent, motionEvent1: MotionEvent, v: Float, v1: Float): Boolean {
        return false
    }

    /**
     * onDoubleTap
     */
    override fun onSingleTapConfirmed(motionEvent: MotionEvent): Boolean {
        return false
    }

    override fun onDoubleTap(motionEvent: MotionEvent): Boolean {
        fitToScreen()
        return false
    }

    override fun onDoubleTapEvent(motionEvent: MotionEvent): Boolean {
        return false
    }

    companion object {

        // Image States
        const val NONE = 0
        const val DRAG = 1
        const val ZOOM = 2
    }
}