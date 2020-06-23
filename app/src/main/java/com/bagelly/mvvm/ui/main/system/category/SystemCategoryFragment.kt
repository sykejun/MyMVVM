package com.bagelly.mvvm.ui.main.system.category

import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.bagelly.mvvm.App
import com.bagelly.mvvm.R
import com.bagelly.mvvm.model.bean.Category
import com.bagelly.mvvm.ui.main.system.SystemFragment
import com.bagelly.mvvm.util.getScreenHeight
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import kotlinx.android.synthetic.main.fragment_system_category.*
/**
 *
 * @ProjectName: MyMVVM
 * @Package: com.bagelly.mvvm.ui.main.system.category
 * @ClassName: SystemCategoryFragment
 * @Description: java类作用描述
 * @Author: bagelly
 * QQ:774169396
 * @CreateDate: 2020/6/22 下午2:21
 */
class SystemCategoryFragment : BottomSheetDialogFragment() {

    private var height: Int? = null
    private var behavior :BottomSheetBehavior<View>?=null

    companion object {
        const val CATEGROY_LIST = "CATEGROY_LIST"
        fun newInstance(categoryList: ArrayList<Category>): SystemCategoryFragment {
            return SystemCategoryFragment().apply {
                arguments = Bundle().apply {
                    putParcelableArrayList(CATEGROY_LIST, categoryList)
                }
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_system_category,container,false)
    }
    fun show(manger: FragmentManager, height: Int? = null) {
        this.height = height ?: (getScreenHeight(App.instance) * 0.75).toInt()
        if (!this.isAdded) {
            super.show(manger, "SystemCategoryFragment")
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val categoryList:List<Category> = arguments?.getParcelableArrayList(CATEGROY_LIST)!!
        val checked=(parentFragment as SystemFragment).getCurrentChecked()
        SystemCateoryAdapter(R.layout.item_system_category,categoryList,checked).run {
            bindToRecyclerView(recyclerView)
            onCheckedListener={
                behavior?.state=BottomSheetBehavior.STATE_HIDDEN
                view.postDelayed({
                    ( parentFragment as SystemFragment).check(it)
                },300)
            }

            view.post{
                (recyclerView.layoutManager as LinearLayoutManager).scrollToPositionWithOffset(checked.first,0)
            }

        }
    }


    override fun onStart() {
        super.onStart()
        val bootomSheet: View =(dialog as BottomSheetDialog).delegate
            .findViewById(com.google.android.material.R.id.design_bottom_sheet)
            ?: return
        behavior=BottomSheetBehavior.from(bootomSheet)
        height?.let { behavior?.peekHeight=it }
        dialog?.window?.let {
            it.setGravity(Gravity.BOTTOM)
            it.setLayout( ViewGroup.LayoutParams.MATCH_PARENT, height ?: ViewGroup.LayoutParams.MATCH_PARENT)
        }
    }
}