/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hardwaresteuerung.factory;

import hardwaresteuerung.IHardwareInformation;
import hardwaresteuerung.IHardwareSteuerung;

/**
 *
 * @author marian
 */
public interface IHardwareFactory {    
    IHardwareInformation CreateHardwareInformation() throws Exception;
    IHardwareSteuerung CreateHardwareSteuerung() throws Exception;
}
