package devx.raju.app.base
import android.app.Application
import android.content.Context
import devx.raju.app.di.localDataSourceModule
import devx.raju.app.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

open class DevxRoomApp : Application() {

    companion object {
        lateinit var applicationCtx: Context
    }

    override fun onCreate() {
        super.onCreate()
        applicationCtx = applicationContext
        startKoin {
            androidContext(this@DevxRoomApp)
            modules(localDataSourceModule)
            modules(viewModelModule)
        }
    }

}