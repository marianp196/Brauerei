/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hardwaresteuerung;

import sensoren.common.messergebnis.MessergebnisMetrisch;

/**
 *
 * @author marian
 */
public interface IHardwareInformation {
    MessergebnisMetrisch GetTemperaturBraukessel();
    boolean GetRuehwerkStatus();
    boolean GetHeizWerkStatus();
}
