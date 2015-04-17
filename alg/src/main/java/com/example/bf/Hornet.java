package com.example.bf;

import java.awt.*;
import java.awt.geom.Arc2D;
import java.awt.image.*;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Hornet extends Bee {
    private int id;
    private double ratoteToAngle;
    private int outBoundTime = 0;
    int x,y;

    public Hornet(int id, int x, int y, double angle, boolean isAlive, Image img) {
        super(id, x, y, angle, isAlive, img);
        this.id = id;
        this.x = x;
        this.y = y;
        ratoteToAngle = angle;
    }

    public void search() {
        String valueStr = BeeFarming.search(id);

        boolean isCatching = checkHoneyBee(valueStr);
        if (!isCatching) {
            boolean isOutofBound = checkBound(valueStr);
        }else {
            if(valueStr.contains("*E~")||valueStr.contains("*N~")||valueStr.contains("*W~")||valueStr.contains("*S~")){
                outBoundTime++;
            }else {
                outBoundTime = 0;
            }
//            if(outBoundTime >= 4){
//                for(int i=0;i<10;i++)
//                    flying1(0);
//            }
        }

        if (ratoteToAngle != angle) {
            ratoteImage(ratoteToAngle);
        }
        setXYs(0);
        setXY();
    }



    /**
     * 如果黄蜂抓到了蜜蜂，则boolean dead==true，黄蜂可以根据dead的值判断蜜蜂知否被杀死。
     * 本方法可以修改，在BeeFarming的killBee方法中当蜜蜂被黄蜂消灭后将被调用
     */
    public boolean isCatched() {
        return true;
    }

    boolean isCatching = false;
    int catchingId = 0;
    private boolean checkHoneyBee(String valueStr) {
        String regexp = "(?<=\\+\\()(.*?)(?=\\))";
        Pattern p = Pattern.compile(regexp);
        Matcher m = p.matcher(valueStr);

        int isFindNum = 0;
        boolean lost = true;
        while (m.find()) {
            isFindNum++;
            System.out.println(m.group(0));
            String[] msg = m.group(1).split(",");
            double beeAngle = Double.parseDouble(msg[2]);
            double beeHeading = Double.parseDouble(msg[1]);

            double beeAngle1 = 90 - angle + beeAngle;
            double beeHeading1 = 90 - angle + beeHeading;
            while (beeAngle1 < 0) {
                beeAngle1 = beeAngle1 + 360;
            }
            while (beeAngle1 > 360) {
                beeAngle1 = beeAngle1 - 360;
            }
            while (beeHeading1 < 0) {
                beeHeading1 = beeHeading1 + 360;
            }
            while (beeHeading1 > 360) {
                beeHeading1 = beeHeading1 - 360;
            }


            if ((x < 80) || (y < 80) || (x > 740) || (y > 540))
                angle = beeAngle1 + 60 * Math.cos(Math.toRadians(90 - beeHeading1 + beeAngle1)) - 90 + angle;
            else
                angle = beeAngle1 + 30 * Math.cos(Math.toRadians(90 - beeHeading1 + beeAngle1)) - 90 + angle;

            ratoteToAngle = Double.parseDouble(msg[1]);
        }

        if (isFindNum>0){
            return true;
        }
        return false;
    }

    private boolean checkBound(String valueStr) {
        double b = correctvalue(angle + 180);
        double a = 0;
        double absAngle = 10;
        if (valueStr.contains("*E~")) {
            do {
                Random ra = new Random();
                a = ra.nextDouble() * 180 + 90;
            } while (Math.abs(b - a) < absAngle);

        } else if (valueStr.contains("*S~")) {
            do {
                Random ra = new Random();
                a = ra.nextDouble() * 180 + 180;
            } while (Math.abs(b - a) < absAngle);

        } else if (valueStr.contains("*W~")) {
            do {
                Random ra = new Random();
                a = ra.nextDouble() * 180 - 90;
                if (a < 0) a = 360 + a;
            } while (Math.abs(b - a) < absAngle);

        } else if (valueStr.contains("*N~")) {
            do {
                Random ra = new Random();
                a = ra.nextDouble() * 180;
            } while (Math.abs(b - a) < absAngle);
        } else {
            return false;
        }
        ratoteToAngle = a;
        return true;
    }

    public double correctvalue(double angle1) {
        if (angle1 < 0)
            while (angle1 < 0) angle1 += 360;
        if (angle1 >= 360)
            while (angle1 >= 360) angle1 -= 360;
        return angle1;
    }

    private void setXY(){
        int dx_sum = 0;
        int dy_sum = 0;
        int deltaX = (int) (18 * Math.cos(Math.toRadians(angle)));
        int deltaY = (int) (18 * Math.sin(Math.toRadians(angle)));
        int[] nextX = new int[9];
        int[] nextY = new int[9];
        for (int i = 0; i < 9; i++) {
            nextX[i] = (int) (deltaX * (i + 1) / 9.0) - dx_sum;
            nextY[i] = (int) (deltaY * (i + 1) / 9.0) - dy_sum;
            dx_sum += nextX[i];
            dy_sum += nextY[i];
        }
        x += dx_sum;
        y += dy_sum;
    }

}