package com.example.myapp

import android.content.Intent
import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.myapp.utils.dpToPx

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // 创建主布局
        val mainLayout = LinearLayout(this).apply {
            orientation = LinearLayout.VERTICAL
            layoutParams = ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT
            )
        }

        // 添加标题
        val title = TextView(this).apply {
            text = "功能目录"
            textSize = 24f
            gravity = Gravity.CENTER
            setPadding(0, 24.dpToPx(), 0, 24.dpToPx())
        }
        mainLayout.addView(title)

        // 添加菜单项
        val menuItems = listOf(
            MenuItem("ViewPager比较测试", ViewPagerTestActivity::class.java),
            MenuItem("测试页面2", MainActivity::class.java),
            MenuItem("测试页面3", MainActivity::class.java)
        )

        menuItems.forEach { item ->
            val menuItem = TextView(this).apply {
                text = item.title
                textSize = 18f
                setPadding(24.dpToPx(), 16.dpToPx(), 24.dpToPx(), 16.dpToPx())
                setOnClickListener {
                    startActivity(Intent(this@MainActivity, item.activityClass))
                }
            }
            mainLayout.addView(menuItem)
        }

        setContentView(mainLayout)
    }
}

data class MenuItem(val title: String, val activityClass: Class<*>)

// 暂时注释掉RecyclerView相关代码，因为缺少布局文件
/*
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
*/