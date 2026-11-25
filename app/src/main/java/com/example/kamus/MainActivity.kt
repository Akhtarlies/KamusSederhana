package com.example.kamus

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Latihan lanjutan 3: Navigation Component menggantikan when + replace manual.
        // Id item menu harus sama dengan id destination di nav_graph.xml.
        val navHost = supportFragmentManager
            .findFragmentById(R.id.fragmentContainer) as NavHostFragment
        findViewById<BottomNavigationView>(R.id.bottomNav)
            .setupWithNavController(navHost.navController)
    }
}
