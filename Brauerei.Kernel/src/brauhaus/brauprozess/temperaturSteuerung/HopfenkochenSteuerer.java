/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package brauhaus.brauprozess.temperaturSteuerung;

import brauhaus.bierData.brauelemente.HopfenKochenElement;
import brauhaus.bierData.brauelemente.TemperaturRastElement;
import hardwaresteuerung.IHardwareInformation;
import hardwaresteuerung.IHardwareSteuerung;
import sensoren.common.messergebnis.MessergebnisMetrisch;

/**
 *
 * @author marian
 */
public class HopfenkochenSteuerer extends TemperaturSteuerung<HopfenKochenElement>
{

    public HopfenkochenSteuerer(HopfenKochenElement rast, IHardwareInformation hardwareInformation, IHardwareSteuerung hardwareSteuerung) {
        super(rast, hardwareInformation, hardwareSteuerung);
    }
    
   
    @Override
    public void Iterate(MessergebnisMetrisch temp) throws Exception {
        if(!getHardwareInformation().GetHeizWerkStatus())
            aufheizenStart();
    }       
    
}
