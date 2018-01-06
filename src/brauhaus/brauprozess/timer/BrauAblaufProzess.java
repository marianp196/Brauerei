/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package brauhaus.brauprozess.timer;

import brauhaus.brauplan.IBrauPlan;
import brauhaus.brauplan.brauelemente.Brauelement;
import brauhaus.brauplan.brauelemente.HopfenKochen;
import brauhaus.brauplan.brauelemente.TemperaturRast;
import brauhaus.brauprozess.BrauProzessInfo;
import brauhaus.brauprozess.brauPlanRepository.BrauPlanRepository;
import brauhaus.brauprozess.timer.temperaturSteuerung.HopfenkochenSteuerer;
import brauhaus.brauprozess.timer.temperaturSteuerung.ITemperaturSteuerung;
import brauhaus.brauprozess.timer.temperaturSteuerung.TemperaturRastSteuerer;
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
        if(timeSinceElementCahange >= brauPlanRepository.GetActualElement().getZeit())
            nextElement();
    }
    
    private void checkTemp() throws Exception {
        MessergebnisMetrisch messergebnisMetrisch = hardwareInformation.GetTemperaturBraukessel();
        temperaturSteuerelement.Iterate(messergebnisMetrisch);
    }          
    
    private void log() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
   
    
    private void nextElement() {
        brauPlanRepository.Next();
        lastElementChange = System.currentTimeMillis();
        
        Brauelement element = brauPlanRepository.GetActualElement();
        if(element instanceof HopfenKochen)
        {
            temperaturSteuerelement = new HopfenkochenSteuerer((HopfenKochen)element, 
                    hardwareInformation, 
                    hardwareSteuerung);
        }else if(element instanceof TemperaturRast)
        {
            temperaturSteuerelement = new TemperaturRastSteuerer((TemperaturRast)element, 
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
