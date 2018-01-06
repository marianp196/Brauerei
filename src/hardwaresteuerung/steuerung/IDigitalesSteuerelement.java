/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hardwaresteuerung.steuerung;

/**
 *
 * @author marian
 */
public interface IDigitalesSteuerelement 
{
    boolean GetState();
    void SetState(boolean b) throws Exception;
    void On() throws Exception;
    void Off() throws Exception;
    void InvertState() throws Exception;    
}
