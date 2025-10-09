package com.example.myapp.utils

import android.content.Context
import android.util.TypedValue
import android.view.View
import com.example.myapp.BaseApplication

/**
 * Int类型的dp转px扩展函数（无参数版本，使用全局ApplicationContext）
 */
fun Int.dpToPx(): Int {
    return dpToPx(BaseApplication.context)
}

/**
 * Int类型的dp转px扩展函数
 * 添加+0.5的偏差是为了在浮点数转整数时实现四舍五入，而不是直接截断小数部分
 * 这在像素计算中非常重要，可以确保尺寸转换的准确性
 */
fun Int.dpToPx(context: Context): Int {
    return (TypedValue.applyDimension(
        TypedValue.COMPLEX_UNIT_DIP,
        this.toFloat(),
        context.resources.displayMetrics
    ) + 0.5f).toInt()
}

/**
 * Float类型的dp转px扩展函数（无参数版本，使用全局ApplicationContext）
 */
fun Float.dpToPx(): Int {
    return dpToPx(BaseApplication.context)
}

/**
 * Float类型的dp转px扩展函数
 * 添加+0.5的偏差是为了在浮点数转整数时实现四舍五入，而不是直接截断小数部分
 */
fun Float.dpToPx(context: Context): Int {
    return (TypedValue.applyDimension(
        TypedValue.COMPLEX_UNIT_DIP,
        this,
        context.resources.displayMetrics
    ) + 0.5f).toInt()
}

/**
 * Int类型的px转dp扩展函数（无参数版本，使用全局ApplicationContext）
 */
fun Int.pxToDp(): Float {
    return pxToDp(BaseApplication.context)
}

/**
 * Int类型的px转dp扩展函数
 */
fun Int.pxToDp(context: Context): Float {
    return this / context.resources.displayMetrics.density
}

/**
 * Float类型的px转dp扩展函数（无参数版本，使用全局ApplicationContext）
 */
fun Float.pxToDp(): Float {
    return pxToDp(BaseApplication.context)
}

/**
 * Float类型的px转dp扩展函数
 */
fun Float.pxToDp(context: Context): Float {
    return this / context.resources.displayMetrics.density
}

/**
 * 为View添加dp转px的扩展函数，方便在View对象上直接调用
 */
fun Int.dp(view: View): Int {
    return dpToPx(view.context)
}

/**
 * 为View添加dp转px的扩展函数，方便在View对象上直接调用
 */
fun Float.dp(view: View): Int {
    return dpToPx(view.context)
}

/**
 * 为View添加px转dp的扩展函数，方便在View对象上直接调用
 */
fun Int.px(view: View): Float {
    return pxToDp(view.context)
}

/**
 * 为View添加px转dp的扩展函数，方便在View对象上直接调用
 */
fun Float.px(view: View): Float {
    return pxToDp(view.context)
}