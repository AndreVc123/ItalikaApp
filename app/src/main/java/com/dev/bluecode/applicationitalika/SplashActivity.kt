package com.dev.bluecode.applicationitalika

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.dev.bluecode.applicationitalika.common.uiUtils.Tools
import com.dev.bluecode.applicationitalika.databinding.ActivitySplashBinding
import com.dev.bluecode.applicationitalika.productsModule.ProductsActivity

class SplashActivity : AppCompatActivity() {
    
    lateinit var binding: ActivitySplashBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root).also {
            Handler().postDelayed( {setProducts()}, 1000)
        }
    }

    private fun setProducts() {
        ItalikaApplication.cart = Tools.getCartRealm()
        ActivityCompat.finishAffinity(this@SplashActivity)
        startActivity(Intent(this, ProductsActivity::class.java))
        finish()
    }
}