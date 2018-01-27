/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package brauhaus.brauprozess.temperaturSteuerung;

import brauhaus.bierData.brauelemente.TemperaturRastElement;
import hardwaresteuerung.IHardwareInformation;
import hardwaresteuerung.IHardwareSteuerung;
import sensoren.common.messergebnis.MessergebnisMetrisch;

/**
 *
 * @author marian
 */
public abstract class TemperaturSteuerung<TElement> implements ITemperaturSteuerung{

    public TemperaturSteuerung(TElement rast, IHardwareInformation hardwareInformation, IHardwareSteuerung hardwareSteuerung) {
        if(rast == null)
            throw new NullPointerException("rast");
        if(hardwareInformation == null)
            throw new NullPointerException("hardwareInformation");
        if(hardwareSteuerung == null)
            throw new NullPointerException("hardwareSteuerung");
        
        this.rast = rast;
        this.hardwareInformation = hardwareInformation;
        this.hardwareSteuerung = hardwareSteuerung;
    }

    @Override
    public abstract void Iterate(MessergebnisMetrisch temp) throws Exception;
        
    
    protected void aufheizenStart() throws Exception {
        hardwareSteuerung.SetHeizwerkStatus(true);
        hardwareSteuerung.SetRuehrwerkStatus(true);
    }
     
    protected void abkuehlenStart() throws Exception {
        hardwareSteuerung.SetRuehrwerkStatus(false);
        hardwareSteuerung.SetHeizwerkStatus(false);
    }
    
    public TElement getRast() {
        return rast;
    }

    public IHardwareInformation getHardwareInformation() {
        return hardwareInformation;
    }

    public IHardwareSteuerung getHardwareSteuerung() {
        return hardwareSteuerung;
    }
    
    
    private TElement rast;
    
    private IHardwareInformation hardwareInformation;    
    private IHardwareSteuerung hardwareSteuerung;    
}
