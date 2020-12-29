package devx.raju.app.di

import android.app.Application
import devx.raju.app.ui.recordlist.RecordListViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel {(application:Application) ->
        RecordListViewModel( application, get())
    }
}