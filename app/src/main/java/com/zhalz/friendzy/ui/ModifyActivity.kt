package com.zhalz.friendzy.ui

import android.app.DatePickerDialog
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.BitmapFactory
import android.icu.util.Calendar
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
import com.zhalz.friendzy.data.database.AppDatabase
import com.zhalz.friendzy.data.database.FriendDao
import com.zhalz.friendzy.data.database.FriendEntity
import com.zhalz.friendzy.databinding.ActivityModifyBinding
import kotlinx.coroutines.launch
import java.io.File

class ModifyActivity : AppCompatActivity() {

    private val binding: ActivityModifyBinding by lazy { DataBindingUtil.setContentView(this, R.layout.activity_modify) }
    private val friendManager: FriendDao by lazy { AppDatabase.getInstance(this).friendDao() }

    private lateinit var photoFile: File

    var name = ""
    var birth = ""
    var description = ""
    private var idFriend = 0

    private var cameraLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            if (it.resultCode == RESULT_OK) {
                val takenImage = BitmapFactory.decodeFile(photoFile.absolutePath)
                binding.photo = takenImage
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding.activity = this

        name = intent.getStringExtra("name") ?: ""
        birth = intent.getStringExtra("birth") ?: ""
        description = intent.getStringExtra("description") ?: ""
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
                val selectedDate = "$selectedDay/${selectedMonth + 1}/$selectedYear"
                binding.etBirth.setText(selectedDate)
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

    private fun createFriend() {
        val newFriend = FriendEntity(name, birth, description)

        lifecycleScope.launch {
                friendManager.insert(newFriend)
                finish()
            }

    }

    private fun editFriend() {
        val newFriend = FriendEntity(name, birth, description).apply { id = idFriend }

            lifecycleScope.launch {
                friendManager.update(newFriend)
                finish()
            }

    }

    private fun deleteFriend() {
        val savedFriend = FriendEntity(name, birth, description).apply { id = idFriend }

        lifecycleScope.launch {
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

        val items = arrayOf("Camera", "Cancel")

        MaterialAlertDialogBuilder(this)
            .setTitle("Select From")
            .setItems(items) { _, which ->
                when (which) {
                    0 -> checkPermissionCamera()
                    1 -> Unit
                }
            }
            .show()
    }

    private fun createImageFile(): File {
        val storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        return File.createTempFile("PHOTO_", ".jpg", storageDir)
    }

    private fun openCamera() {
        val photoFileUri =
            FileProvider.getUriForFile(this, "com.zhalz.friendzy.file-provider", photoFile)

        val cameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE).apply {
            putExtra(MediaStore.EXTRA_OUTPUT, photoFileUri)
        }
        cameraLauncher.launch(cameraIntent)
    }


    /** -- RUNTIME PERMISSION -- **/

    private fun checkPermissionCamera() {
        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            /** Request Permission **/
            ActivityCompat.requestPermissions(this, arrayOf(android.Manifest.permission.CAMERA), REQUEST_CODE_CAMERA)
        } else {
            openCamera()
        }
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
    }

    companion object {
        const val REQUEST_CODE_CAMERA = 100
    }

}