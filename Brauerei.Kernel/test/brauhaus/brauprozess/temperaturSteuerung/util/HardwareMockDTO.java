/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package brauhaus.brauprozess.temperaturSteuerung.util;

import sensoren.common.messergebnis.MessergebnisMetrisch;

/**
 *
 * @author marian
 */
public class HardwareMockDTO {

    public HardwareMockDTO(boolean heizwerkExpect, boolean ruehrwerkExpect, boolean heizwerkReturn, boolean ruehrwerkReturn, MessergebnisMetrisch m) {
        this.heizwerkExpect = heizwerkExpect;
        this.ruehrwerkExpect = ruehrwerkExpect;
        this.heizwerkReturn = heizwerkReturn;
        this.ruehrwerkReturn = ruehrwerkReturn;
        this.m = m;
    }

    public boolean isHeizwerkExpect() {
        return heizwerkExpect;
    }

    public boolean isRuehrwerkExpect() {
        return ruehrwerkExpect;
    }

    public boolean isHeizwerkReturn() {
        return heizwerkReturn;
    }

    public boolean isRuehrwerkReturn() {
        return ruehrwerkReturn;
    }

    public MessergebnisMetrisch getM() {
        return m;
    }
       
    
    private boolean heizwerkExpect ;
    private boolean ruehrwerkExpect;
    private boolean heizwerkReturn ;
    private boolean ruehrwerkReturn;
    private MessergebnisMetrisch m ;
}
