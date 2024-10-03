/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlls;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;
import models.Hotel;

/**
 *
 * @author ASUS
 */
public class HotelMagager implements I_Collection {

    HashMap<String, Hotel> hotelMap = new HashMap<>();
    Utilities ulti = new Utilities();
    static Scanner sc = new Scanner(System.in);

    @Override
    public void add() {
        int getchoice = 9; // biến này để làm cái thăm dò xem có muốn nhập tiêp cai khác ko , nếu nhập 1 thì có , nhập 0 thì thoát ra menu chính luôn

        do {

            String ID = "";

            do {
                ID = ulti.getString("Enter hotel's Id (format: Hxx) : ", 1, 0, 100); // 
                //  Check trùng và định dạng cho mỗi ID
                if (checkDuplicate(ID, 1)) {
                    System.out.println("Hotel's ID cannot be duplicate. Please enter another one !");
                }
                if (!checkProductID(ID)) {
                    System.out.println("Hotel's ID must be Hxx (0 <= x < 10) !");
                }
            } while (checkDuplicate(ID, 1) || !checkProductID(ID));  //ID phải là duy nhất và đúng định dạng 
            String name = ulti.getString("Enter Hotel's name: ", 1, 0, 100);
            int available_room = ulti.getInt("Enter available room (int): ", 0, 100000);
            

            String address = ulti.getString("Enter Hotel's address: ", 1, 0, 10000);
            String phone = ulti.getPhone("Enter Hotel's hotline(9-11 numbers): ");
            int rating = ulti.getInt("Enter Hotel's rating (1-5 Stars): ", 1, 5); // min=1 - max=5
            String ratings = rating+" Stars";
            Hotel hotel = new Hotel(ID, name, available_room, address, phone, ratings);
            // Load data from file before add new a hotel
            hotelMap = loadHotel();
            hotelMap.put(ID, hotel);

            // Save car to file vehicle.txt 
            saveHotel(hotelMap);

            getchoice = ulti.getInt("Do you want to add more product ? (yes: 1 , no: 0)", 0, 2);
            if (getchoice == 0) {
                System.out.println("----------Back to main menu!!!-------------");
                break;
            }
        } while (getchoice != 0); // Do while o day de kiem tra xem co con muon add them ko , neu nhap 1 thi lam tiep , neu nhap 0 thi thoi ^^
//        for (Hotel object : hotelMap.values()) {
//            System.out.println(object);
//        }
            showInfor(hotelMap);

    }
    // passed

    @Override
    public void delete() {  // Can id da co san trong file 
        hotelMap = loadHotel(); // load data len productMap
        String ID = "";
        do {
            ID = ulti.getString("Enter hotel's Id : ", 1, 0, 100);
            // check tinh dupplicate cho moi id 
            if (checkDuplicate(ID, 1) == false) {
                System.out.println("Hotel 's ID does not exits !");
            }
        } while (checkDuplicate(ID, 1) == false);   // id phai ton tai moi nhan 
        int getChoice = ulti.getInt("Do you want to delete this hotel? (Yes:1 , No:0)", 0, 2);
        if (getChoice == 0) {
            System.out.println("Delete failed !!!");

        }
        if (getChoice == 1) {           
            hotelMap.remove(ID);// xoa roi 
            saveHotel(hotelMap);  // cap nhat lai file
            System.out.println("Delete successfully !!!");

        }

    }

