/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package standardFrontend.konsole.programme;

import brauhaus.IBrauKessel;
import standardFrontend.gui.GStandardSteuerFenster;
import java.util.Observable;
import java.util.logging.Level;
import java.util.logging.Logger;
import output.IPrinter;
import shell.abstractions.IProgramm;

/**
 *
 * @author marian
 */
public class TempProgramm implements IProgramm{

    public TempProgramm(IBrauKessel brauKessel, IPrinter printer) {
        this.brauKessel = brauKessel;
        this.printer = printer;
    }
    
    @Override
    public String GetProgrammIdentifier() 
    {
        return "temp";
    }

    @Override
    public String GetErklaerung() 
    {
        return "Gibt die Temperatur in °C aus.";
    }

    @Override
    public void Invoke(String[] param) 
    {
        try {
            printer.PrintLn("Temperatur in °C:  " + brauKessel.GetInfo().getTemp());
        } catch (Exception ex) {
            Logger.getLogger(TempProgramm.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private IBrauKessel brauKessel;
    private IPrinter printer;

    
}
