package com.example.myapplication.base

import com.example.myapplication.R
import com.example.myapplication.base.BaseActivity
import com.example.myapplication.databinding.ActivityRecyclerViewBinding

abstract class BaseRecyclerViewActivity:BaseActivity<ActivityRecyclerViewBinding>() {
    override fun getLayoutId(): Int {
        return R.layout.activity_recycler_view
    }
}