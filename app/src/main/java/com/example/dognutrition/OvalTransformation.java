package com.example.dognutrition;

import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.graphics.Shader;

import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool;
import com.bumptech.glide.load.resource.bitmap.BitmapTransformation;
import java.security.MessageDigest;

public class OvalTransformation extends BitmapTransformation {

    public OvalTransformation() {}

    @Override
    protected Bitmap transform(BitmapPool pool, Bitmap toTransform, int outWidth, int outHeight) {
        return getOvalBitmap(pool, toTransform);
    }

    private Bitmap getOvalBitmap(BitmapPool pool, Bitmap bitmap) {
        if (bitmap == null) return null;

        Bitmap result = pool.get(bitmap.getWidth(), bitmap.getHeight(), Bitmap.Config.ARGB_8888);
        if (result == null) {
            result = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), Bitmap.Config.ARGB_8888);
        }

        Canvas canvas = new Canvas(result);
        Paint paint = new Paint();
        paint.setAntiAlias(true);
        paint.setShader(new BitmapShader(bitmap, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP));

        RectF rectF = new RectF(0, 0, bitmap.getWidth(), bitmap.getHeight());
        Path path = new Path();
        path.addOval(rectF, Path.Direction.CCW);
        canvas.clipPath(path);
        canvas.drawBitmap(bitmap, 0, 0, paint);

        return result;
    }

    @Override
    public void updateDiskCacheKey(MessageDigest messageDigest) {
        messageDigest.update("oval".getBytes(CHARSET));
    }
}
