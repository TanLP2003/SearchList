package com.example.searchlist

import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.SearchView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {
    var listResult: ListView? = null
    var searchBox: SearchView? = null
    private lateinit var studentAdapter: ArrayAdapter<String>
    private lateinit var originalList: MutableList<String>
    private lateinit var filteredList: MutableList<String>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        originalList = getStudentList()
        filteredList = originalList.toMutableList()

        listResult = findViewById(R.id.listResult)
        searchBox = findViewById(R.id.searchBox)

        studentAdapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, filteredList)
        listResult?.adapter = studentAdapter

        searchBox?.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                try {
                    filteredList.clear()

                    if (newText.isNullOrEmpty() || newText.length <= 2) {
                        filteredList.addAll(originalList)
                    } else {
                        filteredList.addAll(originalList.filter {
                            it.contains(newText, ignoreCase = true)
                        })
                    }

                    studentAdapter.notifyDataSetChanged()
                    return true
                } catch (e: Exception) {
                    Log.e("Search", "Error filtering list", e)
                    return false
                }
            }
        })
    }

    private fun getStudentList(): MutableList<String>{
        return mutableListOf(
            "Nguyen Van A - 20210001",
            "Le Huu B - 20210002",
            "Le Van C - 20210003",
            "Pham Thi D - 20190004",
            "Nguyen Van E - 20210006"
        )
    }
}