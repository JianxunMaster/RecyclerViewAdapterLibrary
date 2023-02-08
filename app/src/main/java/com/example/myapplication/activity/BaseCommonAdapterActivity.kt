package com.example.myapplication.activity

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myapplication.R
import com.example.myapplication.base.BaseRecyclerViewActivity
import com.example.myapplication.data.createTestData
import com.example.myapplication.viewholder.MyViewHolder
import com.lijianxun.adapter.library.BaseCommonAdapter
import com.lijianxun.adapter.library.delegate.BaseCommonDelegate

/**
 * 通用适配器（多布局）
 */
class BaseCommonAdapterActivity : BaseRecyclerViewActivity() {
    override fun initView() {
        val adapter = BaseCommonAdapter(createTestData())
        adapter.addViewDelegate(object : BaseCommonDelegate<String, MyViewHolder> {
            override fun getLayoutId(): Int {
                return R.layout.item_test_1
            }

            override fun isViewType(item: String, position: Int): Boolean {
                return position % 3 == 0
            }

            override fun getViewHolder(itemView: View): MyViewHolder {
                return MyViewHolder(itemView)
            }

            override fun convert(adapter: BaseCommonAdapter<String>, holder: MyViewHolder, item: String, position: Int) {
                holder.getView<TextView>(R.id.textTv)?.text = item
            }
        })
        adapter.addViewDelegate(object : BaseCommonDelegate<String, MyViewHolder> {
            override fun getLayoutId(): Int {
                return R.layout.item_test_2
            }

            override fun isViewType(item: String, position: Int): Boolean {
                return position % 3 == 1
            }

            override fun getViewHolder(itemView: View): MyViewHolder {
                return MyViewHolder(itemView)
            }

            override fun convert(adapter: BaseCommonAdapter<String>, holder: MyViewHolder, item: String, position: Int) {
                holder.getView<TextView>(R.id.textTv)?.text = item
            }
        })
        adapter.addViewDelegate(object : BaseCommonDelegate<String, MyViewHolder> {
            override fun getLayoutId(): Int {
                return R.layout.item_test_3
            }

            override fun isViewType(item: String, position: Int): Boolean {
                return position % 3 == 2
            }

            override fun getViewHolder(itemView: View): MyViewHolder {
                return MyViewHolder(itemView)
            }

            override fun convert(adapter: BaseCommonAdapter<String>, holder: MyViewHolder, item: String, position: Int) {
                holder.getView<TextView>(R.id.textTv)?.text = item
            }
        })
        dataBinding.recyclerView.adapter = adapter
        dataBinding.recyclerView.layoutManager = LinearLayoutManager(this)
    }
}