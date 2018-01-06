/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package brauhaus.brauprozess.temperaturSteuerung.util;

import hardwaresteuerung.IHardwareInformation;
import hardwaresteuerung.IHardwareSteuerung;
import junit.framework.Assert;
import sensoren.common.messergebnis.MessergebnisMetrisch;

/**
 *
 * @author marian
 */
public class HardwareMock implements IHardwareInformation, IHardwareSteuerung{

    @Override
    public MessergebnisMetrisch GetTemperaturBraukessel() {
        return m;
    }

    @Override
    public boolean GetRuehwerkStatus() {
        return ruehrwerkReturn;
    }

    @Override
    public boolean GetHeizWerkStatus() {
        return heizwerkReturn;
    }

    @Override
    public void SetHeizwerkStatus(boolean state) throws Exception {
        Assert.assertEquals(heizwerkExpect, state);
    }

    @Override
    public void SetRuehrwerkStatus(boolean state) throws Exception {
        Assert.assertEquals(ruehrwerkExpect, state);
    }
    
    public void SetMockValue(MessergebnisMetrisch m,boolean heizwerkReturn, boolean ruehrwerkReturn)
    {
       this.m = m;
       this.heizwerkReturn = heizwerkReturn;
       this.ruehrwerkReturn = ruehrwerkReturn;
    }
    
    public void SetMockValue(boolean heizwerkExpect, boolean ruehrwerkExpect)
    {
       this.heizwerkExpect = heizwerkExpect;
       this.ruehrwerkExpect = ruehrwerkExpect;
    }
    
    public void SetMockValue(HardwareMockDTO dto)
    {
        SetMockValue(dto.isHeizwerkExpect(), dto.isRuehrwerkExpect());
        SetMockValue(dto.getM(), dto.isHeizwerkReturn(), dto.isRuehrwerkReturn());
    }
    
    private boolean heizwerkExpect = false;
    private boolean ruehrwerkExpect = false;
    private boolean heizwerkReturn = false;
    private boolean ruehrwerkReturn = false;
    private MessergebnisMetrisch m = new MessergebnisMetrisch(1);
}
