/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package brauhaus.brauprozess;

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
             
        initBrauprozess();        
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
        brauProzess.Start();
    }

    @Override
    public void Pause() throws Exception {
        brauProzess.Pause();
    }
   
    private void setTimerIntervall(long timerIntervall1) throws Exception 
    {
        if(timerIntervall1 <= 0)
            throw new Exception("intervall muss groesser sein");
        this.timerIntervall = timerIntervall1;
    }
     
    private void initBrauprozess() throws Exception {
        this.brauProzess = new BrauAblaufProzess(brauPlan, this.hardwareInformation, this.hardwareSteuerung);
        prozessTimer = new Timer();
        prozessTimer.schedule((TimerTask)brauProzess, 0, timerIntervall);
    }

    
    private IBrauPlan brauPlan;
    private IHardwareInformation hardwareInformation;
    private IHardwareSteuerung hardwareSteuerung;
    
    private Timer prozessTimer;
    private long timerIntervall;
    
    private IBrauProzess brauProzess;
}
