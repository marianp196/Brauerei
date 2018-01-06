/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package brauhaus.brauprozess.timer.temperaturSteuerung;

import brauhaus.brauplan.brauelemente.TemperaturRast;
import hardwaresteuerung.IHardwareInformation;
import hardwaresteuerung.IHardwareSteuerung;
import sensoren.common.messergebnis.EPraefix;
import sensoren.common.messergebnis.MessergebnisMetrisch;

/**
 *
 * @author marian
 */
public class TemperaturRastSteuerer extends TemperaturSteuerung<TemperaturRast>{

    public TemperaturRastSteuerer(TemperaturRast rast, IHardwareInformation hardwareInformation, IHardwareSteuerung hardwareSteuerung) {
        super(rast, hardwareInformation, hardwareSteuerung);
    }    
    
    //ToDo: Verbleibende Zeit der Rast muss abhängen von Zeit auf Temperatur
    public void Iterate(MessergebnisMetrisch temp) throws Exception
    {
        checkIfNeedAbkuehlen(temp, getRast());
        
        checkIfNeedAufheizen(temp, getRast());
        
        checkIfAufgeheiztToRightTemperatur(getRast(), temp);
    }
    
    private void checkIfAufgeheiztToRightTemperatur(TemperaturRast rast, MessergebnisMetrisch messergebnisMetrisch) throws Exception {
        if(getHardwareInformation().GetHeizWerkStatus())
        {
            long zielTemp = getMittelwertTemperaturRast(rast);
            if(messergebnisMetrisch.GetPraefixValue(EPraefix.milli) >= zielTemp)
            {
                abkuehlenStart();
            }
        }
    }
   
    private void checkIfNeedAufheizen(MessergebnisMetrisch messergebnisMetrisch, TemperaturRast rast) throws Exception {
        if(messergebnisMetrisch.GetPraefixValue(EPraefix.milli) < rast.GetTemperaturMin())
        {
            if(!getHardwareInformation().GetHeizWerkStatus())
                aufheizenStart();
        }
    }

    private void checkIfNeedAbkuehlen(MessergebnisMetrisch messergebnisMetrisch, TemperaturRast rast) throws Exception {
        if(messergebnisMetrisch.GetPraefixValue(EPraefix.milli) > rast.GetTemperaturMax())
        {
            abkuehlenStart();
        }
    }
    
     private long getMittelwertTemperaturRast(TemperaturRast rast) {
        return rast.GetTemperaturMin()
                + (rast.GetTemperaturMax() - rast.GetTemperaturMin()) / 2;
    }

   
}
