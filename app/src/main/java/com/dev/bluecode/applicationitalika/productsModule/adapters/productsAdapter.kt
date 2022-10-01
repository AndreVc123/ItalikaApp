package com.dev.bluecode.applicationitalika.productsModule.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.dev.bluecode.applicationitalika.R
import com.dev.bluecode.applicationitalika.common.model.product.Product
import com.dev.bluecode.applicationitalika.common.uiUtils.Tools
import com.dev.bluecode.applicationitalika.databinding.ItemCardProductBinding

class ProductsAdapter  (private var lst: MutableList<Any>, private var listener: View.OnClickListener) : RecyclerView.Adapter<ProductsAdapter.viewHolder>() {

    private lateinit var context: Context

    inner class viewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val binding = ItemCardProductBinding.bind(view)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): viewHolder {
        context = parent.context
        val view = LayoutInflater.from(context).inflate(R.layout.item_card_product, parent, false)
        return viewHolder(view)
    }

    override fun onBindViewHolder(holder: viewHolder, position: Int) {
        with(holder) {
            setItemData(lst[position], holder)
        }
    }

    private fun setItemData(itemData: Any, holder: viewHolder) {
        with(holder) {
           itemData as Product

            Glide.with(context).load(itemData.thumbnail?.replace("D", "D_NQ_NP")?.replace("I","O") ?: "").fitCenter().into(binding.imgProduct)
            binding.txtName.text = itemData.title ?: ""
            binding.txtModelo.text = if(itemData.attributes?.size!! >= 3) itemData.attributes!![2].value_name else ""
            binding.txtPriceProduct.text = Tools.moneyFormat(itemData.price?.toFloat()!!, true)
            binding.containerCardProduct.tag = itemData
            binding.containerCardProduct.setOnClickListener(listener)
        }
    }

    override fun getItemCount(): Int = lst.size

    fun setProducts(products: MutableList<Any>) {
        this.lst = products
        notifyDataSetChanged()
    }
}