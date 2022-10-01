package com.dev.bluecode.applicationitalika.cartModule

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.dev.bluecode.applicationitalika.R
import com.dev.bluecode.applicationitalika.cartModule.adapters.ProductsCartAdapter
import com.dev.bluecode.applicationitalika.cartModule.viewModel.CartViewModel
import com.dev.bluecode.applicationitalika.common.model.product.Product
import com.dev.bluecode.applicationitalika.common.model.realm.RealmCart
import com.dev.bluecode.applicationitalika.common.model.realm.RealmProduct
import com.dev.bluecode.applicationitalika.common.uiUtils.Tools
import com.dev.bluecode.applicationitalika.databinding.ActivityCartBinding
import com.dev.bluecode.applicationitalika.databinding.ActivityDetailProductBinding
import com.dev.bluecode.applicationitalika.detailProductModule.DetailProductActivity
import com.dev.bluecode.applicationitalika.detailProductModule.viewModel.DetailProductViewModel
import com.dev.bluecode.applicationitalika.productsModule.adapters.ProductsAdapter
import com.dev.bluecode.loaderapplication.LoaderView
import io.realm.RealmList

class CartActivity : AppCompatActivity() {

    lateinit var binding: ActivityCartBinding
    private lateinit var mCartViewModel: CartViewModel
    private lateinit var mAdapter: ProductsCartAdapter
    lateinit var data: HashMap<String, Any>
    lateinit var loader: LoaderView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCartBinding.inflate(layoutInflater)
        setContentView(binding.root)

        loader = LoaderView(this, null)
        loader.setTextColor(R.color.white)

        loader.setShow(true)
        loader.setText("Cargando datos...")

        data = hashMapOf()
        setupViewModel()
        setListeners()

    }

    private fun setupViewModel() {
        mCartViewModel = ViewModelProvider(this).get(CartViewModel::class.java)
        setCart(mCartViewModel.getCart()!!)
    }

    private fun setListeners() {
        binding.btnSendToBuy.setOnClickListener {
            loader.setText("Se esta realizando tu compra...")
            loader.setShow(true)

            if(validateData()) {
                data["total"] = binding.txtTotal.text.toString()
                if(mCartViewModel.sendCart(data)) {
                    Tools.toastMessageSuccess(binding.root, "Compra exitosa", this).show()
                    Handler().postDelayed({ loader.setShow(false)}, 500)
                    Handler().postDelayed({ finish()}, 1000)
                } else { loader.setShow(false) }
            } else { loader.setShow(false) }
        }
        binding.btnBack.setOnClickListener { finish() }
    }

    private fun validateData(): Boolean {
        if(!binding.edtEmail.editText?.text.toString().isNullOrBlank()) {
            if(Tools.isValidEmail(binding.edtEmail.editText?.text!!)) {
                data["email"] = binding.edtEmail.editText?.text.toString()
            }else {
                Tools.toastMessageError(binding.root, "Debes ingresar un Correo electrónico válido", this).show()
                return false
            }
        }else {
            Tools.toastMessageError(binding.root, "Debes ingresar un Correo electrónico", this).show()
            return false
        }

        if(!binding.edtNumber.editText?.text.toString().isNullOrBlank()) {
            data["phone"] = binding.edtNumber.editText?.text.toString()
        }else {
            Tools.toastMessageError(binding.root, "Debes ingresar un Celúlar", this).show()
            return false
        }

        return true
    }

    private fun setCart(cart: RealmCart?) {
        setTotal(cart?.products)
        setProducts(cart?.products)
    }

    private fun setProducts(products: RealmList<RealmProduct>?) {
        if(!products.isNullOrEmpty()) {
            mAdapter = ProductsCartAdapter(products as RealmList<Any>) {}
            var LinearLayout = LinearLayoutManager(this,RecyclerView.VERTICAL, false)
            binding.recyclerViewProducts.apply {
                setHasFixedSize(true)
                layoutManager = LinearLayout
                adapter = mAdapter
            }
            binding.recyclerViewProducts.post {
                Handler().postDelayed({loader.setShow(false)},500)
            }
        }
    }

    private fun setTotal(products: RealmList<RealmProduct>?) {
        var price = 0F
        var productsTotal = 0

        products?.forEach { 
            price += it.price?.toFloat()!! * it.cant?.toFloat()!!
            productsTotal += it.cant ?: 0
        }

        binding.txtNoProducts.text = "${ productsTotal ?: 0 }"
        binding.txtTotal.text = Tools.moneyFormat(price, true)
    }


}