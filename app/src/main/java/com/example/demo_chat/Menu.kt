package com.example.demo_chat


import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI.setupWithNavController
import com.example.demo_chat.databinding.ActivityMenuBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.*
import de.hdodenhof.circleimageview.CircleImageView


class Menu : AppCompatActivity() {
    var imageView: CircleImageView? = null
    var username: TextView? = null

    var firebaseUser: FirebaseUser? = null
    var reference: DatabaseReference? = null
    private lateinit var binding: ActivityMenuBinding
    private lateinit var navController: NavController


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityMenuBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setUpNavigation();







    }
    fun setUpNavigation() {
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.activity_main_nav_host_fragment) as NavHostFragment?
        setupWithNavController(
            binding.bottomNavigationView,
            navHostFragment!!.navController
        )
    }

    fun logout(view: View?) {
        FirebaseAuth.getInstance().signOut()
        startActivity(Intent(this@Menu, MainActivity::class.java))
        finish()
    }


    internal class ViewPageradapter(fm: FragmentManager) :
        FragmentPagerAdapter(fm) {
        private val fragments: ArrayList<Fragment>
        private val titles: ArrayList<String>
        override fun getItem(position: Int): Fragment {
            return fragments[position]
        }

        override fun getCount(): Int {
            return fragments.size
        }

        override fun getPageTitle(position: Int): CharSequence? {
            return titles[position]
        }

        fun addfragment(fragment: Fragment, title: String) {
            fragments.add(fragment)
            titles.add(title)
        }

        init {
            fragments = ArrayList()
            titles = ArrayList()
        }
    }
}