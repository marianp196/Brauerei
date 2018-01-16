/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package brauhaus.brauprozess;

import brauhaus.bierData.IBrauPlan;
import brauhaus.bierData.brauelemente.Brauelement;
import brauhaus.bierData.brauelemente.HopfenKochenElement;
import brauhaus.bierData.brauelemente.IBrauelement;
import brauhaus.bierData.brauelemente.PauseElement;
import brauhaus.bierData.brauelemente.TemperaturRastElement;
import brauhaus.brauprozess.BrauProzessInfo;
import brauhaus.brauprozess.brauPlanRepository.BrauPlanRepository;
import brauhaus.brauprozess.temperaturSteuerung.HopfenkochenSteuerer;
import brauhaus.brauprozess.temperaturSteuerung.ITemperaturSteuerung;
import brauhaus.brauprozess.temperaturSteuerung.TemperaturRastSteuerer;
import hardwaresteuerung.IHardwareInformation;
import hardwaresteuerung.IHardwareSteuerung;
import java.util.TimerTask;
import java.util.logging.Level;
import java.util.logging.Logger;
import sensoren.common.messergebnis.MessergebnisMetrisch;

/**
 *
 * @author marian
 */
public class BrauAblaufProzess extends TimerTask{

    public BrauAblaufProzess(IBrauPlan brauPlan, 
                             IHardwareInformation hardwareInformation, 
                             IHardwareSteuerung hardwareSteuerung,
                             IBrauProzess brauProzess) throws Exception {
        if(brauPlan == null)
            throw new NullPointerException("brauplan");
        if(hardwareInformation == null)
            throw new NullPointerException("hardwareInformation");
        if(hardwareSteuerung == null)
            throw new NullPointerException("hardwareSteuerung");
        if(brauProzess == null)
            throw new NullPointerException("brauProzess");
        
        this.brauPlan = brauPlan;
        this.hardwareInformation = hardwareInformation;
        this.hardwareSteuerung = hardwareSteuerung;
        this.brauProzess = brauProzess;
        
        this.brauPlanRepository = new BrauPlanRepository(brauPlan.GetBrauElemente());
    }
    
   
    public BrauProzessInfo GetProzessInfo() {
        throw new UnsupportedOperationException("Not supported yet."); 
    }

    public void run() {
        try {          
            prozessIteration();
        } catch (Exception ex) {
            Logger.getLogger(BrauAblaufProzess.class.getName()).log(Level.SEVERE, null, ex);
        }         
    }

    private void prozessIteration() throws Exception {        
        checkTime();
        
        if (brauPlanRepository.IsEof()) {
            return;
        }
        
        if(checkIfPause())
            return;
        
        checkTemp();
        log();        
    }
   
    private boolean checkIfPause() throws Exception {
        if (brauPlanRepository.GetActualElement() instanceof PauseElement) {
            temperaturSteuerelement =null;
            lastElementChange = null;
            brauProzess.Pause();
            return true;
        }
        return false;        
    }
   
    private void checkTime() {
        if(lastElementChange == null)
        {
           nextElement(); 
           return;
        }            
        
        long timeSinceElementCahange = System.currentTimeMillis() - lastElementChange;
        if(timeSinceElementCahange >= ((Brauelement)brauPlanRepository.GetActualElement()).GetZeit())
            nextElement();
    }
    
    private void checkTemp() throws Exception {
        MessergebnisMetrisch messergebnisMetrisch = hardwareInformation.GetTemperaturBraukessel();
        temperaturSteuerelement.Iterate(messergebnisMetrisch);
    }          
    
    private void log() {
        return;
    }
       
    private void nextElement() {
        brauPlanRepository.Next();
        lastElementChange = System.currentTimeMillis();
        
        IBrauelement element = brauPlanRepository.GetActualElement();
        if(element instanceof HopfenKochenElement)
        {
            temperaturSteuerelement = new HopfenkochenSteuerer((HopfenKochenElement)element, 
                    hardwareInformation, 
                    hardwareSteuerung);
        }else if(element instanceof TemperaturRastElement)
        {
            temperaturSteuerelement = new TemperaturRastSteuerer((TemperaturRastElement)element, 
                    hardwareInformation, 
                    hardwareSteuerung);
        }else
        {
            temperaturSteuerelement = null; //ToDo: gefährlich !!!!!!
        }
        
    }

    private ITemperaturSteuerung temperaturSteuerelement;
    
    private Long lastElementChange;    
    private BrauPlanRepository brauPlanRepository;    
    
    private IBrauPlan brauPlan;
    private IHardwareInformation hardwareInformation;    
    private IHardwareSteuerung hardwareSteuerung;
    private IBrauProzess brauProzess;
    
}
