package com.dev.bluecode.applicationitalika.cartModule.model

import com.dev.bluecode.applicationitalika.ItalikaApplication
import com.dev.bluecode.applicationitalika.common.model.product.Product
import com.dev.bluecode.applicationitalika.common.uiUtils.Tools
import kotlinx.coroutines.runBlocking
import java.util.logging.Handler

class CartInteractor {
    fun sendCart(data: HashMap<String, Any>): Boolean {
        Tools.deleteCartRealm()
        ItalikaApplication.cart = null
        var thread = Thread {}
        thread.start()
        thread.join(1000)
        return true
    }
}