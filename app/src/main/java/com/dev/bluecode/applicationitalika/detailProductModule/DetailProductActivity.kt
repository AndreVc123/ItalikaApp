package com.dev.bluecode.applicationitalika.detailProductModule

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.dev.bluecode.applicationitalika.R
import com.dev.bluecode.applicationitalika.cartModule.CartActivity
import com.dev.bluecode.applicationitalika.common.model.product.Product
import com.dev.bluecode.applicationitalika.common.uiUtils.Tools
import com.dev.bluecode.applicationitalika.databinding.ActivityDetailProductBinding
import com.dev.bluecode.applicationitalika.detailProductModule.viewModel.DetailProductViewModel
import com.dev.bluecode.applicationitalika.productsModule.ProductsActivity
import com.dev.bluecode.applicationitalika.productsModule.viewModel.ProductsViewModel
import com.dev.bluecode.loaderapplication.LoaderView

class DetailProductActivity : AppCompatActivity() {

    lateinit var binding: ActivityDetailProductBinding
    private lateinit var mDetailViewModel: DetailProductViewModel
     lateinit var loader: LoaderView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailProductBinding.inflate(layoutInflater)
        setContentView(binding.root)

        loader = LoaderView(this, null)
        loader.setTextColor(R.color.white)


        setupViewModel()
        setListeners()

    }

    private fun setListeners() {
        binding.btnPlus.setOnClickListener {
            var cant = binding.edtCant.text.toString().toIntOrNull() ?: 0
            binding.edtCant.setText("${cant + 1}")
            binding.btnMinus.setImageDrawable(resources.getDrawable(R.drawable.ic_minus_blue))
        }
        binding.btnMinus.setOnClickListener {
            var cant = binding.edtCant.text.toString().toIntOrNull() ?: 0
            if(cant > 1) {
                binding.edtCant.setText("${cant - 1}")
            }

            if((cant - 1) > 1)  binding.btnMinus.setImageDrawable(resources.getDrawable(R.drawable.ic_minus_blue))  else  binding.btnMinus.setImageDrawable(resources.getDrawable(R.drawable.ic_minus_inactive))
        }
        binding.btnAddCart.setOnClickListener {
            loader.setText("Añadiendo a carrito...")
            loader.setShow(true)
            if(mDetailViewModel.addCart(binding.edtCant.text.toString().toIntOrNull() ?: 0)) {
                Tools.setCartNotifications(binding.containerNotifications, binding.txtCartNumItems, binding.icBasket, this,true)
                Handler().postDelayed({loader.setShow(false)},1000)
            }

        }
        binding.btnBack.setOnClickListener { finish() }
        binding.containerNotifications.setOnClickListener {
            if(Tools.getCartRealm() != null) {
                startActivity(Intent(this, CartActivity::class.java))
            }else {
                Tools.toastMessageError(binding.root, "Aún no tienes ningún producto agregado", this).show()
            }
        }
    }

    private fun setupViewModel() {
        mDetailViewModel = ViewModelProvider(this).get(DetailProductViewModel::class.java)
        setProduct(mDetailViewModel.getProduct(intent.extras?.getString("id")!!))
    }

    private fun setProduct(product: Product?) {
        loader.setText("Cargando datos...")
        loader.setShow(true)

        Glide.with(this).load(product?.thumbnail?.replace("D", "D_NQ_NP")?.replace("I","O") ?: "").fitCenter().into(binding.imgProduct)
        binding.txtName.text = product?.title ?: ""
        binding.txtPrice.text = "Precio: " + Tools.moneyFormat(product?.price?.toFloat()!!, true)
        binding.txtCaracteristicas.text = "Caracteristicas\n\n"
        product?.attributes?.forEach {
            binding.txtCaracteristicas.text =  binding.txtCaracteristicas.text.toString() +"${it.name ?: "S/Caracteristica"}: ${it.value_name ?: ""}\n"
        }
        Handler().postDelayed({loader.setShow(false)},500)
    }

    override fun onResume() {
        super.onResume()
        Tools.setCartNotifications(binding.containerNotifications, binding.txtCartNumItems, binding.icBasket, this,false)
    }
}