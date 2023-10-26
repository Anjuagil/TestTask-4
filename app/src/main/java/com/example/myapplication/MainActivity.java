package com.example.myapplication;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private View draggableElement;
    private int parentWidth;
    private int parentHeight;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FrameLayout parentLayout = findViewById(R.id.parentLayout);
        draggableElement = findViewById(R.id.draggableElement);

        
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int screenWidth = displayMetrics.widthPixels;
        int screenHeight = displayMetrics.heightPixels;

        
        int parentWidth = (int) (screenWidth * 0.8);
        int parentHeight = (int) (screenHeight * 0.8);

        
        int horizontalMargin = (screenWidth - parentWidth) / 2;
        int verticalMargin = (screenHeight - parentHeight) / 2;

       
        FrameLayout.LayoutParams parentParams = new FrameLayout.LayoutParams(parentWidth, parentHeight);
        parentParams.setMargins(horizontalMargin, verticalMargin, horizontalMargin, verticalMargin);
        parentLayout.setLayoutParams(parentParams);

        
        parentLayout.post(() -> {
            int maxWidth = parentLayout.getWidth() - draggableElement.getWidth();
            int maxHeight = parentLayout.getHeight() - draggableElement.getHeight();

            draggableElement.setOnTouchListener(new View.OnTouchListener() {
                float dX, dY;

                @SuppressLint("ClickableViewAccessibility")
                @Override
                public boolean onTouch(View view, MotionEvent event) {
                    switch (event.getAction()) {
                        case MotionEvent.ACTION_DOWN:
                            dX = view.getX() - event.getRawX();
                            dY = view.getY() - event.getRawY();
                            break;
                        case MotionEvent.ACTION_MOVE:
                            float newX = event.getRawX() + dX;
                            float newY = event.getRawY() + dY;

                            // Constrain within the parent layout
                            if (newX < 0) newX = 0;
                            if (newY < 0) newY = 0;
                            if (newX > maxWidth) newX = maxWidth;
                            if (newY > maxHeight) newY = maxHeight;

                            view.animate()
                                    .x(newX)
                                    .y(newY)
                                    .setDuration(0)
                                    .start();
                            break;
                        default:
                            return false;
                    }
                    return true;
                }
            });
        });
    }



}
