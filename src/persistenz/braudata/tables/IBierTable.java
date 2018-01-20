/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package persistenz.braudata.tables;

import brauhaus.bierData.Bier;
import java.sql.SQLException;
import java.util.Collection;

/**
 *
 * @author marian
 */
public interface IBierTable {

    void CreateNew(int primaryKey) throws SQLException;

    boolean Exists(int primaryKey) throws SQLException;

    Bier Get(int primaryKey) throws Exception;

    Collection<Integer> GetAllIds() throws SQLException;

    void Remove(int primaryKey) throws SQLException;

    void Update(Bier bier) throws Exception;
    
}
