package devx.raju.app.data.roomdb

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import kotlin.coroutines.coroutineContext


@Database(entities = [(Users::class)], version = 1, exportSchema = false)
abstract class UsersDB : RoomDatabase() {
    init {
        println("initializedddd")
    }
//
//    private var INSTANCE: UsersDB? = null
//    fun getDataBase(context: Context): UsersDB {
//        if (INSTANCE == null) {
//            INSTANCE = Room.databaseBuilder(
//                context.applicationContext,
//                UsersDB::class.java,
//                "users-db"
//            )
//                .allowMainThreadQueries().build()
//        }
//        return INSTANCE as UsersDB
//    }
//
    abstract fun usersDao(): UsersDao
//
//    onc
}