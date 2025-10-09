package com.example.myapp

import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatTextView
import androidx.core.view.WindowCompat
import com.example.myapp.utils.KeyboardUtils
import com.example.myapp.utils.dpToPx

class KeyboardTestActivity : AppCompatActivity(), KeyboardUtils.KeyboardVisibilityCallback {

    private val TAG = "KeyboardTestActivity"
    private lateinit var editText: EditText
    private lateinit var rootLayout: LinearLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // 创建根布局，使用LinearLayout作为主容器
        rootLayout = LinearLayout(this).apply {
            layoutParams = ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT
            )
            orientation = LinearLayout.VERTICAL
        }

        // 添加顶部文本提示
        val topText = AppCompatTextView(this).apply {
            text = "键盘测试页面\n点击下方输入框唤起键盘"
            textSize = 18f
            setPadding(16.dpToPx(), 16.dpToPx(), 16.dpToPx(), 16.dpToPx())
        }
        rootLayout.addView(topText)

        // 创建一个占位视图，将输入框推到底部
        val spaceView = View(this).apply {
            layoutParams = LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                0,
                1.0f
            )
        }
        rootLayout.addView(spaceView)

        // 创建底部的EditText，确保它固定在底部
        editText = EditText(this).apply {
            hint = "请输入内容..."
            textSize = 16f
            setPadding(16.dpToPx(), 16.dpToPx(), 16.dpToPx(), 16.dpToPx())
            layoutParams = LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
            )
            // 设置背景和边框样式
            setBackgroundResource(android.R.drawable.editbox_background_normal)
        }
        rootLayout.addView(editText)

        setContentView(rootLayout)

        // 配置Window以确保键盘监听正常工作
        window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE)
        // 启用系统窗口装饰适配以确保键盘insets正常工作
        WindowCompat.setDecorFitsSystemWindows(window, true)

        // 设置键盘监听回调
        KeyboardUtils.setKeyboardVisibilityCallback(this)
        
        // 设置键盘监听
        KeyboardUtils.setupKeyboardListener(rootLayout)
        
        // 设置焦点变化监听
        setupFocusChangeListener()
    }

    // 实现KeyboardVisibilityCallback接口的方法
    override fun onKeyboardVisibilityChanged(isVisible: Boolean, height: Int) {
        Log.d(TAG, "Keyboard visibility changed: $isVisible, height: $height")
        // 根据键盘状态调整EditText位置
        adjustEditTextPosition(isVisible, height)
    }

    // 当Activity销毁时，清理键盘监听器，避免内存泄漏
    override fun onDestroy() {
        super.onDestroy()
        KeyboardUtils.cleanupKeyboardListener(rootLayout)
        KeyboardUtils.setKeyboardVisibilityCallback(null)
    }

    // 增加EditText焦点变化监听，当获得焦点时检查键盘状态
    private fun setupFocusChangeListener() {
        editText.setOnFocusChangeListener { _, hasFocus ->
            if (hasFocus) {
                Log.d(TAG, "EditText focused, checking keyboard state")
                // 可以在这里添加额外的逻辑
            }
        }
    }

    private fun adjustEditTextPosition(isKeyboardVisible: Boolean, keyboardHeight: Int) {
        if (isKeyboardVisible && keyboardHeight > 0) {
            // 键盘弹出时，将EditText上移键盘高度，使其底部与键盘顶部对齐
            editText.translationY = -keyboardHeight.toFloat()
            Log.d(TAG, "Translate EditText by: -$keyboardHeight to align with keyboard top")
        } else {
            // 键盘收起时，恢复原始位置
            editText.translationY = 0f
            Log.d(TAG, "Reset EditText translationY to 0")
        }
    }

    // 当Activity获得焦点时，自动弹出键盘
    override fun onResume() {
        super.onResume()
        editText.postDelayed({
            editText.requestFocus()
            val imm = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
            imm.showSoftInput(editText, InputMethodManager.SHOW_IMPLICIT)
        }, 300)
    }
}