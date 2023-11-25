package com.zhalz.friendzy.ui

import android.app.DatePickerDialog
import android.icu.util.Calendar
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.lifecycleScope
import com.zhalz.friendzy.R
import com.zhalz.friendzy.data.database.AppDatabase
import com.zhalz.friendzy.data.database.FriendDao
import com.zhalz.friendzy.data.database.FriendEntity
import com.zhalz.friendzy.databinding.ActivityModifyBinding
import kotlinx.coroutines.launch

class ModifyActivity : AppCompatActivity() {

    private val binding: ActivityModifyBinding by lazy { DataBindingUtil.setContentView(this, R.layout.activity_modify) }
    private val friendManager: FriendDao by lazy { AppDatabase.getInstance(this).friendDao() }

    var name = ""
    var birth = ""
    var description = ""
    private var idFriend = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding.activity = this

        name = intent.getStringExtra("name") ?: ""
        birth = intent.getStringExtra("birth") ?: ""
        description = intent.getStringExtra("description") ?: ""
        idFriend = intent.getIntExtra("id", 0)

        toolbarConfiguration()

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

        if (name.isEmpty() || birth.isEmpty() || description.isEmpty()){
            if (name.isEmpty()){ binding.etName.error = getString(R.string.msg_error) }
            if (birth.isEmpty()){ binding.etBirth.error = getString(R.string.msg_error) }
            if (description.isEmpty()){ binding.etDescription.error = getString(R.string.msg_error) }
        }

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

}