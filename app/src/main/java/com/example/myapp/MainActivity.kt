package com.example.myapp

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val menuItems = listOf(
            MenuItem("ViewPager比较测试", ViewPagerTestActivity::class.java),
            MenuItem("测试页面2", MainActivity::class.java),
            MenuItem("测试页面3", MainActivity::class.java)
        )

        val recyclerView = findViewById<RecyclerView>(R.id.menuRecyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.addItemDecoration(
            DividerItemDecoration(this, DividerItemDecoration.VERTICAL)
        )
        recyclerView.adapter = MenuAdapter(menuItems) { item ->
            startActivity(Intent(this, item.activityClass))
        }
    }
}

data class MenuItem(val title: String, val activityClass: Class<*>)

class MenuAdapter(
    private val items: List<MenuItem>,
    private val onClick: (MenuItem) -> Unit
) : RecyclerView.Adapter<MenuViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MenuViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_menu, parent, false)
        return MenuViewHolder(view, onClick)
    }

    override fun onBindViewHolder(holder: MenuViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount(): Int = items.size
}

class MenuViewHolder(
    itemView: View,
    private val onClick: (MenuItem) -> Unit
) : RecyclerView.ViewHolder(itemView) {

    private val titleTextView: TextView = itemView.findViewById(R.id.titleTextView)

    fun bind(item: MenuItem) {
        titleTextView.text = item.title
        itemView.setOnClickListener { onClick(item) }
    }
}