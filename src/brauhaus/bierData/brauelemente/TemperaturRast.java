/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package brauhaus.bierData.brauelemente;

/**
 *
 * @author marian
 */
public class TemperaturRast extends Brauelement
{

    public TemperaturRast(long zeit,int orderNumber, long temperaturMin, long temperaturMax) throws Exception 
    {
        super(zeit, orderNumber);
        setTemperaturMax(temperaturMax);
        setTemperaturMin(temperaturMin);
        if(temperaturMin>=temperaturMax)
            throw new Exception("temperaturMax muss größer sein als temperaturMin");
    }    

    /**
     * Gibt die Temperatur in Milli aus
     * @return 
     */
    public long GetTemperaturMin() {
        return temperaturMin;
    }
    
    /**
     * Gibt die Temperatur in Milli aus
     * @return 
     */
    public long GetTemperaturMax() {
        return temperaturMax;
    }
    
    private void setTemperaturMin(long temperaturMin) throws Exception {
        if(temperaturMin < 0)
            throw new Exception("temp muss größer 0 sein");
        this.temperaturMin = temperaturMin;
    }

    private void setTemperaturMax(long temperaturMax) throws Exception {
         if(temperaturMax < 0)
            throw new Exception("temp muss größer 0 sein");
        this.temperaturMax = temperaturMax;
    }
    
    private Long temperaturMin;
    private Long temperaturMax;
}
