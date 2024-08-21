@file:Suppress("DEPRECATION")

package com.ismailmesutmujde.instakotlinapp.profile.fragment

import android.app.ProgressDialog
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.OnFailureListener
import com.google.android.gms.tasks.Task
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.UploadTask
import com.ismailmesutmujde.instakotlinapp.R
import com.ismailmesutmujde.instakotlinapp.databinding.FragmentProfileEditBinding
import com.ismailmesutmujde.instakotlinapp.models.Users
import com.ismailmesutmujde.instakotlinapp.utils.EventbusDataEvents
import com.ismailmesutmujde.instakotlinapp.utils.UniversalImageLoader
import de.hdodenhof.circleimageview.CircleImageView
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import java.lang.Exception

class ProfileEditFragment : Fragment() {

    private lateinit var bindingPEF : FragmentProfileEditBinding

    lateinit var circleProfileImageFragment : CircleImageView
    lateinit var incomingUserInformation : Users
    lateinit var mDataRef : DatabaseReference
    lateinit var mStorageRef : StorageReference

    var profilePhotoURI : Uri? = null
    var Choose_Picture = 100

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        bindingPEF =  DataBindingUtil.inflate(inflater, R.layout.fragment_profile_edit, container, false)

        mDataRef = FirebaseDatabase.getInstance().reference
        mStorageRef = FirebaseStorage.getInstance().reference

        setupUserInformation()

        bindingPEF.imgClose.setOnClickListener {
            activity?.onBackPressed()
        }

        bindingPEF.tvChangePhoto.setOnClickListener {
            var intent = Intent()
            intent.setType("image/*")
            intent.setAction(Intent.ACTION_PICK)
            startActivityForResult(intent, Choose_Picture)
        }

        bindingPEF.imgBtnSaveChanges.setOnClickListener {

            if (profilePhotoURI != null) {

                var dialogLoading = LoadingFragment()
                dialogLoading.show(requireActivity().supportFragmentManager,"loadingFragment")
                dialogLoading.isCancelable = false

                var uploadTask = mStorageRef.child("users")
                    .child(incomingUserInformation!!.user_id!!)
                    .child(profilePhotoURI!!.lastPathSegment!!)
                    .putFile(profilePhotoURI!!)
                    .addOnCompleteListener(object : OnCompleteListener<UploadTask.TaskSnapshot> {
                        override fun onComplete(p0: Task<UploadTask.TaskSnapshot>) {
                            // getResult().downloadUrl kısmı getResult.uploadSessionUri olarak güncellendi ama test edilmedi
                            if (p0!!.isSuccessful) {
                                //Toast.makeText(activity,"Photo uploaded" + p0!!.getResult().uploadSessionUri.toString(), Toast.LENGTH_SHORT).show()

                                mDataRef.child("users")
                                    .child(incomingUserInformation!!.user_id!!)
                                    .child("user_detail")
                                    .child("profile_picture")
                                    .setValue(p0!!.getResult().uploadSessionUri.toString())

                                dialogLoading.dismiss()
                                updateUsername(true)
                            }
                        }

                    })
                    .addOnFailureListener(object : OnFailureListener {
                        override fun onFailure(p0: Exception) {
                            Log.e("HATA", p0.message!!)
                            updateUsername (false)
                        }

                    })
            } else {
                updateUsername(null)
            }
        }

