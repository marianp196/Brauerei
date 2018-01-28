/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package konfiguration;

/**
 *
 * @author marian
 */
public class SteuerelementKonfiguration {
    public SteuerelementKonfiguration(int elementPin, int signalPin) {
        this.elementPin = elementPin;
        this.signalPin = signalPin;
    } 
  
    
    public int getElementPin() {
        return elementPin;
    }

    public int getSignalPin() {
        return signalPin;
    }
    
     @Override
    public String toString() {
        return "elementPin=" + elementPin + " signalPin=" + signalPin ;
    }
    
    private int elementPin;
    private int signalPin;  
}
