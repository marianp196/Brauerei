/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package braudata;

import braudata.repository.BierRepository;
import braudata.primaryKeyGenerators.IPrimaryKey;
import braudata.primaryKeyGenerators.IntegerPrimaryKeyGenerator;
import braudata.tables.BierTable;
import braudata.tables.IBierTable;
import datenbank.IDatabase;
import braudata.repository.IRepository;

/**
 *
 * @author marian
 */
public class BierRepositoryFactory 
{

    public BierRepositoryFactory(IDatabase database) {
        if(database == null)
            throw new NullPointerException("database");
        this.database = database;
    }
    
    
    public IRepository CreateBierRepositoy() throws Exception
    {
        IPrimaryKey<Integer> primKeyGenerator = new IntegerPrimaryKeyGenerator(database,"ID" , "Bier");
        IBierTable bierTable = new BierTable(database.GetConnection());
        
        return new BierRepository(primKeyGenerator, bierTable, database);
    }
    
    private IDatabase database;
}