    @Override
    public void update() {
        hotelMap = loadHotel();
        Scanner scanner = new Scanner(System.in);

        // Prompt user for hotel id
        System.out.println("Enter the Hotel's id_Hotel:");
        String hotelId = scanner.nextLine();

        // Check if the hotel with the given id exists
        Hotel hotelToUpdate = findHotelById(hotelId);
        if (hotelToUpdate == null) {
            System.out.println("Hotel does not exist");
            return;
        }

        // Prompt user for new information
        System.out.println("Enter new information for the hotel (press Enter to keep the old information):");
        System.out.println("-------------------------------------------------------------------------------");
        System.out.print("New hotel name: ");
        String newName = scanner.nextLine();
        if (!newName.isEmpty()) {
            hotelToUpdate.setName(newName);
        }
        
        while(true){
            System.out.print("Available Rooms: ");
            String newAvailableRoomsStr = scanner.nextLine();
            if (newAvailableRoomsStr.isEmpty()) {
                break;
            }
                try {
                int newAvailableRooms = Integer.parseInt(newAvailableRoomsStr);
                hotelToUpdate.setAvailable_room(newAvailableRooms);
                break;
            } catch (NumberFormatException e) {
                System.out.println("Invalid input for available rooms. Please enter a valid number.");
            }
        }
        
                    
        System.out.print("New Address: ");
        String newAddress = scanner.nextLine();
        if (!newAddress.isEmpty()) {
            hotelToUpdate.setAddress(newAddress);
        }

        
        while(true){
            System.out.print("New Phone: ");
            String newPhone = scanner.nextLine();
            if(newPhone.isEmpty()){
                break;
            }
            if (newPhone.matches("\\d{9,11}")) {
                hotelToUpdate.setPhone(newPhone);
                break;
            } else {
                System.out.println("Invalid phone number. Please enter exactly 9-11 digits.");
                
            }
        }
        
        while(true){
            System.out.print("New rating: ");
            int newRating = scanner.nextInt();
            String newRating1 = String.valueOf(newRating);
            
            if(newRating1.isEmpty()){
                break;
            }
            if (newRating1.matches("[1-5]")){
                hotelToUpdate.setRating(newRating1+" Stars");
                break;
            }
            else{
                System.out.println("Invalid star. Please enter star from 1 to 5.");
            }
            
        }

        
        hotelMap=loadHotel();
        hotelMap.remove(hotelToUpdate.getId());
        hotelMap.put(hotelToUpdate.getId(), hotelToUpdate);
        saveHotel(hotelMap);
        // Print the updated information
        System.out.println("Hotel information updated successfully:");
        System.out.println(hotelToUpdate);

        // Return to the main screen 
    }

    // Helper method to find a hotel by its id
    private Hotel findHotelById(String id) {
        for (Hotel hotel : hotelMap.values()) {
            if (hotel.getId().equals(id)) {
                return hotel;
            }
        }
        return null;
    }

    @Override
    public Hotel search(String s, int opt) {
        hotelMap = loadHotel();
        HashMap<String, Hotel> proMap = new HashMap<>();
        Hotel product = null;
        if (opt == 0) {
            for (Hotel c : hotelMap.values()) {  // check  is exits or not by id
                if (c.getId().equalsIgnoreCase(s)) {
                    proMap.put(c.getId(), c);
                    showInfor(proMap);
                }
            }
        }
        if (opt == 1) {
            for (Hotel c : hotelMap.values()) {  // check  is exits or not by name
                if (c.getName().equalsIgnoreCase(s)) {
                    proMap.put(c.getId(), c);
                    showInfor(proMap);
                }
            }
        }

        if (proMap.isEmpty()) {
            System.out.println("No Hotel Found!!");
        }

        return null;
    }

    @Override
    public Hotel checkExist(String id) {
        hotelMap = loadHotel();
        HashMap<String, Hotel> proMap = new HashMap<>();
        Hotel product = null;
        for (Hotel c : hotelMap.values()) {  // check car is exits or not
            if (c.getId().equalsIgnoreCase(id)) {
                proMap.put(c.getId(), c);
                showInfor(proMap);
            }
        }
        if (proMap.isEmpty()) {
            System.out.println("No Hotel Found!!");
        }

        return null;

    }

    @Override
    public void showInfor(HashMap<String, Hotel> proMap) {

        

        System.out.println("-------------------------------------------------------------------------------------------------------------------");

        System.out.println("#######################################################################################################################");
        System.out.println("#Hotel_ID  #Hotel_Name         #Hotel_Room_Available   #Hotel_Address        #Hotel_Phone          #Hotel Rating      # ");
        System.out.println("#######################################################################################################################");

        for (Hotel p : proMap.values()) {
            String idstr = String.format("%-10s", "ID#");
            String id = String.format("%-10s", p.getId());
            String name = String.format("%-18s", p.getName());
            String room = String.format("%-22s", p.getAvailable_room());
            String address = String.format("%-20s", p.getAddress()); // Right-align the price column
            String phone = String.format("%-20s", p.getPhone());
            String rating = String.format("%-17s", p.getRating());

            System.out.println("#" + id + "# " + name + "# " + room + "# " + address + "# " + phone + "# " + rating + "# ");
        }

        System.out.println("#######################################################################################################################");
        System.out.println("#                                                                                               TOTAL: " + proMap.size() + " hotel type[s]#");
        System.out.println("#######################################################################################################################");
        
        System.out.println("-------------------------------------------------------------------------------------------------------------------");

        System.out.println("");
        System.out.println("");

    }

