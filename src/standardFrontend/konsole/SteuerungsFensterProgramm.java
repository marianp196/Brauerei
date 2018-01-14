/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package standardFrontend.konsole;

import brauhaus.IBrauKessel;
import standardFrontend.gui.GStandardSteuerFenster;
import java.util.Observable;
import shell.abstractions.IProgramm;

/**
 *
 * @author marian
 */
public class SteuerungsFensterProgramm implements IProgramm{

    public SteuerungsFensterProgramm(IBrauKessel brauKessel) {
        this.brauKessel = brauKessel;
    }
    
    @Override
    public String GetProgrammIdentifier() 
    {
        return "control";
    }

    @Override
    public String GetErklaerung() 
    {
        return "Öffnet ein Fenster, das es ermöglicht hardware zu steuern.";
    }

    @Override
    public void Invoke(String[] param) 
    {
        fenster = new GStandardSteuerFenster(brauKessel);
        fenster.setVisible(true);
        ((Observable) brauKessel).addObserver(fenster);
                
    }
    
    private IBrauKessel brauKessel;
    private GStandardSteuerFenster fenster;

    
}
