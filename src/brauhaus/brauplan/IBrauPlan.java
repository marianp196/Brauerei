/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package brauhaus.brauplan;

import brauhaus.brauplan.bierData.Bier;
import brauhaus.brauplan.brauelemente.Brauelement;
import java.util.ArrayList;

/**
 * Ist die Schnittstelle über die der Braukressel die Bier-Infos für den Brauprozess bekommt.
 * @author marian
 */
public interface IBrauPlan 
{
    Bier GetBierData();
    ArrayList<Brauelement> GetBrauElemente();
}
