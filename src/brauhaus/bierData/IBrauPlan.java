/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package brauhaus.bierData;

import brauhaus.bierData.bier.Bier;
import brauhaus.bierData.brauelemente.Brauelement;
import brauhaus.bierData.brauelemente.IBrauelement;
import java.util.ArrayList;

/**
 * Ist die Schnittstelle über die der Braukressel die Bier-Infos für den Brauprozess bekommt.
 * @author marian
 */
public interface IBrauPlan 
{
    Bier GetBierData();
    ArrayList<IBrauelement> GetBrauElemente();
}
