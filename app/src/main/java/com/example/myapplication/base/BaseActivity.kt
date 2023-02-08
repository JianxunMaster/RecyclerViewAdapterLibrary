package com.example.myapplication.base

import android.os.Bundle
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding

/**
 * 基类
 */
abstract class BaseActivity<T : ViewDataBinding> : AppCompatActivity() {
    lateinit var dataBinding: T
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        dataBinding = DataBindingUtil.setContentView(this, getLayoutId())
        initView()
    }

    @LayoutRes
    abstract fun getLayoutId(): Int
    abstract fun initView()
}