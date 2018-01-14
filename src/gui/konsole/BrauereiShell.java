/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui.konsole;

import brauhaus.IBrauKessel;
import input.KonsoleInput;
import java.util.Observer;
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
        shell = new Shell(new StandardPrinter());
        
        KonsoleInput konsolenInput = new KonsoleInput();
        konsolenInput.addObserver((Observer)shell);
        
        shell.SetPrompt("Brauer@Brauerei~$ ");
        shell.SetPraeambel("********************BarauereiShell********************");
        
        shell.AddProgramm(new SteuerungsFensterProgramm(brauKessel));
        shell.Start();
    }
        

    private IBrauKessel brauKessel;  
    private IShell shell; 
}
