package com.example.ozelderstakip.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.ozelderstakip.data.local.dao.LessonDao
import com.example.ozelderstakip.data.local.dao.PaymentDao
import com.example.ozelderstakip.data.local.dao.StudentDao
import com.example.ozelderstakip.data.local.dao.TemplateDao
import com.example.ozelderstakip.data.local.entity.LessonEntity
import com.example.ozelderstakip.data.local.entity.PaymentEntity
import com.example.ozelderstakip.data.local.entity.StudentEntity
import com.example.ozelderstakip.data.local.entity.TemplateEntity

@Database(
    entities = [
        StudentEntity::class,
        LessonEntity::class,
        PaymentEntity::class,
        TemplateEntity::class
    ],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun studentDao(): StudentDao
    abstract fun lessonDao(): LessonDao
    abstract fun paymentDao(): PaymentDao
    abstract fun templateDao(): TemplateDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "ozel_ders_takip_database"
                )
                .fallbackToDestructiveMigration() // Geliştirme aşamasında kolaylık için
                .build()
                INSTANCE = instance
                instance
            }
        }
    }
}
