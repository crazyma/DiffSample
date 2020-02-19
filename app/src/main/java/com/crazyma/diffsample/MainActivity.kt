package com.crazyma.diffsample

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setupRecyclerView()
        populateData2()
    }

    fun buttonClicked(v: View) {
        when (v.id) {
            R.id.addButton -> {
                insertFirstItem()
            }
            R.id.removeButton -> {
                removeFirstItem()
            }
        }
    }

    private fun setupRecyclerView() {
        recyclerView.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(this@MainActivity)
            adapter = MyAdapter()
            getDecoration(this)?.let { addItemDecoration(it) }
        }
    }

    private fun getDecoration(recyclerView: RecyclerView): RecyclerView.ItemDecoration? {
        return (recyclerView.layoutManager as? LinearLayoutManager)?.run {
            DividerItemDecoration(this@MainActivity, this.orientation)
        }
    }


    /**
     * 1. create 1 cell
     * 2. create several cells and insert to position 0 sequentially
     */
    private fun populateData1() {
        GlobalScope.launch(Dispatchers.IO) {
            val list = mutableListOf<MyAdapter.Item>().apply {
                for (i in 0 until 1) {
                    add(MyAdapter.BigItem(i, "Created in first loop"))
                }
            }

            withContext(Dispatchers.Main) {
                (recyclerView.adapter as MyAdapter).items = list
            }

            delay(2000)

            val list2 = mutableListOf<MyAdapter.Item>().apply {
                list.forEach {
                    add(it)
                }
                for (i in 100 until 120) {
                    add(0, MyAdapter.ShortItem(i, "Created in second loop"))
                }
            }

            withContext(Dispatchers.Main) {
                Toast.makeText(this@MainActivity, "updated!", Toast.LENGTH_LONG).show()
                (recyclerView.adapter as MyAdapter).items = list2
            }

        }
    }

    /**
     * 1. create 50 cells
     * 2. create several cells insert after first cell on odd positions
     * 3. create several cells insert before first cell
     */
    private fun populateData2() {
        GlobalScope.launch(Dispatchers.IO) {
            val list = mutableListOf<MyAdapter.Item>().apply {
                for (i in 0 until 50) {
                    add(MyAdapter.BigItem(i, "Created in first loop"))
                }
            }

            withContext(Dispatchers.Main) {
                (recyclerView.adapter as MyAdapter).items = list
            }

            delay(2000)

            val list2 = mutableListOf<MyAdapter.Item>().apply {
                list.forEach {
                    add(it)
                }

                var x = 1
                for (i in 100 until 105) {
                    val randomPosition = x
                    add(randomPosition, MyAdapter.ShortItem(i, "Created in second loop"))
                    x += 2
                }

                for (i in 200 until 205) {
                    add(0, MyAdapter.ShortItem(i, "Created in second loop"))
                }
            }

            withContext(Dispatchers.Main) {
                Toast.makeText(this@MainActivity, "updated!", Toast.LENGTH_LONG).show()
                (recyclerView.adapter as MyAdapter).items = list2
            }

        }
    }

    private fun insertFirstItem() {
        GlobalScope.launch(Dispatchers.IO) {
            val list = (recyclerView.adapter as MyAdapter).items ?: return@launch
            val list2 = mutableListOf<MyAdapter.Item>().apply {
                list.forEach {
                    add(it)
                }

                add(0, MyAdapter.ShortItem(1000 + list.size, "Created in second loop"))
            }

            withContext(Dispatchers.Main) {
                Toast.makeText(this@MainActivity, "updated!", Toast.LENGTH_LONG).show()
                (recyclerView.adapter as MyAdapter).items = list2
                recyclerView.scrollToPosition(0)
            }
        }
    }

    private fun removeFirstItem() {
        GlobalScope.launch(Dispatchers.IO) {
            val list = (recyclerView.adapter as MyAdapter).items ?: return@launch
            val list2 = mutableListOf<MyAdapter.Item>().apply {
                list.forEachIndexed { index, item ->
                    if (index != 0) {
                        add(item)
                    }
                }
            }

            withContext(Dispatchers.Main) {
                Toast.makeText(this@MainActivity, "updated!", Toast.LENGTH_LONG).show()
                (recyclerView.adapter as MyAdapter).items = list2
            }
        }
    }


}
