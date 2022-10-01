package com.dev.bluecode.applicationitalika.cartModule.viewModel

import androidx.lifecycle.ViewModel
import com.dev.bluecode.applicationitalika.ItalikaApplication
import com.dev.bluecode.applicationitalika.cartModule.model.CartInteractor
import com.dev.bluecode.applicationitalika.common.model.product.Product
import com.dev.bluecode.applicationitalika.common.model.realm.RealmCart
import com.dev.bluecode.applicationitalika.detailProductModule.model.DetailProductInteractor

class CartViewModel : ViewModel() {

    private var interactor: CartInteractor =  CartInteractor()
    private var cart: RealmCart? = null


    fun getCart(): RealmCart? {
        cart = ItalikaApplication.cart
        return cart ?: null
    }

    fun sendCart(data: HashMap<String, Any>): Boolean {
        return interactor.sendCart(data)
    }
}