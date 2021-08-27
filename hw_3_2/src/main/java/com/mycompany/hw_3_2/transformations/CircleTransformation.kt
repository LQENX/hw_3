package com.mycompany.hw_3_2.transformations

import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Path
import com.squareup.picasso.Transformation

class CircleTransformation : Transformation {
    override fun transform(source: Bitmap): Bitmap {
        val path = Path()
        path.addCircle(source.width / 2f, source.height / 2f, source.height / 2f, Path.Direction.CCW)

        val resultBitmap = Bitmap.createBitmap(source.width, source.height, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(resultBitmap)
        canvas.clipPath(path)

        val paint = Paint(Paint.ANTI_ALIAS_FLAG)
        canvas.drawBitmap(source, 0f, 0f, paint)
        source.recycle()

        return resultBitmap
    }


    override fun key(): String {
        return "Circle"
    }
}