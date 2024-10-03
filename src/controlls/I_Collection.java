package controlls;

import java.util.HashMap;
import models.Hotel;


// manage products
public interface I_Collection {

    public void add();

    public void delete();

    public void update();

    public Hotel checkExist(String id); 
    public Hotel search(String s, int opt); 

    public void showInfor(HashMap<String,Hotel> proMap);

    public void sort(); 

}
