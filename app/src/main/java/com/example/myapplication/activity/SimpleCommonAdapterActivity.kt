package com.example.myapplication.activity

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myapplication.R
import com.example.myapplication.base.BaseRecyclerViewActivity
import com.example.myapplication.data.createTestData
import com.example.myapplication.viewholder.MyViewHolder
import com.lijianxun.adapter.library.SimpleCommonAdapter

/**
 * 通用适配器（单布局）
 */
class SimpleCommonAdapterActivity : BaseRecyclerViewActivity() {
    override fun initView() {
        dataBinding.recyclerView.adapter = object : SimpleCommonAdapter<String, MyViewHolder>(R.layout.item_test_1, createTestData()) {
            override fun getViewHolder(itemView: View): MyViewHolder {
                return MyViewHolder(itemView)
            }

            override fun convert(adapter: SimpleCommonAdapter<String, MyViewHolder>, holder: MyViewHolder, item: String, position: Int) {
                holder.getView<TextView>(R.id.textTv)?.text = item
            }
        }
        dataBinding.recyclerView.layoutManager = LinearLayoutManager(this)
    }
}