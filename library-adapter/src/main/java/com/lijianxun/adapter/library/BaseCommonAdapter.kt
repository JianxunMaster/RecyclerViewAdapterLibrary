package com.lijianxun.adapter.library

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.NonNull
import androidx.recyclerview.widget.RecyclerView
import com.lijianxun.adapter.library.delegate.BaseCommonDelegate
import com.lijianxun.adapter.library.delegate.BaseWrapperDelegate
import com.lijianxun.adapter.library.viewholder.BaseViewHolder

/**
 * 通用适配器（多布局）
 *
 * @param <T> 泛型，数据的泛型</T>
 */
open class BaseCommonAdapter<T>(var data: MutableList<T>?) : RecyclerView.Adapter<BaseViewHolder>() {
    var onItemClickListener: ((adapter: BaseCommonAdapter<T>, holder: BaseViewHolder, item: T, position: Int) -> Unit)? = null
    var onItemLongClickListener: ((adapter: BaseCommonAdapter<T>, holder: BaseViewHolder, item: T, position: Int) -> Boolean)? = null

    val dataDelegateList: MutableList<BaseCommonDelegate<T, BaseViewHolder>> = mutableListOf() // 所有布局类型
    val headerDelegateList: MutableList<BaseWrapperDelegate<T, BaseViewHolder>> = mutableListOf() // 头部布局类型
    val footerDelegateList: MutableList<BaseWrapperDelegate<T, BaseViewHolder>> = mutableListOf() // 尾部布局类型
    var emptyDelegate: BaseWrapperDelegate<T, BaseViewHolder>? = null // 空布局类型
    val emptyData = mutableListOf<T>()//空数据

    open fun setList(data: MutableList<T>?) {
        this.data = data
        notifyDataSetChanged()
    }

    open fun getContentData(): MutableList<T> {
        return data ?: emptyData
    }

    override fun onCreateViewHolder(@NonNull parent: ViewGroup, viewType: Int): BaseViewHolder {
        emptyDelegate?.let {
            if (it.getViewType() == viewType) {
                return it.getViewHolder(LayoutInflater.from(parent.context).inflate(it.getLayoutId(), parent, false))
            }
        }
        headerDelegateList.firstOrNull { it.getViewType() == viewType }?.let {
            return it.getViewHolder(LayoutInflater.from(parent.context).inflate(it.getLayoutId(), parent, false))
        }
        dataDelegateList.firstOrNull { it.getViewType() == viewType }?.let {
            return it.getViewHolder(LayoutInflater.from(parent.context).inflate(it.getLayoutId(), parent, false))
        }
        footerDelegateList.firstOrNull { it.getViewType() == viewType }?.let {
            return it.getViewHolder(LayoutInflater.from(parent.context).inflate(it.getLayoutId(), parent, false))
        }
        throw java.lang.IllegalArgumentException("未找到 viewType = $viewType 对应的 ViewHolder")
    }

    override fun onBindViewHolder(@NonNull holder: BaseViewHolder, position: Int) {
        when {
            isHeader(position) -> getHeaderDelegate(position).convert(this, holder)
            isFooter(position) -> getFooterDelegate(position).convert(this, holder)
            isEmpty(position) -> getEmptyDelegate(position).convert(this, holder)
            isData(position) -> {
                val p = position - getHeaderSize()
                val item = getContentData()[p]
                onItemClickListener?.let { listener ->
                    holder.itemView.setOnClickListener {
                        listener.invoke(this, holder, item, p)
                    }
                }
                onItemLongClickListener?.let { listener ->
                    holder.itemView.setOnLongClickListener {
                        listener.invoke(this, holder, item, p)
                    }
                }
                getDataDelegate(position).convert(this, holder, item, p)
            }
            else -> throw IllegalArgumentException("找不到 position = $position 匹配的类型")
        }
    }

    override fun getItemCount(): Int {
        return if (isRealDataEmpty()) {
            (if (isShowHeaderEmpty) getHeaderSize() else 0) + getEmptySize() + (if (isShowFooterEmpty) getFooterSize() else 0)
        } else {
            getHeaderSize() + getRealDataSize() + getFooterSize()
        }
    }

    override fun getItemViewType(position: Int): Int {
        return when {
            isHeader(position) -> getHeaderDelegate(position).getViewType()
            isFooter(position) -> getFooterDelegate(position).getViewType()
            isEmpty(position) -> getEmptyDelegate(position).getViewType()
            isData(position) -> getDataDelegate(position).getViewType()
            else -> throw IllegalArgumentException("找不到匹配类型")
        }
    }

    /**
     * 添加视图样式
     *
     * @param delegate 样式
     */
    fun <V : BaseViewHolder> addItemDelegate(delegate: BaseCommonDelegate<T, V>): BaseCommonAdapter<T> {
        dataDelegateList.add(delegate as BaseCommonDelegate<T, BaseViewHolder>)
        return this
    }

    /**
     * 移除视图样式
     *
     * @param delegate 样式
     */
    fun <V : BaseViewHolder> removeItemDelegate(delegate: BaseWrapperDelegate<T, V>): BaseCommonAdapter<T> {
        dataDelegateList.remove(delegate)
        return this
    }

