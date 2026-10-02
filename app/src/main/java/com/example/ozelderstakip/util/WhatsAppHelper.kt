package com.example.ozelderstakip.util

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.widget.Toast
import java.net.URLEncoder

object WhatsAppHelper {
    fun sendMessage(context: Context, phoneNumber: String, message: String) {
        try {
            // Telefon numarasının başındaki sıfırları veya artıları temizle/düzenle
            val formattedNumber = if (phoneNumber.startsWith("+")) {
                phoneNumber.replace("+", "")
            } else if (phoneNumber.startsWith("0")) {
                "90" + phoneNumber.substring(1)
            } else {
                "90$phoneNumber" // Default Turkey code for simplicity in this example
            }

            val url = "https://api.whatsapp.com/send?phone=$formattedNumber&text=${URLEncoder.encode(message, "UTF-8")}"
            val intent = Intent(Intent.ACTION_VIEW).apply {
                data = Uri.parse(url)
            }
            context.startActivity(intent)
        } catch (e: Exception) {
            Toast.makeText(context, "WhatsApp açılamadı.", Toast.LENGTH_SHORT).show()
        }
    }
}
