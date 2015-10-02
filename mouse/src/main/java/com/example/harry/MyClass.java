package com.example.harry;

//import java.util.*;
//
//public class MyClass {
//
//    static void shuffle(int a[], int seed) {
//        int n = a.length;
//        Random rand = new Random(seed);
//        for (; n > 1; n--) {
//            int r = rand.nextInt(n);
//            int tmp = a[n - 1];
//            a[n - 1] = a[r];
//            a[r] = tmp;
//        }
//    }
//
//    static void restore(int a[], int seed) {
//        /* 填写代码 */
//        int n = a.length;
//        Random rand = new Random(seed);
//        Stack<Integer> stack = new Stack<Integer>();
//        for (; n > 1; n--) {
//            int r = rand.nextInt(n);
//            stack.push(r);
//        }
//
//        for (n = 1; n < a.length; n++) {
//            int r = stack.pop();
//            int tmp = a[n];
//            a[n] = a[r];
//            a[r] = tmp;
//        }
//    }
//
//    public static void main(String[] args) {
//        int seed, n, i;
//        int[] a, b;
//        Scanner input = new Scanner(System.in);
//        {
//            seed = input.nextInt();
//            n = input.nextInt();
//            a = new int[n];
//            for (i = 0; i < n; ++i)
//                a[i] = input.nextInt();
//        }
//        b = Arrays.copyOf(a, a.length);
//        shuffle(a, seed);
//        restore(a, seed);
//        for (i = 0; i < n; i++) {
//            if (a[i] != b[i])
//                break;
//        }
//        if (i == n)
//            System.out.printf("Success!\n");
//        else
//            System.out.printf("Failed!\n");
//    }
//}



import java.awt.geom.Point2D;
import java.util.Scanner;

public class MyClass {

    static int[] getClosest(Point2D.Double[] points)
    {
        int[] result = new int[2];
        /* 在这里补充代码, 注意，要求result[0] < result[1] */
        /* distance = points[0].distance(points[1]) */

        double minDistance = Double.MAX_VALUE;
        for(int i = 0;i<points.length-1;i++){
            for(int j=i+1;j<points.length;j++){
                double distance = points[i].distance(points[j]);
                if(distance<minDistance){
                    minDistance = distance;
                    result[0] = i;
                    result[1] = j;
                }
            }
        }
        return result;
    }

    public static void main(String[] args) {
        Point2D.Double[] points;
        Scanner input = new Scanner(System.in);
        {
            int n = input.nextInt();
            input.nextLine();
            points = new Point2D.Double[n];
            for (int i = 0; i < n; ++i) {
                double x = input.nextDouble();
                double y = input.nextDouble();
                input.nextLine();
                points[i] = new Point2D.Double(x, y);
            }
        }
        int[] result = getClosest(points);
        System.out.printf("Closest points: %d, %d\n", result[0], result[1]);
    }
}


















//import java.util.Scanner;
//
//public class MyClass {
//
//
//    public static void main(String[] args) {
//
//        Scanner cin = new Scanner(System.in);
//        while(cin.hasNextInt())
//        {
//            int n = cin.nextInt();
//            int m = cin.nextInt();
//            String result = "";
//            for(int i=n;i<=m;i++){
//                int a = i/100;
//                int b = i/10%10;
//                int c = i%10;
//                double sum = Math.pow(a,3)+Math.pow(b,3)+Math.pow(c,3);
//                if((int)sum==i){
//                    result=result+i+" ";
//                }
//            }
//            if(result.isEmpty()){
//                result="no";
//            }
//            System.out.println(result);
//        }
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
////        Scanner cin = new Scanner(System.in);
//////        ArrayList<String> strings = new ArrayList<String>();
//////        System.out.println(cin.delimiter());
////        while(cin.hasNextInt())
////        {
////            int a = cin.nextInt();
////            int n = cin.nextInt();
////            double sum = a;
////            double next = a;
////            for(int i=0;i<n-1;i++){
////                next=Math.sqrt(next);
////                sum = sum+next;
////            }
////            String result = String .format("%.2f",sum);
////            System.out.println(result);
////        }
//
//    }
//
//
//}
