/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package brauhaus.brauprozess.brauPlanRepository;

import brauhaus.bierData.brauelemente.Brauelement;
import brauhaus.bierData.brauelemente.IBrauelement;
import java.util.ArrayList;
import java.util.Collections;

/**
 *
 * @author marian
 */
public class BrauPlanRepository 
{
    public BrauPlanRepository(ArrayList<IBrauelement> brauelemente) throws Exception 
    {
        if(brauelemente == null)
            throw new Exception("brauelemente");
         brauelementeSotiert = sotieren(brauelemente);        
         
    }
    
    public boolean IsEof()
    {
        return brauelementeSotiert.size() == aktuellerIndex;
    }
    
    public IBrauelement GetActualElement()
    {
        if(aktuellerIndex >= brauelementeSotiert.size())
            return null;
        return brauelementeSotiert.get(aktuellerIndex);
    }
    
    /**
     * Muss am Anfang vor Start stehen. Bei erstem Next geht der  Zeiger auf erstes Element.
     * Wenn zuende, dann gibt das ganze null zurück.
     * @return 
     */
    public IBrauelement Next()
    {
        aktuellerIndex++;
        if(aktuellerIndex == brauelementeSotiert.size())
        {  
            aktuellerIndex--;
            return null;                              
        }        
            
        return brauelementeSotiert.get(aktuellerIndex);
    }
    
    private ArrayList<IBrauelement> sotieren(ArrayList<IBrauelement> brauelemente) 
    {
        ArrayList<IBrauelement> sortList = new ArrayList<>(brauelemente);
        Collections.sort(sortList, (o1, o2) -> {
            return o1.GetOrderNumber() - o2.GetOrderNumber();
        });
        return sortList;     // irgendwie unschönes design mit dem Validator....  
    } 
     
    private ArrayList<IBrauelement> brauelementeSotiert; 
    private int aktuellerIndex = -1;
}

        