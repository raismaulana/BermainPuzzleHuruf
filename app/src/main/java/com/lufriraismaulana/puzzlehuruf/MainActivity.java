package com.lufriraismaulana.puzzlehuruf;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class MainActivity extends AppCompatActivity {

    private final int N = 4;
    private final int[][] BUTTON_IDS = {{R.id.btn1, R.id.btn2, R.id.btn3, R.id.btn4},
            {R.id.btn5, R.id.btn6, R.id.btn7, R.id.btn8},
            {R.id.btn9, R.id.btn10, R.id.btn11, R.id.btn12},
            {R.id.btn13, R.id.btn14, R.id.btn15, R.id.btn16}};
    private Button[][] button;
    private String[][] board;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        button = new Button[N][N];
        board = new String[N][N];
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                button[i][j] = this.findViewById(BUTTON_IDS[i][j]);
                button[i][j].setOnClickListener(onClickListener);
            }
        }
        startGame();
//        testWin();
    }

    View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            for (int i = 0; i < N; i++)
                for (int j = 0; j < N; j++)
                    if (view.getId() == BUTTON_IDS[i][j])
                        buttonFunction(i, j);
        }
    };

    void buttonFunction(int row, int column) {
        moveCard(row, column);
    }

    void moveCard(int boardX, int boardY) {
        int XKosong = -1, YKosong = -1, gap;
        for (int i = 0; i < N; i++)
            for (int j = 0; j < N; j++)
                if (board[i][j].equals("")) {
                    XKosong = i;
                    YKosong = j;
                }

        if (XKosong == boardX || YKosong == boardY) {
            if (!(XKosong == boardX && YKosong == boardY)) {
                if (XKosong == boardX) {
                    gap = boardY - YKosong;
                    if (gap == 1 || gap == -1) {
                        if (YKosong < boardY)
                            board[boardX][YKosong] = board[boardX][YKosong + 1];
                        else
                            board[boardX][YKosong] = board[boardX][YKosong - 1];

                        board[boardX][boardY] = "";
                    }
                }
                if (YKosong == boardY) {
                    gap = boardX - XKosong;
                    if (gap == 1 || gap == -1) {
                        if (XKosong < boardX)
                            board[XKosong][boardY] = board[XKosong + 1][boardY];
                        else
                            board[XKosong][boardY] = board[XKosong - 1][boardY];

                        board[boardX][boardY] = "";
                    }
                }


            }
        }

        showBoard();
    }

    void startGame() {
        String[] array = {"A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O"};
        Random rnd = ThreadLocalRandom.current();
        for (int i = array.length - 1; i > 0; i--) {
            int index = rnd.nextInt(i + 1);

            String temp = array[index];
            array[index] = array[i];
            array[i] = temp;
        }

        int index = 0;
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                if (index == array.length) {
                    button[i][j].setText("");
                    board[i][j] = "";
                    break;
                }
                button[i][j].setText(array[index]);
                board[i][j] = array[index];
                index++;
            }
        }
        checkFinish();
    }

    void showBoard() {
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                button[i][j].setText(board[i][j]);
            }
        }
        checkFinish();
    }

    void checkFinish() {
        String[] checker = {"A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O"};
        int index = 0;
        boolean win = false;

        outerloop:
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                if (index == checker.length) {
                    win = true;
                    break outerloop;
                }
                if (!board[i][j].equals(checker[index])) {
                    break outerloop;
                }
                index++;
            }
        }

        if (win) {
            Toast.makeText(MainActivity.this, "Anda Menang", Toast.LENGTH_LONG).show();
        }
    }

//    void testWin() {
//
//        String[] checker = {"A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O"};
//        int index = 0;
//        for (int i = 0; i < N; i++) {
//            for (int j = 0; j < N; j++) {
//                if (index == 15) {
//                    button[i][j].setText("");
//                    board[i][j] = "";
//                    break;
//                }
//                button[i][j].setText(checker[index]);
//                board[i][j] = checker[index];
//                index++;
//            }
//        }
//
//    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.optionmenu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.menu_ulangi) {
            startGame();
        } else if (item.getItemId() == R.id.menu_keluar) {
            finishAndRemoveTask();
        }
        return true;
    }
}