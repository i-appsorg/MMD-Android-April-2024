package com.MamaDevalayam.Model;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Path;
import android.graphics.RectF;
import android.util.AttributeSet;

import androidx.appcompat.widget.AppCompatImageView;

public class CustomImageView extends AppCompatImageView {
public static float radius=18.0f;

public CustomImageView(Context context){
        super(context);
        }

public CustomImageView(Context context, AttributeSet attrs){
        super(context,attrs);
        }

public CustomImageView(Context context, AttributeSet attrs, int defStyle){
        super(context,attrs,defStyle);
        }

@Override
protected void onDraw(Canvas canvas){
        //float radius = 36.0f;
       /* Path clipPath = new Path();
        RectF rect = new RectF(0, 0, this.getWidth(), this.getHeight());
        clipPath.addRoundRect(rect, radius, radius, Path.Direction.CW);
        canvas.clipPath(clipPath);
        super.onDraw(canvas);*/

        Path path=getPath(radius,true,true,true,true);
        canvas.clipPath(path);
        super.onDraw(canvas);
        }
    private Path getPath(float radius, boolean topLeft, boolean topRight,
                         boolean bottomRight, boolean bottomLeft) {

        final Path path = new Path();
        final float[] radii = new float[8];

        if (topLeft) {
            radii[0] = radius;
            radii[1] = radius;
        }

        if (topRight) {
            radii[2] = radius;
            radii[3] = radius;
        }

        if (bottomRight) {
            radii[4] = radius;
            radii[5] = radius;
        }

        if (bottomLeft) {
            radii[6] = radius;
            radii[7] = radius;
        }

        path.addRoundRect(new RectF(0, 0, getWidth(), getHeight()),
                radii, Path.Direction.CW);

        return path;
    }
        }