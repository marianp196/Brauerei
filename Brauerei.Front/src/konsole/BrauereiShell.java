/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package konsole;

import konsole.programme.HeizwerkProgramm;
import konsole.programme.TempProgramm;
import konsole.programme.RuehrwerkProgramm;
import konsole.programme.SteuerungsFensterProgramm;
import brauhaus.IBrauKessel;
import input.KonsoleInput;
import java.util.Observer;
import output.IPrinter;
import output.StandardPrinter;
import shell.Shell;
import shell.abstractions.IShell;
/**
 *
 * @author marian
 */
public class BrauereiShell
{

    public BrauereiShell(IBrauKessel brauKessel) throws Exception
    {
        this.brauKessel = brauKessel;
        IPrinter printer = new StandardPrinter();
        shell = new Shell(printer);
        
        KonsoleInput konsolenInput = new KonsoleInput();
        konsolenInput.addObserver((Observer)shell);
        
        shell.SetPrompt("Brauer@Brauerei~$ ");
        shell.SetPraeambel("********************BarauereiShell********************");
        
        shell.AddProgramm(new SteuerungsFensterProgramm(brauKessel));
        shell.AddProgramm(new TempProgramm(brauKessel, printer));
        shell.AddProgramm(new RuehrwerkProgramm(brauKessel, printer));
        shell.AddProgramm(new HeizwerkProgramm(brauKessel, printer));
        
        shell.Start();
    }
        

    private IBrauKessel brauKessel;  
    private IShell shell; 
}
