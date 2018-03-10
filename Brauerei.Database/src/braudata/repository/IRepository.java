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
public interface IRepository<TKey, TValue> {
    /**
     * Legt einen neuen Datensatz an und gibt ein Objekt, 
     * einzig gefüllt mit dem PrimaryKey, zurück
     * @return 
     */
    TValue CreateNew() throws Exception;
    /**
     * Gibt vorhandenes Datenbankobjekt zurück.
     * @param id
     * @return 
     */
    TValue Get(TKey id) throws Exception;
    
    void Remove(TValue bier) throws Exception;
    void Update(TValue bier)throws Exception;
    
    Collection<TValue> List() throws Exception;
}
