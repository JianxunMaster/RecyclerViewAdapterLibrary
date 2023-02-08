package com.example.myapplication.activity

import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myapplication.R
import com.example.myapplication.base.BaseRecyclerViewActivity
import com.example.myapplication.data.createTestData
import com.example.myapplication.databinding.ItemTest1Binding
import com.example.myapplication.databinding.ItemTest2Binding
import com.example.myapplication.databinding.ItemTest3Binding
import com.lijianxun.adapter.library.BaseCommonAdapter
import com.lijianxun.adapter.library.delegate.CommonBinderDelegate
import com.lijianxun.adapter.library.viewholder.BaseBinderHolder

/**
 * 通用适配器（dataBinding 多布局）
 */
class BaseCommonBinderAdapterActivity : BaseRecyclerViewActivity() {
    override fun initView() {
        val adapter = BaseCommonAdapter(createTestData())
        adapter.addViewDelegate(object : CommonBinderDelegate<String, ItemTest1Binding> {
            override fun getLayoutId(): Int {
                return R.layout.item_test_1
            }

            override fun isViewType(item: String, position: Int): Boolean {
                return position % 3 == 0
            }

            override fun convert(adapter: BaseCommonAdapter<String>, holder: BaseBinderHolder<ItemTest1Binding>, item: String, position: Int) {
                holder.dataBinding.textTv.text = item
            }
        })
        adapter.addViewDelegate(object : CommonBinderDelegate<String, ItemTest2Binding> {
            override fun getLayoutId(): Int {
                return R.layout.item_test_2
            }

            override fun isViewType(item: String, position: Int): Boolean {
                return position % 3 == 1
            }

            override fun convert(adapter: BaseCommonAdapter<String>, holder: BaseBinderHolder<ItemTest2Binding>, item: String, position: Int) {
                holder.dataBinding.textTv.text = item
            }
        })
        adapter.addViewDelegate(object : CommonBinderDelegate<String, ItemTest3Binding> {
            override fun getLayoutId(): Int {
                return R.layout.item_test_3
            }

            override fun isViewType(item: String, position: Int): Boolean {
                return position % 3 == 2
            }

            override fun convert(adapter: BaseCommonAdapter<String>, holder: BaseBinderHolder<ItemTest3Binding>, item: String, position: Int) {
                holder.dataBinding.textTv.text = item
            }
        })
        dataBinding.recyclerView.adapter = adapter
        dataBinding.recyclerView.layoutManager = LinearLayoutManager(this)
    }
}