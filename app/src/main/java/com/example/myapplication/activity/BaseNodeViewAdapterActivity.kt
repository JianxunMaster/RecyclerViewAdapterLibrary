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
import com.lijianxun.adapter.library.BaseNodeAdapter
import com.lijianxun.adapter.library.delegate.NodeViewDelegate
import com.lijianxun.adapter.library.viewholder.BaseViewHolder

/**
 * 节点适配器（view 多布局）
 */
class BaseNodeViewAdapterActivity : BaseRecyclerViewActivity() {
    override fun initView() {
        val adapter: BaseNodeAdapter<String> = BaseNodeAdapter(createTestNodeData())
        adapter.addItemDelegate(object : NodeViewDelegate<String> {

            override fun getLayoutId(): Int {
                return R.layout.item_test_1
            }

            override fun isViewType(item: Node<String>, position: Int): Boolean {
                return item.isRoot()
            }

            override fun convert(adapter: BaseNodeAdapter<String>, holder: BaseViewHolder, item: Node<String>, position: Int) {
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
                holder.itemView.findViewById<ImageView>(R.id.iv_select).setOnClickListener {
                    item.changeSelectStatePart2All2None()
                    adapter.notifyDataSetChanged()
                }
                holder.itemView.findViewById<TextView>(R.id.textTv).text = item.data
                holder.itemView.setOnClickListener { adapter.toggleOpenOrCloseNode(item) }
            }
        })
        adapter.addItemDelegate(object : NodeViewDelegate<String> {

            override fun getLayoutId(): Int {
                return R.layout.item_test_2
            }

            override fun isViewType(item: Node<String>, position: Int): Boolean {
                return !item.isRoot() && !item.isLeaf()
            }

            override fun convert(adapter: BaseNodeAdapter<String>, holder: BaseViewHolder, item: Node<String>, position: Int) {
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
        })
        adapter.addItemDelegate(object : NodeViewDelegate<String> {

            override fun getLayoutId(): Int {
                return R.layout.item_test_3
            }

            override fun isViewType(item: Node<String>, position: Int): Boolean {
                return item.isLeaf()
            }

            override fun convert(adapter: BaseNodeAdapter<String>, holder: BaseViewHolder, item: Node<String>, position: Int) {
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
        })
        dataBinding.recyclerView.adapter = adapter
        dataBinding.recyclerView.layoutManager = LinearLayoutManager(this)
    }
}