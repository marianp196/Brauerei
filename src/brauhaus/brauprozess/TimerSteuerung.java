/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package brauhaus.brauprozess;

import brauhaus.bierData.Bier;
import brauhaus.brauprozess.BrauProzessInfo;
import brauhaus.brauprozess.IBrauProzess;
import brauhaus.brauprozess.brauPlanRepository.BrauPlanRepository;
import hardwaresteuerung.IHardwareInformation;
import hardwaresteuerung.IHardwareSteuerung;
import java.util.Timer;
import java.util.TimerTask;

/**
 *
 * @author marian
 */
public class TimerSteuerung implements IBrauProzess 
{
    public TimerSteuerung(Bier bier, long timerIntervall,
            IHardwareInformation hardwareInformation, IHardwareSteuerung hardwareSteuerung) throws Exception 
    {
        if(bier == null)
            throw new NullPointerException("bier");
        if(hardwareInformation == null)
            throw new NullPointerException("hardwareInformation");
        if(hardwareSteuerung == null)
            throw new NullPointerException("hardwareSteuerung");
        
        this.bier = bier;
        this.hardwareInformation = hardwareInformation;
        this.hardwareSteuerung = hardwareSteuerung;
        
        setTimerIntervall(timerIntervall);             
        initBrauprozess();
        
        staus = EState.Play;
    }
    
    @Override
    public Bier GetBier() {
        return bier;
    }

    @Override
    public BrauProzessInfo GetProzessInfo() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void Start() throws Exception {
        prozessTimer = new Timer();
        prozessTimer.schedule((TimerTask)brauProzess, 0, timerIntervall);
        staus = EState.Play;
    }

    @Override
    public void Pause() throws Exception 
    {
        if(prozessTimer == null)
            return;
        prozessTimer.cancel();
        prozessTimer = null;
        staus = EState.Pause;
    }
   
    private void setTimerIntervall(long timerIntervall1) throws Exception 
    {
        if(timerIntervall1 <= 0)
            throw new Exception("intervall muss groesser sein");
        this.timerIntervall = timerIntervall1;
    }
     
    private void initBrauprozess() throws Exception {
        this.brauProzess = new BrauAblaufProzess(bier.getBrauelemente(), 
                                                 this.hardwareInformation, 
                                                 this.hardwareSteuerung, 
                                                 this);
    }

    EState staus;
    
    private Bier bier;
    private IHardwareInformation hardwareInformation;
    private IHardwareSteuerung hardwareSteuerung;
    
    private Timer prozessTimer;
    private long timerIntervall;
    
    private BrauAblaufProzess brauProzess;
}
