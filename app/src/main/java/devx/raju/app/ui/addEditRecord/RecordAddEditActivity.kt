package devx.raju.app.ui.addEditRecord

import android.os.Bundle
import android.text.TextUtils
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import devx.raju.app.R
import devx.raju.app.ui.recordlist.RecordListViewModel
import devx.raju.app.data.roomdb.Users
import devx.raju.app.data.roomdb.UsersDB
import devx.raju.app.data.roomdb.UsersDao
import kotlinx.android.synthetic.main.activity_record_add_edit.*
import org.koin.android.ext.android.get
import org.koin.android.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf


class RecordAddEditActivity : AppCompatActivity() {
    private var usersDao: UsersDao? = null

    //    private var viewModel: RecordListViewModel? = null
    var bookList = arrayListOf<String>()
    private val viewModel: RecordListViewModel by viewModel() { parametersOf(application, db) }

    private var currentUser: Int? = null
    private var users: Users? = null
    val db: UsersDB by lazy { get<UsersDB>() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_record_add_edit)

        usersDao = db.usersDao()
        bookList = arrayListOf<String>()
        bookList.add("Book A")
        bookList.add("Book B")
        bookList.add("Book C")

//        viewModel = ViewModelProviders.of(this).get(RecordListViewModel::class.java)
        currentUser = intent.getIntExtra("idRecord", -1)
        if (currentUser != -1) {
            setTitle(R.string.edit_record_title)
            users = usersDao!!.getUsersById(currentUser!!)
            nameEditText.setText(users!!.name)
            mobileNoEditText.setText(users!!.number)
            bookSpinner.setSelection(bookList.indexOf(users!!.bookName) + 1)
        } else {
            setTitle(R.string.add_record_title)
            invalidateOptionsMenu()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        var inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.menu_items, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item!!.itemId) {
            R.id.done_item -> {
                if (currentUser == -1) {
                    if (isValidInput()) {
                        saveRecord()
                        finish()
                    }
                } else {
                    if (isValidInput()) {
                        updateRecord()
                        finish()
                    }
                }

            }
            R.id.delete_item -> {
                deleteRecord()
                finish()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onPrepareOptionsMenu(menu: Menu): Boolean {
        super.onPrepareOptionsMenu(menu)
        if (currentUser == -1) {
            menu.findItem(R.id.delete_item).isVisible = false
        }
        return true
    }

    private fun saveRecord() {
        val nameRecord = nameEditText.text.toString()
        val numberRecord = mobileNoEditText.text.toString()
        val bookNameRecord = bookSpinner.selectedItem.toString()
        val record = Users(0, nameRecord, numberRecord, bookNameRecord)
        viewModel!!.addRecord(record)
        Toast.makeText(this, getString(R.string.recordSaved), Toast.LENGTH_SHORT)
            .show()
    }

    private fun deleteRecord() {
        usersDao!!.deleteUser(users!!)
        Toast.makeText(this, getString(R.string.userRecordDeleted), Toast.LENGTH_SHORT)
            .show()
    }

    fun isValidInput(): Boolean {
        var flag = true
        if (TextUtils.isEmpty(nameEditText.text.toString())) {
            Toast.makeText(this, getString(R.string.enterName), Toast.LENGTH_SHORT).show()
            flag = false
        } else if (TextUtils.isEmpty(mobileNoEditText.text.toString())) {
            Toast.makeText(this, getString(R.string.enterMobileNumber), Toast.LENGTH_SHORT).show()
            flag = false
        } else if (mobileNoEditText.text.toString().length != 10) {
            Toast.makeText(this, getString(R.string.enterValidMobileNumber), Toast.LENGTH_SHORT)
                .show()
            flag = false
        } else if (!TextUtils.isEmpty(bookSpinner.selectedItem.toString()) &&
            bookSpinner.selectedItem.toString().equals(getString(R.string.selectBook))
        ) {
            Toast.makeText(this, getString(R.string.selectBook), Toast.LENGTH_SHORT).show()
            flag = false
        }
        return flag
    }

    private fun updateRecord() {
        val userName = nameEditText.text.toString()
        val userNo = mobileNoEditText.text.toString()
        val userBook = bookSpinner.selectedItem.toString()
        val user = Users(users!!.id, userName, userNo, userBook)
        usersDao!!.updateUser(user)
        Toast.makeText(this, getString(R.string.recordUpdated), Toast.LENGTH_SHORT)
            .show()
    }
}
