package com.example.myapplication.activity

import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myapplication.R
import com.example.myapplication.base.BaseRecyclerViewActivity
import com.example.myapplication.data.createTestNodeData
import com.example.myapplication.databinding.ItemTest1Binding
import com.lijianxun.adapter.library.node.Node
import com.lijianxun.adapter.library.state.SelectState
import com.lijianxun.adapter.library.SimpleNodeBinderAdapter
import com.lijianxun.adapter.library.viewholder.BaseBinderHolder

/**
 * 节点适配器（dataBinding 单布局）
 */
class SimpleNodeBinderAdapterActivity : BaseRecyclerViewActivity() {
    override fun initView() {
        dataBinding.recyclerView.adapter = object : SimpleNodeBinderAdapter<String, ItemTest1Binding>(R.layout.item_test_1, createTestNodeData()) {
            override fun convert(adapter: SimpleNodeBinderAdapter<String, ItemTest1Binding>, holder: BaseBinderHolder<ItemTest1Binding>, item: Node<String>, position: Int) {
                holder.dataBinding.testLayout.setPadding(item.getLevel() * 50, 0, 0, 0)
                holder.dataBinding.ivSelect.setImageResource(when (item.getSelectState()) {
                    SelectState.ALL -> R.drawable.ic_baseline_check_box_24
                    SelectState.NONE -> R.drawable.ic_baseline_check_box_outline_blank_24
                    SelectState.PART -> R.drawable.ic_baseline_indeterminate_check_box_24
                })
                holder.dataBinding.ivOpen.setImageResource(when {
                    item.isLeaf() -> 0
                    item.opened -> R.drawable.ic_baseline_arrow_drop_down_24
                    else -> R.drawable.ic_baseline_arrow_right_24
                })
                holder.dataBinding.ivSelect.setOnClickListener {
                    item.setSelectStatePart2None2All()
                    adapter.notifyDataSetChanged()
                }
                holder.dataBinding.textTv.text = item.data
                holder.itemView.setOnClickListener { adapter.openOrCloseNode(item) }
            }
        }
        dataBinding.recyclerView.layoutManager = LinearLayoutManager(this)
    }
}