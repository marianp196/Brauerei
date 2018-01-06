/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package brauhaus;

import sensoren.common.messergebnis.MessergebnisMetrisch;

/**
 *
 * @author marian
 */
public class BrauhausInfo {

    public BrauhausInfo(MessergebnisMetrisch temp, boolean heizWerkAn, boolean ruehrwerkAn) {
        this.temp = temp;
        this.heizWerkAn = heizWerkAn;
        this.ruehrwerkAn = ruehrwerkAn;
    }   
    

    public double getTemp() throws Exception {
        return temp.GetWert();
    }

    public boolean isHeizWerkAn() {
        return heizWerkAn;
    }

    public boolean isRuehrwerkAn() {
        return ruehrwerkAn;
    }
    
    private MessergebnisMetrisch temp;
    private boolean heizWerkAn;
    private boolean ruehrwerkAn;
}
