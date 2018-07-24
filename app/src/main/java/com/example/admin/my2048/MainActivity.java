package com.example.admin.my2048;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    GridLayout gridLayout;
    float startX = 0, startY = 0, endX, endY;
    Maps maps = new Maps();
    private TextView score;
    private TextView best;

    void init() {
        gridLayout = (GridLayout)findViewById(R.id.grid);

        for(int i = 0; i < 4; i++) {
            for(int j = 0; j < 4; j++) {
                Button btn = new Button(this);
                btn.setClickable(false);
                btn.setText("");
                btn.setTextSize(30);
                btn.setWidth(100);

                btn.setHeight(270);
                btn.setTypeface(Typeface.DEFAULT_BOLD);

                GridLayout.Spec rowSpec = GridLayout.spec(i+2);
                GridLayout.Spec columnSpec = GridLayout.spec(j);
               // String msg = "rowSpec: "+ (i+2) + "-columnSpec: "+j;
                GridLayout.LayoutParams params = new GridLayout.LayoutParams(rowSpec, columnSpec);

                gridLayout.addView(btn,params);
                maps.addButton(i, j, btn);
            }
        }

        score = (TextView)findViewById(R.id.score);
        score.setText("0");
        best = (TextView)findViewById(R.id.best);
        maps.setScore(score);
        maps.setBest(best);
        maps.init();


    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        int action = event.getAction();
        if(action == MotionEvent.ACTION_DOWN) {
            startX = event.getX();
            startY = event.getY();
        }else if(action == MotionEvent.ACTION_UP) {
            endX = event.getX();
            endY = event.getY();
            int direction = GetSlideDirection(startX, startY, endX, endY);
            boolean gameOver = maps.Slide(direction);
            if(gameOver) {
                if(maps.getScore() > maps.getBestScore()) {
                    Toast.makeText(this,"恭喜你超过最佳纪录！！！",Toast.LENGTH_LONG).show();
                    maps.setBestScore(maps.getScore());
                    best.setText(maps.getScore()+"");
                }else {
                    Toast.makeText(this,"gameOver",Toast.LENGTH_LONG).show();
                }
            }
        }
        return super.dispatchTouchEvent(event);
    }

    private int GetSlideDirection(float startX, float startY, float endX, float endY) {
        float dy = startY - endY;
        float dx = endX - startX;
        int result = Direction.NONE;

        if(Math.abs(dx) < 2 && Math.abs(dy) < 2) {
            return result;
        }
        double angle = getSlideAngle(dx, dy);
        if(angle >= -45 && angle <= 45) {
            return Direction.RIGHT;
        }else if(angle >= 45 && angle < 135) {
            return Direction.UP;
        }else if(angle >= -135 && angle < -45) {
            return Direction.DOWN;
        }else if((angle >=135 && angle <= 180) || (angle >= -180 && angle < -135)) {
            return Direction.LEFT;
        }

        return result;

    }

    private double getSlideAngle(float dx, float dy) {
        return Math.atan2(dy,dx) * 180 / Math.PI;
    }

    public void reset(View view) {
       maps.init();
    }
}
