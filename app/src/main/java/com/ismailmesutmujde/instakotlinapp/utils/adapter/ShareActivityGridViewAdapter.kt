package com.ismailmesutmujde.instakotlinapp.utils.adapter



import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.ProgressBar
import com.ismailmesutmujde.instakotlinapp.R
import com.ismailmesutmujde.instakotlinapp.utils.GridImageView
import com.ismailmesutmujde.instakotlinapp.utils.UniversalImageLoader



class ShareActivityGridViewAdapter(context: Context?, resource: Int, var filesInFolder: ArrayList<String>) : ArrayAdapter<String> (context!!, resource, filesInFolder) {

    var inflater : LayoutInflater
    var singleColumnPhoto : View? = null
    lateinit var viewHolder : ViewHolder

    init {
        inflater = LayoutInflater.from(context)
    }

    inner class ViewHolder() {
        lateinit var imageView:GridImageView
        lateinit var progressBar : ProgressBar
    }
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {

        singleColumnPhoto = convertView

        if (singleColumnPhoto == null) {
            singleColumnPhoto = inflater.inflate(R.layout.single_column_grid_photo, parent,false)
            viewHolder = ViewHolder()
            viewHolder.imageView = singleColumnPhoto!!.findViewById(R.id.imgSingleColumnImage)
            viewHolder.progressBar = singleColumnPhoto!!.findViewById(R.id.progressBarGridPhoto)
            singleColumnPhoto!!.setTag(viewHolder)
        } else {
            viewHolder = singleColumnPhoto!!.getTag() as ViewHolder

        }

        UniversalImageLoader.setImage(filesInFolder.get(position), viewHolder.imageView, viewHolder.progressBar,"file:/")
        return singleColumnPhoto!!
    }

}



