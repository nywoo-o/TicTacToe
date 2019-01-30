package com.example.bernath.tictactoe;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private final int ROW = 3;
    private final int COLOUMN = 3;
    private Button[][] buttons = new Button[ROW][COLOUMN];
    private boolean player1Turn = true;

    private int roundCount = 0;
    private int player1Points = 0;
    private int player2Points = 0;

    private TextView textViewPlayer1;
    private TextView textViewPlayer2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textViewPlayer1 = findViewById(R.id.text_view_p1);
        textViewPlayer2 = findViewById(R.id.text_view_p2);

        for (int i = 0; i < ROW; ++i){
            for (int j = 0; j < COLOUMN;++j){
                String buttonId = "button_" + i + j;
                int resId = getResources().getIdentifier(buttonId, "id", getPackageName());
                buttons[i][j] = findViewById(resId);
                buttons[i][j].setOnClickListener(this);
            }
        }

        Button buttonReset = findViewById(R.id.button_reset);
        buttonReset.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view){
                player1Turn = true;
                roundCount = 0;
                player1Points = 0;
                player2Points = 0;
                textViewPlayer1.setText("Player1 : 0");
                textViewPlayer2.setText("Player2 : 0");
                resetButtons();
            }
        });
    }

    public void onClick(View view){
        if (!((Button)view).getText().toString().equals("")) return;
        //버튼의 string이 o, x 중 하나라면 -> 이미 사용한 버튼
        if (player1Turn){
            ((Button)view).setText("O");
        }else {
            ((Button)view).setText("X");
        }

        roundCount++;

        if (checkForWin()){
            if (player1Turn){
                player1Win();
            }
            else {
                player2Win();
            }
        } else if (roundCount == ROW*COLOUMN){
            draw();
        }  else {
           player1Turn = !player1Turn;
        }
    }

    private void draw() {
        Toast.makeText(this, "Draw!", Toast.LENGTH_SHORT).show();
        resetButtons();

    }

    private void player1Win() {
        player1Points++;
        Toast.makeText(this, "Player 1 wins!", Toast.LENGTH_SHORT).show();
        textViewPlayer1.setText("Player1 : "+player1Points);
        resetButtons();
    }


    private void player2Win() {
        player2Points++;
        Toast.makeText(this, "Player 2 wins!", Toast.LENGTH_SHORT).show();
        textViewPlayer2.setText("Player2 : " + player2Points);
        resetButtons();
    }

    private void resetButtons() {
        roundCount = 0;
        player1Turn = true;
        for (int i = 0; i < ROW; ++i){
            for (int j = 0; j < COLOUMN; ++j){
                buttons[i][j].setText("");
            }
        }
    }

    private boolean checkForWin() {
        String[][] field = new String[3][3];

        for (int i= 0; i < field.length; ++i){
            for (int j  = 0;  j < field[i].length; ++j){
                field[i][j] = buttons[i][j].getText().toString();
            }
        }

        for (int i = 0; i < field.length; ++i){
            if (field[i][0].equals("")) continue;
            if (field[i][0].equals(field[i][1]) && field[i][0].equals(field[i][2])) return true;
        }

        for (int i = 0; i < field.length; ++i){
            if (field[0][i].equals("")) continue;
            if (field[0][i].equals(field[1][i]) &&field[0][i].equals(field[2][i])) return true;
        }

        return (field[0][0].equals(field[1][1]) && field[0][0].equals(field[2][2]) && !field[0][0].equals("")) ||
                (field[0][2].equals(field[1][1]) && field[1][1].equals(field[2][0]) && !field[2][0].equals(""));
    }
}
