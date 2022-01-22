package com.example.benefit.util

import android.content.Context
import android.content.Intent
import android.content.res.AssetManager
import android.net.Uri
import android.os.Build
import android.util.Log
import androidx.core.content.FileProvider
import com.example.benefit.BuildConfig
import java.io.File
import java.io.IOException
import java.io.InputStream
import java.io.OutputStream

object PDFHelper {


    fun makeIntentFromPdfAsset(context: Context, fileName: String): Intent {
        val assetManager: AssetManager = context.assets
        val input: InputStream?
        val out: OutputStream?
        val file = File(context.filesDir, fileName)
        try {
            input = assetManager.open(fileName)
            out = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                context.openFileOutput(file.name, Context.MODE_PRIVATE)
            } else {
                context.openFileOutput(file.name, Context.MODE_WORLD_READABLE)
            }
            copyFile(input, out)
            input.close()
            out.flush()
            out.close()
        } catch (e: Exception) {
            Log.e("tag", e.message ?: "")
        }
        val pdfFileURI: Uri
        val intent = Intent(Intent.ACTION_VIEW)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            pdfFileURI = FileProvider.getUriForFile(
                context,
                BuildConfig.APPLICATION_ID + ".fileprovider",
                file
            )
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
        } else {
            pdfFileURI = Uri.parse("file://" + context.filesDir + "/" + fileName)
        }
        intent.setDataAndType(pdfFileURI, "application/pdf")
        return intent
    }


    @Throws(IOException::class)
    private fun copyFile(input: InputStream?, out: OutputStream?) {
        val buffer = ByteArray(1024)
        var read = 0
        while (input!!.read(buffer).also { read = it } != -1) {
            out!!.write(buffer, 0, read)
        }
    }
}
