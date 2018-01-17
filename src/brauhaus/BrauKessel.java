/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package brauhaus;

import hardwaresteuerung.IHardwareInformation;
import hardwaresteuerung.IHardwareSteuerung;
import java.util.Observable;
import java.util.Observer;
import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Level;
import java.util.logging.Logger;
import persistenz.Konfiguration.IKonfiguration;
import brauhaus.actions.IActionTryChangeSteuerelementState;
import brauhaus.bierData.Bier;
import brauhaus.bierData.IBrauPlan;
import brauhaus.brauprozess.TimerSteuerung;

/**
 *
 * @author marian
 */
public class BrauKessel extends Observable implements IBrauKessel {

     public BrauKessel(IHardwareSteuerung hardwareSteuerung, 
            IHardwareInformation hardwareInformation, 
            IKonfiguration konfiguration) 
    {
         commonConstructor(hardwareSteuerung, hardwareInformation, konfiguration, null);
    }
    
    public BrauKessel(IHardwareSteuerung hardwareSteuerung, 
            IHardwareInformation hardwareInformation, 
            IKonfiguration konfiguration,
            IActionTryChangeSteuerelementState actionSignal) 
    {
        
        commonConstructor(hardwareSteuerung, hardwareInformation, konfiguration, actionSignal);
    }

   
    @Override
    public BrauhausInfo GetInfo() {        
        return new BrauhausInfo(hardwareInformation.GetTemperaturBraukessel(),//Unnötige Beziehung
                                hardwareInformation.GetHeizWerkStatus(),
                                hardwareInformation.GetRuehwerkStatus());
    }

    @Override
    public void SetRuehwerkStatus(boolean state) 
    {
        try {
            hardwareSteuerung.SetRuehrwerkStatus(state);
            signalAction();
        } catch (Exception ex) {
            Logger.getLogger(BrauKessel.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void SetHeizwerkStatus(boolean state) {
        try {
            hardwareSteuerung.SetHeizwerkStatus(state);
            signalAction();
        } catch (Exception ex) {
            Logger.getLogger(BrauKessel.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void StartBrauProzess(Bier bier) throws Exception 
    {
        if(brauProzess != null)
            throw new Exception("Aktueller Brauprozess wurde nicht gestoppt!");
        if(bier == null)
            throw new NullPointerException("bier");
        if(bier.getBrauelemente() == null || bier.getBrauelemente().isEmpty())
            throw new Exception("kein Baruprozess konfiguriert");
        
        brauProzess = new TimerSteuerung(bier, 500, 
                hardwareInformation, hardwareSteuerung);
        brauProzess.Start();
    }
    
    @Override
    public void PlayBrauProzess() throws Exception 
    {
        if(brauProzess == null)
            throw new Exception("Aktueller Brauprozess existiert nicht!");
        brauProzess.Start();
    }
   

    @Override
    public void PauseBrauProzess() throws Exception {
        if(brauProzess == null)
           throw new Exception("Aktueller Brauprozess existiert nicht!");
        brauProzess.Pause();
    }

    @Override
    public void StopBrauProzess() throws Exception {
        if(brauProzess == null)
           throw new Exception("Aktueller Brauprozess existiert nicht!");
        brauProzess.Pause();
        brauProzess = null;
    }
    
     private void commonConstructor(IHardwareSteuerung hardwareSteuerung1, IHardwareInformation hardwareInformation1, IKonfiguration konfiguration1, IActionTryChangeSteuerelementState actionSignal1) throws NullPointerException {
        if (hardwareSteuerung1 == null) {
            throw new NullPointerException("hardwareSteuerung");
        }
        if (hardwareInformation1 == null) {
            throw new NullPointerException("hardwareInformation");
        }
        if (konfiguration1 == null) {
            throw new NullPointerException("konfiguration");
        }
        this.hardwareSteuerung = hardwareSteuerung1;
        this.hardwareInformation = hardwareInformation1;
        this.konfiguration = konfiguration1;
        this.actionSignal = actionSignal1;
        setObserverTimer();
    }
    
    private void setObserverTimer() {
        Timer infoObserverProzess = new Timer();
        infoObserverProzess.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                setChanged();
                notifyObservers((Object)GetInfo());
            }
        }
        , 0, 100);
    }
    
    private void signalAction() {
        if(actionSignal != null)
            actionSignal.MachEs();//Ich will C#
    }
    
    private Timer infoObserverProzess;
    private TimerSteuerung brauProzess;
    
    private IHardwareSteuerung hardwareSteuerung;
    private IHardwareInformation hardwareInformation;
    private IKonfiguration konfiguration;
    private IActionTryChangeSteuerelementState actionSignal;  

    
}
