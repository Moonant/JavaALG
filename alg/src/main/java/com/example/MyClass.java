package com.example;

import java.io.File;

public class MyClass {


    public static void main(String[] args){
        System.out.println("hello world");
        File file = new File("res/flourish.mid");
        if(file.exists()){
            System.out.println("exist");
        }else {
            System.out.println("exist");
        }

//        try {
//            file.createNewFile();
//        }catch (IOException e){
//            e.printStackTrace();
//        }
    }
}
