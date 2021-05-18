package cnns.com.example.kotlintestapp

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import kotlin.random.Random


class MainActivity : AppCompatActivity(), ExampleAdapter.OnItemClickListener {
    private val exampleList = generateDummyList(500)
    private val adapter = ExampleAdapter(exampleList, this)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        recycler_view.adapter = adapter
        recycler_view.layoutManager = LinearLayoutManager(this)
        recycler_view.setHasFixedSize(true)
        startProperly();
    }
    fun startProperly(){
        val newItem = ExampleItem(
            R.drawable.ic_android,
            "Départ",
            "Ceci est un élément template, cliquez sur fetcher !"
        )
        exampleList.add(0, newItem)
        adapter.notifyItemInserted(0)
    }
    fun insertItem(view: View) {
        val index = Random.nextInt(8)
        val newItem = ExampleItem(
            R.drawable.ic_android,
            "New item at position $index",
            "Line 2"
        )
        exampleList.add(index, newItem)
        adapter.notifyItemInserted(index)
    }
    fun fetcher(view: View) {
        val index = Random.nextInt(8)
        val newItem = ExampleItem(
            R.drawable.ic_android,
            "New item at position $index",
            "En savoir plus sur les fetchs ?"
        )
        exampleList.add(index, newItem)
        adapter.notifyItemInserted(index)
    }
    fun removeItem(view: View) {
        val index = Random.nextInt(8)
        exampleList.removeAt(index)
        adapter.notifyItemRemoved(index)
    }
    override fun onItemClick(position: Int) {
        Toast.makeText(this, "Item $position clicked", Toast.LENGTH_SHORT).show()
        val clickedItem = exampleList[position]
        adapter.notifyItemChanged(position)
        val intent = Intent(this, DetailElement::class.java)
        intent.putExtra("Name", clickedItem.text1)
        clickedItem.text1 = "Clicked"
        startActivity(intent)
    }
    private fun generateDummyList(size: Int): ArrayList<ExampleItem> {

        val list = ArrayList<ExampleItem>()
        for (i in 0 until size) {
            val drawable = when (i % 3) {
                0 -> R.drawable.ic_android
                1 -> R.drawable.ic_audio
                else -> R.drawable.ic_sun
            }
            GlobalScope.launch(Dispatchers.Main) {
                try {
                    val response = ApiClient.apiService.getPostById(i)

                    if (response.isSuccessful && response.body() != null) {
                        val content = response.body()
                        println(content)
                        val item = ExampleItem(drawable, "Item $i", content?.title)
                        list += item

                        //do something
                    } else {
//                        Toast.makeText(
//                            this@MainActivity,
//                            "Error Occurred: ${response.message()}",
//                            Toast.LENGTH_LONG
//                        ).show()
                    }

                } catch (e: Exception) {
//                    Toast.makeText(
//                        this@MainActivity,
//                        "Error Occurred: ${e.message}",
//                        Toast.LENGTH_LONG
//                    ).show()
                }
            }
        }

        return list
    }

}