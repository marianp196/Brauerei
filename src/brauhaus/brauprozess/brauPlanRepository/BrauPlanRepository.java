/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package brauhaus.brauprozess.brauPlanRepository;

import brauhaus.brauplan.brauelemente.Brauelement;
import java.util.ArrayList;
import java.util.Collections;

/**
 *
 * @author marian
 */
public class BrauPlanRepository 
{
    public BrauPlanRepository(ArrayList<Brauelement> brauelemente) throws Exception 
    {
        if(brauelemente == null)
            throw new Exception("brauelemente");
         brauelementeSotiert = sotieren(brauelemente);
         
         /*ToDo: Das Konzept mit der Validierung überdenken. 
            Möglicherweise übergibt der Nutzer Arraylist, in die Unstimmigkeiten geloggt werden.
         */
         BrauPlanValidator validator = new BrauPlanValidator(brauelemente);
         validator.Validate();
    }
    
    public boolean IsEof()
    {
        return brauelementeSotiert.size() == aktuellerIndex;
    }
    
    public Brauelement GetActualElement()
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
    public Brauelement Next()
    {
        aktuellerIndex++;
        if(aktuellerIndex == brauelementeSotiert.size())
        {  
            aktuellerIndex--;
            return null;                              
        }        
            
        return brauelementeSotiert.get(aktuellerIndex);
    }
    
    private ArrayList<Brauelement> sotieren(ArrayList<Brauelement> brauelemente) 
    {
        ArrayList<Brauelement> sortList = new ArrayList<>(brauelemente);
        Collections.sort(sortList, (o1, o2) -> {
            return o1.getOrderNumber() - o2.getOrderNumber();
        });
        return sortList;     // irgendwie unschönes design mit dem Validator....  
    } 
     
    private ArrayList<Brauelement> brauelementeSotiert; 
    private int aktuellerIndex = -1;
}

        