package com.md12.rio.bangkitstory.widget

import android.content.Context
import android.widget.RemoteViews
import android.widget.RemoteViewsService
import androidx.room.Room
import com.md12.rio.bangkitstory.R
import com.md12.rio.bangkitstory.data.local.StoryAppDatabase
import com.md12.rio.bangkitstory.data.local.entity.StoryEntity
import com.md12.rio.bangkitstory.utils.ConstVal.DB_NAME
import com.md12.rio.bangkitstory.utils.urlToBitmap

internal class StackRemoteViewsFactory(private val mContext: Context) : RemoteViewsService.RemoteViewsFactory {

    private var stories: MutableList<StoryEntity> = mutableListOf()

    override fun onCreate() {
    }

    override fun onDataSetChanged() {
        val database = Room.databaseBuilder(
            mContext.applicationContext, StoryAppDatabase::class.java,
            DB_NAME
        ).build()
        database.getStoryDao().getAllStories().forEach {
            stories.add(
                StoryEntity(
                    it.id,
                    it.photoUrl
                )
            )
        }
    }

    override fun onDestroy() {
    }

    override fun getCount(): Int = stories.size

    override fun getViewAt(position: Int): RemoteViews {
        val rv = RemoteViews(mContext.packageName, R.layout.widget_item)
        rv.setImageViewBitmap(R.id.imageView, urlToBitmap(stories[position].photoUrl))

        return rv
    }

    override fun getLoadingView(): RemoteViews? = null

    override fun getViewTypeCount(): Int = 1

    override fun getItemId(i: Int): Long = 0

    override fun hasStableIds(): Boolean = false
}