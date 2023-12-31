package com.zhalz.friendzy.ui.activity

import android.app.DatePickerDialog
import android.content.pm.PackageManager
import android.graphics.BitmapFactory
import android.icu.util.Calendar
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.crocodic.core.extension.openCamera
import com.crocodic.core.extension.openGallery
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.zhalz.friendzy.R
import com.zhalz.friendzy.base.BaseActivity
import com.zhalz.friendzy.data.friend.FriendEntity
import com.zhalz.friendzy.databinding.ActivityModifyBinding
import com.zhalz.friendzy.helper.BitmapHelper
import com.zhalz.friendzy.ui.viewmodel.ModifyViewModel
import dagger.hilt.android.AndroidEntryPoint
import java.io.File
import java.text.SimpleDateFormat
import java.util.Locale

@AndroidEntryPoint
class ModifyActivity : BaseActivity<ActivityModifyBinding, ModifyViewModel>(R.layout.activity_modify) {

    private val newFriend: FriendEntity by lazy { FriendEntity(name, birth, description, photo) }
    private val savedFriend: FriendEntity by lazy { FriendEntity(name, birth, description, photo).apply { id = idFriend } }

    private lateinit var photoFile: File

    var name = ""
    var birth = ""
    var description = ""
    var photo = ""
    private var idFriend = 0

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
                            viewModel.deleteFriend(savedFriend)
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
                viewModel.editFriend(savedFriend)
            )
        }

        else {
            showConfirmation(
                getString(R.string.title_create),
                getString(R.string.msg_create),
                viewModel.createFriend(newFriend)
            )
        }

    }

    private fun showConfirmation(
        title: String,
        message: String,
        positiveAction: Unit
    ) {
        AlertDialog
            .Builder(this)
            .setTitle(title)
            .setMessage(message)
            .setPositiveButton(R.string.action_positive) { dialog, _ ->
                positiveAction
                finish()
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
        MaterialAlertDialogBuilder(this)
            .setTitle(getString(R.string.title_select))
            .setItems(R.array.select_image_source) { _, which ->
                when (which) {
                    0 -> checkPermissionCamera()
                    1 -> checkPermissionGallery()

                }
            }
            .show()
    }

    private fun createImageFile(): File {
        val storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        return File.createTempFile("PHOTO_", ".jpg", storageDir)
    }

    private fun resizeInputPhoto(file: File) {
        val bitmap = BitmapFactory.decodeFile(file.absolutePath)
        val resizeTakenImage = BitmapHelper().resizeBitmap(bitmap, 512f)

        binding.ivProfile.setImageBitmap(bitmap)
        photo = BitmapHelper().bitmapToString(resizeTakenImage)
        photoFile.delete()
    }

    private fun openCamera() {
        activityLauncher.openCamera(this, "com.zhalz.friendzy.file-provider") { file, _ ->
            if (file != null) resizeInputPhoto(file)
        }
    }

    private fun openGallery() {
        activityLauncher.openGallery(this) { file, _ ->
            if (file != null) resizeInputPhoto(file)
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
                Toast.makeText(this, getString(R.string.permission_granted), Toast.LENGTH_SHORT).show()
                openCamera()
            } else {
                Toast.makeText(this, getString(R.string.permission_not_granted), Toast.LENGTH_SHORT).show()
            }
        }

        else if (requestCode == REQUEST_CODE_GALLERY) {
            if (grantResults.all { it == PackageManager.PERMISSION_GRANTED }) {
                Toast.makeText(this, getString(R.string.permission_granted), Toast.LENGTH_SHORT).show()
                openGallery()
            } else {
                Toast.makeText(this, getString(R.string.permission_not_granted), Toast.LENGTH_SHORT).show()
            }
        }
    }

    companion object {
        const val REQUEST_CODE_CAMERA = 100
        const val REQUEST_CODE_GALLERY = 110
    }

}