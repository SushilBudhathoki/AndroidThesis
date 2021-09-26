package com.xrest.finalassignment.RoomDatabase

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.xrest.finalassignment.Class.Food
import com.xrest.finalassignment.Class.Person
import com.xrest.finalassignment.Models.User

@Database(

        entities = [Person::class, Food::class],
        version = 1,
        exportSchema = true
)
abstract class UserDB : RoomDatabase() {


    abstract fun getDAO(): DAO
    abstract fun getFoodDAO(): FoodDAO

    companion object {

        @Volatile
        var instance: UserDB? = null
        fun getInstance(context: Context): UserDB {
            if (instance == null) {
                synchronized(UserDB::class) {

                    instance = buildContext(context)
                }
            }


            return instance!!
        }

        fun buildContext(context: Context): UserDB {
            return Room.databaseBuilder(context.applicationContext, UserDB::class.java, "Food").build()
        }

    }


}