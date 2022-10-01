package com.dev.bluecode.applicationitalika.detailProductModule.model

import com.dev.bluecode.applicationitalika.ItalikaApplication
import com.dev.bluecode.applicationitalika.common.model.product.Product
import com.dev.bluecode.applicationitalika.common.model.realm.RealmCart
import com.dev.bluecode.applicationitalika.common.model.realm.RealmProduct
import com.dev.bluecode.applicationitalika.common.uiUtils.Tools
import io.realm.Realm
import io.realm.RealmList
import org.jetbrains.anko.doAsync

class DetailProductInteractor {

    fun addCartProduct(cant: Int, product: Product): Boolean {
        var cart = Tools.getCartRealm()
        var cartUpdate = RealmCart()
        var productRealm = RealmProduct()

        productRealm.id = product.id
        productRealm.site_id = product.site_id
        productRealm.title = product.title
        productRealm.price = product.price
        productRealm.thumbnail = product.thumbnail
        productRealm.cant = cant

        val realm: Realm? = Realm.getDefaultInstance()
        realm!!.executeTransaction {
            if(cart == null) {
                cartUpdate?.clv_cart = (1..1000000).random()
                cartUpdate?.products = RealmList(productRealm)
            } else {
                var productos = cart.products
                productos?.add(productRealm)

                cartUpdate.clv_cart = cart.clv_cart
                cartUpdate?.products = productos
            }
            realm.copyToRealmOrUpdate(cartUpdate)
        }
        ItalikaApplication.cart = Tools.getCartRealm()

        return true
    }

}