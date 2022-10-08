package com.example.tictac;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {


    private int gridsize;
    TableLayout gameBoard;
    TextView txtturn;
    char [][] board;
    char turn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        gridsize = Integer.parseInt(getString(R.string.size_of_board));
        board = new char [gridsize][gridsize];
        gameBoard = (TableLayout) findViewById(R.id.mainBoard);
        txtturn = (TextView) findViewById(R.id.turn);

        resetBoard();
        txtturn.setText("Turn: "+turn);

        for(int i = 0; i< gameBoard.getChildCount(); i++){
            TableRow row = (TableRow) gameBoard.getChildAt(i);
            for(int j = 0; j<row.getChildCount(); j++){
                TextView tv = (TextView) row.getChildAt(j);
                tv.setText(R.string.none);
                tv.setOnClickListener(Move(i, j, tv));
            }
        }

        Button reset_btn = (Button) findViewById(R.id.reset);
        reset_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent current = getIntent();
                finish();
                startActivity(current);
            }
        });
    }

    protected void resetBoard(){
        turn = 'X';
        for(int i = 0; i< gridsize; i++){
            for(int j = 0; j< gridsize; j++){
                board[i][j] = ' ';
            }
        }
    }

    protected int gameStatus(){

        int rowX = 0, colX = 0, rowO = 0, colO = 0;
        for(int i = 0; i< gridsize; i++){
            if(checkRow(i,'X'))
                return 1;
            if(checkColumn(i, 'X'))
                return 1;
            if(checkRow(i,'O'))
                return 2;
            if(checkColumn(i,'O'))
                return 2;
            if(checkDiagonal('X'))
                return 1;
            if(checkDiagonal('O'))
                return 2;
        }

        boolean boardFull = true;
        for(int i = 0; i< gridsize; i++){
            for(int j = 0; j< gridsize; j++){
                if(board[i][j]==' ')
                    boardFull = false;
            }
        }
        if(boardFull)
            return -1;
        else return 0;
    }

    protected boolean checkDiagonal(char p){
        int count_Equal1 = 0,count_Equal2 = 0;
        for(int i = 0; i< gridsize; i++)
            if(board[i][i]==p)
                count_Equal1++;
        for(int i = 0; i< gridsize; i++)
            if(board[i][gridsize -1-i]==p)
                count_Equal2++;
        if(count_Equal1== gridsize || count_Equal2== gridsize)
            return true;
        else return false;
    }

    protected boolean checkRow(int q, char p){
        int countEqual=0;
        for(int i = 0; i< gridsize; i++){
            if(board[q][i]==p)
                countEqual++;
        }

        if(countEqual== gridsize)
            return true;
        else
            return false;
    }

    protected boolean checkColumn(int q, char player){
        int countEqual=0;
        for(int i = 0; i< gridsize; i++){
            if(board[i][q]==player)
                countEqual++;
        }

        if(countEqual== gridsize)
            return true;
        else
            return false;
    }

    protected boolean CellSet(int q, int r){
        return !(board[q][r]==' ');
    }

    protected void stopMatch(){
        for(int i = 0; i< gameBoard.getChildCount(); i++){
            TableRow row = (TableRow) gameBoard.getChildAt(i);
            for(int j = 0; j<row.getChildCount(); j++){
                TextView tv = (TextView) row.getChildAt(j);
                tv.setOnClickListener(null);
            }
        }
    }

    View.OnClickListener Move(final int q, final int r, final TextView tv){

        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(!CellSet(q,r)) {
                    board[q][r] = turn;
                    if (turn == 'X') {
                        tv.setText(R.string.X);
                        turn = 'O';
                    } else if (turn == 'O') {
                        tv.setText(R.string.O);
                        turn = 'X';
                    }
                    if (gameStatus() == 0) {
                        txtturn.setText("Turn: " + turn);
                    }
                    else if(gameStatus() == -1){
                        txtturn.setText("This is a Draw match");
                        stopMatch();
                    }
                    else{
                        txtturn.setText(turn+" Loses!");
                        stopMatch();
                    }
                }
                else{
                    txtturn.setText(txtturn.getText()+" Choose another Call");

                }
            }
        };
    }

}