package cn.today.jetpack.db

import androidx.room.Database
import androidx.room.RoomDatabase
import cn.today.jetpack.entity.ReceivedEvent
import cn.today.jetpack.entity.Repo

@Database(entities = [ReceivedEvent::class, Repo::class], version = 1)
abstract class UserDatabase : RoomDatabase() {

    abstract fun userReceivedEventDao(): UserReceivedEventDao

    abstract fun userReposDao(): UserReposDao
}