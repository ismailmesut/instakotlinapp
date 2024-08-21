package com.ismailmesutmujde.instakotlinapp.share.fragment

import android.app.Activity
import android.content.DialogInterface.OnClickListener
import android.os.Bundle
import android.os.Environment
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.AdapterView.OnItemSelectedListener
import android.widget.ArrayAdapter
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import com.ismailmesutmujde.instakotlinapp.R
import com.ismailmesutmujde.instakotlinapp.databinding.FragmentShareGalleryBinding
import com.ismailmesutmujde.instakotlinapp.utils.FileOperations
import com.ismailmesutmujde.instakotlinapp.utils.UniversalImageLoader
import com.ismailmesutmujde.instakotlinapp.utils.adapter.ShareActivityGridViewAdapter

class ShareGalleryFragment : Fragment() {

    private lateinit var bindingSGF : FragmentShareGalleryBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?) : View? {
        // Inflate the layout for this fragment
        bindingSGF = DataBindingUtil.inflate(inflater, R.layout.fragment_share_gallery, container, false)


        var folderPaths = ArrayList<String>()
        var folderNames = ArrayList<String>()

        var root : String = Environment.getExternalStorageDirectory().path
        var cameraPhotos : String = root + "/DCIM/Camera"
        var downloadPhotos : String = root + "/Download"
        var whatsappPhotos : String = root + "/WhatsApp/Media/WhatsApp Images"

        folderPaths.add(cameraPhotos)
        folderPaths.add(downloadPhotos)
        folderPaths.add(whatsappPhotos)

        folderNames.add("Camera")
        folderNames.add("Downloads")
        folderNames.add("WhatsApp")

        var spinnerArrayAdapter : ArrayAdapter<String> = ArrayAdapter(requireActivity(), android.R.layout.simple_spinner_item, folderNames)
        spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

        bindingSGF.spnFolderNames.adapter = spinnerArrayAdapter
        bindingSGF.spnFolderNames.onItemSelectedListener = object : OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {

            }

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {

                setupGridView(FileOperations.getFilesInFolder(folderPaths.get(position)))



                /*
                for (str:String in filesInFolder) {
                    Log.e("FILES", str)
                }
                */
            }

        }



        return bindingSGF.root
    }

    fun setupGridView(selectedFilesInFolder : ArrayList<String>) {
        var gridAdapter = ShareActivityGridViewAdapter(activity, R.layout.single_column_grid_photo, selectedFilesInFolder)
        bindingSGF.gridphotos.adapter = gridAdapter
        bindingSGF.gridphotos.setOnItemClickListener(object : AdapterView.OnItemClickListener{
            override fun onItemClick(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                UniversalImageLoader.setImage(selectedFilesInFolder.get(position), bindingSGF.imgBigPhoto, null, "file:/")
            }

        })
    }

}