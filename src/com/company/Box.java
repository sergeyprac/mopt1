package com.company;


public class Box {
    private int delta = 0;
    private int price;
    private double cntProduct;
    private boolean free = false;
    private int signOfBox = -1;
    private int cntTimesInBox = 0;

    Box(int price) {
        this.price = price;
    }


    public void AddCntTimesInBoc() {
        cntTimesInBox++;
    }
    public void setSign(int x) {
        signOfBox = x;
    }
    public void MinCntTimesInBoc() {
        cntTimesInBox--;
    }

    public void SetCntTimesInBox() {
        cntTimesInBox = 0;
    }

    public int GetCntTimesInBox() {
        return cntTimesInBox;
    }

    public int GetSign() {
        return signOfBox;
    }

    public void setCntProduct(double x) {
        cntProduct = x;
    }

    public int GetPrice() {
        return price;
    }

    public double getProduct() {
        return cntProduct;
    }

    public void SetTrue() {
        free = true;
    }

    public void SetFalse() {
        free = false;
    }

    public boolean getState() {
        return free;
    }

    public void SetDelta(int delta) {
        this.delta = delta;
    }

    public int GetDelta() {
        return delta;
    }
}
