/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package brauhaus.brauprozess.brauPlanRepository;

import brauhaus.bierData.brauelemente.Brauelement;
import brauhaus.bierData.brauelemente.IBrauelement;
import brauhaus.bierData.brauelemente.TemperaturRastElement;
import java.util.ArrayList;
import java.util.Collections;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author marian
 */
public class BrauPlanValidator {
    
    public BrauPlanValidator(ArrayList<IBrauelement> brauelemente) throws Exception 
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
    
     private ArrayList<IBrauelement> sotieren(ArrayList<IBrauelement> brauelemente) 
    {
        ArrayList<IBrauelement> sortList = new ArrayList<>(brauelemente);
        Collections.sort(sortList, (o1, o2) -> {
            return o1.GetOrderNumber() - o2.GetOrderNumber();
        });
        return sortList;       
    }    

    private void checkTemperaturOfTempRast() throws Exception {
        TemperaturRastElement rastAlt = null;
        for(IBrauelement b : brauelementeSotiert)
        {
            if(b instanceof TemperaturRastElement)
            {
                TemperaturRastElement rast = (TemperaturRastElement)b;
                if(rastAlt!=null && rastAlt.GetTemperaturMax() > rast.GetTemperaturMin())
                    throw new Exception("Violation: RastreihenFolge nicht korrekt");
                rastAlt = rast;
            }
        }
    }
    
    private void checkForUniqueIDs() throws Exception {
        IBrauelement bAlt = null;
        for(IBrauelement b : brauelementeSotiert)
        {
            if(bAlt != null && b.GetOrderNumber() == bAlt.GetOrderNumber())
            {
                throw new Exception("OrderNumbers der Brauelemente nicht eindeutig");
            }
            bAlt = b;
        }
    }
    
    private ArrayList<IBrauelement> brauelementeSotiert; 
}
