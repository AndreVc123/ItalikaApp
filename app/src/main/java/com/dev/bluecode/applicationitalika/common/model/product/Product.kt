package com.dev.bluecode.applicationitalika.common.model.product

class Product {
    var id: String? = ""
    var site_id: String? = ""
    var title: String? = ""
    var price: Int? = 0
    var thumbnail: String? = ""
    var attributes: MutableList<Attributes>? = mutableListOf()
}