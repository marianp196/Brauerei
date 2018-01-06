/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package brauhaus.brauprozess.brauPlanRepository;

import brauhaus.brauplan.brauelemente.Brauelement;
import brauhaus.brauplan.brauelemente.TemperaturRast;
import java.util.ArrayList;
import java.util.Collections;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author marian
 */
public class BrauPlanValidator {
    
    public BrauPlanValidator(ArrayList<Brauelement> brauelemente) throws Exception 
    {
        if(brauelemente == null)
            throw new Exception("brauelemente");
         brauelementeSotiert = sotieren(brauelemente);         
    }
       
    public void Validate() throws Exception 
    {
        checkForUniqueIDs();
        
        try {
            checkTemperaturOfTempRast();
        } catch (Exception ex) {
            Logger.getLogger(BrauPlanRepository.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
     private ArrayList<Brauelement> sotieren(ArrayList<Brauelement> brauelemente) 
    {
        ArrayList<Brauelement> sortList = new ArrayList<>(brauelemente);
        Collections.sort(sortList, (o1, o2) -> {
            return o1.getOrderNumber() - o2.getOrderNumber();
        });
        return sortList;       
    }    

    private void checkTemperaturOfTempRast() throws Exception {
        TemperaturRast rastAlt = null;
        for(Brauelement b : brauelementeSotiert)
        {
            if(b instanceof TemperaturRast)
            {
                TemperaturRast rast = (TemperaturRast)b;
                if(rastAlt!=null && rastAlt.GetTemperaturMax() > rast.GetTemperaturMin())
                    throw new Exception("Violation: RastreihenFolge nicht korrekt");
                rastAlt = rast;
            }
        }
    }
    
    private void checkForUniqueIDs() throws Exception {
        Brauelement bAlt = null;
        for(Brauelement b : brauelementeSotiert)
        {
            if(bAlt != null && b.getOrderNumber() == bAlt.getOrderNumber())
            {
                throw new Exception("OrderNumbers der Brauelemente nicht eindeutig");
            }
            bAlt = b;
        }
    }
    
    private ArrayList<Brauelement> brauelementeSotiert; 
}
