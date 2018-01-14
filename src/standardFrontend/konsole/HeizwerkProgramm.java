/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package standardFrontend.konsole;

import brauhaus.IBrauKessel;
import output.IPrinter;
import shell.abstractions.IProgramm;
import standardFrontend.gui.GStandardSteuerFenster;

/**
 *
 * @author marian
 */
public class HeizwerkProgramm implements IProgramm{

    public HeizwerkProgramm(IBrauKessel brauKessel, IPrinter printer) {
        this.brauKessel = brauKessel;
        this.printer = printer;
    }
    
    @Override
    public String GetProgrammIdentifier() 
    {
        return "heiz";
    }

    @Override
    public String GetErklaerung() 
    {
        return "Steuert das Heizwerk.\n Parameter: -u up -d down";
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
            brauKessel.SetHeizwerkStatus(true);
            printer.PrintLn("Heizwerk an");
        }else if(param[0].equals("-d"))
        {
            brauKessel.SetHeizwerkStatus(false);
            printer.PrintLn("Heizwerk aus");
        }else
        {
            printer.PrintLn("Ungültige Aktion");
        }
    }
    
    private IBrauKessel brauKessel;
    private IPrinter printer;
    private GStandardSteuerFenster fenster;

    
}
