package devx.raju.app.data.roomdb
import androidx.lifecycle.LiveData
import androidx.room.*


@Dao
interface UsersDao {
    @Query("select * from users")
    fun getAllUsers(): LiveData<List<Users>>

    @Query("select * from users where idUser in (:id)")
    fun getUsersById(id: Int): Users

    @Query("delete from users")
    fun deleteAllUsers()

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertUser(users: Users)

    @Update
    fun updateUser(users: Users)

    @Delete
    fun deleteUser(users: Users)
}