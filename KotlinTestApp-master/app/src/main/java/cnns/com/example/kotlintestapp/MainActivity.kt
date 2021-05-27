package cnns.com.example.kotlintestapp

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.util.*
import kotlin.collections.ArrayList
import kotlin.random.Random


class MainActivity : AppCompatActivity(), ExampleAdapter.OnItemClickListener {
    private val exampleList = generateDummyList(500)
    private val adapter = ExampleAdapter(exampleList, this)


    override fun onCreate(savedInstanceState: Bundle?) {
        //Système de cache
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        recycler_view.adapter = adapter
        recycler_view.layoutManager = LinearLayoutManager(this)
        recycler_view.setHasFixedSize(true)

    }

    public override fun onResume() {

        super.onResume()
        val newItem = ExampleItem(
            R.drawable.ic_android,
            "Cycle de vie",
            "Passage sur resume",
            0
        )
        generateDummyList(500)
        exampleList.add(0, newItem)
        adapter.notifyItemInserted(0)
    }

    fun insertItem(view: View) {
        val index = Random.nextInt(8)
        val newItem = ExampleItem(
            R.drawable.ic_android,
            "New item at position $index",
            "Line 2",
            index
        )
        exampleList.add(index, newItem)
        adapter.notifyItemInserted(index)
    }
    fun fetcher(view: View) {
        val index = Random.nextInt(8)
        val newItem = ExampleItem(
            R.drawable.ic_android,
            "New item at position $index",
            "En savoir plus sur les fetchs ?",
            index
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
        intent.putExtra("Description", clickedItem.text2)

        clickedItem.text1 = "Clicked"
        startActivity(intent)
    }
     fun generateDummyList(size: Int): ArrayList<ExampleItem> {

        val list = ArrayList<ExampleItem>()

         title = "KotlinApp"
         val context = this
         val db = DataBaseHandler(context)
         val item = ExampleItem(0, "Item"," content?.title", 0)

//         db.readData()
//         if(db.readData().size < 2) {
             for ( i in 0 until 50) {
                 val drawable = when (i % 3) {
                     0 -> R.drawable.ic_android
                     1 -> R.drawable.ic_audio
                     else -> R.drawable.ic_sun
                 }
                 GlobalScope.launch(Dispatchers.Main) {
                     println("db.readData(500).size" + db.readData(500).size)
                     println(db.readData(500).size)
                     try {
                         if(db.readData(500).size < 5){

                             val response = ApiClient.apiService.getPostById(i)

                             if (response.isSuccessful && response.body() != null) {
                                 val content = response.body()
                                 //                        println(content)
                                 val item = ExampleItem(drawable, "Item $i", content?.title, 0)
                                 list += item
                                 db.insertData(item)
                                 //do something
                             } else {
                                 //                        Toast.makeText(
                                 //                            this@MainActivity,
                                 //                            "Error Occurred: ${response.message()}",
                                 //                            Toast.LENGTH_LONG
                                 //                        ).show()
                             }
                         } else {
                             val a = db.readData(size-1).get(i)
                             println(a)
                            val item = ExampleItem( R.drawable.ic_android, a.text1, a.text2, a.id)
                             list += item
                             db.insertData(item)
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