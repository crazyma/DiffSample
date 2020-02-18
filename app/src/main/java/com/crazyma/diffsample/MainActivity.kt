package com.crazyma.diffsample

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setupRecyclerView()
        populateData()
    }

    private fun setupRecyclerView() {
        recyclerView.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(this@MainActivity)
            adapter = MyAdapter()
        }
    }

    private fun populateData() {
        GlobalScope.launch(Dispatchers.IO) {
            val list = mutableListOf<MyAdapter.Item>().apply {
                for (i in 0 until 1) {
                    add(MyAdapter.Item(i, "Created in first loop"))
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
                    add(0, MyAdapter.Item(i, "Created in second loop"))
                }
//                for(i in 200 until 203){
//                    add(15, MyAdapter.Item(i, "Created in second loop"))
//                }
            }

            withContext(Dispatchers.Main) {
                Toast.makeText(this@MainActivity, "updated!", Toast.LENGTH_LONG).show()
                (recyclerView.adapter as MyAdapter).items = list2
            }

        }
    }
}
