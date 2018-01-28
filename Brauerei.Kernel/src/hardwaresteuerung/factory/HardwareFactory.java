/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hardwaresteuerung.factory;

import abstractions.ISensor;
import factories.ISensorProvider;
import gpio.Interfaces.IControler;
import hardwaresteuerung.Hardware;
import hardwaresteuerung.IHardwareInformation;
import hardwaresteuerung.IHardwareSteuerung;
import hardwaresteuerung.steuerung.BrauSteuerelement;
import hardwaresteuerung.steuerung.IDigitalesSteuerelement;
import java.util.logging.Level;
import java.util.logging.Logger;
import konfiguration.IKonfiguration;
import konfiguration.SteuerelementKonfiguration;
import sensoren.Thermometer_DS18B20.IThermometer_DS18B20;
import sensoren.common.messergebnis.MessergebnisMetrisch;
import sensoren.common.oneWire.OneWireKonfiguration;

/**
 *
 * @author marian
 */
public class HardwareFactory implements IHardwareFactory{

     public HardwareFactory(IKonfiguration konfiguration, 
                            ISensorProvider sensorProvider, 
                            IControler controler) {
        if(konfiguration == null)
            throw new NullPointerException("konfiguration");
        if(sensorProvider == null)
            throw new NullPointerException("sensorProvider");
        if(controler == null)
            throw new NullPointerException("controler");
        this.konfiguration = konfiguration;
        this.sensorProvider = sensorProvider;
        this.controler = controler;

    }   
    
    @Override
    public IHardwareInformation CreateHardwareInformation() throws Exception {
        initObject();
        return hardware; 
    }

    @Override
    public IHardwareSteuerung CreateHardwareSteuerung() throws Exception {
        initObject();
        return hardware;
    }
    
     private void initObject() throws Exception {  
        if(hardware != null)
            return;
        this.hardware = new Hardware(getThermometer(),getRuehrwerk(),getHeizwerk());       
    }
    
    /* ToDo    
    -Unnötige Beziehung..Neues Interface für Sensor, dass nur die Form des Ergebnissses kennt als basis für dieses.
    Beschränkt sich aber nur auf Implementierung....Interfaces nicht betroffen.
    
    -Vielleicht sollten Sensoren die Exceptions doch nach außen abgeben....ja müssten sie
    
    -Im Provider eine Methode mit der man generisch sensor anfragenh kann, über Interface, dass aus IKonfiguration kommt.
    
    */
    private ISensor<OneWireKonfiguration, MessergebnisMetrisch> getThermometer() throws Exception
    {
        OneWireKonfiguration konf = (OneWireKonfiguration)konfiguration.GetThermometerKonfiguration();
        return sensorProvider.<IThermometer_DS18B20>RegisterThermometer(konf);
    }
    
    private IDigitalesSteuerelement getRuehrwerk() throws Exception
    {
        SteuerelementKonfiguration konf = konfiguration.GetRuehrwerkKonfiguration();
        return new BrauSteuerelement(konf.getSignalPin(), konf.getElementPin(), controler);
    }
    
    private IDigitalesSteuerelement getHeizwerk() throws Exception
    {
        SteuerelementKonfiguration konf = konfiguration.GetHeizwerkKonfiguration();
        return new BrauSteuerelement(konf.getSignalPin(), konf.getElementPin(), controler);
    }
    
    private IKonfiguration  konfiguration;
    private ISensorProvider sensorProvider;
    private IControler controler;
    private Hardware hardware;
    
}
