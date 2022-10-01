package com.dev.bluecode.applicationitalika.common.apiRetrofit

import com.dev.bluecode.applicationitalika.common.model.response.ProductsResponse
import io.reactivex.Observable
import retrofit2.http.GET

interface ProductsInterfaces {

    @GET("/sites/MLA/search?category=MLA1763")
    fun getProducts(): Observable<ProductsResponse>
}