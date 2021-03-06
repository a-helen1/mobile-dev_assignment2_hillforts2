package org.wit.hillforts.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_splash_screen.*
import org.wit.hillforts.R
import org.wit.hillforts.views.hillfortlist.HillfortListView
import org.wit.hillforts.views.login.LoginView

class SplashScreenActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)

        iv_landscape.alpha = 0f
        iv_landscape.animate().setDuration(2000).alpha(1f).withEndAction() {
            val i = Intent(this, LoginView::class.java)
            startActivity(i)
            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
            finish()
        }
    }
}