/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package brauhaus.bierData;

import brauhaus.bierData.brauelemente.IBrauelement;
import java.util.ArrayList;

/**
 *
 * @author marian
 */
public class Bier {

    public Bier(int id)
    {
        this.id = id;
    }
    
    public int getId() {
        return id;
    }   

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<IBrauelement> getBrauelemente() {
        return brauelemente;
    }

    public void setBrauelemente(ArrayList<IBrauelement> brauelemente) {
        this.brauelemente = brauelemente;
    }
       
    
    private int id;
    private String name;
    private ArrayList<IBrauelement> brauelemente;
}
