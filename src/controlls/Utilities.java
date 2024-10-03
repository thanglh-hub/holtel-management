/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlls;

import java.util.Scanner;


public class Utilities { // Các điều kiện ràng buộc dữ liệu để ở đây , ví dụ số tự nhiên mà nhập vào dạng chữ thì bắt nhập lại

    private static Scanner scanner = new Scanner(System.in);

    public static int getInt(String sms, int min, int max) { // Check số int
        int n = 0;

        while (true) {
            try {
                System.out.print(sms+"  ");
                n = Integer.parseInt(scanner.nextLine());
                if (n >= min && n < max) {
                    return n;
                }

            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a valid number.");
            }
        }

        //   return n;
    }


    public static String getString(String sms, int opt, int min, int max) {    //Check chữ, ko cho bỏ trống 

        // opt=1 -----> Enter String in min & max
        //opt =2 -----> Enter String opt1 or opt2
        String s = "";
        while (true) {
            try {
                System.out.println(sms);
                s = scanner.nextLine();

                if (opt == 1) {
                    if (!s.isEmpty() && s.length() > min && s.length() < max) {
                        return s;
                    }
                }
                if (opt == 2) {
                   

                    if (s.isEmpty()) {
                        System.out.println("Please enter a string of at least 1 character!!!");
                    }
                }
            } catch (Exception e) {
                System.out.println("PLease enter the valid information!");
            }

        }
    }

    public String getPhone(String msg) {     // Check phone number nếu đề yêu cầu
        String s = "";
        while (true) {
            try {
                System.out.println(msg);
                s = scanner.nextLine();
                if (!s.isEmpty() && s.matches("^[0-9]{9,11}$")) {

                    return s;

                } else {
                    System.out.println("Please enter an valid phone number");
                }
            } catch (Exception e) {
                System.out.println("Please enter an valid phone number");
            }
        }
    }
}
              
  

    

   

