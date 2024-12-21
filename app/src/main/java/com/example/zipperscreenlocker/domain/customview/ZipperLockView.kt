package com.example.zipperscreenlocker.domain.customview

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.ValueAnimator
import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.RectF
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import android.view.animation.DecelerateInterpolator
import android.graphics.drawable.Drawable
import com.example.zipperscreenlocker.R

class ZipperLockView(context: Context, attrs: AttributeSet?) : View(context, attrs) {

    private val zipperPaint = Paint().apply {
        color = Color.DKGRAY
        strokeWidth = 8f
        style = Paint.Style.FILL
    }

    private val sliderPaint = Paint().apply {
        color = Color.GRAY
        style = Paint.Style.FILL
    }

    private val teethPaint = Paint().apply {
        color = Color.BLACK
        style = Paint.Style.FILL
    }

    private var sliderPosition = 0f
    private val sliderHeight = 120f
    private val teethSize = 20f
    private var isZipperOpen = false

    private val maxSliderPosition: Float
    private val minSliderPosition: Float = 0f

    private var zipperStateCallback: ZipperStateCallback? = null
    private var animator: ValueAnimator? = null

    private var zipperSliderDrawable: Drawable? = null

    private val teethOffsets = mutableListOf<Float>()

    init {
        // Obtain the custom attributes for the zipper slider drawable
        context.theme.obtainStyledAttributes(
            attrs,
            R.styleable.ZipperLockView,
            0, 0
        ).apply {
            try {
                zipperSliderDrawable = getDrawable(R.styleable.ZipperLockView_zipperSlider)
            } finally {
                recycle()
            }
        }

        maxSliderPosition = 800f // Adjust this based on your view height
        sliderPosition = minSliderPosition

        // Initialize teeth offsets
        val teethCount = (maxSliderPosition / (teethSize * 1.5)).toInt()
        repeat(teethCount) { teethOffsets.add(0f) }
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        val zipperCenterX = width / 2f

        // Draw zipper teeth dynamically with movement effect
        val teethCount = (height / (teethSize * 1.5)).toInt()
        for (i in 0 until teethCount) {
            val offset = i * teethSize * 1.5f
            val teethTop = offset
            val teethBottom = (teethTop + teethSize).coerceAtMost(height.toFloat())

            // Adjust teeth movement based on slider position
            val teethOffset = if (teethTop < sliderPosition) {
                val progress = sliderPosition - teethTop
                (progress / maxSliderPosition) * 40f
            } else 0f

            // Left side teeth moving left
            canvas.drawRect(
                zipperCenterX - 40f - teethOffset, teethTop, zipperCenterX - 20f - teethOffset, teethBottom, teethPaint
            )

            // Right side teeth moving right
            canvas.drawRect(
                zipperCenterX + 20f + teethOffset, teethTop, zipperCenterX + 40f + teethOffset, teethBottom, teethPaint
            )
        }

        // Draw slider
        val sliderTop = sliderPosition
        val sliderBottom = sliderPosition + sliderHeight
        zipperSliderDrawable?.let { drawable ->
            val left = zipperCenterX - 50f
            val right = zipperCenterX + 50f
            drawable.setBounds(left.toInt(), sliderTop.toInt(), right.toInt(), sliderBottom.toInt())
            drawable.draw(canvas)
        } ?: run {
            canvas.drawRoundRect(
                zipperCenterX - 50f, sliderTop, zipperCenterX + 50f, sliderBottom,
                10f, 10f, sliderPaint
            )
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onTouchEvent(event: MotionEvent): Boolean {
        when (event.action) {
            MotionEvent.ACTION_DOWN, MotionEvent.ACTION_MOVE -> {
                animator?.cancel()

                sliderPosition = event.y.coerceIn(minSliderPosition, maxSliderPosition)
                invalidate()
                checkZipperState()
            }
            MotionEvent.ACTION_UP -> {
                if (sliderPosition > maxSliderPosition / 2) {
                    animateSliderTo(maxSliderPosition)
                } else {
                    animateSliderTo(minSliderPosition)
                }
            }
        }
        return true
    }

    private fun checkZipperState() {
        val isOpen = sliderPosition >= maxSliderPosition * 0.8f
        if (isOpen != isZipperOpen) {
            isZipperOpen = isOpen
            zipperStateCallback?.onZipperStateChanged(isZipperOpen)
        }
    }

    private fun animateSliderTo(targetPosition: Float) {
        animator = ValueAnimator.ofFloat(sliderPosition, targetPosition).apply {
            duration = 300
            interpolator = DecelerateInterpolator()
            addUpdateListener { animation ->
                sliderPosition = animation.animatedValue as Float
                invalidate()
            }
            addListener(object : AnimatorListenerAdapter() {
                override fun onAnimationEnd(animation: Animator) {
                    checkZipperState()
                }
            })
            start()
        }
    }

    fun forceOpen() {
        animateSliderTo(maxSliderPosition)
    }

    fun forceClose() {
        animateSliderTo(minSliderPosition)
    }

    fun setZipperStateCallback(callback: ZipperStateCallback) {
        this.zipperStateCallback = callback
    }

    interface ZipperStateCallback {
        fun onZipperStateChanged(isOpen: Boolean)
    }
}
