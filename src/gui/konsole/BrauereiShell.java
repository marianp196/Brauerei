/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui.konsole;

import brauhaus.BrauKessel;
import brauhaus.IBrauKessel;
import brauhaus.startup.IPrinter;
import gui.GStandardSteuerFenster;
import konsoleninputtest.IKonsoleInputAction;
import konsoleninputtest.KonsoleInput;

/**
 *
 * @author marian
 */
public class BrauereiShell implements IKonsoleInputAction 
{

    public BrauereiShell(IBrauKessel brauKessel, IPrinter printer) {
        commonConstructor(brauKessel, printer);
    }
        
    public BrauereiShell(IBrauKessel brauKessel) {
        commonConstructor(brauKessel, null);
    }

    @Override
    public void ActionConsoleInput(String inputText) 
    {
        interpretiereBefehl(inputText);
        printPrompt();
    }

    private void printPrompt() {
        System.out.print("Brauerei@Brauer$ ");
    }
    
    private void interpretiereBefehl(String befehl) 
    {
        befehl = befehl.trim();
       
        if(befehl.equals(starteSteuerung))
            steuerungStarten(printer, brauKessel);
        else
            print("befehl nicht interpretierbar");
    }
       
    private  void steuerungStarten(IPrinter printer, IBrauKessel braukessel) {
        printer.PrintLn("Starte Gui Steuerung....");
        GStandardSteuerFenster steuerFenster = new GStandardSteuerFenster(braukessel);
        ((BrauKessel)braukessel).addObserver(steuerFenster);
        steuerFenster.setVisible(true);
    }
    
    private void commonConstructor(IBrauKessel brauKessel1, IPrinter printer1) {
        this.brauKessel = brauKessel1;
        this.printer = printer1;
        konsoleInput = new KonsoleInput(this);
        printBefehlsReferenz();
        printPrompt();
    }
    
    
    private void printBefehlsReferenz() {
        if(printer == null)
            return;
        print("Konsolenanwendung gestartet");
        printer.PrintLn("Befehlsreferenz:");
        printer.PrintLn("Starte Steuerung:  " + starteSteuerung);
        printer.PrintLn("");
    }   
    
    
    private void print(String txt) {
        if(printer== null)
            return;
        printer.PrintLn(txt + "...");
    }
    
    private IPrinter printer;
    private KonsoleInput konsoleInput;
    private IBrauKessel brauKessel;  
    
    private String starteSteuerung  = "startS";;

}
