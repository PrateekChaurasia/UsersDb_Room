package devx.raju.app.ui.recordlist
import android.app.Application
import android.os.AsyncTask
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import devx.raju.app.data.roomdb.Users
import devx.raju.app.data.roomdb.UsersDB


class RecordListViewModel(application: Application, val appDb: UsersDB ) : AndroidViewModel(application) {

    var listUsers: LiveData<List<Users>>

    init {
//        appDb = get<UsersDB>()
        listUsers = appDb.usersDao().getAllUsers()
    }

    fun getListRecords(): LiveData<List<Users>> {
        return listUsers
    }

    fun addRecord(users: Users) {
        addAsynTask(appDb).execute(users)
    }


    class addAsynTask(db: UsersDB) : AsyncTask<Users, Void, Void>() {
        private var recordDb = db
        override fun doInBackground(vararg params: Users): Void? {
            recordDb.usersDao().insertUser(params[0])
            return null
        }

    }

}