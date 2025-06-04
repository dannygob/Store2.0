package application

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.view.animation.AccelerateDecelerateInterpolator
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen // Optional: For Android 12+ splash behavior
import app.R // Correct R class import for app module

@SuppressLint("CustomSplashScreen") // Suppressing if not using Android 12+ specific API for older targets
class SplashActivity : AppCompatActivity() {

    private val SPLASH_DELAY: Long = 3000 // 3 seconds

    override fun onCreate(savedInstanceState: Bundle?) {
        // Optional: Handle Android 12+ splash screen transition smoothly.
        // installSplashScreen() // Call before super.onCreate() if you want to customize exit animation

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash) // Use app.R

        val splashImage: ImageView = findViewById(R.id.splash_image)

        // Initial state for slide-in: off-screen below
        splashImage.translationY = 500f
        splashImage.alpha = 0f

        // Slide-in animation
        val slideIn = ObjectAnimator.ofFloat(splashImage, View.TRANSLATION_Y, 0f).apply {
            duration = 1000 // 1 second
            interpolator = AccelerateDecelerateInterpolator()
        }

        // Alpha fade-in animation
        val fadeIn = ObjectAnimator.ofFloat(splashImage, View.ALPHA, 1f).apply {
            duration = 1000 // 1 second
        }

        // Twist (rotation) animation
        val twist = ObjectAnimator.ofFloat(splashImage, View.ROTATION, 0f, 360f).apply {
            duration = 1200 // 1.2 seconds
            interpolator = AccelerateDecelerateInterpolator()
        }

        // Combine slide-in and fade-in to run together
        val entranceAnimation = AnimatorSet().apply {
            playTogether(slideIn, fadeIn)
        }

        // Chain animations: entrance first, then twist
        val fullAnimation = AnimatorSet().apply {
            play(entranceAnimation).before(twist)
            start()
        }


        Handler(Looper.getMainLooper()).postDelayed({
            val intent = Intent(this@SplashActivity, MainActivity::class.java)
            startActivity(intent)
            finish()
        }, SPLASH_DELAY)
    }
}
