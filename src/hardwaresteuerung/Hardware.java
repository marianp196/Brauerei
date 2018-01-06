/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hardwaresteuerung;

import abstractions.ISensor;
import hardwaresteuerung.steuerung.IDigitalesSteuerelement;
import sensoren.common.messergebnis.MessergebnisMetrisch;
import sensoren.common.oneWire.OneWireKonfiguration;

/**
 * @author marian
 */
public class Hardware implements IHardwareInformation, IHardwareSteuerung{
    
    public Hardware(ISensor<OneWireKonfiguration, MessergebnisMetrisch> thermometer,
                    IDigitalesSteuerelement heizwerk,
                    IDigitalesSteuerelement ruehrwerk) throws Exception {
        if(thermometer == null)
            throw new NullPointerException("thermometer");
        if(!thermometer.IsActive())
            throw new Exception("thermoeter nicht aktiv");
        if(heizwerk==null)
            throw new NullPointerException("heizwerk");
        if(ruehrwerk==null)
            throw new NullPointerException("ruehrwerk");
        
        this.thermometer = thermometer;
        this.heizwerk = heizwerk;
        this.ruehrwerk = ruehrwerk;
    }
    
    @Override
    public MessergebnisMetrisch GetTemperaturBraukessel() {
        return thermometer.GetMessergebnis();
    }

    @Override
    public boolean GetRuehwerkStatus() {
        return ruehrwerk.GetState();
    }

    @Override
    public boolean GetHeizWerkStatus() {
        return heizwerk.GetState();
    }

    @Override
    public void SetHeizwerkStatus(boolean state) throws Exception {
        heizwerk.SetState(state);
    }

    @Override
    public void SetRuehrwerkStatus(boolean state) throws Exception {
        ruehrwerk.SetState(state);
    }
    
    private ISensor<OneWireKonfiguration, MessergebnisMetrisch> thermometer;
    private IDigitalesSteuerelement ruehrwerk;
    private IDigitalesSteuerelement heizwerk;
   
}
