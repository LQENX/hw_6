package com.mycompany.hw_5

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.mycompany.hw_5.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var startFragment: ContactsFragment

    // Время, когда была нажата кнопка "назад" в миллисекундах
    private var backPressed: Long = 0


    companion object {
        private const val BACK_PRESSED_OFFSET = 2000
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        startFragment = ContactsFragment()
        setStartFragment(startFragment)

    }

    private fun setStartFragment(startFragment: Fragment) {
        supportFragmentManager
                .beginTransaction()
                .add(
                        binding.fragmentContainer.id,
                        startFragment,
                        startFragment::class.qualifiedName
                )
                .addToBackStack(null)
                .setReorderingAllowed(true)
                .commit()
    }


    fun navigateTo(fragment: Fragment) {
        if (fragment::class.qualifiedName == startFragment::class.qualifiedName) {
            supportFragmentManager
                    .beginTransaction()
                    .replace(
                            binding.fragmentContainer.id,
                            fragment,
                            fragment::class.qualifiedName
                    )
                    .setReorderingAllowed(true)
                    .commit()
        } else {
            supportFragmentManager
                    .beginTransaction()
                    .replace(
                            binding.fragmentContainer.id,
                            fragment,
                            fragment::class.qualifiedName
                    )
                    .setReorderingAllowed(true)
                    .addToBackStack(null)
                    .commit()
        }
    }

    override fun onBackPressed() {
        if (supportFragmentManager.backStackEntryCount == 1) {
            if (backPressed + BACK_PRESSED_OFFSET > System.currentTimeMillis()) {
                finish()
            } else
                Toast.makeText(this, "Нажмите еще раз для выхода", Toast.LENGTH_SHORT).show()
            backPressed = System.currentTimeMillis()
        } else {
            super.onBackPressed()
        }
    }
}