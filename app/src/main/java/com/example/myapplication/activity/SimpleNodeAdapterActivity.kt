package com.example.myapplication.activity

import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myapplication.R
import com.example.myapplication.base.BaseRecyclerViewActivity
import com.example.myapplication.data.createTestNodeData
import com.example.myapplication.viewholder.MyViewHolder
import com.lijianxun.adapter.library.node.Node
import com.lijianxun.adapter.library.state.SelectState
import com.lijianxun.adapter.library.SimpleNodeAdapter

/**
 * 节点适配器（单布局）
 */
class SimpleNodeAdapterActivity : BaseRecyclerViewActivity() {
    override fun initView() {
        dataBinding.recyclerView.adapter = object : SimpleNodeAdapter<String, MyViewHolder>(R.layout.item_test_1, createTestNodeData()) {
            override fun getViewHolder(itemView: View): MyViewHolder {
                return MyViewHolder(itemView)
            }

            override fun convert(adapter: SimpleNodeAdapter<String, MyViewHolder>, holder: MyViewHolder, item: Node<String>, position: Int) {
                holder.getView<LinearLayout>(R.id.testLayout)?.setPadding(item.getLevel() * 50, 0, 0, 0)
                holder.getView<ImageView>(R.id.iv_select)?.setImageResource(when (item.getSelectState()) {
                    SelectState.ALL -> R.drawable.ic_baseline_check_box_24
                    SelectState.NONE -> R.drawable.ic_baseline_check_box_outline_blank_24
                    SelectState.PART -> R.drawable.ic_baseline_indeterminate_check_box_24
                })
                holder.getView<ImageView>(R.id.iv_open)?.setImageResource(when {
                    item.isLeaf() -> 0
                    item.opened -> R.drawable.ic_baseline_arrow_drop_down_24
                    else -> R.drawable.ic_baseline_arrow_right_24
                })
                holder.getView<ImageView>(R.id.iv_select)?.setOnClickListener {
                    item.setSelectStatePart2None2All()
                    adapter.notifyDataSetChanged()
                }
                holder.getView<TextView>(R.id.textTv)?.text = item.data
                holder.itemView.setOnClickListener { adapter.openOrCloseNode(item) }
            }
        }
        dataBinding.recyclerView.layoutManager = LinearLayoutManager(this)
    }
}