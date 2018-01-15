/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package brauhaus.brauprozess.temperaturSteuerung;

import brauhaus.bierData.brauelemente.TemperaturRastElement;
import hardwaresteuerung.IHardwareInformation;
import hardwaresteuerung.IHardwareSteuerung;
import sensoren.common.messergebnis.EPraefix;
import sensoren.common.messergebnis.MessergebnisMetrisch;

/**
 *
 * @author marian
 */
public class TemperaturRastSteuerer extends TemperaturSteuerung<TemperaturRastElement>{

    public TemperaturRastSteuerer(TemperaturRastElement rast, IHardwareInformation hardwareInformation, IHardwareSteuerung hardwareSteuerung) {
        super(rast, hardwareInformation, hardwareSteuerung);
    }    
    
    //ToDo: Verbleibende Zeit der Rast muss abhängen von Zeit auf Temperatur
    public void Iterate(MessergebnisMetrisch temp) throws Exception
    {
        checkIfNeedAbkuehlen(temp, getRast());
        
        checkIfNeedAufheizen(temp, getRast());
        
        checkIfAufgeheiztToRightTemperatur(getRast(), temp);
    }
    
    private void checkIfAufgeheiztToRightTemperatur(TemperaturRastElement rast, MessergebnisMetrisch messergebnisMetrisch) throws Exception {
        if(getHardwareInformation().GetHeizWerkStatus())
        {
            long zielTemp = getMittelwertTemperaturRast(rast);
            if(messergebnisMetrisch.GetPraefixValue(EPraefix.milli) >= zielTemp)
            {
                abkuehlenStart();
            }
        }
    }
   
    private void checkIfNeedAufheizen(MessergebnisMetrisch messergebnisMetrisch, TemperaturRastElement rast) throws Exception {
        if(messergebnisMetrisch.GetPraefixValue(EPraefix.milli) < rast.GetTemperaturMin())
        {
            if(!getHardwareInformation().GetHeizWerkStatus())
                aufheizenStart();
        }
    }

    private void checkIfNeedAbkuehlen(MessergebnisMetrisch messergebnisMetrisch, TemperaturRastElement rast) throws Exception {
        if(messergebnisMetrisch.GetPraefixValue(EPraefix.milli) > rast.GetTemperaturMax())
        {
            abkuehlenStart();
        }
    }
    
     private long getMittelwertTemperaturRast(TemperaturRastElement rast) {
        return rast.GetTemperaturMin()
                + (rast.GetTemperaturMax() - rast.GetTemperaturMin()) / 2;
    }

   
}
