package com.example.myapplication.activity

import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myapplication.R
import com.example.myapplication.base.BaseRecyclerViewActivity
import com.example.myapplication.data.createTestData
import com.example.myapplication.databinding.ItemTest1Binding
import com.lijianxun.adapter.library.SimpleCommonBinderAdapter
import com.lijianxun.adapter.library.viewholder.BaseBinderHolder

/**
 * 通用适配器（dataBinding 单布局）
 */
class SimpleCommonBinderAdapterActivity : BaseRecyclerViewActivity() {
    override fun initView() {
        dataBinding.recyclerView.adapter = object : SimpleCommonBinderAdapter<String, ItemTest1Binding>(R.layout.item_test_1, createTestData()) {
            override fun convert(adapter: SimpleCommonBinderAdapter<String, ItemTest1Binding>, holder: BaseBinderHolder<ItemTest1Binding>, item: String, position: Int) {
                holder.dataBinding.textTv.text = item
            }
        }
        dataBinding.recyclerView.layoutManager = LinearLayoutManager(this)
    }
}