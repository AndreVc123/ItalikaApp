package com.dev.bluecode.applicationitalika.productsModule.model

import android.content.Intent
import androidx.core.app.ActivityCompat
import com.dev.bluecode.applicationitalika.ItalikaApplication
import com.dev.bluecode.applicationitalika.common.apiRetrofit.InterfacesServices
import com.dev.bluecode.applicationitalika.common.model.product.Product
import com.dev.bluecode.applicationitalika.common.model.response.ProductsResponse
import com.dev.bluecode.applicationitalika.common.uiUtils.Tools
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.observers.DisposableObserver
import io.reactivex.schedulers.Schedulers
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread

class ProductsInteractor {
    fun getProducts(callback: (MutableList<Product>) -> Unit) {
        InterfacesServices.productsServices.getProducts()
            .subscribeOn(Schedulers.io()).observeOn(
                AndroidSchedulers.mainThread()).subscribe(object : DisposableObserver<ProductsResponse>() {
                override fun onNext(response: ProductsResponse) {
                    ItalikaApplication.products = response.results as MutableList<Product>
                    callback(response.results as MutableList<Product>)
                }
                override fun onError(e: Throwable) {
                    callback(mutableListOf())
                }
                override fun onComplete() {}
            })
    }
}