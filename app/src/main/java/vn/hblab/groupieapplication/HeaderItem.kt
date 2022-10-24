package vn.hblab.groupieapplication

import android.view.View
import com.xwray.groupie.viewbinding.BindableItem
import vn.hblab.groupieapplication.databinding.ItemTypeBinding

class HeaderItem(val type: String) : BindableItem<ItemTypeBinding>() {
    override fun bind(viewBinding: ItemTypeBinding, position: Int) {
        viewBinding.textView.text = type
    }

    override fun getLayout(): Int {
        return R.layout.item_type
    }

    override fun initializeViewBinding(view: View): ItemTypeBinding {
        return ItemTypeBinding.bind(view)
    }
}