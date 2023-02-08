package com.example.myapplication

import android.content.Intent
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myapplication.activity.*
import com.example.myapplication.base.BaseActivity
import com.example.myapplication.databinding.ActivityMainBinding
import com.example.myapplication.databinding.ItemTest1Binding
import com.lijianxun.adapter.library.SimpleCommonAdapter
import com.lijianxun.adapter.library.viewholder.BaseBinderHolder

/**
 * 首页
 */
class MainActivity : BaseActivity<ActivityMainBinding>() {
    override fun getLayoutId(): Int {
        return R.layout.activity_main
    }

    override fun initView() {
        val data = mutableListOf(
                "演示示例",
                "通用适配器（多布局）",
                "通用适配器（dataBinding多布局）",
                "通用适配器（view多布局）",
                "节点适配器（多布局）",
                "节点适配器（dataBinding多布局）",
                "节点适配器（view多布局）",
                "通用适配器（单布局）",
                "通用适配器（dataBinding单布局）",
                "通用适配器（view单布局）",
                "节点适配器（单布局）",
                "节点适配器（dataBinding单布局）",
                "节点适配器（view单布局）"
        )
        val classes = mutableListOf(
                DemoActivity::class.java,
                BaseCommonAdapterActivity::class.java,
                BaseCommonBinderAdapterActivity::class.java,
                BaseCommonViewAdapterActivity::class.java,
                BaseNodeAdapterActivity::class.java,
                BaseNodeBinderAdapterActivity::class.java,
                BaseNodeViewAdapterActivity::class.java,
                SimpleCommonAdapterActivity::class.java,
                SimpleCommonBinderAdapterActivity::class.java,
                SimpleCommonViewAdapterActivity::class.java,
                SimpleNodeAdapterActivity::class.java,
                SimpleNodeBinderAdapterActivity::class.java,
                SimpleNodeViewAdapterActivity::class.java,
        )
        val mAdapter = object : SimpleCommonAdapter<String, BaseBinderHolder<ItemTest1Binding>>(R.layout.item_test_1, data) {
            override fun getViewHolder(itemView: View): BaseBinderHolder<ItemTest1Binding> {
                return BaseBinderHolder(itemView)
            }

            override fun convert(adapter: SimpleCommonAdapter<String, BaseBinderHolder<ItemTest1Binding>>, holder: BaseBinderHolder<ItemTest1Binding>, item: String, position: Int) {
                holder.dataBinding.textTv.text = item
            }
        }
        mAdapter.onItemClickListener = { adapter, holder, item, position ->
            startActivity(Intent(this, classes[position]))
        }
        dataBinding.recyclerView.adapter = mAdapter
        dataBinding.recyclerView.layoutManager = LinearLayoutManager(this)
    }
}