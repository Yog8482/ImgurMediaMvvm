package com.yogendra.imgurmediamvvm

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.view.WindowManager
import androidx.databinding.DataBindingUtil
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import com.standalone.cooeyhealthtinder.connectivity.base.ConnectivityProvider
import com.yogendra.imgurmediamvvm.databinding.MainActivityBinding
import com.yogendra.imgurmediamvvm.utils.IS_INTERNET_AVAILABLE

class MainActivity : AppCompatActivity(), ConnectivityProvider.ConnectivityStateListener {

    var searchItem: MenuItem? = null
    private lateinit var navController: NavController
    private lateinit var appBarConfiguration: AppBarConfiguration

    private val provider: ConnectivityProvider by lazy { ConnectivityProvider.createProvider(this) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        setContentView(R.layout.main_activity)
        val binding: MainActivityBinding =
            DataBindingUtil.setContentView(this, R.layout.main_activity)
        IS_INTERNET_AVAILABLE = provider.getNetworkState().hasInternet()

        setSupportActionBar(binding.actMainToolbar)
        window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)


        navController = findNavController(R.id.nav_host_fragment)

        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.navigation_list
            )
        )

        setupActionBarWithNavController(navController, appBarConfiguration)

    }


    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }


    override fun onStart() {
        super.onStart()
        provider.addListener(this)
    }

    override fun onStop() {
        super.onStop()
        provider.removeListener(this)
    }

    override fun onStateChange(state: ConnectivityProvider.NetworkState) {
        IS_INTERNET_AVAILABLE = state.hasInternet()
    }

    private fun ConnectivityProvider.NetworkState.hasInternet(): Boolean {
        return (this as? ConnectivityProvider.NetworkState.ConnectedState)?.hasInternet == true
    }
}