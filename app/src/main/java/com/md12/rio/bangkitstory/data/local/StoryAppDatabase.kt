package com.md12.rio.bangkitstory.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.md12.rio.bangkitstory.data.local.dao.StoryDao
import com.md12.rio.bangkitstory.data.local.entity.StoryEntity

@Database(
    entities =[StoryEntity::class],
    version = 1,
    exportSchema = false
)
abstract class StoryAppDatabase: RoomDatabase() {

    abstract fun getStoryDao(): StoryDao

}