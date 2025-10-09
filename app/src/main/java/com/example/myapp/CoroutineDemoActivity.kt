package com.example.myapp

import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.myapp.utils.dpToPx
import kotlinx.coroutines.*
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

class CoroutineDemoActivity : AppCompatActivity() {

    private val TAG = "CoroutineDemoActivity"
    private lateinit var resultTextView: TextView
    private lateinit var startButton: Button
    private var job: Job? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // 创建简单布局
        val layout = androidx.appcompat.widget.LinearLayoutCompat(this).apply {
            orientation = androidx.appcompat.widget.LinearLayoutCompat.VERTICAL
            setPadding(32.dpToPx(), 32.dpToPx(), 32.dpToPx(), 32.dpToPx())
        }

        // 标题
        val title = TextView(this).apply {
            text = "协程测试页面"
            textSize = 20f
            setPadding(0, 0, 0, 32.dpToPx())
        }
        layout.addView(title)

        // 结果显示区域
        resultTextView = TextView(this).apply {
            text = "点击开始测试查看结果..."
            textSize = 16f
            setPadding(0, 0, 0, 32.dpToPx())
        }
        layout.addView(resultTextView)

        // 开始按钮
        startButton = Button(this).apply {
            text = "开始协程测试"
            setOnClickListener { startCoroutineDemo() }
        }
        layout.addView(startButton)

        setContentView(layout)
    }

    override fun onDestroy() {
        super.onDestroy()
        job?.cancel()
    }

    private fun startCoroutineDemo() {
        job = CoroutineScope(Dispatchers.Main).launch {
            try {
                resultTextView.text = "协程测试开始..."

                // 演示1: 将传统接口回调转换为suspend函数
                val result1 = suspendCoroutineDemo()
                resultTextView.text = "回调转换结果: $result1"

                delay(1000)

                // 演示2: 异步网络请求模拟
                val result2 = simulateNetworkRequest()
                resultTextView.text = "网络请求结果: $result2"

                delay(1000)

                // 演示3: 并发执行多个任务
                val results = executeConcurrentTasks()
                resultTextView.text = "并发任务结果: ${results.joinToString()}"

                delay(1000)

                // 演示4: 异常处理
                val safeResult = executeWithExceptionHandling()
                resultTextView.text = "异常处理结果: $safeResult"

            } catch (e: Exception) {
                resultTextView.text = "测试出错: ${e.message}"
                Log.e(TAG, "协程测试异常", e)
            }
        }
    }

    /**
     * 演示1: 将传统接口回调转换为suspend函数
     * 传统接口方式:
     * interface Callback {
     *     fun onSuccess(result: String)
     *     fun onError(error: Throwable)
     * }
     *
     * fun doAsyncWork(callback: Callback) {
     *     // 异步执行...
     * }
     */
    private suspend fun suspendCoroutineDemo(): String = suspendCoroutine { continuation ->
        // 模拟传统回调接口的异步操作
        CoroutineScope(Dispatchers.IO).launch {
            try {
                delay(500) // 模拟异步操作延迟
                val result = "异步操作完成 - ${System.currentTimeMillis()}"

                // 传统回调方式: callback.onSuccess(result)
                // 转换为suspend方式: 恢复协程
                continuation.resume(result)

            } catch (e: Exception) {
                // 传统回调方式: callback.onError(e)
                // suspend方式: 恢复异常
                continuation.resumeWithException(e)
            }
        }
    }

    /**
     * 演示2: 模拟网络请求
     */
    private suspend fun simulateNetworkRequest(): String = withContext(Dispatchers.IO) {
        delay(800) // 模拟网络延迟
        "网络数据加载完成 - ${System.currentTimeMillis()}"
    }

    /**
     * 演示3: 并发执行多个任务
     */
    private suspend fun executeConcurrentTasks(): List<String> = coroutineScope {
        val task1 = async {
            delay(300)
            "任务1完成"
        }
        val task2 = async {
            delay(500)
            "任务2完成"
        }
        val task3 = async {
            delay(200)
            "任务3完成"
        }

        listOf(task1.await(), task2.await(), task3.await())
    }

    /**
     * 演示4: 异常处理演示
     */
    private suspend fun executeWithExceptionHandling(): String = try {
        // 模拟可能失败的操作
        if (System.currentTimeMillis() % 2 == 0L) {
            throw RuntimeException("模拟异常情况")
        }
        "操作成功完成"
    } catch (e: Exception) {
        "操作失败: ${e.message}"
    }
}

/**
 * 传统接口回调方式的示例（用于对比）
 */
interface TraditionalCallback {
    fun onSuccess(result: String)
    fun onError(error: Throwable)
}

/**
 * 传统方式的异步操作（对比用）
 */
class TraditionalAsyncWorker {
    fun doAsyncWork(callback: TraditionalCallback) {
        Thread {
            try {
                Thread.sleep(500)
                callback.onSuccess("传统回调结果")
            } catch (e: Exception) {
                callback.onError(e)
            }
        }.start()
    }
}