/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package brauhaus.brauprozess;

import brauhaus.brauplan.bierData.Bier;
import brauhaus.brauplan.IBrauPlan;

/**
 *
 * @author marian
 */
public interface IBrauProzess {
    IBrauPlan GetBrauPlan();
    BrauProzessInfo GetProzessInfo();
    void Start() throws Exception;
    void Stop() throws Exception;
    void Pause() throws Exception;
}
