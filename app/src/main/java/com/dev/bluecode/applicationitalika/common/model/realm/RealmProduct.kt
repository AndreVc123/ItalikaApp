package com.dev.bluecode.applicationitalika.common.model.realm

import io.realm.RealmObject
import io.realm.annotations.PrimaryKey

open class RealmProduct : RealmObject() {
    @PrimaryKey
    var id: String? = ""
    var site_id: String? = ""
    var title: String? = ""
    var price: Int? = 0
    var thumbnail: String? = ""
    var cant: Int? = 0
}