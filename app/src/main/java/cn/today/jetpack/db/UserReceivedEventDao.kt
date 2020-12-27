package cn.today.jetpack.db

import androidx.annotation.WorkerThread
import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import cn.today.jetpack.entity.ReceivedEvent

@Dao
interface UserReceivedEventDao {

    @WorkerThread
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(receivedEvents: List<ReceivedEvent>)

    @WorkerThread
    @Query("select * from user_received_events order by indexInResponse ASC")
    fun queryEvents(): PagingSource<Int, ReceivedEvent>

    @WorkerThread
    @Query("delete from user_received_events")
    suspend fun clearReceivedEvents()

    @WorkerThread
    @Query("select MAX(indexInResponse) + 1 FROM user_received_events")
    suspend fun getNextIndexInReceivedEvents(): Int?
}