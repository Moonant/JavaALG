package com.example.bf;

import java.awt.*;
import java.util.*;

public class Hornet2 extends Bee {
    private int id;
    private boolean dead = false;
    private boolean seen = false;

    private int x, y;
    private int num = 0;
    private String strVision, strVision1;
    private String[] info;
    private String[] beeInfos;
    private String[] flwInfos;
    private String flwInfo = new String();
    private String beeInfo = new String();
    private int flwVolumn, beeId;
    private double flwAngle, beeAngle, beeHeading, beeAngle1, beeHeading1;
    private int[] nextX = new int[9];
    private int[] nextY = new int[9];
    private int dx_sum = 0;
    private int dy_sum = 0;
    private int deltaX, deltaY;
    private double newAngle;
    private int maxVolumn;
    private int chasingId = 9;//记录当前目标
    private Random ra = new Random();

    public Hornet2(int id, int x, int y, double angle, boolean isAlive, Image img) {
        super(id, x, y, angle, isAlive, img);
        this.id = id;
    }

    /**此方法是需要重写的核心代码，蜜蜂采蜜的主要个性在此类体现*/
    /**
     * 此方法是需要重写的核心代码，蜜蜂采蜜的主要个性在此类体现
     */
    public void search() {
        strVision = BeeFarming.search(id);
        BeeFarming.update(new FlyingStatus(id, x, y, angle + 180, true, 0));
        strVision1 = BeeFarming.search(id);
        maxVolumn = 0;
        newAngle = 0;
        if (strVision.length() > 6 )
        {
            num = 0;
            beeInfo = beeInfo.substring(beeInfo.length());
            info = strVision.split("~", 0);
            for (int i = 0; i < info.length; i++) {
                if (info[i].indexOf('+') == 0) {
                    beeInfo = beeInfo.concat(info[i]);
                }
            }
            beeInfos = beeInfo.split("[()]");
            if (beeInfos.length > 1) {
                for (int i = 0; i < beeInfos.length; i++) {
                    if ((beeInfos[i].indexOf('+') != 0) && (beeInfos[i].length() > 0)) {

                        beeId = Integer.parseInt(beeInfos[i].substring(0, beeInfos[i].indexOf(',')));
                        //没目标时，重新锁定目标
                        if (seen == false) {
                            chasingId = beeId;
                            seen = true;
                        }
                        //有目标时，只追当前目标对象
                        if ((seen == true) && (beeId == chasingId)) {
                            beeAngle = Double.parseDouble(beeInfos[i].substring(beeInfos[i].indexOf(',') + 1, beeInfos[i].indexOf(',', 4)));
                            beeHeading = Double.parseDouble(beeInfos[i].substring(beeInfos[i].indexOf(',', 4) + 1));

                            //归一化观测到的蜜蜂的角度值
                            beeAngle1 = 90 - angle + beeAngle;
                            beeHeading1 = 90 - angle + beeHeading;
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
                        }
                    }

                }
            }
            ratoteImage(angle);
            setXYs(0);
        }
        else  {
            checkBound(strVision);
        }


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
//            ratoteImage(angle);
            setXYs(0);
            return false;
        }
//        ratoteToAngle = a;
        ratoteImage(a);
        setXYs(0);
        return true;
    }

    private void setXY(){
        dx_sum = 0;
        dy_sum = 0;
        deltaX = (int) (18 * Math.cos(Math.toRadians(angle)));
        deltaY = (int) (18 * Math.sin(Math.toRadians(angle)));
        for (int i = 0; i < 9; i++) {
            nextX[i] = (int) (deltaX * (i + 1) / 9.0) - dx_sum;
            nextY[i] = (int) (deltaY * (i + 1) / 9.0) - dy_sum;
            dx_sum += nextX[i];
            dy_sum += nextY[i];
        }
        x += dx_sum;
        y += dy_sum;
    }

    public double correctvalue(double angle1) {
        if (angle1 < 0)
            while (angle1 < 0) angle1 += 360;
        if (angle1 >= 360)
            while (angle1 >= 360) angle1 -= 360;
        return angle1;
    }

    public boolean isCatched() {
        chasingId = 9;
        seen = false;
        dead = true;
        return dead;
    }

}