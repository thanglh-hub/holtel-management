/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package views;

import controlls.I_Menu;
import controlls.HotelMagager;
import controlls.Utilities;

/**
 *
 * @author ASUS
 */
public class Main implements I_Menu {

    Utilities ulti = new Utilities();

    int choice;

    public static void main(String[] args) {
        Main m = new Main();
        m.showMenu();
    }

    @Override
    public void showMenu() {

        HotelMagager hotel = new HotelMagager();
        

        do {
            Menu menu = new Menu("<----------- MainMenu ------------>");
            menu.addNewOptiont("1. Add new hotel");
            menu.addNewOptiont("2. Check exist hotel by id");
            menu.addNewOptiont("3. Update hotel information");
            menu.addNewOptiont("4. Delete hotel");
            menu.addNewOptiont("5. Search hotel (by ID or name)");
            menu.addNewOptiont("6. Display hotel list (descending by Hotel_Name).");
            menu.addNewOptiont("7. Exit.");
            menu.printMenu();

            choice = menu.getChoice();
            System.out.println("------------------------------");
            if (choice == 1) {
                hotel.add();
            }
            if (choice == 2) {
                String id = ulti.getString("Enter hotel's Id (format: Hxx) : ", 1, 0, 1000); // H00
                hotel.checkExist(id);
            }
            if (choice == 3) {
              hotel.update();
            }
            if (choice == 4) {

                hotel.delete();
            }
           
            if (choice == 5) {
                int choice1;
                do {
                    Menu menu1 = new Menu("5. Search ");
                    menu1.addNewOptiont("       5.1. Search by Hotel_id");
                    menu1.addNewOptiont("       5.2. Search by Hotel_name");
                    menu1.addNewOptiont("       5.3. Exit");
                    menu1.printMenu();

                    choice1 = menu1.getChoice();

                    if (choice1 == 1) {
                        String id = ulti.getString("Enter hotel's Id (format: Hxx) : ", 1, 0, 1000); // H00
                        hotel.search(id, 0);
                    }
                    if (choice1 == 2) {
                         String name = ulti.getString("Enter hotel's Name  : ", 1, 0, 1000); // H00
                        hotel.search(name, 1);
                    }

                } while (choice1 != 3);
            }
            if (choice == 6) {

               hotel.sort();
            }
        } while (choice != 7);

    }

}
