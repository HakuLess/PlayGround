package com.example.myapp.utils

import android.app.Activity
import android.util.Log
import android.view.View
import android.view.ViewTreeObserver
import android.view.WindowInsets
import java.util.concurrent.atomic.AtomicBoolean

/**
 * 键盘相关工具类，处理键盘状态检测和高度计算
 * 专注于使用较新的Android API，不考虑低版本兼容
 */
object KeyboardUtils {
    private const val TAG = "KeyboardUtils"
    private const val KEYBOARD_THRESHOLD = 200 // 键盘显示的最小高度阈值（px）
    private val isKeyboardVisible = AtomicBoolean(false)
    private var keyboardHeight = 0
    private var keyboardVisibilityCallback: KeyboardVisibilityCallback? = null
    private var layoutListener: ViewTreeObserver.OnGlobalLayoutListener? = null

    /**
     * 键盘可见性变化回调接口
     */
    interface KeyboardVisibilityCallback {
        fun onKeyboardVisibilityChanged(isVisible: Boolean, height: Int)
    }

    /**
     * 设置键盘可见性变化回调
     */
    fun setKeyboardVisibilityCallback(callback: KeyboardVisibilityCallback?) {
        keyboardVisibilityCallback = callback
    }

    /**
     * 初始化键盘监听
     * 结合使用WindowInsets API（Android 11+）和全局布局变化监听两种方案
     */
    fun setupKeyboardListener(rootView: View) {
        // 清除之前的监听器
        cleanupKeyboardListener(rootView)

        // 方案1：使用WindowInsets API（Android 11+）
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.R) {
            setupWindowInsetsListener(rootView)
        }

        // 方案2：使用全局布局变化监听作为备选方案
        setupGlobalLayoutListener(rootView)
    }

    /**
     * 使用WindowInsetsCompat设置键盘监听
     */
    /**
     * 使用WindowInsets API设置键盘监听（Android 11+）
     */
    private fun setupWindowInsetsListener(rootView: View) {
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.R) {
            rootView.setOnApplyWindowInsetsListener { view, windowInsets ->
                
                // 获取IME（输入法）的可见性和高度
                val imeVisible = windowInsets.isVisible(WindowInsets.Type.ime())
                val imeHeight = if (imeVisible) {
                    windowInsets.getInsets(WindowInsets.Type.ime()).bottom
                } else {
                    0
                }

                Log.d(TAG, "WindowInsets imeVisible: $imeVisible, imeHeight: $imeHeight")

                // 只有当键盘状态或高度发生变化时才通知回调
                if (imeVisible != isKeyboardVisible.get() || (imeVisible && imeHeight != keyboardHeight)) {
                    isKeyboardVisible.set(imeVisible)
                    keyboardHeight = if (imeVisible) imeHeight else 0
                    
                    Log.d(TAG, "Updated keyboard state - visible: $imeVisible, height: $keyboardHeight (from WindowInsets)")
                    
                    // 通知回调
                    keyboardVisibilityCallback?.onKeyboardVisibilityChanged(imeVisible, keyboardHeight)
                }

                // 正确返回处理后的insets，应用默认行为
                view.onApplyWindowInsets(windowInsets)
            }
        }
    }

    /**
     * 使用全局布局变化监听设置键盘监听
     */
    private fun setupGlobalLayoutListener(rootView: View) {
        layoutListener = ViewTreeObserver.OnGlobalLayoutListener {
            // 获取根布局的可见区域
            val r = android.graphics.Rect()
            rootView.getWindowVisibleDisplayFrame(r)
            
            // 计算高度差（屏幕高度减去可见区域底部）
            val screenHeight = rootView.resources.displayMetrics.heightPixels
            val heightDiff = screenHeight - r.bottom
            
            // 保存原始计算数据用于调试
            Log.d(TAG, "Visible rect: left=${r.left}, top=${r.top}, right=${r.right}, bottom=${r.bottom}")
            Log.d(TAG, "Height calculation - Screen height: $screenHeight, Visible bottom: ${r.bottom}, Height difference: $heightDiff")

            // 判断键盘是否可见（使用阈值过滤）
            val visible = heightDiff > KEYBOARD_THRESHOLD
            
            // 只有当键盘状态或高度发生变化时才通知回调
            if (visible != isKeyboardVisible.get() || (visible && heightDiff != keyboardHeight)) {
                isKeyboardVisible.set(visible)
                keyboardHeight = if (visible) heightDiff else 0
                
                Log.d(TAG, "Updated keyboard state - visible: $visible, height: $keyboardHeight (from GlobalLayout)")
                
                // 通知回调
                keyboardVisibilityCallback?.onKeyboardVisibilityChanged(visible, keyboardHeight)
            }
        }

        // 添加布局监听器
        val viewTreeObserver = rootView.viewTreeObserver
        if (viewTreeObserver.isAlive) {
            viewTreeObserver.addOnGlobalLayoutListener(layoutListener)
        }
    }

    /**
     * 清除键盘监听器，避免内存泄漏
     */
    fun cleanupKeyboardListener(rootView: View) {
        if (layoutListener != null) {
            val viewTreeObserver = rootView.viewTreeObserver
            if (viewTreeObserver.isAlive) {
                viewTreeObserver.removeOnGlobalLayoutListener(layoutListener)
            }
            layoutListener = null
        }
        
        // 移除WindowInsets监听器
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.R) {
            rootView.setOnApplyWindowInsetsListener(null)
        }
        
        keyboardVisibilityCallback = null
    }

    /**
     * 获取当前键盘是否可见
     */
    fun isKeyboardVisible(): Boolean {
        return isKeyboardVisible.get()
    }

    /**
     * 获取当前键盘高度
     */
    fun getKeyboardHeight(): Int {
        return keyboardHeight
    }
}