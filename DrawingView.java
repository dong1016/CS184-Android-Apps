package edu.ucsb.he.dong.dhedrawingmultitouch;

import android.graphics.PointF;
import android.view.View;
import android.content.Context;
import android.util.AttributeSet;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.view.MotionEvent;

import java.util.HashMap;

/**
 * Created by Dong on 10/15/17.
 */

public class DrawingView extends View {

    private Paint drawPaint, canvasPaint;
    private int defaultColor = 0xFF000000;
    public Canvas drawCanvas;
    private Bitmap canvasBitmap;

    private HashMap<Integer, PointF> fingerPos = new HashMap<>();
    private HashMap<Integer, Path> fingerPaths = new HashMap<>();

    public DrawingView(Context context, AttributeSet attrs) {
        super(context, attrs);
        setupDrawing();
    }

    private void setupDrawing() {
        canvasBitmap = Bitmap.createBitmap(10, 10, Bitmap.Config.ARGB_8888);
        drawCanvas = new Canvas(canvasBitmap);
        drawPaint = new Paint();
        drawPaint.setColor(defaultColor);
        drawPaint.setAntiAlias(true);
        drawPaint.setStrokeWidth(10);
        drawPaint.setStyle(Paint.Style.STROKE);
        drawPaint.setStrokeJoin(Paint.Join.ROUND);
        drawPaint.setStrokeCap(Paint.Cap.ROUND);
        canvasPaint = new Paint(Paint.DITHER_FLAG);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        int new_max_size = Math.max(w, h);
        if(canvasBitmap.getWidth() < new_max_size) {
            Bitmap canvasBitmapNew = Bitmap.createBitmap(new_max_size, new_max_size, Bitmap.Config.ARGB_8888);
            drawCanvas.setBitmap(canvasBitmapNew);
            drawCanvas.drawBitmap(canvasBitmap, 0, 0, null);
            canvasBitmap = canvasBitmapNew;

        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawBitmap(canvasBitmap, 0, 0, canvasPaint);
        for(int i : fingerPos.keySet()){
            PointF point = fingerPos.get(i);
            Path tPath = fingerPaths.get(i);
            if(((point != null) && (tPath != null))){

                if(i < 4){
                    drawPaint.setColor(MainActivity.colors[MainActivity.temp[i]]);
                }else{
                    drawPaint.setColor(defaultColor);
                }

                tPath.lineTo(point.x, point.y);
                drawCanvas.drawPath(tPath, drawPaint);
            }
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        int pointerIndex = event.getActionIndex();
        int pointerId = event.getPointerId(pointerIndex);
        int action = event.getActionMasked();

        switch (action) {
            case MotionEvent.ACTION_DOWN:
            case MotionEvent.ACTION_POINTER_DOWN:
                PointF point = new PointF();
                point.x = event.getX(pointerIndex);
                point.y = event.getY(pointerIndex);
                Path tempP = new Path();
                tempP.moveTo(point.x, point.y);
                fingerPos.put(pointerId, point);
                fingerPaths.put(pointerId, tempP);
                break;
            case MotionEvent.ACTION_MOVE:
                for(int i = 0; i < event.getPointerCount(); i++){
                    PointF curPoint = fingerPos.get(event.getPointerId(i));
                    if(curPoint != null){
                        curPoint.x = event.getX(i);
                        curPoint.y = event.getY(i);
                    }
                }
                break;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_POINTER_UP:
                fingerPos.remove(pointerId);
                fingerPaths.remove(pointerId);
                break;
            default:
                return false;
        }

        invalidate();
        return true;
    }

}