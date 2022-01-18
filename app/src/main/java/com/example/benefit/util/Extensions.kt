package com.example.benefit.util

import android.content.ContentResolver
import android.content.Context
import android.graphics.Bitmap
import android.net.Uri
import android.provider.OpenableColumns
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.ImageView
import androidx.annotation.DrawableRes
import androidx.appcompat.app.AlertDialog
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.Target.SIZE_ORIGINAL
import java.io.ByteArrayOutputStream


/**
 * Created by jahon on 22-May-20
 */


fun Bitmap.toByteArray(quality: Int): ByteArray = run {
    val stream = ByteArrayOutputStream()
    this.compress(Bitmap.CompressFormat.JPEG, quality, stream)
    stream.toByteArray()
}


fun Any.getString(context: Context): String = when (this) {
    is String -> this
    is CharSequence -> this.toString()
    is Int -> context.resources.getString(this)
    else -> throw Exception("text type not found")
}


fun Context.showMessageDialog(
    title: Any,
    message: Any,
    negativBtnText: Any? = "",
    negativBtnAction: (() -> Unit)? = null,
    positiveBtnText: Any? = "",
    positiveBtnAction: (() -> Unit)? = null
) {
    val builder = AlertDialog.Builder(this)
        .setTitle(title.getString(this))
        .setMessage(message.getString(this))

    if (negativBtnText != null)
        builder.setNegativeButton(
            negativBtnText.getString(this)
        ) { p0, p1 ->
            negativBtnAction?.invoke()
        }
    if (positiveBtnText != null)
        builder.setPositiveButton(
            positiveBtnText.getString(this)
        ) { p0, p1 ->
            positiveBtnAction?.invoke()
        }

    builder.show()

}

fun ImageView.loadImageUrl(url: String) {
    val circularProgressDrawable = CircularProgressDrawable(this.context)
    circularProgressDrawable.strokeWidth = 5f
    circularProgressDrawable.centerRadius = 30f
    circularProgressDrawable.start()

    Glide.with(this.context).load(url)/*.placeholder(circularProgressDrawable)*/
        .apply(RequestOptions().centerInside()).into(this)
}

fun ImageView.loadImageUrlAndShrink(url: String) {
    val circularProgressDrawable = CircularProgressDrawable(this.context)
    circularProgressDrawable.strokeWidth = 5f
    circularProgressDrawable.centerRadius = 30f
    circularProgressDrawable.start()

    Glide.with(this.context).load(url)/*.placeholder(circularProgressDrawable)*/
        .override(300, SIZE_ORIGINAL)
        .apply(RequestOptions().centerInside()).into(this)
}

fun ImageView.loadCircleImageUrl(url: String?) {
    if (url == null) return
    Glide.with(this.context).load(url).apply(RequestOptions().circleCrop()).into(this)
}

fun ImageView.loadCircleBitmap(bitmap: Bitmap) {
    Glide.with(this.context).load(bitmap).apply(RequestOptions().circleCrop()).into(this)
}

fun ImageView.loadDrawableCircleCrop(@DrawableRes drawable: Int) {
    Glide.with(this.context).load(drawable).apply(RequestOptions().circleCrop()).into(this)
}

fun ImageView.loadBitmap(bitmap: Bitmap) {
    Glide.with(this.context).load(bitmap).into(this)
}

fun View.hideKeyboard() {
    val imm = this.context!!.getSystemService(Context.INPUT_METHOD_SERVICE)!! as InputMethodManager
    imm.hideSoftInputFromWindow(this.windowToken, 0)
}

fun View.showKeyboard() {
    val imm = this.context!!.getSystemService(Context.INPUT_METHOD_SERVICE)!! as InputMethodManager
    imm.toggleSoftInputFromWindow(this.applicationWindowToken, InputMethodManager.SHOW_FORCED, 0)
}

fun ContentResolver.getFileName(fileUri: Uri): String {
    var name = ""
    val returnCursor = this.query(fileUri, null, null, null, null)
    if (returnCursor != null) {
        val nameIndex = returnCursor.getColumnIndex(OpenableColumns.DISPLAY_NAME)
        returnCursor.moveToFirst()
        name = returnCursor.getString(nameIndex)
        returnCursor.close()
    }
    return name
}
//
//fun View.updateNextButtonState(enabled: Boolean) {
//    this.isEnabled = enabled
//
//    if (this.isEnabled) {
//        val bg = this.background
//        bg.setColorFilter(ContextCompat.getColor(this.context, R.color.colorAccent),
//                          PorterDuff.Mode.SRC_ATOP)
//        this.background = bg
//    } else {
//        val bg = this.background
//        bg.setColorFilter(ContextCompat.getColor(this.context, R.color.lightGreen),
//                          PorterDuff.Mode.SRC_ATOP)
//        this.background = bg
//    }
//}
//
//
//fun TextView.updateReadyTextViewState(enabled: Boolean) {
//    this.isEnabled = enabled
//    if (this.isEnabled) {
//        this.setTextColor(ContextCompat.getColor(this.context!!, R.color.colorAccent))
//    } else {
//        this.setTextColor(ContextCompat.getColor(this.context!!, R.color.text_grey_medium))
//    }
//}

val <T> T.exhaustive: T
    get() = this

fun String.numericOnly(): String {
    return Regex("[^0-9]").replace(this, "")
}


 fun String.isNumeric(): Boolean {
    return this.matches("-?\\d+(\\.\\d+)?".toRegex())
}


///**
// * Extension function to simplify setting an afterTextChanged action to EditText components.
// */
//fun EditText.afterTextChanged(afterTextChanged: (String) -> Unit) {
//    this.addTextChangedListener(object : TextWatcher {
//        override fun afterTextChanged(editable: Editable?) {
//            afterTextChanged.invoke(editable.toString())
//        }
//
//        override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
//
//        override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}
//    })
//}

