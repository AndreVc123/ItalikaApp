package com.dev.bluecode.applicationitalika.cartModule.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.dev.bluecode.applicationitalika.R
import com.dev.bluecode.applicationitalika.common.model.product.Product
import com.dev.bluecode.applicationitalika.common.model.realm.RealmProduct
import com.dev.bluecode.applicationitalika.common.uiUtils.Tools
import com.dev.bluecode.applicationitalika.databinding.ItemCardProductBinding
import com.dev.bluecode.applicationitalika.databinding.ItemCartProductBinding
import com.dev.bluecode.applicationitalika.productsModule.adapters.ProductsAdapter
import io.realm.RealmList

class ProductsCartAdapter  (private var lst: RealmList<Any>, private var listener: View.OnClickListener) : RecyclerView.Adapter<ProductsCartAdapter.viewHolder>() {

    private lateinit var context: Context

    inner class viewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val binding = ItemCartProductBinding.bind(view)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): viewHolder {
        context = parent.context
        val view = LayoutInflater.from(context).inflate(R.layout.item_cart_product, parent, false)
        return viewHolder(view)
    }

    override fun onBindViewHolder(holder: viewHolder, position: Int) {
        with(holder) {
            lst[position]?.let { setItemData(it, holder) }
        }
    }

    private fun setItemData(itemData: Any, holder: viewHolder) {
        with(holder) {
            itemData as RealmProduct

            Glide.with(context).load(itemData.thumbnail?.replace("D", "D_NQ_NP")?.replace("I","O") ?: "").fitCenter().into(binding.imgProduct)
            binding.txtName.text = itemData.title ?: ""
            binding.txtModelo.visibility = View.GONE
            binding.txtPriceProduct.text = Tools.moneyFormat((itemData.price?.toFloat()!!) * (itemData.cant ?: 0), true)
            binding.txtCant.text = "${itemData.cant ?: 0}"
            binding.containerCardProduct.tag = itemData
            binding.containerCardProduct.setOnClickListener(listener)
        }
    }

    override fun getItemCount(): Int = if(lst.isValid) lst.size else 0

}