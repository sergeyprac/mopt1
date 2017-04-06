package com.company;

/**
 * Created by Сергей on 05.04.2017.
 */
public class Coordinates {
    private int x, y;
    private boolean changeDircthn = false;

    public int GetX() {
        return x;
    }

    public void ChangeDircthn() {
        if (changeDircthn) {
            changeDircthn = false;
        } else
            changeDircthn = true;
    }

    public boolean GetDir() {
        return changeDircthn;
    }

    public int GetY() {
        return y;
    }

    public void SetX(int x) {
        this.x = x;
    }

    public void SetY(int x) {
        this.y = y;
    }
}
