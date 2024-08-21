package com.ismailmesutmujde.instakotlinapp.utils

import java.io.File
import java.util.Arrays

class FileOperations {

    companion object {
        fun getFilesInFolder(folderName : String) : ArrayList<String> {
            var allFiles = ArrayList<String>()

            var file = File(folderName)

            // parametre olarak gönderdiğimiz klasördeki tüm dosyaları alınız
            var allFilesInFolder = file.listFiles()

            // parametre olarak gönderdiğimiz klasör yolunda eleman olup olmadığı kontrol edildi.
            if (allFilesInFolder != null) {

                // galeriden gelen resimlerin tarihe göre sondan başa doğru listelenmesi
                if (allFilesInFolder.size>1) {
                    Arrays.sort(allFilesInFolder, object : Comparator<File>{
                        override fun compare(o1: File?, o2: File?): Int {
                            if(o1!!.lastModified() > o2!!.lastModified()) {
                                return -1
                            }else return 1

                        }

                    })
                }

                // sadece dosyalara bakılır
                for (i in 0..allFilesInFolder.size-1) {
                    if (allFilesInFolder[i].isFile) {
                        // okuduğumuz dosyanın telefondaki yer ve de adını içerir.
                        // files://root/logo.png
                        var readFilePath  = allFilesInFolder[i].absolutePath
                        var fileType = readFilePath.substring( readFilePath.lastIndexOf("."))

                        if (fileType.equals(".jpg") || fileType.equals(".jpeg") || fileType.equals(".png") || fileType.equals(".mp4")) {
                            allFiles.add(readFilePath)
                        }
                    }
                }


            }


            return allFiles

        }
    }
}