package com.example.myapplication.activity

import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myapplication.R
import com.example.myapplication.base.BaseRecyclerViewActivity
import com.example.myapplication.data.createTestNodeData
import com.lijianxun.adapter.library.node.Node
import com.lijianxun.adapter.library.state.SelectState
import com.lijianxun.adapter.library.SimpleNodeViewAdapter
import com.lijianxun.adapter.library.viewholder.BaseViewHolder

/**
 * 节点适配器（view 单布局）
 */
class SimpleNodeViewAdapterActivity : BaseRecyclerViewActivity() {
    override fun initView() {
        dataBinding.recyclerView.adapter = object : SimpleNodeViewAdapter<String>(R.layout.item_test_1, createTestNodeData()) {
            override fun convert(adapter: SimpleNodeViewAdapter<String>, holder: BaseViewHolder, item: Node<String>, position: Int) {
                holder.itemView.findViewById<LinearLayout>(R.id.testLayout).setPadding(item.getLevel() * 50, 0, 0, 0)
                holder.itemView.findViewById<ImageView>(R.id.iv_select).setImageResource(when (item.getSelectState()) {
                    SelectState.ALL -> R.drawable.ic_baseline_check_box_24
                    SelectState.NONE -> R.drawable.ic_baseline_check_box_outline_blank_24
                    SelectState.PART -> R.drawable.ic_baseline_indeterminate_check_box_24
                })
                holder.itemView.findViewById<ImageView>(R.id.iv_open).setImageResource(when {
                    item.isLeaf() -> 0
                    item.opened -> R.drawable.ic_baseline_arrow_drop_down_24
                    else -> R.drawable.ic_baseline_arrow_right_24
                })
                holder.itemView.findViewById<TextView>(R.id.textTv).text = item.data
                holder.itemView.findViewById<ImageView>(R.id.iv_select).setOnClickListener {
                    item.changeSelectStatePart2All2None()
                    adapter.notifyDataSetChanged()
                }
                holder.itemView.setOnClickListener { adapter.toggleOpenOrCloseNode(item) }
            }
        }
        dataBinding.recyclerView.layoutManager = LinearLayoutManager(this)
    }
}