/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package braudata.repository;

import brauhaus.bierData.Bier;
import java.util.Collection;

/**
 *
 * @author marian
 */
public interface IBierRepository {
    /**
     * Legt einen neuen Datensatz an und gibt ein Objekt, 
     * einzig gefüllt mit dem PrimaryKey, zurück
     * @return 
     */
    Bier CreateNew() throws Exception;
    /**
     * Gibt vorhandenes Datenbankobjekt zurück.
     * @param id
     * @return 
     */
    Bier Get(int id) throws Exception;
    
    void Remove(Bier bier) throws Exception;
    void Update(Bier bier)throws Exception;
    
    Collection<Bier> List() throws Exception;
}
