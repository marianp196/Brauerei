/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package persistenz.braudata.repository.primaryKeyGenerators;

import datenbank.IDatabase;

/**
 *
 * @author marian
 */
public class IntegerPrimaryKeyGenerator implements IPrimaryKey<Integer> {
    
    @Override
    public Integer GetNewPrimaryKey() throws Exception {
        return null;
    }

    private IDatabase database;
}
