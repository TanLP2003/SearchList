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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        val studentList = getStudentList()
        listResult = findViewById(R.id.listResult)
        searchBox = findViewById(R.id.searchBox)

        if (listResult == null || searchBox == null) {
            Log.e("MainActivity", "ListView hoặc SearchView không được tìm thấy.")
            return
        }

        studentAdapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, studentList)
        listResult?.adapter = studentAdapter

        searchBox?.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                try {
                    val filteredList = if (newText != null && newText.length > 2) {
                        studentList.filter {
                            it.contains(newText, ignoreCase = true)
                        }
                    } else {
                        studentList
                    }
                    studentAdapter.clear()
                    studentAdapter.addAll(filteredList)
                    studentAdapter.notifyDataSetChanged()
                    return true
                }
                catch(e : Exception) {
                    Log.i("ERROR", "${e.message}")
                    return false
                }
            }
        })
    }

    private fun getStudentList(): List<String>{
        return listOf(
            "Nguyen Van A - 20210001",
            "Le Huu B - 20210002",
            "Le Van C - 20210003",
            "Pham Thi D - 20190004",
            "Nguyen Van E - 20210006"
        )
    }
}