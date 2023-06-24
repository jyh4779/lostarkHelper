package com.android.lostarkraid

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Gravity
import android.view.Window
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.android.lostarkraid.databinding.ActivityMainBinding

class MainActivity : FragmentActivity() {
    lateinit var binding : ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        requestWindowFeature(Window.FEATURE_NO_TITLE)

        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        val transaction = supportFragmentManager
            .beginTransaction()
            .add(R.id.frameLayout,HomeFragment())
        transaction.commit()

        binding.homeBtn.setOnClickListener{
            supportFragmentManager
                .beginTransaction()
                .replace(R.id.frameLayout,HomeFragment())
                .commit()
            binding.drawerLayout.closeDrawer(Gravity.LEFT)
        }

        binding.menuBtn.setOnClickListener {
            if (!binding.drawerLayout.isDrawerOpen(Gravity.LEFT)) {
                binding.drawerLayout.openDrawer(Gravity.LEFT)
            } else {
                binding.drawerLayout.closeDrawer(Gravity.LEFT)
            }
        }
        binding.dolBtn.setOnClickListener{
            supportFragmentManager
                .beginTransaction()
                .replace(R.id.frameLayout,BingoFragment())
                .commit()
            binding.drawerLayout.closeDrawer(Gravity.LEFT)
        }
    }
    fun changeFrament (raid: String){
        when(raid){
            "BTHARD1" -> {
                supportFragmentManager
                    .beginTransaction()
                    .replace(R.id.frameLayout,BaltanHardFragment())
                    .commit()
            }
            "BTHARD2" -> {
                supportFragmentManager
                    .beginTransaction()
                    .replace(R.id.frameLayout,BaltanHard2Fragment())
                    .commit()
            }
            "BINGORESET" -> {
                supportFragmentManager
                    .beginTransaction()
                    .replace(R.id.frameLayout,BingoFragment())
                    .commit()
            }
        }
    }
}

