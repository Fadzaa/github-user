package com.example.github_api.model.local

import android.content.Context
import androidx.room.Database
import androidx.room.*
import androidx.room.Room.databaseBuilder
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase


@Database(entities = [User::class], version = 3)
abstract class UserRoomDatabase : RoomDatabase() {

    abstract fun userDao(): UserDao

    companion object {
        @Volatile
        private var INSTANCE: UserRoomDatabase? = null

        private val MIGRATION_1_2 = object : Migration(1, 2) {
                override fun migrate(db: SupportSQLiteDatabase) {
                db.execSQL("ALTER TABLE user ADD COLUMN avatar TEXT")
            }
        }

        private val MIGRATION_2_3 = object : Migration(2, 3) {
            override fun migrate(db: SupportSQLiteDatabase) {
                db.execSQL("ALTER TABLE user ADD COLUMN followers INTEGER")
                db.execSQL("ALTER TABLE user ADD COLUMN following INTEGER")
                db.execSQL("ALTER TABLE user ADD COLUMN public_repos INTEGER")
            }
        }

        @JvmStatic
        fun getDatabase(context: Context): UserRoomDatabase {
            if (INSTANCE == null) {
                synchronized(UserRoomDatabase::class.java) {
                    INSTANCE = databaseBuilder(context.applicationContext,
                        UserRoomDatabase::class.java, "user_database")
                        .addMigrations(MIGRATION_2_3)
                        .build()
                }
            }
            return INSTANCE as UserRoomDatabase
        }
    }

}