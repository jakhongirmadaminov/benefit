package com.example.benefit.util

import android.content.ContentResolver
import android.content.Context
import android.graphics.*
import android.net.Uri
import android.provider.OpenableColumns
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.ImageView
import androidx.annotation.DrawableRes
import androidx.appcompat.app.AlertDialog
import androidx.core.graphics.drawable.RoundedBitmapDrawableFactory
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.BitmapImageViewTarget
import com.bumptech.glide.request.target.Target.SIZE_ORIGINAL
import com.example.benefit.remote.models.ServiceField
import com.example.benefit.ui.viewgroups.ItemLoading
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.kotlinandroidextensions.GroupieViewHolder
import java.io.ByteArrayOutputStream

/**
 * Created by jahon on 22-May-20
 */

fun List<ServiceField>.allFilled(): Boolean {
    return this.find { it.userSelection.isNullOrBlank() } == null
}

fun GroupAdapter<GroupieViewHolder>.setLoadingSpinner() {
    clear()
    add(ItemLoading())
}


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


/**
 * Load model into ImageView as a circle image with borderSize (optional) using Glide
 *
 * @param model - Any object supported by Glide (Uri, File, Bitmap, String, resource id as Int, ByteArray, and Drawable)
 * @param borderSize - The border size in pixel
 * @param borderColor - The border color
 */
fun <T> ImageView.loadCircularImage(
    model: T,
    borderSize: Float = 0F,
    borderColor: Int = Color.WHITE
) {
    Glide.with(context)
        .asBitmap()
        .load(model)
        .apply(RequestOptions.circleCropTransform())
        .into(object : BitmapImageViewTarget(this) {
            override fun setResource(resource: Bitmap?) {
                setImageDrawable(
                    resource?.run {
                        RoundedBitmapDrawableFactory.create(
                            resources,
                            if (borderSize > 0) {
                                createBitmapWithBorder(borderSize, borderColor)
                            } else {
                                this
                            }
                        ).apply {
                            isCircular = true
                        }
                    }
                )
            }
        })
}

/**
 * Create a new bordered bitmap with the specified borderSize and borderColor
 *
 * @param borderSize - The border size in pixel
 * @param borderColor - The border color
 * @return A new bordered bitmap with the specified borderSize and borderColor
 */
fun Bitmap.createBitmapWithBorder(borderSize: Float, borderColor: Int): Bitmap {
    val borderOffset = (borderSize * 2).toInt()
    val halfWidth = width / 2
    val halfHeight = height / 2
    val circleRadius = Math.min(halfWidth, halfHeight).toFloat()
    val newBitmap = Bitmap.createBitmap(
        width + borderOffset,
        height + borderOffset,
        Bitmap.Config.ARGB_8888
    )

    // Center coordinates of the image
    val centerX = halfWidth + borderSize
    val centerY = halfHeight + borderSize

    val paint = Paint()
    val canvas = Canvas(newBitmap).apply {
        // Set transparent initial area
        drawARGB(0, 0, 0, 0)
    }

    // Draw the transparent initial area
    paint.isAntiAlias = true
    paint.style = Paint.Style.FILL
    canvas.drawCircle(centerX, centerY, circleRadius, paint)

    // Draw the image
    paint.xfermode = PorterDuffXfermode(PorterDuff.Mode.SRC_IN)
    canvas.drawBitmap(this, borderSize, borderSize, paint)

    // Draw the createBitmapWithBorder
    paint.xfermode = null
    paint.style = Paint.Style.STROKE
    paint.color = borderColor
    paint.strokeWidth = borderSize
    canvas.drawCircle(centerX, centerY, circleRadius, paint)
    return newBitmap
}