        return bindingPEF.root
    }

    // profil resmi değişti
    // true  : başarılı bir şekilde resim storage yüklenmiş ve veritabanına yazılmıştır.
    // false : resim yüklenirken hata oluşmuştur.
    // null  : kullanıcı resmini değiştirmek istememiştir.
    private fun updateUsername(changedProfilePhoto : Boolean?) {
        if (!incomingUserInformation!!.user_name!!.equals(bindingPEF.etEditUsername.text.toString())) {
            mDataRef.child("users").orderByChild("user_name").addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {

                    var isUsernameInUse = false

                    for (ds in snapshot!!.children) {
                        var readUsername = ds!!.getValue(Users::class.java)!!.user_name
                        //Log.e("ERROR", "read username : "+ readUsername)
                        if (readUsername!!.equals(bindingPEF.etEditUsername.text.toString())) {
                            Toast.makeText(activity, "Username in use", Toast.LENGTH_SHORT).show()
                            isUsernameInUse = true
                            updateProfileInformation(changedProfilePhoto, false)
                            break
                        }
                    }
                    if (isUsernameInUse == false) {
                        mDataRef.child("users")
                            .child(incomingUserInformation!!.user_id!!)
                            .child("user_name")
                            .setValue(bindingPEF.etEditUsername.text.toString())
                        updateProfileInformation(changedProfilePhoto, true)
                    }
                }

                override fun onCancelled(error: DatabaseError) {

                }

            })
        } else {
            updateProfileInformation(changedProfilePhoto, null)
        }
    }

    private fun updateProfileInformation(changedProfilePhoto: Boolean?, changedUsername : Boolean?) {

        var isProfileUpdated : Boolean? = null

        if(!incomingUserInformation!!.name_surname!!.equals(bindingPEF.etEditName.text.toString())){
            mDataRef.child("users")
                .child(incomingUserInformation!!.user_id!!)
                .child("name_surname")
                .setValue(bindingPEF.etEditName.text.toString())
            isProfileUpdated = true
        }

        if (!incomingUserInformation!!.user_detail!!.biography!!.equals(bindingPEF.etEditUserBiography.text.toString())) {
            mDataRef.child("users")
                .child(incomingUserInformation!!.user_id!!)
                .child("user_detail")
                .child("biography")
                .setValue(bindingPEF.etEditUserBiography.text.toString())
            isProfileUpdated = true
        }

        if (!incomingUserInformation!!.user_detail!!.web_site!!.equals(bindingPEF.etEditUserWebsite.text.toString())) {
            mDataRef.child("users")
                .child(incomingUserInformation!!.user_id!!)
                .child("user_detail")
                .child("web_site")
                .setValue(bindingPEF.etEditUserWebsite.text.toString())
            isProfileUpdated = true
        }

        if (changedProfilePhoto == null && changedUsername == null && isProfileUpdated == null) {
            Toast.makeText(activity,"There is no change in profile information.", Toast.LENGTH_SHORT).show()
        } else if (changedUsername == false && (isProfileUpdated == true || changedProfilePhoto == true) ) {
            Toast.makeText(activity,"Profile information updated but username in use.", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(activity,"Profile information updated.", Toast.LENGTH_SHORT).show()
            requireActivity()!!.onBackPressed()
        }

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == Choose_Picture && resultCode == AppCompatActivity.RESULT_OK && data!!.data != null) {
            profilePhotoURI = data!!.data!!
            circleProfileImageFragment.setImageURI(profilePhotoURI)
        }
    }

    private fun setupUserInformation() {
        // nesnelere databinding ile erişebiliyoruz..
        bindingPEF!!.etEditName.setText(incomingUserInformation!!.name_surname)
        bindingPEF!!.etEditUsername.setText(incomingUserInformation!!.user_name)

        if(!incomingUserInformation!!.user_detail!!.biography!!.isNullOrEmpty()){
            bindingPEF!!.etEditUserBiography.setText(incomingUserInformation!!.user_detail!!.biography)
        }
        if(!incomingUserInformation!!.user_detail!!.web_site!!.isNullOrEmpty()) {
            bindingPEF!!.etEditUserWebsite.setText(incomingUserInformation!!.user_detail!!.web_site)
        }
        var imgURL = incomingUserInformation!!.user_detail!!.profile_picture
        UniversalImageLoader.setImage(imgURL!!, bindingPEF.circleProfileImage, bindingPEF.progressBar2,"")
    }

    /*
    private fun setupProfilePicture() {
        //https://orig00.deviantart.net/67cd/f/2012/309/8/c/android_icon_by_gabrydesign-dm47he9.png

        var imgURL = "orig00.deviantart.net/67cd/f/2012/309/8/c/android_icon_by_gabrydesign-dm47he9.png"
        UniversalImageLoader.setImage(imgURL, circleProfileImageFragment, bindingPEF.progressBar2,"https://")
    }*/


    @Subscribe(sticky = true)
    internal fun onUserInformationEvent(userInformation : EventbusDataEvents.SendUserInformation) {
        incomingUserInformation = userInformation!!.user!!

    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        EventBus.getDefault().register(this)
    }

    override fun onDetach() {
        super.onDetach()
        EventBus.getDefault().unregister(this)
    }
}