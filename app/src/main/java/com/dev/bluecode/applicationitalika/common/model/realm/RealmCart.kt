package com.dev.bluecode.applicationitalika.common.model.realm

import com.dev.bluecode.applicationitalika.common.model.product.Product
import io.realm.RealmList
import io.realm.RealmObject
import io.realm.annotations.PrimaryKey

open class RealmCart : RealmObject() {
    @PrimaryKey
    var clv_cart: Int? = null
    var products: RealmList<RealmProduct>? = RealmList()
}