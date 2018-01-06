/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package brauhaus;

import brauhaus.brauplan.IBrauPlan;

/**
 * Obesrste Ebene über die ein Brauprozess besteuert werden kann.
 * @author marian
 */
public interface IBrauKessel
{
    BrauhausInfo GetInfo();
    void SetRuehwerkStatus(boolean state);
    void SetHeizwerkStatus(boolean state);
    
    void PlayBrauProzess(IBrauPlan brauPlan);
    void PauseBrauProzess();
    void StopBrauProzess();
}
