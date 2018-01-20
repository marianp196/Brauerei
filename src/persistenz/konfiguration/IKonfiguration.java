/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package persistenz.konfiguration;

import abstractions.ISensorKonfiguration;
import sensoren.common.oneWire.OneWireKonfiguration;

/**
 *
 * @author marian
 */
public interface IKonfiguration {
    ISensorKonfiguration GetThermometerKonfiguration() throws Exception;
    SteuerelementKonfiguration GetHeizwerkKonfiguration() throws Exception;
    SteuerelementKonfiguration GetRuehrwerkKonfiguration() throws Exception;    
}
