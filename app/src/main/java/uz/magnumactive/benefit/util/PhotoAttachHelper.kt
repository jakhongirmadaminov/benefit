//package uz.magnumactive.benefit.util
//
//import android.Manifest
//import android.app.Activity
//import android.content.Intent
//import android.content.pm.PackageManager
//import android.os.Build
//import android.provider.MediaStore
//import androidx.core.content.ContextCompat
//
///**
// * Created by jahon on 06-Aug-20
// */
//
//const val AVATAR = 10
//const val PASSPORT1 = 20
//const val PASSPORT2 = 30
//const val ADDITIONAL0 = 40
//const val ADDITIONAL1 = 41
//const val ADDITIONAL2 = 42
//const val ADDITIONAL3 = 43
//const val ADDITIONAL4 = 44
//const val ADDITIONAL5 = 45
//const val ADDITIONAL6 = 46
//const val ADDITIONAL7 = 47
//const val ADDITIONAL8 = 48
//const val REQUEST_GALLERY = 11
//const val REQUEST_IMAGE_CAPTURE = 22
//
//
//object PhotoAttachHelper {
//
//
//    fun openCameraOrStorageDialog(activity: Activity,
//                                  requestCodeHalf: Int) {
//        Dialog.bottomSheetDialog()
//            .option(
//                title = R.string.camera,
//                avatar = R.drawable.ic_camera_selever_24,
//                textColor = R.color.black,
//                selectAction = {
//                    openCamera(activity, (requestCodeHalf.toString() + REQUEST_IMAGE_CAPTURE.toString()).toInt())
//                })
//            .option(
//                title = R.string.gallery,
//                avatar = R.drawable.ic_image_24,
//                textColor = R.color.black,
//                selectAction = {
//                    choseFile(activity, (requestCodeHalf.toString() + REQUEST_GALLERY.toString()).toInt())
//                })
//            .show(activity)
//    }
//
//    fun openCamera(activity: Activity,
//                   photoTypeHashCode: Int) {
//        checkPermissionAndRequest(activity,
//                                  {
//                                      Intent(MediaStore.ACTION_IMAGE_CAPTURE).also { takePictureIntent ->
//                                          activity.packageManager?.let {
//                                              takePictureIntent.resolveActivity(it)?.also {
//                                                  activity.startActivityForResult(takePictureIntent,
//                                                                                  photoTypeHashCode)
//                                              }
//                                          }
//                                      }
//                                  },
//                                  {
//                                      activity.showMessageDialog(R.string.warning,
//                                                                 R.string.you_need_all_permission)
//                                  },
//                                  Manifest.permission.CAMERA,
//                                  Manifest.permission.READ_EXTERNAL_STORAGE,
//                                  Manifest.permission.WRITE_EXTERNAL_STORAGE
//        )
//    }
//
//    fun choseFile(activity: Activity,
//                  photoTypeHashCode: Int) {
//        Intent(Intent.ACTION_PICK).also {
//            it.type = "image/*"
//            val mimeType = arrayOf("image/jpeg", "image/png")
//            it.putExtra(Intent.EXTRA_MIME_TYPES, mimeType)
//            activity.startActivityForResult(it, photoTypeHashCode)
//        }
//    }
//
//    private val PERMISSION_REQUEST = 1111
//    private var permissionGrantedAction: (() -> Unit)? = null
//    private var permissionFailed: (() -> Unit)? = null
//    private var permissionsList: Array<out String>? = emptyArray()
//    private fun checkPermissionAndRequest(activity: Activity,
//                                          function: () -> Unit,
//                                          fail: () -> Unit,
//                                          vararg list: String
//    ) {
//        if (list.isNotEmpty()) {
//            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
//                list.forEach {
//                    if (activity.let { it1 -> ContextCompat.checkSelfPermission(it1, it) }
//                        != PackageManager.PERMISSION_GRANTED
//                    ) {
//                        permissionGrantedAction = function
//                        permissionFailed = fail
//                        permissionsList = list
//                        activity.requestPermissions(list, PERMISSION_REQUEST)
//                        return
//                    }
//                }
//                function.invoke()
//            } else {
//                function.invoke()
//            }
//        }
//    }
//}