    @Override
    public void sort() {
        List<Hotel> list = new ArrayList<>();
        hotelMap = loadHotel();
        for (Hotel value : hotelMap.values()) {
            list.add(value);
        }
        displayDescendingByName(list);
    }

    public void displayDescendingByName(List<Hotel> list) {
        // Sort order by name in descending order
        Collections.sort(list, new Comparator<Hotel>() {
            @Override
            public int compare(Hotel hotel1, Hotel hotel2) {
                return hotel2.getName().compareToIgnoreCase(hotel1.getName());
            }
        });

        System.out.println("-------------------------------------------------------------------------------------------------------------------");

        System.out.println("#######################################################################################################################");
        System.out.println("#Hotel_ID  #Hotel_Name         #Hotel_Room_Available   #Hotel_Address        #Hotel_Phone          #Hotel Rating      # ");
        System.out.println("#######################################################################################################################");

        for (Hotel p : list) {
            String idstr = String.format("%-10s", "ID#");
            String id = String.format("%-10s", p.getId());
            String name = String.format("%-18s", p.getName());
            String room = String.format("%-22s", p.getAvailable_room());
            String address = String.format("%-20s", p.getAddress()); // Right-align the price column
            String phone = String.format("%-20s", p.getPhone());
            String rating = String.format("%-17s", p.getRating());

            System.out.println("#" + id + "# " + name + "# " + room + "# " + address + "# " + phone + "# " + rating + "# ");
        }

        System.out.println("#######################################################################################################################");
        System.out.println("#                                                                                               TOTAL: " + list.size() + " hotel type[s]#");
        System.out.println("#######################################################################################################################");
        
        System.out.println("-------------------------------------------------------------------------------------------------------------------");

        System.out.println("");
        System.out.println("");
    }

    public static boolean checkProductID(String str) { // check format Hxx cho ID 
        if (str.length() != 3) {
            return false;
        }

        if (str.charAt(0) != 'H') {
            return false;
        }

        for (int i = 1; i < str.length(); i++) {
            if (!Character.isDigit(str.charAt(i))) {
                return false;
            }
        }

        return true;
    }

    // Hàm kiểm tra xem id nhập vào có trùng với id của cai khác đã tồn tại trogn file ko . nếu trùng thì hàm trả về true => bắt nhập id khác ^^
    public boolean checkDuplicate(String s, int opt) {
        hotelMap = loadHotel();
        Hotel product = null;
        if (opt == 1) {

            for (Hotel c : hotelMap.values()) {  // check car is exits or not
                if (c.getId().equalsIgnoreCase(s)) {
                    return true;
                }
            }
        }
        if (opt == 2) {

            for (Hotel c : hotelMap.values()) {  // check car is exits or not
                if (c.getName().equalsIgnoreCase(s)) {
                    return true;
                }
            }
        }

        return false;
    }

    // Hàm lưu HashMap<Product> vào file .dat
    public void saveHotel(HashMap<String, Hotel> hotelMap) {
        ArrayList<Hotel> hotelList = new ArrayList<>(hotelMap.values());

        try {
            BufferedWriter bw = new BufferedWriter(new FileWriter("Hotel.dat"));
            String line;
            for (Hotel hotel : hotelList) {
                line = hotel.toString() + "\n";
                bw.write(line);
            }
            bw.close();
            System.out.println("Saved Successfully!!!");
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public HashMap<String, Hotel> loadHotel() {
        HashMap<String, Hotel> hotelMap = new HashMap<>();
        try {
            BufferedReader br = new BufferedReader(new FileReader("Hotel.dat"));
            String line;
            while ((line = br.readLine()) != null) {
                String parts[] = line.split(",");
                String id = parts[0].trim();
                String name = parts[1].trim();
                int room = Integer.parseInt(parts[2].trim());
                String address = parts[3].trim();
                String phone = parts[4].trim();
                String rating = parts[5].trim();
                Hotel hotel = new Hotel(id, name, room, address, phone, rating);
                hotelMap.put(id, hotel);

            }
            br.close();
        } catch (Exception e) {
        }
        return hotelMap;
    }

   

}
