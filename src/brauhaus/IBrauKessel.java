/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package brauhaus;

import brauhaus.bierData.IBrauPlan;

/**
 * Obesrste Ebene über die ein Brauprozess besteuert werden kann.
 * @author marian
 */
public interface IBrauKessel
{
    BrauhausInfo GetInfo();
    void SetRuehwerkStatus(boolean state);
    void SetHeizwerkStatus(boolean state);
    
    void StartBrauProzess(IBrauPlan brauPlan) throws Exception;
    void PlayBrauProzess() throws Exception;
    void PauseBrauProzess() throws Exception;
    void StopBrauProzess() throws Exception;
}
