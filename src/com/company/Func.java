package com.company;

import java.util.Arrays;
import java.util.Random;

/**
 * Created by Сергей on 08.03.2017.
 */
public class Func {
    enum Direction {
        left, up, right, down;

        public Direction add() {
            switch (this) {
                case left:
                    return up;
                case right:
                    return down;
                case up:
                    return right;
                case down:
                    return left;
                default:
                    break;
            }
            return null;
        }

        public Direction min() {
            switch (this) {
                case left:
                    return down;
                case right:
                    return up;
                case up:
                    return left;
                case down:
                    return right;
                default:
                    break;
            }
            return null;
        }
        public void print() {
            switch (this) {
                case left:
                    System.out.println("left");
                    break;
                case right:
                    System.out.println("right");
                    break;
                case up:
                    System.out.println("up");
                    break;
                case down:
                    System.out.println("down");
                    break;
                default:
                    break;
            }
        }
    }

    public static int minCycleLenght = 100, cycleLenght = 0, firstStr = 0, firstClmn = 0;

    public static void FreeCntTimesInBox(Box[][] boxes, int m, int n) {
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                boxes[i][j].SetCntTimesInBox();
                boxes[i][j].setSign(-1);
                boxes[i][j].SetCntTimesInBox();
            }
        }
    }

    public static void Recount(Box[][] boxes, int m, int n) {
        int numMinBoxStr = 0, numMinBoxClmn = 0;
        Box box = boxes[0][0];
        FreeCntTimesInBox(boxes, m, n);
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (boxes[i][j].GetDelta() < box.GetDelta()) {
                    box = boxes[i][j];
                    numMinBoxStr = i;
                    numMinBoxClmn = j;
                }
            }
        }
        cycleLenght = 0;
        minCycleLenght = 100;
        firstClmn = numMinBoxClmn;
        firstStr = numMinBoxStr;
        FindCycleLenght(boxes, numMinBoxStr, numMinBoxClmn, null);
        Coordinates[] cycle = new Coordinates[minCycleLenght];
        cycleLenght = 0;
        CreateCycle(boxes, numMinBoxStr, numMinBoxClmn, null, cycle);
        FillTable(boxes, cycle);
    }

    public static void FillTable(Box[][] boxes, Coordinates[] cycle) {
        Box box = box = boxes[cycle[0].GetY()][cycle[0].GetX()];
        double delta = 100;
        int sign = 1;
        box.setSign(sign);
        for (int i = 1; i < minCycleLenght; i++) {
            box = boxes[cycle[i].GetY()][cycle[i].GetX()];
            if (box.getState() && cycle[i].GetDir()) {
                box.setSign(sign);
                if (sign == 0) {
                    delta = box.getProduct();
                }
                if (sign == 1) sign = 0;
                else sign = 1;
            }
        }
        for (int i = 0; i < minCycleLenght; i++) {
            box = boxes[cycle[i].GetY()][cycle[i].GetX()];
            if (box.GetSign() == 0 && delta < box.getProduct()) {
                delta = box.getProduct();
            }
        }
        for (int i = 0; i < minCycleLenght; i++) {
            box = boxes[cycle[i].GetY()][cycle[i].GetX()];
            if (sign == 0) {
                box.setCntProduct(box.getProduct() - delta);
            }
            if (sign == 1) {
                box.setCntProduct(box.getProduct() + delta);
            }
        }
        for (int i = 0; i < minCycleLenght; i++) {
            box = boxes[cycle[i].GetY()][cycle[i].GetX()];
            if(box.getProduct() == 0)
                box.SetFalse();
        }
    }

    public static void CreateCycle(Box[][] boxes, int m, int n, Direction direction, Coordinates[] cycle) {
        cycleLenght++;
        cycle[cycleLenght - 1].SetX(n);
        cycle[cycleLenght - 1].SetY(m);
        int str = 0, clmn = 0;
        if (direction == null) {
            if (m - 1 >= 0) {
                FindCycleLenght(boxes, m - 1, n, Direction.up);
            }
            if (m + 1 < 4) {
                FindCycleLenght(boxes, m + 1, n, Direction.down);
            }
            if (n - 1 >= 0) {
                FindCycleLenght(boxes, m, n - 1, Direction.left);
            }
            if (n + 1 < 5) {
                FindCycleLenght(boxes, m - 1, n, Direction.right);
            }
        } else {
            if (firstClmn == n && firstStr == m) {
                if (minCycleLenght == cycleLenght) {
                    return;
                }
            } else {
                if (cycleLenght < minCycleLenght) {
                    if (!boxes[m][n].getState()) {
                        boxes[m][n].AddCntTimesInBoc();
                        str = m;
                        clmn = n;
                        switch (direction) {
                            case down:
                                str = m + 1;
                                break;
                            case up:
                                str = m - 1;
                                break;
                            case left:
                                clmn = n - 1;
                                break;
                            case right:
                                clmn = n + 1;
                                break;
                            default:
                                break;
                        }
                        if (str < 4 && str >= 0 && clmn < 5 && clmn >= 0 && !(boxes[str][clmn].getState() && boxes[str][clmn].GetCntTimesInBox() > 0)) {
                            FindCycleLenght(boxes, str, clmn, direction);
                        }
                    } else {
                        str = m;
                        clmn = n;
                        switch (direction) {
                            case down:
                                str = m + 1;
                                break;
                            case up:
                                str = m - 1;
                                break;
                            case left:
                                clmn = n - 1;
                                break;
                            case right:
                                clmn = n + 1;
                                break;
                            default:
                                break;
                        }
                        if (str < 4 && str >= 0 && clmn < 5 && clmn >= 0 && !(boxes[str][clmn].getState() && boxes[str][clmn].GetCntTimesInBox() > 0)) {
                            if (cycle[cycleLenght].GetDir())
                                cycle[cycleLenght].ChangeDircthn();
                            FindCycleLenght(boxes, str, clmn, direction);
                        }
                        str = m;
                        clmn = n;
                        switch (direction.min()) {
                            case down:
                                str = m + 1;
                                break;
                            case up:
                                str = m - 1;
                                break;
                            case left:
                                clmn = n - 1;
                                break;
                            case right:
                                clmn = n + 1;
                                break;
                            default:
                                break;
                        }

                        if (str < 4 && str >= 0 && clmn < 5 && clmn >= 0 && !(boxes[str][clmn].getState() && boxes[str][clmn].GetCntTimesInBox() > 0)) {
                            if (!cycle[cycleLenght].GetDir())
                                cycle[cycleLenght].ChangeDircthn();
                            FindCycleLenght(boxes, str, clmn, direction.min());
                        }
                        str = m;
                        clmn = n;
                        switch (direction.add()) {
                            case down:
                                str = m + 1;
                                break;
                            case up:
                                str = m - 1;
                                break;
                            case left:
                                clmn = n - 1;
                                break;
                            case right:
                                clmn = n + 1;
                                break;
                            default:
                                break;
                        }
                        if (str < 4 && str >= 0 && clmn < 5 && clmn >= 0 && !(boxes[str][clmn].getState() && boxes[str][clmn].GetCntTimesInBox() > 0)) {

                            if (!cycle[cycleLenght].GetDir())
                                cycle[cycleLenght].ChangeDircthn();
                            FindCycleLenght(boxes, str, clmn, direction.add());
                        }
                        boxes[m][n].MinCntTimesInBoc();
                    }
                }
            }
        }
        cycleLenght--;
    }

    public static void FindCycleLenght(Box[][] boxes, int m, int n, Direction direction) {
        cycleLenght++;
        int str = 0, clmn = 0;
        if (direction == null) {
            if (m - 1 >= 0) {
                FindCycleLenght(boxes, m - 1, n, Direction.up);
            }
            if (m + 1 < 4) {
                FindCycleLenght(boxes, m + 1, n, Direction.down);
            }
            if (n - 1 >= 0) {
                FindCycleLenght(boxes, m, n - 1, Direction.left);
            }
            if (n + 1 < 5) {
                FindCycleLenght(boxes, m - 1, n, Direction.right);
            }
        } else {
            direction.print();
            if (firstClmn == n && firstStr == m) {
                minCycleLenght = cycleLenght;
            } else {
                if (cycleLenght < minCycleLenght) {
                    if (!boxes[m][n].getState()) {
                        str = m;
                        clmn = n;
                        switch (direction) {
                            case down:
                                str = m + 1;
                                break;
                            case up:
                                str = m - 1;
                                break;
                            case left:
                                clmn = n - 1;
                                break;
                            case right:
                                clmn = n + 1;
                                break;
                            default:
                                break;
                        }
                        if (str < 4 && str >= 0 && clmn < 5 && clmn >= 0 && !(boxes[str][clmn].getState() && boxes[str][clmn].GetCntTimesInBox() > 0)) {
                            boxes[m][n].AddCntTimesInBoc();
                            FindCycleLenght(boxes, str, clmn, direction);
                            boxes[m][n].MinCntTimesInBoc();
                        }
                    } else {
                        str = m;
                        clmn = n;
                        switch (direction) {
                            case down:
                                str = m + 1;
                                break;
                            case up:
                                str = m - 1;
                                break;
                            case left:
                                clmn = n - 1;
                                break;
                            case right:
                                clmn = n + 1;
                                break;
                            default:
                                break;
                        }
                        if (str < 4 && str >= 0 && clmn < 5 && clmn >= 0 && !(boxes[str][clmn].getState() && boxes[str][clmn].GetCntTimesInBox() > 0)) {
                            boxes[m][n].AddCntTimesInBoc();
                            FindCycleLenght(boxes, str, clmn, direction);
                            boxes[m][n].MinCntTimesInBoc();
                        }
                        str = m;
                        clmn = n;
                        switch (direction.min()) {
                            case down:
                                str = m + 1;
                                break;
                            case up:
                                str = m - 1;
                                break;
                            case left:
                                clmn = n - 1;
                                break;
                            case right:
                                clmn = n + 1;
                                break;
                            default:
                                break;
                        }
                        if (str < 4 && str >= 0 && clmn < 5 && clmn >= 0 && !(boxes[str][clmn].getState() && boxes[str][clmn].GetCntTimesInBox() > 0)) {
                            boxes[m][n].AddCntTimesInBoc();
                            FindCycleLenght(boxes, str, clmn, direction.min());
                            boxes[m][n].MinCntTimesInBoc();
                        }
                        str = m;
                        clmn = n;
                        switch (direction.add()) {
                            case down:
                                str = m + 1;
                                break;
                            case up:
                                str = m - 1;
                                break;
                            case left:
                                clmn = n - 1;
                                break;
                            case right:
                                clmn = n + 1;
                                break;
                            default:
                                break;
                        }
                        if (str < 4 && str >= 0 && clmn < 5 && clmn >= 0 && !(boxes[str][clmn].getState() && boxes[str][clmn].GetCntTimesInBox() > 0)) {
                            boxes[m][n].AddCntTimesInBoc();
                            FindCycleLenght(boxes, str, clmn, direction.add());
                            boxes[m][n].MinCntTimesInBoc();
                        }
                    }
                }
            }
        }
        cycleLenght--;
    }

    public static void MethodMinEl(Box[][] boxes, int m, int n, double[] stock1, double[] req1) {
        Box box;
        double[] req = Arrays.copyOf(req1, req1.length);
        double[] stock = Arrays.copyOf(stock1, stock1.length);
        int x = 0, y = 0;
        for (int i = 0; i < n; i++) {
            box = boxes[0][i];
            x = i;
            y = 0;
            while (req[i] != 0) {
                for (int j = 0; j < m; j++) {
                    if (boxes[j][i].GetPrice() <= box.GetPrice() && stock[j] != 0) {
                        box = boxes[j][i];
                        x = i;
                        y = j;
                    }
                }
                if (req[x] >= stock[y]) {
                    box.setCntProduct(stock[y]);
                    box.SetTrue();
                    req[x] -= stock[y];
                    stock[y] = 0;
                } else {
                    box.setCntProduct(req[x]);
                    box.SetTrue();
                    stock[y] -= req[x];
                    req[x] = 0;
                }
                if (stock[y] == 0) {
                    for (int j = 0; j < m; j++) {
                        if (stock[j] != 0) {
                            box = boxes[j][i];
                            x = i;
                            y = j;
                        }
                    }
                }
            }
        }
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (boxes[i][j].getProduct() == 0)
                    boxes[i][j].SetFalse();
            }
        }
    }

    public static void PrintTable(Box[][] boxes, int m, int n, double[] req, double[] stock, int[] u, int[] v) {
        System.out.println("\t\t\tB1      B2      B3      B4      B5      Зап.    u");
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (j == 0) {
                    System.out.print("A" + (i + 1) + "\t\t\t");
                }
                if (boxes[i][j].getState())
                    System.out.print(boxes[i][j].GetPrice() + ", " + (int) boxes[i][j].getProduct() + "\t");
                else
                    System.out.print(boxes[i][j].GetPrice() + ", /\t");
                if (j == n - 1) {
                    System.out.print((int) stock[i] + " \t\t" + u[i] + " \t");
                }
            }
            System.out.println("\n");
            for (int j = 0; j < n; j++) {
                if (j == 0) {
                    System.out.print("\t\t\t");
                }
                if (boxes[i][j].getState()) {
                    System.out.print("/, ");
                } else {
                    System.out.print(boxes[i][j].GetDelta() + ", ");
                }
                if (boxes[i][j].GetSign() == -1)
                    System.out.print("/\t");
                else {
                    if (boxes[i][j].GetSign() == 1) {
                        System.out.print("+\t");
                    } else {
                        System.out.print("-\t");
                    }
                }
            }
            System.out.println("\n");
        }
        System.out.print("Потреб.\t\t");
        for (int i = 0; i < n; i++) {
            System.out.print((int) req[i] + " \t\t");
        }
        System.out.println();
        System.out.print("v\t\t\t");
        for (int i = 0; i < n; i++) {
            System.out.print(v[i] + " \t\t");
        }
        System.out.println("\n");
        System.out.println("////////////////////////////////////////////////////////////////////\n");

    }

    public static void CheckCorrections(Box[][] boxes, int m, int n) {
        int cntBaseBoxes = 0;
        int x = 0;
        int y = 0;
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (boxes[i][j].getState())
                    cntBaseBoxes++;
            }
        }
        while (m + n - 1 > cntBaseBoxes) {
            Random rnd = new Random(System.currentTimeMillis());
            x = rnd.nextInt(n + 1);
            y = rnd.nextInt(m + 1);
            if (!boxes[y][x].getState()) {
                boxes[y][x].SetTrue();
                boxes[y][x].setCntProduct(0);
                cntBaseBoxes++;
            }
        }
    }

    public static boolean CheckOpt(Box[][] boxes, int m, int n) {
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (!boxes[i][j].getState() && boxes[i][j].GetDelta() < 0)
                    return false;
            }
        }
        return true;
    }

    public static int CheckCntFreePtnl(boolean[] u, boolean[] v) {
        int cnt = 0;
        for (int i = 0; i < u.length; i++) {
            if (u[i] == false) {
                cnt++;
            }
        }
        for (int i = 0; i < v.length; i++) {
            if (v[i] == false) {
                cnt++;
            }
        }
        return cnt;
    }

    public static void FindPotentialU(Box[][] boxes, int[] v, boolean[] checkV, int strNum, int value) {
        for (int i = 0; i < v.length; i++) {
            if (boxes[strNum][i].getState() && checkV[i] == false) {
                v[i] = boxes[strNum][i].GetPrice() - value;
                checkV[i] = true;
            }
        }
    }

    public static void FindPotentialV(Box[][] boxes, int[] u, boolean[] checkU, int clmnNum, int value) {
        for (int i = 0; i < u.length; i++) {
            if (boxes[i][clmnNum].getState() && checkU[i] == false) {
                u[i] = boxes[i][clmnNum].GetPrice() - value;
                checkU[i] = true;
            }
        }
    }

    public static void SetDelta(Box[][] boxes, int[] u, int[] v) {
        for (int i = 0; i < u.length; i++) {
            for (int j = 0; j < v.length; j++) {
                boxes[i][j].SetDelta(0);
            }
        }
        for (int i = 0; i < u.length; i++) {
            for (int j = 0; j < v.length; j++) {
                if (!boxes[i][j].getState()) {
                    boxes[i][j].SetDelta(boxes[i][j].GetPrice() - (v[j] - u[i]));
                }
            }
        }
    }

    public static boolean FindPotential(Box[][] boxes, int[] u, int[] v, int m, int n) {
        boolean[] checkU = new boolean[m];
        boolean[] checkV = new boolean[n];
        int predCheckCntFreePtnl = 0, nextCheckCntFreePtnl = 0;
        u[0] = 0;
        checkU[0] = true;
        while ((predCheckCntFreePtnl != (nextCheckCntFreePtnl = CheckCntFreePtnl(checkU, checkV))) && nextCheckCntFreePtnl != 0) {
            predCheckCntFreePtnl = nextCheckCntFreePtnl;
            for (int i = 0; i < u.length; i++) {
                if (checkU[i] == true) {
                    FindPotentialU(boxes, v, checkV, i, u[i]);
                }
            }
            for (int i = 0; i < v.length; i++) {
                if (checkV[i] == true) {
                    FindPotentialV(boxes, u, checkU, i, v[i]);
                }
            }
        }
        if (CheckCntFreePtnl(checkU, checkV) != 0) {
            return false;
        } else {
            SetDelta(boxes, u, v);
            return true;
        }
    }
}
