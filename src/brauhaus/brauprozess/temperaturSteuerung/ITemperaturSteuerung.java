/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package brauhaus.brauprozess.temperaturSteuerung;

import sensoren.common.messergebnis.MessergebnisMetrisch;

/**
 *
 * @author marian
 */
public interface ITemperaturSteuerung {
    void Iterate(MessergebnisMetrisch temp) throws Exception;
}
