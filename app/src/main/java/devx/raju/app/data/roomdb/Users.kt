package devx.raju.app.data.roomdb
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "users")
data class Users(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "idUser")
    var id: Int = 0,

    var name: String = "",

    var number: String = "",

    var bookName: String = ""

)