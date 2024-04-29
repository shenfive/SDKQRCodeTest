package com.example.mqtt

import android.content.res.Resources
import android.graphics.Bitmap
import com.google.zxing.BarcodeFormat
import com.google.zxing.EncodeHintType
import com.google.zxing.MultiFormatWriter
import com.google.zxing.WriterException
import com.google.zxing.common.BitMatrix
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel

public class MQtest(key:String) {

    var apiKey = key

    fun generateQRCode(text: String): Bitmap? {
        val screenWidth = Resources.getSystem().displayMetrics.widthPixels / 2
        val width = screenWidth
        val height = screenWidth

        val bitMatrix: BitMatrix
        try {
            val hints = mapOf(
                EncodeHintType.ERROR_CORRECTION to ErrorCorrectionLevel.H,  // 設定容錯率為最高（30%）
                EncodeHintType.CHARACTER_SET to "UTF-8"  // 設定字元集為 UTF-8
            )
            bitMatrix = MultiFormatWriter().encode(
                apiKey + text,
                BarcodeFormat.QR_CODE,
                width, height, hints
            )
        } catch (Illegalargumentexception: IllegalArgumentException) {
            return null
        } catch (e: WriterException) {
            return null
        }

        val pixels = IntArray(width * height)
        for (y in 0 until height) {
            val offset = y * width
            for (x in 0 until width) {
                pixels[offset + x] = if (bitMatrix[x, y]) -0x1000000 else -0x1
            }
        }
        val bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)
        bitmap.setPixels(pixels, 0, width, 0, 0, width, height)
        return bitmap
    }
}
