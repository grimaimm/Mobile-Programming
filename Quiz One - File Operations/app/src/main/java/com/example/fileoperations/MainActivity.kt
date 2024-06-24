package com.example.fileoperations

import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import com.example.fileoperations.ui.theme.QuizOneFileOperationsTheme
import java.io.*

// Fungsi utama MainActivity yang dijalankan saat aplikasi dibuka
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            QuizOneFileOperationsTheme {
                // Container permukaan menggunakan warna latar belakang dari tema
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    // Memanggil fungsi FileOperationsScreen untuk menampilkan UI
                    FileOperationsScreen(this)
                }
            }
        }
    }
}

// Fungsi composable untuk operasi file dan tampilan UI
@Composable
fun FileOperationsScreen(context: Context) {
    var fileContent by remember { mutableStateOf("") }

    // LaunchedEffect untuk menjalankan operasi file satu kali saat composable dimuat
    LaunchedEffect(Unit) {
        // Data yang akan ditulis ke file
        val data = "Quiz 1 Pemrograman Seluler\nMuhammad Rahim\n21.83.0643"
        // Memanggil fungsi untuk menulis data ke file
        writeToFile(context, "example.txt", data)
        // Membaca data dari file dan menyimpan hasilnya ke variabel fileContent
        fileContent = readFromFile(context, "example.txt")
    }

    // Menampilkan data di tengah layar menggunakan Box dan Column
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier.fillMaxSize()
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Memisahkan dan menampilkan setiap baris teks dari fileContent
            fileContent.split('\n').forEach { line ->
                Text(text = line, fontSize = 24.sp)
            }
        }
    }
}

// Fungsi untuk menulis data ke file
fun writeToFile(context: Context, fileName: String, data: String) {
    val fileOutputStream: FileOutputStream
    try {
        // Membuka file output stream dalam mode private
        fileOutputStream = context.openFileOutput(fileName, Context.MODE_PRIVATE)
        // Menulis data ke file
        fileOutputStream.write(data.toByteArray())
        // Menutup file output stream
        fileOutputStream.close()
    } catch (e: Exception) {
        e.printStackTrace()
    }
}

// Fungsi untuk membaca data dari file
fun readFromFile(context: Context, fileName: String): String {
    val stringBuilder = StringBuilder()
    try {
        // Membuka file input stream
        val fileInputStream = context.openFileInput(fileName)
        val inputStreamReader = InputStreamReader(fileInputStream)
        val bufferedReader = BufferedReader(inputStreamReader)
        var text: String?

        // Membaca setiap baris teks dari file dan menambahkannya ke stringBuilder
        while (bufferedReader.readLine().also { text = it } != null) {
            stringBuilder.append(text).append("\n")
        }
    } catch (e: Exception) {
        e.printStackTrace()
    }
    // Mengembalikan string hasil pembacaan, menghapus spasi di akhir
    return stringBuilder.toString().trim()
}

// Fungsi untuk pratinjau tampilan di Android Studio
@Preview(showBackground = true)
@Composable
fun FileOperationsScreenPreview() {
    QuizOneFileOperationsTheme {
        // Pratinjau tidak akan menampilkan file yang dibaca, jadi hanya menunjukkan teks placeholder
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier.fillMaxSize()
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(text = "Quiz 1 Pemrograman Seluler", fontSize = 24.sp)
                Text(text = "Muhammad Rahim 21.83.0643", fontSize = 24.sp)
                Text(text = "21.83.0643", fontSize = 24.sp)
            }
        }
    }
}
