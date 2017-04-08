package com.company;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Scanner;

import static com.company.Func.*;

public class Main {

    public static void main(String[] args) {
        //double add = 0;
        double[] stock = new double[4];
        double[] req = new double[5];
        int[] u = new int[4];
        int[] v = new int[5];
        Box[][] boxes = new Box[4][5];
        Scanner n = null;
        try {
            n = new Scanner(new File("resources//data.txt"));
            for (int i = 0; i < 4; i++) {
                stock[i] = n.nextDouble();
            }
            for (int i = 0; i < 5; i++) {
                req[i] = n.nextDouble();
                //add+=0.00001;
            }
            //stock[0]+=add;
            for (int i = 0; i < 4; i++) {
                for (int j = 0; j < 5; j++) {
                    boxes[i][j] = new Box(n.nextInt());
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            n.close();
        }
        MethodMinEl(boxes, 4, 5, stock, req);
        PrintTable(boxes, 4, 5, req, stock, u, v);
        CheckCorrections(boxes, 4, 5);
        while (!FindPotential(boxes, u, v, 4, 5)) {
            CheckCorrections(boxes, 4, 5);
        }
        PrintTable(boxes, 4, 5, req, stock, u, v);
        /*Recount(boxes, 4, 5);
        PrintTable(boxes, 4, 5, req, stock, u, v);
        while(!FindPotential(boxes, u, v, 4, 5)){
            CheckCorrections(boxes, 4, 5);
        }
        PrintTable(boxes, 4, 5, req, stock, u, v);*/
        while (!CheckOpt(boxes, 4, 5)) {
            Recount(boxes, 4, 5);
            while (!FindPotential(boxes, u, v, 4, 5)) {
                CheckCorrections(boxes, 4, 5);
            }
            Clean(boxes, 4, 5);
            SetDelta(boxes, u, v);
            PrintTable(boxes, 4, 5, req, stock, u, v);
        }
        double coast = 0;
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 5; j++) {
                if (boxes[i][j].getState())
                    coast += boxes[i][j].GetPrice() * boxes[i][j].getProduct();
            }
        }
        System.out.println("Стоимость перевозки  = " + (int)coast);
    }
}
