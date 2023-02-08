package com.example.myapplication.activity

import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myapplication.R
import com.example.myapplication.base.BaseRecyclerViewActivity
import com.example.myapplication.data.createTestData
import com.lijianxun.adapter.library.SimpleCommonViewAdapter
import com.lijianxun.adapter.library.viewholder.BaseViewHolder

/**
 * 通用适配器（view 单布局）
 */
class SimpleCommonViewAdapterActivity : BaseRecyclerViewActivity() {
    override fun initView() {
        dataBinding.recyclerView.adapter = object : SimpleCommonViewAdapter<String>(R.layout.item_test_1, createTestData()) {
            override fun convert(adapter: SimpleCommonViewAdapter<String>, holder: BaseViewHolder, item: String, position: Int) {
                holder.itemView.findViewById<TextView>(R.id.textTv).text = item
            }
        }
        dataBinding.recyclerView.layoutManager = LinearLayoutManager(this)
    }
}