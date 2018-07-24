package com.example.admin.my2048;

import android.content.SharedPreferences;
import android.widget.Button;
import android.widget.TextView;



import java.util.Random;

/**
 * Created by admin on 2018/6/5.
 */

public class Maps {
    private TextView score,best;
    private Button[][] maps = new Button[4][4];

    public void init() {
        for(int i = 0;i < 4;i++) {
            for(int j = 0;j < 4;j++) {
                maps[i][j].setText("");
            }
        }

        score.setText("0");
        addNumber();
        addNumber();
    }

    private void addNumber() {
        Random random = new Random();
        int x = random.nextInt(4);
        int y = random.nextInt(4);
        int number = random.nextInt(20);
        if(number == 0) {
            number = 4;
        }else {
            number = 2;
        }

        while (maps[x][y].getText().toString().length() != 0) {
            x = random.nextInt(4);
            y = random.nextInt(4);
        }

        maps[x][y].setText(number+"");


    }

    private boolean isFull() {
        for(int i = 0; i < 4; i++) {
            for(int j = 0; j < 4; j++) {
                if(maps[i][j].getText().toString().length() == 0) {
                    return false;
                }
            }
        }
        return true;
    }

    public boolean Slide(int direction) {
        if(direction == Direction.LEFT) {
            left_remove_blank();
            left();
            addNumber();
            if(isDead()){
                return true;
            }
        }else if(direction == Direction.RIGHT) {
            right_remove_blank();
            right();
            addNumber();
            if(isDead()){
                return true;
            }
        }else if(direction == Direction.UP) {
            up_remove_blank();
            up();
            addNumber();
            if(isDead()){
                return true;
            }
        }else if(direction == Direction.DOWN) {
            down_remove_blank();
            down();
            addNumber();
            if(isDead()){
                return true;
            }
        }
        return false;
    }

    public boolean isDead() {
        for(int i = 0; i< 4; i++) {
            for(int j = 0; j < 4; j++) {
                if(i-1>=0) {
                    if(maps[i-1][j].getText().toString().length() == 0
                            || maps[i][j].getText().toString()
                            .equals(maps[i-1][j].getText().toString())) return false;
                }
                if(i+1<4) {
                    if(maps[i+1][j].getText().toString().length() == 0
                            || maps[i][j].getText().toString()
                            .equals(maps[i+1][j].getText().toString())) return false;
                }
                if(j-1>=0) {
                    if(maps[i][j-1].getText().toString().length() == 0
                            || maps[i][j].getText().toString()
                            .equals(maps[i][j-1].getText().toString())) return false;
                }
                if(j+1<4) {
                    if(maps[i][j+1].getText().toString().length() == 0
                            || maps[i][j].getText().toString()
                            .equals(maps[i][j+1].getText().toString())) return false;
                }

            }
        }
        return true;
    }

    private void left() {
        int i,j;
        for (i = 0; i < 4; i++) {
            for (j = 0; j < 3; j++) {
                String s1 = maps[i][j].getText().toString();
                String s2 = maps[i][j+1].getText().toString();
                if(s1.equals(s2) && !s1.equals("")) {
                    Integer sum = Integer.valueOf(s1);
                    sum += Integer.valueOf(s2);
                    int total = Integer.valueOf(score.getText().toString());
                    score.setText(String.valueOf(sum+total));
                    maps[i][j].setText(sum.toString());
                    maps[i][j+1].setText("");
                    left_remove_blank();
                }
            }
        }
    }

    private void right() {
        int i,j;
        for (i = 0; i < 4; i++) {
            for (j = 3; j >= 1; j--) {
                String s1 = maps[i][j].getText().toString();
                String s2 = maps[i][j-1].getText().toString();
                if(s1.equals(s2) && !s1.equals("")) {
                    Integer sum = Integer.valueOf(s1);
                    sum += Integer.valueOf(s2);
                    Integer total = Integer.valueOf(score.getText().toString());
                    score.setText(String.valueOf(sum+total));
                    maps[i][j].setText(sum.toString());
                    maps[i][j-1].setText("");
                    right_remove_blank();
                }
            }
        }
    }

