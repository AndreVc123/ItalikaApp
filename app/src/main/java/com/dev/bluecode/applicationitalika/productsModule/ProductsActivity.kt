package com.dev.bluecode.applicationitalika.productsModule

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.dev.bluecode.applicationitalika.R
import com.dev.bluecode.applicationitalika.cartModule.CartActivity
import com.dev.bluecode.applicationitalika.common.model.product.Product
import com.dev.bluecode.applicationitalika.common.uiUtils.Tools
import com.dev.bluecode.applicationitalika.databinding.ActivityProductsBinding
import com.dev.bluecode.applicationitalika.detailProductModule.DetailProductActivity
import com.dev.bluecode.applicationitalika.productsModule.adapters.ProductsAdapter
import com.dev.bluecode.applicationitalika.productsModule.viewModel.ProductsViewModel
import com.dev.bluecode.loaderapplication.LoaderView

class ProductsActivity : AppCompatActivity() {

    lateinit var binding: ActivityProductsBinding
    private lateinit var mAdapter: ProductsAdapter
    private lateinit var mGridLayout: GridLayoutManager
    private lateinit var mMainViewModel: ProductsViewModel
     lateinit var loader: LoaderView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProductsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        loader = LoaderView(this, null)
        loader.setText("Cargando datos...")
        loader.setTextColor(R.color.white)


        loader.setShow(true)
        setupViewModel()
        setupRecylcerView()
        setListeners()
    }

    private fun setListeners() {
        binding.edtSearch.editText?.addTextChangedListener(object: TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                mMainViewModel.searchProduct(p0.toString())
            }
            override fun afterTextChanged(p0: Editable?) {}
        })
        binding.containerNotifications.setOnClickListener {
            if(Tools.getCartRealm() != null) {
                startActivity(Intent(this, CartActivity::class.java))
            }else {
                Tools.toastMessageError(binding.root, "Aún no tienes ningún producto agregado", this).show()
            }
        }

    }

    private fun setupViewModel() {
        mMainViewModel = ViewModelProvider(this).get(ProductsViewModel::class.java)
        mMainViewModel.getProducts().observe(this, {
            mAdapter.setProducts(it as MutableList<Any>)
            loader.setShow(false)
        })
    }

    private fun setupRecylcerView() {
        mAdapter = ProductsAdapter(mutableListOf()) {
            var intent = Intent(this, DetailProductActivity::class.java)
            intent.putExtra("id", (it.tag as Product).id)
            startActivity(intent)
        }
        mGridLayout = GridLayoutManager(this, 2)
        binding.recyclerViewProducts.apply {
            setHasFixedSize(true)
            layoutManager = mGridLayout
            adapter = mAdapter
        }
    }

    override fun onResume() {
        super.onResume()
        Tools.setCartNotifications(binding.containerNotifications, binding.txtCartNumItems, binding.icBasket, this,false)
    }
}