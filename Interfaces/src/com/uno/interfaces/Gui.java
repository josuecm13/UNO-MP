package com.uno.interfaces;

import java.util.Scanner;

public class Gui {

    public static int menu(String titulo,String[] stringList){
        System.out.println("=====" + titulo + "====");
        for(int i = 0; i < stringList.length; i++){
            System.out.println((i+1) + ". " + stringList[i]);
        }
        return getValue();
    }

    public static String input(String titulo, String pregunta){
        System.out.println("=====" + titulo + "====");
        Scanner in = new Scanner(System.in);
        System.out.print(pregunta);
        return in.nextLine();
    }


    private static int getValue(){
        Scanner input = new Scanner(System.in);
        String option = input.nextLine();
        try{
            return Integer.parseInt(option)-1;
        }catch(NumberFormatException e){
            return Integer.MAX_VALUE;
        }
    }

}
