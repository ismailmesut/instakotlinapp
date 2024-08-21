@file:Suppress("DEPRECATION")

package com.ismailmesutmujde.instakotlinapp.profile.activity

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.ismailmesutmujde.instakotlinapp.R
import com.ismailmesutmujde.instakotlinapp.databinding.ActivityProfileBinding
import com.ismailmesutmujde.instakotlinapp.login.activity.LoginActivity
import com.ismailmesutmujde.instakotlinapp.models.Users
import com.ismailmesutmujde.instakotlinapp.profile.fragment.ProfileEditFragment
import com.ismailmesutmujde.instakotlinapp.utils.BottomNavigationViewHelper
import com.ismailmesutmujde.instakotlinapp.utils.EventbusDataEvents
import com.ismailmesutmujde.instakotlinapp.utils.UniversalImageLoader
import org.greenrobot.eventbus.EventBus

class ProfileActivity : AppCompatActivity() {

    private lateinit var bindingPA: ActivityProfileBinding

    private val ACTIVITY_NO = 4
    private val TAG = "ProfileActivity"

    lateinit var mAuth : FirebaseAuth
    lateinit var mAuthListener : FirebaseAuth.AuthStateListener
    lateinit var mUser : FirebaseUser
    lateinit var mDataRef : DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        bindingPA = ActivityProfileBinding.inflate(layoutInflater)
        setContentView(bindingPA.root)

        setupAuthListener()
        mAuth = FirebaseAuth.getInstance()
        mUser = mAuth.currentUser!!
        mDataRef = FirebaseDatabase.getInstance().reference


        setupToolbar()
        setupNavigationView()
        getUserInformation()
        setupProfilePhoto()
    }

    private fun getUserInformation() {

        bindingPA.tvEditProfile.isEnabled = false
        bindingPA.imgProfileSettings.isEnabled = false
        mDataRef.child("users").child(mUser!!.uid).addValueEventListener(object: ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                if(snapshot!!.getValue() != null) {
                    var readUserDetails = snapshot!!.getValue(Users::class.java)

                    EventBus.getDefault().postSticky(EventbusDataEvents.SendUserInformation(readUserDetails))
                    bindingPA.tvEditProfile.isEnabled = true
                    bindingPA.imgProfileSettings.isEnabled = true

                    bindingPA.tvProfileNameToolbar.setText(readUserDetails!!.user_name)
                    bindingPA.tvProfileRealName.setText(readUserDetails!!.name_surname)
                    bindingPA.tvFollowersCount.setText(readUserDetails!!.user_detail!!.follower)
                    bindingPA.tvFollowingCount.setText(readUserDetails!!.user_detail!!.following)
                    bindingPA.tvPostsCount.setText(readUserDetails!!.user_detail!!.post)

                    var imgURL : String = readUserDetails!!.user_detail!!.profile_picture!!
                    UniversalImageLoader.setImage(imgURL, bindingPA.circleProfileImage, bindingPA.progressBar, "")

                    if (!readUserDetails!!.user_detail!!.biography!!.isNullOrEmpty()){
                        bindingPA.tvBiography.visibility = View.VISIBLE
                        bindingPA.tvBiography.setText(readUserDetails!!.user_detail!!.biography!!)
                    }
                    if (!readUserDetails!!.user_detail!!.web_site!!.isNullOrEmpty()) {
                        bindingPA.tvWebSite.visibility = View.VISIBLE
                        bindingPA.tvWebSite.setText(readUserDetails!!.user_detail!!.web_site!!)
                    }
                }

            }

            override fun onCancelled(error: DatabaseError) {

            }

        })
    }

    //https://orig00.deviantart.net/67cd/f/2012/309/8/c/android_icon_by_gabrydesign-dm47he9.png


    private fun setupProfilePhoto() {
        // "http://www.ids.com.tr/IDS-Media/image/Android-logo.png"
        //"https://www.wallpaperup.com/uploads/wallpapers/2016/06/24/991808/9ab236cccae5470451c20329ca43ec6b-1400.jpg"
        var imgURL = "www.wallpaperup.com/uploads/wallpapers/2016/06/24/991808/9ab236cccae5470451c20329ca43ec6b-1400.jpg"
        UniversalImageLoader.setImage(imgURL, bindingPA.circleProfileImage, bindingPA.progressBar, "https://")
    }

    private fun setupToolbar() {
        bindingPA.imgProfileSettings.setOnClickListener {
            var intent = Intent(this, ProfileSettingsActivity::class.java).addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION)
            startActivity(intent)
        }

        bindingPA.tvEditProfile.setOnClickListener {
            bindingPA.profileRoot.visibility = View.GONE
            var transaction = supportFragmentManager.beginTransaction()
            transaction.replace(R.id.profileContainer, ProfileEditFragment())
            transaction.addToBackStack("profileEditFragment added.")
            transaction.commit()
        }
    }

    private fun setupNavigationView() {
        BottomNavigationViewHelper.setupBottomNavigationView(bindingPA.bnvProfile)
        BottomNavigationViewHelper.setupNavigation(this, bindingPA.bnvProfile)
        var menu = bindingPA.bnvProfile.menu
        var menuItem=menu.getItem(ACTIVITY_NO)
        menuItem.setChecked(true)
    }

    override fun onBackPressed() {
        bindingPA.profileRoot.visibility = View.VISIBLE
        super.onBackPressed()
    }

    private fun setupAuthListener() {

        mAuthListener = object : FirebaseAuth.AuthStateListener{
            override fun onAuthStateChanged(p0: FirebaseAuth) {
                var user = FirebaseAuth.getInstance().currentUser
                if (user == null) {
                    var intent = Intent(this@ProfileActivity, LoginActivity::class.java).addFlags(
                        Intent.FLAG_ACTIVITY_NO_ANIMATION)
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
                    startActivity(intent)
                    finish()
                } else {

                }
            }
        }
    }

    override fun onStart() {
        super.onStart()
        mAuth.addAuthStateListener(mAuthListener)
    }

    override fun onStop() {
        super.onStop()
        if(mAuthListener != null) {
            mAuth.removeAuthStateListener(mAuthListener)
        }

    }
}