package com.yashgweiland.nativeandroidtask.ui.activity

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.navigation.NavController
import androidx.navigation.NavOptions
import androidx.navigation.Navigation
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.yashgweiland.nativeandroidtask.R
import com.yashgweiland.nativeandroidtask.common.BaseActivity
import com.yashgweiland.nativeandroidtask.common.BaseViewModel
import com.yashgweiland.nativeandroidtask.databinding.ActivityMainBinding
import com.yashgweiland.nativeandroidtask.notifiers.Notify
import com.yashgweiland.nativeandroidtask.ui.fragments.EShopFragment
import com.yashgweiland.nativeandroidtask.ui.fragments.ExchangeFragment
import com.yashgweiland.nativeandroidtask.ui.fragments.LaunchPadFragment
import com.yashgweiland.nativeandroidtask.ui.fragments.WalletFragment
import com.yashgweiland.nativeandroidtask.ui.viewmodel.MainViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : BaseActivity() {
    private val mainViewModel: MainViewModel by viewModel()
    private val binding: ActivityMainBinding by lazyBinding()
    override val dataBinding: Boolean = true
    private var bottomNav: BottomNavigationView? = null
    private var navController: NavController? = null
    override val layoutResource: Int = R.layout.activity_main
    override fun getViewModel(): BaseViewModel {
        return mainViewModel
    }

    override fun setBindings() {
        binding.viewModel = mainViewModel
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun onNotificationReceived(data: Notify) {

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setToolBar(resources.getString(R.string.exchange))

        bottomNav = findViewById<BottomNavigationView>(R.id.bottomNav)
        navController = Navigation.findNavController(this, R.id.fragment)
        navController?.let {
            bottomNav?.setupWithNavController(it)
        }
//        bottomNav?.selectedItemId = R.id.exchangeFragment;
        bottomNav?.let {
            setUpBottomContent(it)
        }

    }

    private fun setUpBottomContent(bottomNavigation: BottomNavigationView) {
        bottomNavigation.setOnNavigationItemSelectedListener { item ->
            val navHostFragment = supportFragmentManager.findFragmentById(R.id.fragment)
            val fragment = navHostFragment?.childFragmentManager!!.fragments[0]
            when (item.itemId) {
                R.id.eShopFragment -> {
                    if (fragment !is EShopFragment) {
                        if (fragment is ExchangeFragment || fragment is LaunchPadFragment || fragment is WalletFragment) {
                            navController?.navigate(
                                item.itemId,
                                null,
                                getNavOptions(R.anim.slide_in_right, R.anim.slide_out_left)
                            )
                        } else {
                            navController?.navigate(
                                item.itemId,
                                null,
                                getNavOptions(R.anim.slide_in_left, R.anim.slide_out_right)
                            )
                        }
                        true
                    } else {
                        false
                    }
                }

                R.id.exchangeFragment -> {
                    if (fragment !is ExchangeFragment) {

                        if (fragment is EShopFragment) {
                            navController?.navigate(
                                item.itemId,
                                null,
                                getNavOptions(R.anim.slide_in_right, R.anim.slide_out_left)
                            )
                        } else if (fragment is LaunchPadFragment || fragment is WalletFragment) {
                            navController?.navigate(
                                item.itemId,
                                null,
                                getNavOptions(R.anim.slide_in_left, R.anim.slide_out_right)
                            )
                        }

                        true
                    } else {
                        false
                    }
                }

                R.id.launchpadsFragment -> {
                    if (fragment !is LaunchPadFragment) {

                        if (fragment is EShopFragment || fragment is ExchangeFragment) {
                            navController?.navigate(
                                item.itemId,
                                null,
                                getNavOptions(R.anim.slide_in_right, R.anim.slide_out_left)
                            )
                        } else if (fragment is WalletFragment) {
                            navController?.navigate(
                                item.itemId,
                                null,
                                getNavOptions(R.anim.slide_in_left, R.anim.slide_out_right)
                            )
                        }

                        true
                    } else {
                        false
                    }
                }

                R.id.walletFragment -> {
                    if (fragment !is WalletFragment) {
                        if (fragment is EShopFragment || fragment is ExchangeFragment || fragment is LaunchPadFragment) {
                            navController?.navigate(
                                item.itemId,
                                null,
                                getNavOptions(R.anim.slide_in_right, R.anim.slide_out_left)
                            )
                        } else {
                            navController?.navigate(
                                item.itemId,
                                null,
                                getNavOptions(R.anim.slide_in_left, R.anim.slide_out_right)
                            )
                        }
                        true
                    } else {
                        false
                    }
                }
                else -> {
                    false
                }
            }
        }
    }

    protected fun getNavOptions(enterAnim: Int, exitanim: Int): NavOptions {
        return NavOptions.Builder()
            .setEnterAnim(enterAnim)
            .setExitAnim(exitanim)
            .setPopEnterAnim(enterAnim)
            .setPopExitAnim(exitanim)
            .build()
    }

    // Setting Up the back button
    override fun onSupportNavigateUp(): Boolean {
        return NavigationUI.navigateUp(Navigation.findNavController(this, R.id.fragment), null)
    }

    override fun onBackPressed() {
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.fragment)
        val fragment = navHostFragment?.childFragmentManager!!.fragments[0]

        if (fragment is ExchangeFragment) {
            finish()
        } else {
            restBackActionDone()
        }
    }

    private fun restBackActionDone() {
        navController?.popBackStack(R.id.exchangeFragment, true)
        bottomNav?.selectedItemId = R.id.exchangeFragment
    }

    fun setTitleBar(titleValue: String){
        setToolBar(titleValue)
    }
}