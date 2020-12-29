package devx.raju.app.ui.recordlist
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import devx.raju.app.R
import devx.raju.app.data.roomdb.Users


class RecordRecyclerAdapter(users: ArrayList<Users>, listener: OnItemClickListener) :
    RecyclerView.Adapter<RecordRecyclerAdapter.RecyclerViewHolder>() {

    private var listUsers: List<Users> = users

    private var listenerRecord: OnItemClickListener = listener

    interface OnItemClickListener {
        fun onItemClick(users: Users)
    }

    override fun getItemCount(): Int {
        return listUsers.size
    }


    fun updateUI(listUsers: List<Users>) {
        this.listUsers = listUsers
        notifyDataSetChanged()
    }


    class RecyclerViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var mName = itemView.findViewById<TextView>(R.id.name_record)!!
        var mNumber = itemView.findViewById<TextView>(R.id.number_record)!!
        var mBook = itemView.findViewById<TextView>(R.id.book_record)!!

        fun bind(users: Users, listener: OnItemClickListener) {
            itemView.setOnClickListener {
                listener.onItemClick(users)
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerViewHolder {
        return RecyclerViewHolder(
            LayoutInflater.from(parent!!.context)
                .inflate(R.layout.item_record_list, parent, false)
        )
    }

    override fun onBindViewHolder(holder: RecyclerViewHolder, position: Int) {
        var currentUsers: Users = listUsers[position]

        var nameRecord = currentUsers.name
        var numberRecord = currentUsers.number
        var bookRecord = currentUsers.bookName

        holder!!.mName.text = nameRecord
        holder!!.mNumber.text = numberRecord
        holder!!.mBook.text = bookRecord

        holder.bind(currentUsers, listenerRecord)
    }
}