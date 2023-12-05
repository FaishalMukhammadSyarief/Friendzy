package com.zhalz.friendzy.ui.activity

import android.app.DatePickerDialog
import android.content.ActivityNotFoundException
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.icu.util.Calendar
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.lifecycleScope
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.zhalz.friendzy.R
import com.zhalz.friendzy.data.AppDatabase
import com.zhalz.friendzy.data.friend.FriendDao
import com.zhalz.friendzy.data.friend.FriendEntity
import com.zhalz.friendzy.databinding.ActivityModifyBinding
import com.zhalz.friendzy.helper.BitmapHelper
import kotlinx.coroutines.launch
import java.io.File
import java.io.FileOutputStream
import java.text.SimpleDateFormat
import java.util.Locale

class ModifyActivity : AppCompatActivity() {

    private val binding: ActivityModifyBinding by lazy { DataBindingUtil.setContentView(this, R.layout.activity_modify) }
    private val friendManager: FriendDao by lazy { AppDatabase.getInstance(this).friendDao() }

    private lateinit var photoFile: File

    var name = ""
    var birth = ""
    var description = ""
    var photo = ""
    private var idFriend = 0

    private var cameraLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            if (it.resultCode == RESULT_OK) {
                val takenImage = BitmapFactory.decodeFile(photoFile.absolutePath)
                resizeInputPhoto(takenImage)
            }
        }

    private var galleryLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == RESULT_OK) {
                result.data?.data?.let { uri ->
                    contentResolver.openInputStream(uri)?.use { inputStream ->
                        val outputStream = FileOutputStream(photoFile)
                        inputStream.copyTo(outputStream)
                    }

                    val takenImage = BitmapFactory.decodeFile(photoFile.absolutePath)
                    resizeInputPhoto(takenImage)
                }
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding.activity = this

        name = intent.getStringExtra("name") ?: ""
        birth = intent.getStringExtra("birth") ?: ""
        description = intent.getStringExtra("description") ?: ""
        photo = intent.getStringExtra("photo") ?: ""
        idFriend = intent.getIntExtra("id", 0)

        toolbarConfiguration()

        photoFile = createImageFile()

    }

    private fun isEdit(): Boolean{
        return idFriend != 0
    }

    private fun toolbarConfiguration(){
        if (isEdit()){
            binding.toolbar.title = "EDIT"
            binding.toolbar.inflateMenu(R.menu.toolbar_edit)

            binding.toolbar.setOnMenuItemClickListener {
                when (it.itemId) {
                    R.id.menu_delete ->
                        showConfirmation(
                            getString(R.string.title_delete),
                            getString(R.string.msg_delete),
                            ::deleteFriend
                        )
                }
                true
            }

        }
    }

    fun datePicker(){
        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)

        DatePickerDialog(
            this,
            { _, selectedYear, selectedMonth, selectedDay ->
                val selectedDate = Calendar.getInstance().apply {
                    set(selectedYear, selectedMonth, selectedDay)
                }

                val dateFormat = SimpleDateFormat("dd MMMM yyyy", Locale.getDefault())
                val formattedDate = dateFormat.format(selectedDate.time)

                binding.etBirth.setText(formattedDate)
            },
            year, month, day
        ).show()

    }

    fun save() {

        if (name.isEmpty()) binding.etName.error = getString(R.string.msg_error)
        if (birth.isEmpty()) binding.etBirth.error = getString(R.string.msg_error)
        if (description.isEmpty()) binding.etDescription.error = getString(R.string.msg_error)

        else if (isEdit()) {
            showConfirmation(
                getString(R.string.title_edit),
                getString(R.string.msg_edit),
                ::editFriend
            )
        }

        else {
            showConfirmation(
                getString(R.string.title_create),
                getString(R.string.msg_create),
                ::createFriend
            )
        }

    }

    /** -- CRUD OPERATION -- **/
    private fun createFriend() {
        lifecycleScope.launch {
            val newFriend = FriendEntity(name, birth, description, photo)
            friendManager.insert(newFriend)
            finish()
        }
    }
    private fun editFriend() {
        lifecycleScope.launch {
            val newFriend = FriendEntity(name, birth, description, photo).apply { id = idFriend }
            friendManager.update(newFriend)
            finish()
        }
    }
    private fun deleteFriend() {
        lifecycleScope.launch {
            val savedFriend = FriendEntity(name, birth, description, photo).apply { id = idFriend }
            friendManager.delete(savedFriend)
            finish()
        }
    }

    private fun showConfirmation(
        title: String,
        message: String,
        positiveAction: () -> Unit
    ) {
        AlertDialog
            .Builder(this)
            .setTitle(title)
            .setMessage(message)
            .setPositiveButton(R.string.action_positive) { dialog, _ ->
                positiveAction.invoke()
                dialog.dismiss()
            }
            .setNegativeButton(R.string.action_negative) { dialog, _ ->
                dialog.dismiss()
            }
            .show()
    }

    fun finishActivity() {
        finish()
    }

    fun addPhoto(){
        val items = arrayOf("Camera", "Gallery", "Cancel")
        MaterialAlertDialogBuilder(this)
            .setTitle("Select From")
            .setItems(items) { _, which ->
                when (which) {
                    0 -> checkPermissionCamera()
                    1 -> checkPermissionGallery()
                    2 -> Unit
                }
            }
            .show()
    }

    private fun createImageFile(): File {
        val storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        return File.createTempFile("PHOTO_", ".jpg", storageDir)
    }

    private fun resizeInputPhoto(bitmap: Bitmap) {
        val resizeTakenImage = BitmapHelper().resizeBitmap(bitmap, 512f)

        binding.ivProfile.setImageBitmap(bitmap)
        photo = BitmapHelper().bitmapToString(resizeTakenImage)
        photoFile.delete()
    }

    private fun openCamera() {
        val photoFileUri =
            FileProvider.getUriForFile(this, "com.zhalz.friendzy.file-provider", photoFile)

        val cameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE).apply {
            putExtra(MediaStore.EXTRA_OUTPUT, photoFileUri)
        }
        cameraLauncher.launch(cameraIntent)
    }

    private fun openGallery() {
        val galleryIntent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)

        try { galleryLauncher.launch(galleryIntent) }
        catch (e: ActivityNotFoundException) {
            Toast.makeText(this, "Cannot use Gallery", Toast.LENGTH_SHORT).show()
            e.printStackTrace()
        }
    }


    /** -- RUNTIME PERMISSION -- **/
    private fun checkPermissionCamera() {
        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            /** Request Permission **/
            ActivityCompat.requestPermissions(this, arrayOf(android.Manifest.permission.CAMERA), REQUEST_CODE_CAMERA)
        }
        else openCamera()
    }

    private fun checkPermissionGallery() {
        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.READ_MEDIA_IMAGES) != PackageManager.PERMISSION_GRANTED) {
            /** Request Permission **/
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) ActivityCompat.requestPermissions(this, arrayOf(android.Manifest.permission.READ_MEDIA_IMAGES), REQUEST_CODE_GALLERY)
            else ActivityCompat.requestPermissions(this, arrayOf(android.Manifest.permission.READ_EXTERNAL_STORAGE), REQUEST_CODE_GALLERY)
        }
        else openGallery()
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        if (requestCode == REQUEST_CODE_CAMERA) {
            if (grantResults.all { it == PackageManager.PERMISSION_GRANTED }) {
                Toast.makeText(this, "Permission Granted", Toast.LENGTH_SHORT).show()
                openCamera()
            } else {
                Toast.makeText(this, "Permission not Granted", Toast.LENGTH_SHORT).show()
            }
        }

        else if (requestCode == REQUEST_CODE_GALLERY) {
            if (grantResults.all { it == PackageManager.PERMISSION_GRANTED }) {
                Toast.makeText(this, "Permission Granted", Toast.LENGTH_SHORT).show()
                openGallery()
            } else {
                Toast.makeText(this, "Permission not Granted", Toast.LENGTH_SHORT).show()
            }
        }
    }

    companion object {
        const val REQUEST_CODE_CAMERA = 100
        const val REQUEST_CODE_GALLERY = 110
    }

}