    fun <V : BaseViewHolder> addHeaderDelegate(delegate: BaseWrapperDelegate<T, V>): BaseCommonAdapter<T> {
        headerDelegateList.add(delegate as BaseWrapperDelegate<T, BaseViewHolder>)
        return this
    }

    fun <V : BaseViewHolder> removeHeaderDelegate(delegate: BaseWrapperDelegate<T, V>): BaseCommonAdapter<T> {
        headerDelegateList.remove(delegate)
        return this
    }

    fun <V : BaseViewHolder> addFooterDelegate(delegate: BaseWrapperDelegate<T, V>): BaseCommonAdapter<T> {
        footerDelegateList.add(delegate as BaseWrapperDelegate<T, BaseViewHolder>)
        return this
    }

    fun <V : BaseViewHolder> removeFooterDelegate(delegate: BaseWrapperDelegate<T, V>): BaseCommonAdapter<T> {
        footerDelegateList.remove(delegate)
        return this
    }

    fun <V : BaseViewHolder> setEmptyDelegate(delegate: BaseWrapperDelegate<T, V>?): BaseCommonAdapter<T> {
        emptyDelegate = delegate as? BaseWrapperDelegate<T, BaseViewHolder>
        return this
    }

    var isShowEmpty = true//显示空布局
    var isShowHeaderEmpty = false//同时显示头部和空布局
    var isShowFooterEmpty = false//同时显示尾部和空布局

    fun getHeaderSize(): Int {
        return headerDelegateList.size
    }

    fun getFooterSize(): Int {
        return footerDelegateList.size
    }

    fun getEmptySize(): Int {
        return if (emptyDelegate != null && isShowEmpty) 1 else 0
    }

    fun getRealDataSize(): Int {
        return getContentData().size
    }

    fun isRealDataEmpty(): Boolean {
        return getContentData().isEmpty()
    }

    fun isHeader(position: Int): Boolean {
        return if (isRealDataEmpty()) {
            if (isShowHeaderEmpty) {
                0 <= position && position < getHeaderSize()
            } else {
                false
            }
        } else {
            0 <= position && position < getHeaderSize()
        }
    }

    fun getHeaderDelegate(position: Int): BaseWrapperDelegate<T, BaseViewHolder> {
        return if (isRealDataEmpty()) {
            if (isShowHeaderEmpty) {
                headerDelegateList[position]
            } else {
                throw IllegalArgumentException("找不到头部delegate")
            }
        } else {
            headerDelegateList[position]
        }
    }

    fun isFooter(position: Int): Boolean {
        return if (isRealDataEmpty()) {
            if (isShowFooterEmpty) {
                if (isShowHeaderEmpty) {
                    getHeaderSize() + getEmptySize() <= position && position < getHeaderSize() + getEmptySize() + getFooterSize()
                } else {
                    getEmptySize() <= position && position < getEmptySize() + getFooterSize()
                }
            } else {
                false
            }
        } else {
            getHeaderSize() + getRealDataSize() <= position && position < getHeaderSize() + getRealDataSize() + getFooterSize()
        }
    }

    fun getFooterDelegate(position: Int): BaseWrapperDelegate<T, BaseViewHolder> {
        return if (isRealDataEmpty()) {
            if (isShowFooterEmpty) {
                if (isShowHeaderEmpty) {
                    footerDelegateList[position - getHeaderSize() - getEmptySize()]
                } else {
                    footerDelegateList[position - getEmptySize()]
                }
            } else {
                throw IllegalArgumentException("找不到尾部delegate")
            }
        } else {
            footerDelegateList[position - getHeaderSize() - getRealDataSize()]
        }
    }

    fun isEmpty(position: Int): Boolean {
        return if (isRealDataEmpty()) {
            if (isShowHeaderEmpty) {
                getHeaderSize() <= position && position < getHeaderSize() + getEmptySize()
            } else {
                0 <= position && position < getEmptySize()
            }
        } else {
            false
        }
    }

    fun getEmptyDelegate(position: Int): BaseWrapperDelegate<T, BaseViewHolder> {
        return if (isRealDataEmpty()) {
            if (isShowHeaderEmpty) {
                if (getHeaderSize() <= position && position < getHeaderSize() + getEmptySize()) {
                    emptyDelegate!!
                } else {
                    throw IllegalArgumentException("找不到空部delegate")
                }
            } else {
                if (0 <= position && position < getEmptySize()) {
                    emptyDelegate!!
                } else {
                    throw IllegalArgumentException("找不到空部delegate")
                }
            }
        } else {
            throw IllegalArgumentException("找不到空部delegate")
        }
    }

    fun isData(position: Int): Boolean {
        return if (isRealDataEmpty()) {
            false
        } else {
            getHeaderSize() <= position && position < getHeaderSize() + getRealDataSize()
        }
    }

    fun getDataDelegate(position: Int): BaseCommonDelegate<T, BaseViewHolder> {
        return if (isRealDataEmpty()) {
            throw IllegalArgumentException("找不到空部delegate")
        } else {
            val item = getContentData()[position - getHeaderSize()]
            dataDelegateList.first { it.isViewType(item, position - getHeaderSize()) }
        }
    }
}