/*
 * ******************************************************************************
 *  Copyright Ⓒ 2016. TrinhQuan. All right reserved
 *  Author: TrinhQuan. Created on 2016/12/20
 *  Contact: trinhquan.171093@gmail.com
 * ******************************************************************************
 */

package com.tq.klibs.adapter

import android.content.Context
import com.tq.klibs.exception.RangeException
import java.util.*

abstract class ArrayRecyclerAdapter<VH, DATA>(context: Context?)
: BaseRecyclerViewAdapter<VH, DATA>(context)
where VH : BaseRecyclerViewAdapter.BaseViewHolder<DATA> {

    private val mData = ArrayList<DATA>()

    open fun addData(data: DATA) {
        val count = itemCount
        mData.add(data)
        notifyItemInserted(count)
    }

    open fun addData(data: Collection<DATA>) {
        val original = itemCount
        mData.addAll(data)
        notifyItemRangeInserted(original, data.size)
    }

    open fun setData(data: DATA) {
        clear()
        addData(data)
    }

    open fun setData(datas: Collection<DATA>) {
        clear()
        addData(datas)
    }

    private fun clear() {
        val count = itemCount
        mData.clear()
        notifyItemRangeRemoved(0, count)
    }

    fun <T : MutableList<DATA>> getData(des: T): T {
        Collections.copy(des, mData)
        return des
    }

    val data: MutableList<DATA>
        get() {
            return getData<MutableList<DATA>>(ArrayList<DATA>(itemCount))
        }

    val dataByReference: MutableList<DATA>
        get() = mData

    override fun release() {
        super.release()
        mData.clear()
    }

    override fun getItemCount(): Int {
        return mData.size
    }

    override fun getItemAtPosition(position: Int): DATA? {
        if (position < 0 || position >= itemCount) {
            throw RangeException()
        }
        return mData[position]
    }
}
