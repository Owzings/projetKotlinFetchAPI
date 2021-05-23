package cnns.com.example.kotlintestapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView

class DetailElement : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_element)

        val actionBar = supportActionBar

        actionBar!!.title = "Détail d'un élément"
        actionBar.setDisplayHomeAsUpEnabled(true)

        val intent = intent
        val name = intent.getStringExtra("Name")
        val descriptionTexte = intent.getStringExtra("Description")

        //textview
        val textePrincipal = findViewById<TextView>(R.id.nametext)
        textePrincipal.text = name
        val texteSecondaire = findViewById<TextView>(R.id.nametext2)
        texteSecondaire.text = descriptionTexte

    }
}