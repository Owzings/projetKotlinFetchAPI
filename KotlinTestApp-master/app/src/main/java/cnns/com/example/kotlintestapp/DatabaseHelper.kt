package cnns.com.example.kotlintestapp

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.widget.Toast
import cnns.com.example.kotlintestapp.models.User

val DATABASENAME = "DB_NAME"
val TABLENAME = "TABLE_NAME"
val COL_TITLE = "title"
val COL_DESC = "description"
val COL_ID = "id"

class DataBaseHandler(var context: Context) : SQLiteOpenHelper(context, DATABASENAME, null,
    1) {
    override fun onCreate(db: SQLiteDatabase?) {
        val createTable = "CREATE TABLE " + TABLENAME + " (" + COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + COL_TITLE + " VARCHAR(256)," + COL_DESC + " INTEGER)"
        db?.execSQL(createTable)
    }
    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        //onCreate(db);
    }
    fun insertData(item: ExampleItem) {
        val database = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put(COL_TITLE, item.text1)
        contentValues.put(COL_DESC, item.text2)
        val result = database.insert(TABLENAME, null, contentValues)
        if (result == (0).toLong()) {
            Toast.makeText(context, "Echec d'ajout", Toast.LENGTH_SHORT).show()
        }
        else {
            Toast.makeText(context, "Bien ajouté dans la BD", Toast.LENGTH_SHORT).show()
        }
    }

    fun readData(limitMax: Int) : MutableList<ExampleItem> {
        val list: MutableList<ExampleItem> = ArrayList()
        val db = this.readableDatabase
        val query = "Select * from $TABLENAME LIMIT $limitMax"
        val result = db.rawQuery(query, null)
        if (result.moveToFirst()) {
            do {
                val item = ExampleItem(0 , "", "", 0)
                item.id = result.getString(result.getColumnIndex(COL_ID)).toInt()
                item.text1 = result.getString(result.getColumnIndex(COL_TITLE))
                item.text2 = result.getString(result.getColumnIndex(COL_DESC))
                list.add(item)
            }
            while (result.moveToNext())
        }
        return list
    }
}