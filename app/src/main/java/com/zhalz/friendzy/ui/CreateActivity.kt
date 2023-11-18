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

    private val binding: ActivityCreateBinding by lazy { DataBindingUtil.setContentView(this, R.layout.activity_create) }
    private val friendManager: FriendDao by lazy { AppDatabase.getInstance(this).friendDao() }

    var name = ""
    var birth = ""
    var description = ""
    private var idFriend = 0
    var toolbarTitle = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding.activity = this

        name = intent.getStringExtra("name") ?: ""
        birth = intent.getStringExtra("birth") ?: ""
        description = intent.getStringExtra("description") ?: ""
        idFriend = intent.getIntExtra("id", 0)

        toolbarTitle =
            if (isEdit()){"EDIT"}
            else {"CREATE"}
    }

    private fun isEdit(): Boolean{
        return idFriend != 0
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

        if (isEdit()) {
            showConfirmation(
                "EDIT",
                "Are you sure to edit this Friend?",
                ::editFriend
            )
        }

        else {
            showConfirmation(
                "CREATE",
                "Are you sure to create this Friend?",
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

    private fun showConfirmation(
        title: String,
        message: String,
        positiveAction: () -> Unit
    ) {
        AlertDialog
            .Builder(this)
            .setTitle(title)
            .setMessage(message)
            .setPositiveButton("YES") { dialog, _ ->
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