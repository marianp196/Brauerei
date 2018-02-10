/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

import brauhaus.IBrauKessel;
import konsole.BrauereiShell;
import startup.IPrinter;
import startup.StandardStartupOneKessel;

/**
 *
 * @author marian
 */
public class BrauereiFront {
    
    public static void main(String[] args) throws Exception
    {
     
        IPrinter printer = new ConsolePrinter();
        StandardStartupOneKessel startup = new StandardStartupOneKessel(printer);
        IBrauKessel braukessel = startup.BuildBrauKessel();
             
        BrauereiShell brauereiShell = new BrauereiShell(braukessel);        
    }
   
    
}
