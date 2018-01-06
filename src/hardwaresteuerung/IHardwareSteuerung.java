/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hardwaresteuerung;

/**
 *
 * @author marian
 */
public interface IHardwareSteuerung 
{
    void SetHeizwerkStatus(boolean state) throws Exception;
    void SetRuehrwerkStatus(boolean state) throws Exception;
}
