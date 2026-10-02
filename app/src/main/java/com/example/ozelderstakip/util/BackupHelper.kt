package com.example.ozelderstakip.util

import android.content.Context
import android.os.Environment
import android.widget.Toast
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream

object BackupHelper {
    
    // Veritabanı dosyasının adı AppDatabase.kt içerisinde belirtildiği gibi olmalı.
    private const val DB_NAME = "ozel_ders_takip_database"

    fun exportDatabase(context: Context) {
        try {
            val dbFile = context.getDatabasePath(DB_NAME)
            val exportDir = File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS), "OzelDersTakipYedek")
            
            if (!exportDir.exists()) {
                exportDir.mkdirs()
            }

            val backupFile = File(exportDir, "${DB_NAME}_backup.db")
            
            FileInputStream(dbFile).use { input ->
                FileOutputStream(backupFile).use { output ->
                    input.copyTo(output)
                }
            }
            Toast.makeText(context, "Yedek başarıyla alındı: ${backupFile.absolutePath}", Toast.LENGTH_LONG).show()
        } catch (e: Exception) {
            e.printStackTrace()
            Toast.makeText(context, "Yedekleme başarısız!", Toast.LENGTH_SHORT).show()
        }
    }

    // İhtiyaç dahilinde importDatabase fonksiyonu da buraya eklenebilir.
}
