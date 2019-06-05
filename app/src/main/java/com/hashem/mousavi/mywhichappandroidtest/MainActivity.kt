package com.hashem.mousavi.mywhichappandroidtest

import android.animation.Animator
import android.app.Activity
import android.os.Bundle
import android.os.Handler
import android.util.TypedValue
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.view.animation.DecelerateInterpolator
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.TextView
import kotlinx.android.synthetic.main.activity_main.*
import kotlin.random.Random

class MainActivity : Activity() {

    var mHeight = 0
    var mWidth = 0

    val handler = Handler()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)

        initTextSwitcher()

        frame.post {
            //layout pass must be completed, otherwise we get 0 w and h
            mHeight = frame.height
            mWidth = frame.width
        }

        val rnd = Random(System.currentTimeMillis())

        circleBtn.click {
            var count = rnd.nextInt(0, 50) + 1

            switcher.setCurrentText(count.toString())
            switcher.show()

            handler.post(object : Runnable{
                override fun run() {
                    val coin = createCoin().apply {
                        tag = count - 1
                    }
                    frame.addView(coin)

                    coin.startAnimation(AnimationUtils.loadAnimation(this@MainActivity, R.anim.scale))

                    startTransition(coin)

                    count--

                    if (count == 0) {
                        return
                    }

                    handler.postDelayed(this, 200)
                }

            })

        }


    }

    private fun initTextSwitcher() {
        for (i in 1..2) {
            val myTextView = TextView(this).apply {
                setTextSize(TypedValue.COMPLEX_UNIT_SP, 14f)
                gravity = Gravity.CENTER
            }

            switcher.addView(myTextView, 0, FrameLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT).apply {
                gravity = Gravity.CENTER
            })

            switcher.inAnimation = AnimationUtils.loadAnimation(this, R.anim.slide_in_top)
            switcher.outAnimation = AnimationUtils.loadAnimation(this, R.anim.slide_out_bottom)
        }
    }

    private fun startTransition(view: View) {
        view.animate()
            .setDuration(2000)
            .setInterpolator(DecelerateInterpolator())
            .translationY((-mHeight / 2 - dp(25) / 2).toFloat())
            .translationX((-mWidth / 2 - dp(25) / 2).toFloat())
            .setListener(object : Animator.AnimatorListener {
                override fun onAnimationRepeat(animation: Animator?) {

                }

                override fun onAnimationCancel(animation: Animator?) {
                }

                override fun onAnimationStart(animation: Animator?) {
                }

                override fun onAnimationEnd(animation: Animator?) {
                    frame?.removeView(view)
                    if (view.tag == 0) {
                        switcher?.hide()
                    }
                    switcher.setText(view.tag.toString())

                }

            })
            .start()
    }

    private val params = FrameLayout.LayoutParams(dp(50), dp(50)).apply {
        gravity = Gravity.CENTER
    }

    private fun createCoin(): View {
        return ImageView(this).apply {
            scaleType = ImageView.ScaleType.FIT_XY
            setImageResource(R.drawable.coin)
            layoutParams = params

            supportsLollipop {
                //circle button has maximum elevation value 4, so we should set greater value for coins
                elevation = dp(5).toFloat()
            }
        }
    }
}
