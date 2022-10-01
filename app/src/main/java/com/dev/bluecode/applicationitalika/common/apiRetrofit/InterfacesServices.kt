package com.dev.bluecode.applicationitalika.common.apiRetrofit


object InterfacesServices {

    val productsServices: ProductsInterfaces
        get() = RetrofitHelper().apiProductsServices
}