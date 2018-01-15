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
public class BrauAblaufProzess extends TimerTask {

    public BrauAblaufProzess(IBrauPlan brauPlan, IHardwareInformation hardwareInformation, IHardwareSteuerung hardwareSteuerung) throws Exception {
        if(brauPlan == null)
            throw new NullPointerException("brauplan");
        if(hardwareInformation == null)
            throw new NullPointerException("hardwareInformation");
        if(hardwareSteuerung == null)
            throw new NullPointerException("hardwareSteuerung");
        
        this.brauPlan = brauPlan;
        this.hardwareInformation = hardwareInformation;
        this.hardwareSteuerung = hardwareSteuerung;
        
        this.brauPlanRepository = new BrauPlanRepository(brauPlan.GetBrauElemente());
    }
    
    @Override
    public void run() {
        try {            
            prozessIteration();
        } catch (Exception ex) {
            Logger.getLogger(BrauAblaufProzess.class.getName()).log(Level.SEVERE, null, ex);
        }         
    }

    private void prozessIteration() throws Exception {
        if(checkIfFirstIteration())
            nextElement();
        if (brauPlanRepository.IsEof()) {
            return;
        }
        checkTime();
        checkTemp();
        log();        
    }
     
    public BrauProzessInfo GetBrauProzessInfo()
    {
        return null;
    }
   
    private void checkTime() {
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
        }
    }

    private boolean checkIfFirstIteration() {
        return lastElementChange == null;
    }
        
    private ITemperaturSteuerung temperaturSteuerelement;
    
    private Long lastElementChange;    
    private BrauPlanRepository brauPlanRepository;    
    
    private IBrauPlan brauPlan;
    private IHardwareInformation hardwareInformation;    
    private IHardwareSteuerung hardwareSteuerung;

}
