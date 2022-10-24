package vn.hblab.groupieapplication

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.xwray.groupie.Group
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.GroupieViewHolder
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import vn.hblab.groupieapplication.databinding.ActivityMainBinding
import java.text.SimpleDateFormat
import java.util.Calendar

class MainActivity : AppCompatActivity(), ListListener, View.OnClickListener {
    private val groupAdapter = GroupAdapter<GroupieViewHolder>()
    private lateinit var listListener: ListListener
    private lateinit var recyclerView: RecyclerView
    private var isLoading: Boolean = false

    companion object {
        private const val FORMAT_DATE = "dd/MM/yyyy HH:mm:ss"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding: ActivityMainBinding =
            DataBindingUtil.setContentView(this, R.layout.activity_main)

        val toolbar = binding.toolbar
        this.setSupportActionBar(toolbar)

        recyclerView = binding.rcvList
        listListener = this
        recyclerView.setup()
        recyclerView.apply {
            val linearLayoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
            layoutManager = linearLayoutManager
        }
        groupAdapter.update(createData())

        binding.buttonAdd.setOnClickListener(this)
    }

    private fun createData(): MutableList<Group> {
        val dataItem = mutableListOf<Group>()
        for (i in 0..100) {
            if (i == 0) {
                dataItem.add(HeaderItem("${getString(R.string.tx_old)} - ${getContent(true)}"))
            }
            val title = getString(R.string.title, i + 1)
            dataItem.add(SampleItem(SampleModel(title, getContent(), i)))
        }
        return dataItem
    }

    private fun RecyclerView.setup() {
        adapter = groupAdapter.apply {
            setOnItemClickListener { item, _ ->
                (item as? SampleItem).let {
                    if (it != null) {
                        listListener.onClickItem(it.data)
                    }
                }
            }
        }
    }

    override fun onClickItem(item: SampleModel) {
        Toast.makeText(
            applicationContext,
            "${item.title}\n${item.content}",
            Toast.LENGTH_SHORT
        ).show()
    }

    override fun onClick(v: View?) {
        val title = getString(R.string.title, groupAdapter.itemCount + 1)
        val loadingItem = LoadingItem()
        GlobalScope.launch(Dispatchers.Main) {
            if (!isLoading) {
                isLoading = true
                groupAdapter.add(loadingItem)
                recyclerView.smoothScrollToPosition(groupAdapter.itemCount)
                delay(2000)
                groupAdapter.add(HeaderItem("New data - ${getContent(true)}"))
                for (position in 0 until groupAdapter.itemCount) {
                    if (groupAdapter.getItem(position) == loadingItem) {
                        groupAdapter.remove(loadingItem)
                        break
                    }
                }
                groupAdapter.add(SampleItem(SampleModel(title, getContent(), groupAdapter.itemCount)))
                recyclerView.smoothScrollToPosition(groupAdapter.itemCount)
                isLoading = false
            } else {
                Toast.makeText(
                    applicationContext,
                    getString(R.string.tx_loading),
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

    @SuppressLint("SimpleDateFormat")
    private fun getContent(isHeader: Boolean = false): String {
        val simpleDateFormat = SimpleDateFormat(FORMAT_DATE)
        return if (isHeader) {
            simpleDateFormat.format(Calendar.getInstance().time)
        } else {
            getString(R.string.content, simpleDateFormat.format(Calendar.getInstance().time))
        }
    }
}