package vn.hblab.groupieapplication

import android.view.View
import com.xwray.groupie.viewbinding.BindableItem
import vn.hblab.groupieapplication.databinding.ItemSampleBinding

class SampleItem(val data: SampleModel) : BindableItem<ItemSampleBinding>() {
    override fun bind(viewBinding: ItemSampleBinding, position: Int) {
        viewBinding.content.text = data.content
        viewBinding.title.text = data.title
    }

    override fun getLayout(): Int {
        return R.layout.item_sample
    }

    override fun initializeViewBinding(view: View): ItemSampleBinding {
        return ItemSampleBinding.bind(view)
    }

}