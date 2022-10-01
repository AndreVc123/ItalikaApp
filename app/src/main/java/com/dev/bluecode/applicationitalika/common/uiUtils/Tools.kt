package com.dev.bluecode.applicationitalika.common.uiUtils

import android.content.Context
import android.os.Handler
import android.text.TextUtils
import android.view.View
import android.view.animation.AnimationUtils
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import com.dev.bluecode.applicationitalika.ItalikaApplication
import com.dev.bluecode.applicationitalika.R
import com.dev.bluecode.applicationitalika.common.model.realm.RealmCart
import com.google.android.material.snackbar.Snackbar
import io.realm.Realm
import java.text.DecimalFormat

object Tools {

    fun isValidEmail(target: CharSequence): Boolean { return !TextUtils.isEmpty(target) && android.util.Patterns.EMAIL_ADDRESS.matcher(target).matches() }

    fun toastMessageError(view: View, message: String, context: Context) : Snackbar {
        return Snackbar.make(view, message, Snackbar.LENGTH_LONG).setBackgroundTintList(context.resources.getColorStateList(R.color.colorRed)).setTextColor(context.resources.getColor(
            R.color.white))
    }

    fun toastMessageSuccess(view: View, message: String, context: Context) : Snackbar {
        return Snackbar.make(view, message, Snackbar.LENGTH_LONG).setBackgroundTintList(context.resources.getColorStateList(R.color.colorGreen)).setTextColor(context.resources.getColor(R.color.white))
    }

    fun moneyFormat(quantity: Float,decimal:Boolean): String {
        val df = if (decimal) DecimalFormat("#,###,##0.00") else DecimalFormat("#,###,##0")
        return "$"+df.format(quantity)
    }

    fun deleteCartRealm() {
        val realm: Realm? = Realm.getDefaultInstance()
        realm?.executeTransaction{ realm.delete(RealmCart::class.java) }

    }

    fun getCartRealm(): RealmCart? {
        return Realm.getDefaultInstance().where(RealmCart::class.java).findFirst()
    }

    fun setCartNotifications(
        containerNotifications: RelativeLayout?,
        txtCartNumItems: TextView?,
        icBasket: ImageView?,
        context: Context,
        anim: Boolean
    ){
        val items = if (!ItalikaApplication.cart?.products.isNullOrEmpty())ItalikaApplication.cart?.products?.size else null
        if (items != null && items > 0){
            txtCartNumItems?.visibility = View.VISIBLE
            txtCartNumItems?.text = if (items <= 99) items.toString() else "99+"
        }else{
            txtCartNumItems?.visibility = View.GONE
        }
        if (anim){
            // load the animation
            val animZoomIn = AnimationUtils.loadAnimation(context, R.anim.zoom_in)
            val animZoomOut = AnimationUtils.loadAnimation(context, R.anim.zoom_out)
            icBasket?.startAnimation(animZoomIn)
            Handler().postDelayed({ icBasket?.startAnimation(animZoomOut) }, 400)
        }
    }
}