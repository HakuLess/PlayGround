package com.example.myapp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.LinearLayout
import android.view.Gravity
import android.view.ViewGroup
import android.widget.Toast

class UIKitDemoActivity : AppCompatActivity() {
    private var counter = 0
    private lateinit var counterTextView: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        
        // 创建主布局
        val mainLayout = LinearLayout(this).apply {
            orientation = LinearLayout.VERTICAL
            setPadding(16, 16, 16, 16)
            gravity = Gravity.CENTER_HORIZONTAL
        }
        
        // 创建标题
        val titleTextView = TextView(this).apply {
            text = "UI Kit 演示"
            textSize = 24f
            layoutParams = LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
            )
            setPadding(0, 0, 0, 24)
        }
        mainLayout.addView(titleTextView)
        
        // 创建计数器示例
        val counterLayout = LinearLayout(this).apply {
            orientation = LinearLayout.HORIZONTAL
            gravity = Gravity.CENTER
            layoutParams = LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
            )
            setPadding(0, 0, 0, 32)
        }
        
        // 减号按钮
        val minusButton = Button(this).apply {
            text = "-"
            layoutParams = LinearLayout.LayoutParams(
                80, 80
            ).apply {
                setMargins(8, 0, 8, 0)
            }
            setOnClickListener {
                counter--
                updateCounter()
            }
        }
        counterLayout.addView(minusButton)
        
        // 计数器显示
        counterTextView = TextView(this).apply {
            text = "0"
            textSize = 24f
            layoutParams = LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
            )
            setPadding(16, 0, 16, 0)
        }
        counterLayout.addView(counterTextView)
        
        // 加号按钮
        val plusButton = Button(this).apply {
            text =("+")
            layoutParams = LinearLayout.LayoutParams(
                80, 80
            ).apply {
                setMargins(8, 0, 8, 0)
            }
            setOnClickListener {
                counter++
                updateCounter()
            }
        }
        counterLayout.addView(plusButton)
        
        mainLayout.addView(counterLayout)
        
        // 创建文本输入示例
        val inputLayout = LinearLayout(this).apply {
            orientation = LinearLayout.VERTICAL
            layoutParams = LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
            )
            setPadding(0, 0, 0, 32)
        }
        
        val inputLabel = TextView(this).apply {
            text = "文本输入示例:"
            textSize = 16f
            layoutParams = LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
            )
            setPadding(0, 0, 0, 8)
        }
        inputLayout.addView(inputLabel)
        
        val editText = EditText(this).apply {
            layoutParams = LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
            )
            hint = "请输入文本"
        }
        inputLayout.addView(editText)
        
        mainLayout.addView(inputLayout)
        
        // 创建普通按钮
        val demoButton = Button(this).apply {
            text = "演示按钮"
            layoutParams = LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
            )
            setOnClickListener {
                Toast.makeText(this@UIKitDemoActivity, "按钮被点击了", Toast.LENGTH_SHORT).show()
            }
        }
        mainLayout.addView(demoButton)
        
        // 设置内容视图
        setContentView(mainLayout)
    }
    
    private fun updateCounter() {
        counterTextView.text = counter.toString()
    }
}