package com.prj.amrafeta.minesweeperhw.Model;


import com.prj.amrafeta.minesweeperhw.View.MineSweeperView;

public class MineSweeperModel {

    private static MineSweeperModel instance = null;

    private MineSweeperModel() {
    }

    public static MineSweeperModel getInstance() {
        if (instance == null) {
            instance = new MineSweeperModel();
        }

        return instance;
    }

    public static final short EMPTY = 0;
    public static final short NUMBERD = 1;
    public static final short BOMB = 2;
    public static final short FLAG = 3;



    private short[][] model = {
            {EMPTY, EMPTY, EMPTY,BOMB,EMPTY},
            {EMPTY, EMPTY, EMPTY,EMPTY,EMPTY},
            {BOMB, EMPTY, EMPTY,EMPTY,EMPTY},
            {EMPTY, EMPTY, EMPTY,EMPTY,EMPTY},
            {EMPTY, EMPTY, EMPTY,BOMB,EMPTY}
    };

    private short nextPlayer = EMPTY;

    public void resetModel() {
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                model[i][j] = EMPTY;
            }
        }
        createBomb();


        nextPlayer = EMPTY;
    }



    public void changeStatus(final short change) {
        nextPlayer = change;
    }


    public void setField(int x, int y, short player) {
        model[x][y] = player;
    }

    public short getField(int x, int y) {
        return model[x][y];
    }

    public short getNextPlayer() {
        return nextPlayer;
    }

    private void createBomb() {
        model[2][0]= BOMB;
        model[0][3]= BOMB;
        model[4][3]= BOMB;
    }
    public int bombNumber(){
        int counter=0;
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                if( model[i][j] == BOMB){
                    counter++;
                }
            }
        }
        return counter;
    }
}
