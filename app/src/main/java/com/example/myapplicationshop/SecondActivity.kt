package com.example.myapplicationshop

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import Product
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.ListView
import android.widget.TextView
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlin.math.PI

class SecondActivity : AppCompatActivity() {

    private lateinit var lwList: ListView
    private lateinit var rwGrid: RecyclerView
    private lateinit var listAdapter: ProductAdapter
    private lateinit var gridAdapter: ProductGridAdapter

    private val products = listOf(
        Product(1, "Кольцо 1", 50.0, "Описание 1", R.drawable.one),
        Product(2, "Кольцо 2", 25.5, "Описание 2", R.drawable.two),
        Product(3, "Цепочка 1", 40.0, "Описание 3", R.drawable.three),
        Product(4, "Цепочка 2", 70.5, "Описание 4", R.drawable.four),
        Product(5, "Браслет", 60.0, "Описание 5", R.drawable.five)
    )
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_second)


        val prefs = getSharedPreferences("settings", MODE_PRIVATE)

        val toolbar = findViewById<Toolbar>(R.id.topBar)
        setSupportActionBar(toolbar)

        // 1. Находим оба списка на экране
        lwList = findViewById(R.id.lvCatalog)
        rwGrid = findViewById(R.id.rvCatalogGrid)

        // 2. Находим адаптер для LW
        listAdapter = ProductAdapter(this, products)

        // 3. Находим адаптер для RW
        gridAdapter = ProductGridAdapter(this, products)

        // 4. Соединяем адаптер и список LW
        lwList.adapter = listAdapter

        // 5. Соединяем адаптер и список RW
        rwGrid.layoutManager = GridLayoutManager(this, 2)
        rwGrid.adapter = gridAdapter
    }

    private fun showList(){
        lwList.visibility = View.VISIBLE
        rwGrid.visibility = View.GONE
    }

    private fun showGrid(){
        lwList.visibility = View.GONE
        rwGrid.visibility = View.VISIBLE
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_second, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.action_list){
            showList()
            return true
        }

        if (item.itemId == R.id.action_grid){
            showGrid()
            return true
        }

        return super.onOptionsItemSelected(item)

    }


//        val container = findViewById<LinearLayout>(R.id.catalogContainer)
//
//        products.forEach {product ->
//            val view = layoutInflater.inflate(R.layout.item_product, container, false)
//
//            view.findViewById<ImageView>(R.id.ivProductImage).setImageResource(product.ImageRes)
//            view.findViewById<TextView>(R.id.tvProductName).text = product.name
//            view.findViewById<TextView>(R.id.tvProductPrice).text = "${product.price} $"
//
//            view.findViewById<Button>(R.id.btnDetails).setOnClickListener {
//                val intent = Intent(this, DetailActivity::class.java).apply {
//                    putExtra("name", product.name)
//                    putExtra("price", product.price)
//                    putExtra("ImageRes", product.ImageRes)
//                    putExtra("description", product.description)
//                }
//                startActivity(intent)
//            }
//
//            container.addView(view)
//
//        }

    }


class ProductAdapter(
    private val context: android.content.Context,
    private val products: List<Product>  // передаем в адаптер список товаров
) : android.widget.BaseAdapter(){

    // перезапись обязательных функций

    override fun getCount() = products.size // количество элементов
    override fun getItem(position: Int) = products[position] // получение элемента по индексу

    override fun getItemId(position: Int) = position.toLong() // нахождение индекса элемента

    override fun getView(pos: Int, convertView: View?, parent: ViewGroup?): View? { // создание одной карточки товара
        val view = convertView ?: LayoutInflater.from(context)
            .inflate(R.layout.item_product, parent, false)

        val product = getItem(pos)

        val image = view.findViewById<ImageView>(R.id.ivProductImage)
        val name = view.findViewById<TextView>(R.id.tvProductName)
        val price = view.findViewById<TextView>(R.id.tvProductPrice)
        val button = view.findViewById<Button>(R.id.btnDetails)

        image.setImageResource(product.ImageRes)
        name.text = product.name
        price.text = "${product.price} $"

        button.setOnClickListener {
                val intent = Intent(context, DetailActivity::class.java).apply {
                    putExtra("name", product.name)
                    putExtra("price", product.price)
                    putExtra("ImageRes", product.ImageRes)
                    putExtra("description", product.description)
                }
                context.startActivity(intent)
            }

        return view
    }

}






