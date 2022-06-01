package kong.droid.motionexample

import android.app.Activity
import android.content.Context
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.TypedValue
import android.view.View
import android.view.ViewGroup
import android.view.WindowInsets
import android.view.WindowManager
import androidx.constraintlayout.motion.widget.MotionLayout
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import kong.droid.motionexample.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding

    //애니메이션 동작 여부 체크
    var alreadyAnimation = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        makeStatusTrans()
        initActionBar()
        initInset()
        initView()
    }

    fun initInset()= with(binding) {
        ViewCompat.setOnApplyWindowInsetsListener(coordinator){ v:View, insets: WindowInsetsCompat ->
            val params = v.layoutParams as ViewGroup.MarginLayoutParams
            params.bottomMargin = insets.systemWindowInsetBottom
            toolbarContainer.layoutParams = (toolbarContainer.layoutParams as ViewGroup.MarginLayoutParams).apply {
                setMargins(0,insets.systemWindowInsetTop,0,0)
            }
            collapsiongTool.layoutParams= (collapsiongTool.layoutParams as ViewGroup.MarginLayoutParams).apply {
                setMargins(0,0,0,0)
            }
            insets.consumeSystemWindowInsets()
        }
    }

    fun initActionBar() = with(binding){
        toolbar.navigationIcon = null
        toolbar.setContentInsetsAbsolute(0,0)
        setSupportActionBar(binding.toolbar)
        supportActionBar?.let {
            it.setHomeButtonEnabled(false)
            it.setDisplayHomeAsUpEnabled(false)
            it.setDisplayShowHomeEnabled(false)
        }
    }

    private fun initView() = with(binding) {


        scrollView.viewTreeObserver.addOnScrollChangedListener {
            if (scrollView.scrollY > 150f.dpToPx()) {
                if (alreadyAnimation.not()) {
                    motionLayout.transitionToEnd()
                    wholeMotion.transitionToEnd()
                }
            } else {
                if (alreadyAnimation.not()) {
                    motionLayout.transitionToStart()
                    wholeMotion.transitionToStart()
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
            ) = Unit

            override fun onTransitionTrigger(
                motionLayout: MotionLayout?,
                triggerId: Int,
                positive: Boolean,
                progress: Float
            ) = Unit
        })
    }

    fun Float.dpToPx(): Float {
        return TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_DIP,
            this,
            this@MainActivity.resources.displayMetrics
        )
    }

    fun Activity.makeStatusTrans() {
        window.apply {
            decorView.systemUiVisibility =
                View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or
                        View.SYSTEM_UI_FLAG_LAYOUT_STABLE
            addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            statusBarColor = Color.TRANSPARENT
        }
    }
}