    private void up() {
        int i,j;
        for (j = 0; j < 4; j++) {
            for (i = 0; i < 3; i++) {
                String s1 = maps[i][j].getText().toString();
                String s2 = maps[i+1][j].getText().toString();
                if(s1.equals(s2) && !s1.equals("")) {
                    Integer sum = Integer.valueOf(s1);
                    sum += Integer.valueOf(s2);
                    Integer total = Integer.valueOf(score.getText().toString());
                    score.setText(String.valueOf(sum+total));
                    maps[i][j].setText(sum.toString());
                    maps[i+1][j].setText("");
                    up_remove_blank();
                }
            }
        }
    }

    private void down() {
        int i,j;
        for (j = 0; j < 4; j++) {
            for (i = 3; i >= 1; i--) {
                String s1 = maps[i][j].getText().toString();
                String s2 = maps[i-1][j].getText().toString();
                if(s1.equals(s2) && !s1.equals("")) {
                    Integer sum = Integer.valueOf(s1);
                    sum += Integer.valueOf(s2);
                    Integer total = Integer.valueOf(score.getText().toString());
                    score.setText(String.valueOf(sum+total));
                    maps[i][j].setText(sum.toString());
                    maps[i-1][j].setText("");
                    down_remove_blank();
                }
            }
        }
    }

    private void left_remove_blank() {
        int i,j,k;
        for (i = 0; i < 4; i++) {
            for (j = 1; j < 4; j++) {
                k = j;
                while (k-1 >= 0 && maps[i][k-1].getText().toString().length() == 0) {
                    swapText(maps[i][k], maps[i][k-1]);
                    k--;
                }
            }
        }
    }

    private void right_remove_blank() {
        int i,j,k;
        for (i = 0; i < 4; i++) {
            for (j = 2; j >= 0; j--) {
                k = j;
                while (k+1 <= 3 && maps[i][k+1].getText().toString().length() == 0) {
                    swapText(maps[i][k], maps[i][k+1]);
                    k++;
                }
            }
        }
    }

    private void up_remove_blank() {
        int i,j,k;
        for (j = 0; j < 4; j++) {
            for (i = 1; i < 4; i++) {
                k = i;
                while (k-1 >= 0 && maps[k-1][j].getText().toString().length() == 0) {
                    swapText(maps[k][j], maps[k-1][j]);
                    k--;
                }
            }
        }
    }

    private void down_remove_blank() {
        int i,j,k;
        for (j = 0; j < 4; j++) {
            for (i = 2; i >= 0; i--) {
                k = i;
                while (k+1 <= 3 && maps[k+1][j].getText().toString().length() == 0) {
                    swapText(maps[k][j], maps[k+1][j]);
                    k++;
                }
            }
        }
    }



    private void swapText(Button bt1, Button bt2) {
        CharSequence text = bt1.getText();

        bt1.setText(bt2.getText());
        bt2.setText(text);

    }

    public void addButton(int i, int j, Button btn) {
       maps[i][j] = btn;
    }

    public void setScore(TextView score) {
        this.score = score;
    }

    public void setBest(TextView best) {
        this.best = best;
        best.setText(getBestScore()+"");
    }

    public int getScore() {
        return Integer.valueOf(score.getText().toString());
    }

    public int getBestScore() {
        SharedPreferences sp = best.getContext().getSharedPreferences("bestScore",0);
        int bestScore = sp.getInt("bestScore",0);
        return bestScore;
    }

    public void setBestScore(int score) {
        SharedPreferences sp = best.getContext().getSharedPreferences("bestScore",0);
        SharedPreferences.Editor edit = sp.edit();
        edit.putInt("bestScore",score);
        edit.commit();
    }

}
