/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package brauhaus.brauprozess;

import brauhaus.bierData.Bier;

/**
 *
 * @author marian
 */
public interface IBrauProzess {
    Bier GetBier();
    BrauProzessInfo GetProzessInfo();
    void Start() throws Exception;
    void Pause() throws Exception;
}
