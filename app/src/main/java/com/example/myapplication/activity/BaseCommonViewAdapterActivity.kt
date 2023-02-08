package com.example.myapplication.activity

import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myapplication.R
import com.example.myapplication.base.BaseRecyclerViewActivity
import com.example.myapplication.data.createTestData
import com.lijianxun.adapter.library.BaseCommonAdapter
import com.lijianxun.adapter.library.delegate.CommonViewDelegate
import com.lijianxun.adapter.library.viewholder.BaseViewHolder

/**
 * 通用适配器（view 多布局）
 */
class BaseCommonViewAdapterActivity : BaseRecyclerViewActivity() {
    override fun initView() {
        val adapter = BaseCommonAdapter(createTestData())
        adapter.addItemDelegate(object : CommonViewDelegate<String> {
            override fun getLayoutId(): Int {
                return R.layout.item_test_1
            }

            override fun isViewType(item: String, position: Int): Boolean {
                return position % 3 == 0
            }

            override fun convert(adapter: BaseCommonAdapter<String>, holder: BaseViewHolder, item: String, position: Int) {
                holder.itemView.findViewById<TextView>(R.id.textTv).text = item
            }
        })
        adapter.addItemDelegate(object : CommonViewDelegate<String> {
            override fun getLayoutId(): Int {
                return R.layout.item_test_2
            }

            override fun isViewType(item: String, position: Int): Boolean {
                return position % 3 == 1
            }

            override fun convert(adapter: BaseCommonAdapter<String>, holder: BaseViewHolder, item: String, position: Int) {
                holder.itemView.findViewById<TextView>(R.id.textTv).text = item
            }
        })
        adapter.addItemDelegate(object : CommonViewDelegate<String> {
            override fun getLayoutId(): Int {
                return R.layout.item_test_3
            }

            override fun isViewType(item: String, position: Int): Boolean {
                return position % 3 == 2
            }

            override fun convert(adapter: BaseCommonAdapter<String>, holder: BaseViewHolder, item: String, position: Int) {
                holder.itemView.findViewById<TextView>(R.id.textTv).text = item
            }
        })
        dataBinding.recyclerView.adapter = adapter
        dataBinding.recyclerView.layoutManager = LinearLayoutManager(this)
    }
}