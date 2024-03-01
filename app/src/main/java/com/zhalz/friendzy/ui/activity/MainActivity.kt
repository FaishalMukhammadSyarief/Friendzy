package com.zhalz.friendzy.ui.activity

import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.crocodic.core.extension.openActivity
import com.zhalz.friendzy.R
import com.zhalz.friendzy.base.BaseActivity
import com.zhalz.friendzy.databinding.ActivityMainBinding
import com.zhalz.friendzy.ui.viewmodel.MainViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : BaseActivity<ActivityMainBinding, MainViewModel>(R.layout.activity_main) {

    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.activity = this

        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host) as NavHostFragment
        navController = navHostFragment.navController

        binding.bottomNavigation.setupWithNavController(navController)

        binding.toolbar.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.menu_logout -> showConfirmation()
            }
            true
        }

    }

    fun toCreate() = openActivity<ModifyActivity>()

    override fun onSupportNavigateUp(): Boolean {
        return super.onSupportNavigateUp() || navController.navigateUp()
    }

    private fun logout() {
        viewModel.deleteUser()
        openActivity<LoginActivity>()
        finish()
    }

    private fun showConfirmation() {
        AlertDialog
            .Builder(this)
            .setTitle(getString(R.string.title_logout))
            .setMessage(getString(R.string.msg_logout))
            .setPositiveButton(R.string.action_positive) { dialog, _ ->
                logout()
                finish()
                dialog.dismiss()
            }
            .setNegativeButton(R.string.action_negative) { dialog, _ ->
                dialog.dismiss()
            }
            .show()
    }

}

