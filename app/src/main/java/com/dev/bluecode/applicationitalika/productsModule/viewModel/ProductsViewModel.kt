package com.dev.bluecode.applicationitalika.productsModule.viewModel

import android.content.Loader
import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dev.bluecode.applicationitalika.common.model.product.Product
import com.dev.bluecode.applicationitalika.productsModule.model.ProductsInteractor
import com.dev.bluecode.loaderapplication.LoaderView

class ProductsViewModel : ViewModel() {

    private var interactor: ProductsInteractor =  ProductsInteractor()

    private var productsList: MutableList<Product> = mutableListOf()



    fun getProducts() : LiveData<List<Product>> {
        return products
    }

    private val products: MutableLiveData<List<Product>> by lazy {
        MutableLiveData<List<Product>>().also {
            loadProducts()
        }
    }
    private fun loadProducts() {
        interactor.getProducts {
            this.products.value = it
            productsList = it
        }
    }

    fun searchProduct(query: String) {
         var pattern = query.toLowerCase().toRegex()
        this.products.value = this.productsList.filter { pattern.containsMatchIn(it.title?.toLowerCase().toString()) }
    }
}