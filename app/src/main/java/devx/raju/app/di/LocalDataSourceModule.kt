package devx.raju.app.di

import androidx.room.Room
import devx.raju.app.data.roomdb.UsersDB
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val localDataSourceModule = module {
    single<UsersDB> { Room.databaseBuilder(
        androidContext().applicationContext,
        UsersDB::class.java,
        "users-db"
    )
        .allowMainThreadQueries().build() }

}
