/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hardwaresteuerung.steuerung;

import gpio.Enums.EModus;
import gpio.Enums.EStatus;
import gpio.Interfaces.IControler;

/**
 *
 * @author marian
 */
public class BrauSteuerelement implements IDigitalesSteuerelement {
    
    
    public BrauSteuerelement(int signalPin, int elementPin, IControler controler) throws Exception {
        if(controler== null)
            throw new NullPointerException("controler");
                
        this.signalPin = signalPin;
        this.elementPin = elementPin;
        this.controler = controler;
        
        init();
        
        state = false;
    }
    
    @Override
    public boolean GetState() 
    {
        return state;
    }

    @Override
    public void SetState(boolean b) throws Exception {
        controler.setSate(signalPin, getStatus(b));
        controler.setSate(elementPin, getStatus(b));
        state = b;
    }

    @Override
    public void On() throws Exception 
    {
        SetState(true);
    }

    @Override
    public void Off() throws Exception 
    {
        SetState(false);
    }

    @Override
    public void InvertState() throws Exception 
    {
        SetState(!state);
    }
   
    private void init() throws Exception 
    {
        controler.register(signalPin, EModus.Out);
        controler.register(elementPin, EModus.Out);
        controler.setLow(signalPin);
        controler.setLow(elementPin);
    }
    
    private EStatus getStatus(boolean b)
    {
        if(b)
            return EStatus.High;
        else
            return EStatus.Low;
    }
    
    private boolean state;
    
    private int signalPin;
    private int elementPin;
    private IControler controler;
 
    
}
