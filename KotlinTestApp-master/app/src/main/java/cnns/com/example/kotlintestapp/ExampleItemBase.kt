package cnns.com.example.kotlintestapp

import android.provider.BaseColumns

object ExampleItemBase {
        // Table contents are grouped together in an anonymous object.
        object FeedEntry : BaseColumns {
            const val TABLE_NAME = "item"
            const val id = 0
            const val COLUMN_NAME_TITLE = "title"
            const val COLUMN_NAME_SUBTITLE = "subtitle"
        }
}