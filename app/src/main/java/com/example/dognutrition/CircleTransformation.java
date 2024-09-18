package com.example.dognutrition;

import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Shader;

import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool;
import com.bumptech.glide.load.resource.bitmap.BitmapTransformation;

import java.security.MessageDigest;

public class CircleTransformation extends BitmapTransformation {

    public CircleTransformation() {}

    @Override
    protected Bitmap transform(BitmapPool pool, Bitmap toTransform, int outWidth, int outHeight) {
        return getCircleBitmap(pool, toTransform);
    }

    private Bitmap getCircleBitmap(BitmapPool pool, Bitmap bitmap) {
        if (bitmap == null) return null;

        // Determine the size of the square to which the image will be scaled
        int size = Math.min(bitmap.getWidth(), bitmap.getHeight());

        // Create a square bitmap with the size of the smaller dimension
        Bitmap result = pool.get(size, size, Bitmap.Config.ARGB_8888);
        if (result == null) {
            result = Bitmap.createBitmap(size, size, Bitmap.Config.ARGB_8888);
        }

        Canvas canvas = new Canvas(result);
        Paint paint = new Paint();
        paint.setAntiAlias(true);

        // Create a circle path
        Path path = new Path();
        path.addCircle(size / 2f, size / 2f, size / 2f, Path.Direction.CCW);

        // Clip the canvas to the circle path
        canvas.clipPath(path);

        // Create a bitmap shader to draw the image
        BitmapShader shader = new BitmapShader(bitmap, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP);
        paint.setShader(shader);

        // Create a rectangle to specify the area of the original bitmap to be drawn
        Rect srcRect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());
        RectF dstRect = new RectF(0, 0, size, size);
        canvas.drawBitmap(bitmap, srcRect, dstRect, paint);

        return result;
    }

    @Override
    public void updateDiskCacheKey(MessageDigest messageDigest) {
        messageDigest.update("circle".getBytes(CHARSET));
    }
}
