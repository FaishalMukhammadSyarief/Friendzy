package com.zhalz.friendzy.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.lifecycleScope
import com.zhalz.friendzy.R
import com.zhalz.friendzy.data.database.AppDatabase
import com.zhalz.friendzy.data.database.FriendDao
import com.zhalz.friendzy.data.database.FriendEntity
import com.zhalz.friendzy.databinding.ActivityCreateBinding
import kotlinx.coroutines.launch

class CreateActivity : AppCompatActivity() {

    private val binding: ActivityCreateBinding by lazy {
        DataBindingUtil.setContentView(
            this,
            R.layout.activity_create
        )
    }
    private val friendManager: FriendDao by lazy { AppDatabase.getInstance(this).friendDao() }

    var name = ""
    var birth = ""
    var description = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding.activity = this

    }

    fun save() {

        if (name.isEmpty()) {
            binding.etName.error = "Cannot Empty"
        }

        if (birth.isEmpty()) {
            binding.etBirth.error = "Cannot Empty"
        }

        if (description.isEmpty()) {
            binding.etDescription.error = "Cannot Empty"
        }

        else {
            showConfirmation(
                "CREATE",
                "Are you sure to create this Friend?",
                "YES",
                ::createFriend
            )
        }

    }

    private fun createFriend() {
        lifecycleScope.launch {
            val newFriend = FriendEntity(name, birth, description)
            friendManager.insert(newFriend)
            finish()
        }
    }

    private fun showConfirmation(
        title: String,
        message: String,
        positiveText: String,
        positiveAction: () -> Unit
    ) {
        AlertDialog
            .Builder(this)
            .setTitle(title)
            .setMessage(message)
            .setPositiveButton(positiveText) { dialog, _ ->
                positiveAction.invoke()
                dialog.dismiss()
            }
            .setNegativeButton("CANCEL") { dialog, _ ->
                dialog.dismiss()
            }
            .show()
    }

    fun finishActivity() {
        finish()
    }
}