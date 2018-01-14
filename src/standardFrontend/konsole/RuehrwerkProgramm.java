/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package standardFrontend.konsole;

import brauhaus.IBrauKessel;
import standardFrontend.gui.GStandardSteuerFenster;
import java.util.Observable;
import output.IPrinter;
import shell.abstractions.IProgramm;

/**
 *
 * @author marian
 */
public class RuehrwerkProgramm implements IProgramm{

    public RuehrwerkProgramm(IBrauKessel brauKessel, IPrinter printer) {
        this.brauKessel = brauKessel;
        this.printer = printer;
    }
    
    @Override
    public String GetProgrammIdentifier() 
    {
        return "ruehr";
    }

    @Override
    public String GetErklaerung() 
    {
        return "Steuert das Rührwerk.\n Parameter: -u up -d down";
    }

    @Override
    public void Invoke(String[] param) 
    {
        if(param.length != 1)
        {
            printer.PrintLn("Ungültige Aktion");
            return;
        }
        
        if(param[0].equals("-u"))
        {
            brauKessel.SetRuehwerkStatus(true);
            printer.PrintLn("Rührwerk an");
        }else if(param[0].equals("-d"))
        {
            brauKessel.SetRuehwerkStatus(false);
            printer.PrintLn("Rührwerk aus");
        }else
        {
            printer.PrintLn("Ungültige Aktion");
        }
    }
    
    private IBrauKessel brauKessel;
    private IPrinter printer;
    private GStandardSteuerFenster fenster;

    
}
