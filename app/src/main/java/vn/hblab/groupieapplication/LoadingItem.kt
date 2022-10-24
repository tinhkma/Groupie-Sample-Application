package vn.hblab.groupieapplication

import android.view.View
import com.xwray.groupie.viewbinding.BindableItem
import vn.hblab.groupieapplication.databinding.ItemLoadingBinding

class LoadingItem() : BindableItem<ItemLoadingBinding>() {
    override fun bind(viewBinding: ItemLoadingBinding, position: Int) {

    }

    override fun getLayout(): Int {
        return R.layout.item_loading
    }

    override fun initializeViewBinding(view: View): ItemLoadingBinding {
        return ItemLoadingBinding.bind(view)
    }
}