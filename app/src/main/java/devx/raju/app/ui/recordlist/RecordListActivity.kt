package devx.raju.app.ui.recordlist

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import devx.raju.app.R
import devx.raju.app.data.roomdb.Users
import devx.raju.app.data.roomdb.UsersDB
import devx.raju.app.ui.addEditRecord.RecordAddEditActivity
import kotlinx.android.synthetic.main.activity_record_list.*
import org.koin.android.ext.android.inject
import org.koin.android.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf


class RecordListActivity : AppCompatActivity(),
    RecordRecyclerAdapter.OnItemClickListener {
    private var recyclerViewAdapter: RecordRecyclerAdapter? = null

    private val viewModel: RecordListViewModel by viewModel() { parametersOf(application) }

//    private var db: UsersDB? = null
private val db by inject<UsersDB>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_record_list)
//        db =  get<UsersDB>()

        recordsRecyclerView!!.layoutManager = LinearLayoutManager(this)
        recordsRecyclerView!!.adapter = recyclerViewAdapter
        val dividerItemDecoration = DividerItemDecoration(
            recordsRecyclerView!!.getContext(),
            LinearLayoutManager.VERTICAL
        )
        recordsRecyclerView?.addItemDecoration(dividerItemDecoration)
//        viewModel = ViewModelProviders.of(this).get(RecordListViewModel::class.java)

        viewModel!!.getListRecords().observe(this, Observer { records ->
            records?.let {
                if (recyclerViewAdapter == null) {
                    recyclerViewAdapter =
                        RecordRecyclerAdapter(
                            it as ArrayList<Users>,
                            this@RecordListActivity
                        )
                    recordsRecyclerView!!.adapter = recyclerViewAdapter
                } else {
                    Handler().postDelayed({
                        recyclerViewAdapter?.updateUI(records)
                    }, 500)
                }
            }
        })

        fabButton.setOnClickListener {
            var intent = Intent(applicationContext, RecordAddEditActivity::class.java)
            startActivity(intent)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.delete_all_items -> {
                deleteAllRecords()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun deleteAllRecords() {
        db!!.usersDao().deleteAllUsers()
    }

    override fun onItemClick(users: Users) {
        var intent = Intent(applicationContext, RecordAddEditActivity::class.java)
        intent.putExtra("idRecord", users.id)
        startActivity(intent)
    }

}