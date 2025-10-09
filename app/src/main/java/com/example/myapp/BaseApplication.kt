package com.example.myapp

import android.app.Application
import android.content.Context

/**
 * Application基类，作为单例提供全局的ApplicationContext
 */
class BaseApplication : Application() {

    companion object {
        /**
         * 全局ApplicationContext单例
         */
        lateinit var context: Context
            private set

        /**
         * 初始化标志，确保Application被正确初始化
         */
        var isInitialized = false
            private set
    }

    override fun onCreate() {
        super.onCreate()
        context = applicationContext
        isInitialized = true
    }
}