package com.dev.bluecode.applicationitalika.detailProductModule.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dev.bluecode.applicationitalika.ItalikaApplication
import com.dev.bluecode.applicationitalika.common.model.product.Product
import com.dev.bluecode.applicationitalika.detailProductModule.model.DetailProductInteractor
import com.dev.bluecode.applicationitalika.productsModule.model.ProductsInteractor

class DetailProductViewModel : ViewModel() {
    private var interactor: DetailProductInteractor =  DetailProductInteractor()
    private var product: Product? = null


    fun getProduct(id: String) : Product? {
        var products = ItalikaApplication.products?.filter { it.id == id }
        product = if(!products.isNullOrEmpty()) {
            products[0]
        } else {
            null
        }
        return product ?: Product()
    }

    fun addCart(cant: Int): Boolean {
        return interactor.addCartProduct(cant, product!!)
    }
}