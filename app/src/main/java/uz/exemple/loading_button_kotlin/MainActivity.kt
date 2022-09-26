package uz.exemple.loading_button_kotlin

import android.animation.ValueAnimator
import android.content.Context
import android.content.res.Resources
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Color
import android.os.Bundle
import android.os.Handler
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import br.com.simplepass.loadingbutton.animatedDrawables.ProgressType
import br.com.simplepass.loadingbutton.customViews.CircularProgressButton
import br.com.simplepass.loadingbutton.customViews.ProgressButton


class MainActivity : AppCompatActivity() {
    lateinit var bitmap: Bitmap
    lateinit var btn:CircularProgressButton
    lateinit var btn2:CircularProgressButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initViews()

    }
    fun initViews(){
        val context = this

        btn = findViewById(R.id.btn_id)
        btn2 = findViewById(R.id.btn_2)

        btn.setOnClickListener(View.OnClickListener {
            bitmap = BitmapFactory.decodeResource(resources,R.drawable.ic_shopping_cart_white_24)
            btn.setBackgroundResource(R.drawable.button_shape_default_rounded)
            btn.morphDoneAndRevert(context,Color.parseColor("#333639"),bitmap,3000,6000)

        })

        btn2.run { setOnClickListener { morphAndRevert(2000) } }
    }

    private fun defaultColor(context: Context) = ContextCompat.getColor(context, android.R.color.black)

    private fun defaultDoneImage(resources: Resources) =
        BitmapFactory.decodeResource(resources, R.drawable.ic_shopping_cart_white_24)

    private fun ProgressButton.morphDoneAndRevert(
        context: Context,
        fillColor: Int,
        bitmap: Bitmap ,
        doneTime: Long = 3000,
        revertTime: Long = 4000
    ) {
        progressType = ProgressType.INDETERMINATE
        startAnimation()
        Handler().run {
            postDelayed({ doneLoadingAnimation(fillColor, bitmap) }, doneTime)
            postDelayed(::revertAnimation, revertTime)
        }
    }

    private fun ProgressButton.morphAndRevert(revertTime: Long = 3000, startAnimationCallback: () -> Unit = {}) {
        startAnimation(startAnimationCallback)
        Handler().postDelayed(::revertAnimation, revertTime)
    }

    private fun ProgressButton.morphStopRevert(stopTime: Long = 1000, revertTime: Long = 2000) {
        startAnimation()
        Handler().postDelayed(::stopAnimation, stopTime)
        Handler().postDelayed(::revertAnimation, revertTime)
    }

    private fun progressAnimator(progressButton: ProgressButton) = ValueAnimator.ofFloat(0F, 100F).apply {
        duration = 1500
        startDelay = 500
        addUpdateListener { animation ->
            progressButton.setProgress(animation.animatedValue as Float)
        }
    }


}