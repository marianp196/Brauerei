/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package startup;

import brauhaus.BrauKessel;
import brauhaus.IBrauKessel;
import factories.ISensorProvider;
import factories.SensorProviderFactory;
import gpio.ControlerFactory;
import gpio.Interfaces.IControler;
import hardwaresteuerung.factory.HardwareFactory;
import hardwaresteuerung.factory.IHardwareFactory;
import hardwaresteuerung.IHardwareInformation;
import hardwaresteuerung.IHardwareSteuerung;
import brauhaus.actions.BuzzerSignal;
import java.io.File;
import java.util.logging.Level;
import java.util.logging.Logger;
import konfiguration.IKonfiguration;
import konfiguration.XMLKonfigurationReader;
import sensoren.common.messergebnis.MessergebnisMetrisch;
import brauhaus.actions.IActionTryChangeSteuerelementState;

/**
 *
 * @author marian
 */
public class StandardStartupOneKessel {

    /*
    ToDo: zu Schnell zusammengepflanscht..weniger kaffee..refactor
    */
    public StandardStartupOneKessel(IPrinter printer) {
        if(printer==null)
            throw new NullPointerException("printer");
        this.printer = printer;
    }
    
    public StandardStartupOneKessel() {
    }
    
    public String getKonfigFile() {
        return konfigFile;
    }

    public void setKonfigFile(String konfigFileName) {
        if(konfigFileName == null)
            throw new NullPointerException("konfigFile");
        this.konfigFile = konfigFileName;
    }
    
    public IBrauKessel BuildBrauKessel() throws Exception
    {
        printPraeambel();
        printer.PrintLn("\nLade Module\n");
               
        IKonfiguration konf = getKonfiguration();
        if(konf == null)
            beendeProgramm();
        
        printer.PrintLn("");
        
        print("Lade GPIO-Steuerung");
        IControler con = ControlerFactory.getInstance();
        print("Lade Sensor-Provider");
        ISensorProvider provider = SensorProviderFactory.createInstance();        
        IHardwareFactory factory = new HardwareFactory(konf, provider, con);        
        
        IHardwareInformation hardwareInformation = getHardwareInformation(konf, provider, factory);
        if(hardwareInformation == null)
            beendeProgramm();
        
        printer.PrintLn("");
        
        IHardwareSteuerung hardwareSteuerung = getHardwareSteuerung(konf, provider, factory);
        if(hardwareSteuerung==null)
            beendeProgramm();
        
        IActionTryChangeSteuerelementState signal =null;
        try {
            print("Lade Buzzer für Pin 19");
            signal = new BuzzerSignal(con, 19);
        } catch (Exception exception) {
            print("Probleme mit Buzzer/Konnte nicht geladen werden");
        }
        
        
        print("Build Braukessel");
        printAfterAmbel();
        
        return new BrauKessel(hardwareSteuerung, hardwareInformation, konf, signal);      
    }
    
    private IKonfiguration getKonfiguration() throws Exception
    {
        print("Lade Konfiguration");
        File konfigFile = getFileRelativeToProgram("brauenKonfig.xml");
        print("KonfigurationsFile: " + konfigFile.getAbsolutePath());
        //Prüfen auf Existenz und Rechte erfolgt im XmlKonfReader...
        IKonfiguration konfiguration = null;
        try {
            konfiguration = new XMLKonfigurationReader(konfigFile);
        } catch (Exception ex) {
            print("Fehler bei Konfiguration");
            print("Fehler: " + ex.getMessage());
            Logger.getLogger(StandardStartupOneKessel.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
        print("Thermometer-Konfiguration geladen:");
        print(konfiguration.GetThermometerKonfiguration().toString());
        print("Ruehrwerk-Konfiguration geladen:");
        print(konfiguration.GetRuehrwerkKonfiguration().toString());
        print("Heizwerk-Konfiguration geladen");
        print(konfiguration.GetHeizwerkKonfiguration().toString());
        return konfiguration;
    }
    
    private IHardwareInformation getHardwareInformation(IKonfiguration konf, ISensorProvider provider, IHardwareFactory factory) {
        print("Lade HardwareInformationen");
        IHardwareInformation hardwareInformation = null;
                
        try {
            hardwareInformation = factory.CreateHardwareInformation();
            MessergebnisMetrisch m = hardwareInformation.GetTemperaturBraukessel();
            print("Aktuelle Temperatur messen:  " + m.GetWert() +  "°C");
            print("Heizwerk-State: " + hardwareInformation.GetHeizWerkStatus());
            print("RuehrwerkWerk-State: " + hardwareInformation.GetRuehwerkStatus());
        } catch (Exception ex) {
            print("Fehler beim Laden der HardwareInformation");
            print("Fehler:" + ex.getMessage());
            Logger.getLogger(StandardStartupOneKessel.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
        return hardwareInformation;        
    }
    
    private IHardwareSteuerung getHardwareSteuerung(IKonfiguration konf, ISensorProvider provider, IHardwareFactory factory) {
        print("Lade HardwareSteuerung");        
        IHardwareSteuerung hardwareSteuerung = null;      
        
        
        try {
            hardwareSteuerung = factory.CreateHardwareSteuerung();            
        } catch (Exception ex) {
            print("Fehler beim Laden der HardwareSteuerung");
            print("Fehler:" + ex.getMessage());
            Logger.getLogger(StandardStartupOneKessel.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
        return hardwareSteuerung;        
    }
    
    
    
    private void print(String text)
    {
        if(printer == null || text == null)
            return;
        text += ".....";
        printer.PrintLn(text);
    }
    
    private File getFileRelativeToProgram(String name)
    {
        String path = System.getProperty("user.dir");
        path += "/"+name;
        return new File(path);
    }
    
    private void beendeProgramm() {
        print("Beende Programm");
        System.exit(0);
    }  
       

    private void printPraeambel() {
        if(printer == null)
            return;
        printer.PrintLn("******************************************************");
        printer.PrintLn("******************************************************");
        printer.PrintLn("**********Brauereisteuerung 2017 - 2018***************");
        printer.PrintLn("*************Konsoleanwendung-RasPi3******************");
        printer.PrintLn("******************Version: 1.0.1**********************");
        printer.PrintLn("*********************MCPDK****************************");
        printer.PrintLn("******************************************************");
        printer.PrintLn("******************************************************");        
    }

    private void printAfterAmbel() {
        if(printer == null)
            return;
        printer.PrintLn("******************************************************");
        printer.PrintLn("******************************************************");
        print("Bereit");
    }

    
    private IPrinter printer;
    private String konfigFile;
   
}
