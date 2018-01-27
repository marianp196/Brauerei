/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package brauhaus.actions;

import gpio.Enums.EModus;
import gpio.Enums.EStatus;
import gpio.Interfaces.IControler;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author marian
 */
public class BuzzerSignal implements IActionTryChangeSteuerelementState {

    public BuzzerSignal(IControler controler, int pin) throws Exception {
        if(controler == null)
            throw new NullPointerException("controler");
        
        this.controler = controler;
        this.pin = pin;
        
        controler.register(pin, EModus.Out);
    }
    
    @Override
    public void MachEs() 
    {
        try {
            controler.setHigh(pin);
            Thread.sleep(30);        
            controler.setLow(pin);
        } catch (Exception ex) {
            Logger.getLogger(BuzzerSignal.class.getName()).log(Level.SEVERE, null, ex);
        }
    }    
    
    private IControler controler;
    private int pin;
    
}
