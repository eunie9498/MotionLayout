package kong.droid.motionexample

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.TypedValue
import androidx.constraintlayout.motion.widget.MotionLayout
import kong.droid.motionexample.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    lateinit var binding : ActivityMainBinding

    //애니메이션 동작 여부 체크
    var alreadyAnimation = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initView()
    }

    private fun initView() = with(binding) {


        scrollView.viewTreeObserver.addOnScrollChangedListener {
            if(scrollView.scrollY > 150f.dpToPx()) {
                if(alreadyAnimation.not()) {
                    motionLayout.transitionToEnd()
                }
            }
            else {
                if(alreadyAnimation.not()) {
                    motionLayout.transitionToStart()
                }
            }
        }

        motionLayout.setTransitionListener(object : MotionLayout.TransitionListener {
            override fun onTransitionCompleted(motionLayout: MotionLayout?, currentId: Int) {
                alreadyAnimation = false
            }

            override fun onTransitionStarted(
                motionLayout: MotionLayout?,
                startId: Int,
                endId: Int
            ) {
                alreadyAnimation = true
            }

            override fun onTransitionChange(
                motionLayout: MotionLayout?,
                startId: Int,
                endId: Int,
                progress: Float
            )  = Unit
            override fun onTransitionTrigger(
                motionLayout: MotionLayout?,
                triggerId: Int,
                positive: Boolean,
                progress: Float
            ) = Unit
        })
    }

    fun Float.dpToPx() : Float {
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, this, this@MainActivity.resources.displayMetrics)
    }
}