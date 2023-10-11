package com.example.project6

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.internal.synchronized

@Database(entities = [Note::class], version = 1)
abstract class NotesDatabase: RoomDatabase() {
    abstract val notesDao: NotesDao
    companion object {
        @Volatile
        private var INSTANCE: NotesDatabase? = null
        @OptIn(InternalCoroutinesApi::class)
        fun getInstance(context: Context): NotesDatabase {
            synchronized(this) {
                var instance = INSTANCE
                if (instance == null) {
                    instance = Room.databaseBuilder(context.applicationContext, NotesDatabase::class.java, "notes_database").build()
                    INSTANCE = instance
                }
                return instance
            }
        }
    }
}