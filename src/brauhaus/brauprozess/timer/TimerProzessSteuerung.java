/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package brauhaus.brauprozess.timer;

import brauhaus.bierData.IBrauPlan;
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
public class TimerProzessSteuerung implements IBrauProzess 
{
    public TimerProzessSteuerung(IBrauPlan brauPlan, long timerIntervall,
            IHardwareInformation hardwareInformation, IHardwareSteuerung hardwareSteuerung) throws Exception 
    {
        if(brauPlan == null)
            throw new NullPointerException("brauplan");
        if(hardwareInformation == null)
            throw new NullPointerException("hardwareInformation");
        if(hardwareSteuerung == null)
            throw new NullPointerException("hardwareSteuerung");
        
        this.brauPlan = brauPlan;
        this.hardwareInformation = hardwareInformation;
        this.hardwareSteuerung = hardwareSteuerung;
        setTimerIntervall(timerIntervall);
             
        this.brauProzess = new BrauAblaufProzess(brauPlan,this.hardwareInformation, this.hardwareSteuerung);        
    }

    @Override
    public IBrauPlan GetBrauPlan() {
        return brauPlan;
    }

    @Override
    public BrauProzessInfo GetProzessInfo() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void Start() throws Exception {
        if(prozessTimer != null)
            throw new Exception("Prozess läuft bereits");
        if(terminated)
            throw new Exception("Prozess durchgelaufen");       
       
        prozessTimer = new Timer();
        prozessTimer.schedule(brauProzess, 0, timerIntervall);
    }

    @Override
    public void Stop() throws Exception {
        if(prozessTimer == null)
            throw new Exception("Prozess nicht gestartet");
        prozessTimer.cancel();
        prozessTimer = null;
    }

    @Override
    public void Pause() throws Exception {
        Stop();
    }    
        
    private void setTimerIntervall(long timerIntervall1) throws Exception 
    {
        if(timerIntervall1 <= 0)
            throw new Exception("intervall muss groesser sein");
        this.timerIntervall = timerIntervall1;
    }
         
    
    private IBrauPlan brauPlan;
    private IHardwareInformation hardwareInformation;
    private IHardwareSteuerung hardwareSteuerung;
    
    private Timer prozessTimer;
    private long timerIntervall;
    
    private TimerTask brauProzess;
    private boolean terminated = false;
}
