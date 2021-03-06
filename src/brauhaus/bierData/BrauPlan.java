/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package brauhaus.bierData;

import brauhaus.bierData.bier.Bier;
import brauhaus.bierData.brauelemente.Brauelement;
import brauhaus.bierData.brauelemente.HopfenKochen;
import brauhaus.bierData.brauelemente.TemperaturRast;
import java.util.ArrayList;

/**
 *
 * @author marian
 */
public class BrauPlan implements IBrauPlan
{
    public BrauPlan(Bier bier) {
        if(bier==null)
            throw new NullPointerException("bier");
        this.bier = bier;
        brauelemente = new ArrayList<>();
    }
    
    public void AddHopfenkochen(HopfenKochen hk)
    {
        if(hk == null)
            throw new NullPointerException();
        brauelemente.add(hk);
    }

    public void AddTemperaturRast(TemperaturRast tr)
    {
        if(tr == null)
            throw new NullPointerException();
        brauelemente.add(tr);
    }
    
    @Override
    public Bier GetBierData() {
        return bier;
    }

    @Override
    public ArrayList<Brauelement> GetBrauElemente() {
        return brauelemente;
    }
    
    private Bier bier;    
    private ArrayList<Brauelement> brauelemente;
    
}
