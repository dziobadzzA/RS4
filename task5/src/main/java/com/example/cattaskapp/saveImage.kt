package com.example.cattaskapp

import android.content.Context
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.os.AsyncTask
import android.os.Environment
import android.util.Log
import android.widget.Toast
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.bitmap.DownsampleStrategy
import com.bumptech.glide.request.RequestOptions
import java.io.File
import java.io.FileOutputStream
import java.io.OutputStream
import java.lang.ref.WeakReference

@Suppress("DEPRECATION")
class saveImage(context: Context) : AsyncTask<String, Unit, Unit>() {
    private var mContext: WeakReference<Context> = WeakReference(context)

    override fun doInBackground(vararg params: String?) {
        val url = params[0]

        var name = url?.split('/')
        name = (name?.get(name.size - 1))?.split('.')

        val istiname = name?.get(0)

        val requestOptions = RequestOptions().override(100)
                .downsample(DownsampleStrategy.CENTER_INSIDE)
                .skipMemoryCache(true)
                .diskCacheStrategy(DiskCacheStrategy.NONE)

        mContext.get()?.let {

            val bitmap = Glide.with(it)
                    .asBitmap()
                    .load(url)
                    .apply(requestOptions)
                    .submit()
                    .get()


            val any = try {



                var filecash = File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES), "Cats$istiname")
                var outcash = FileOutputStream(filecash)

                bitmap.compress( Bitmap.CompressFormat.JPEG , 100, outcash)

                outcash.flush()
                outcash.close()

                Log.i("Seiggailion", "Image saved.")

            } catch (e: Exception) {
                Log.i("Seiggailion", "Failed to save image.")
            }
            any
        }
    }
}