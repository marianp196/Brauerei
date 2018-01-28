/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package braudata.repository;

import brauhaus.bierData.Bier;
import datenbank.IDatabase;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collection;
import java.util.logging.Level;
import java.util.logging.Logger;
import braudata.repository.primaryKeyGenerators.IPrimaryKey;
import braudata.tables.BierTable;
import braudata.tables.IBierTable;

/**
 *
 * @author marian
 */

public class BierRepository implements IBierRepository{
    /*
    ToDo: Hierrüber könnte man noch gut eine Abstraktion schaffen....
    */
        
    public BierRepository(IPrimaryKey<Integer> primKeyGeneratorBier, 
            IBierTable bierTable, IDatabase database) throws Exception {
        this.connection = database.GetConnection();
        this.primKeyGeneratorBier = primKeyGeneratorBier;
        this.bierTable = bierTable; 
    }
    
    @Override
    public Bier CreateNew() throws Exception 
    {
        //ToDo: Hier sollte umbedingt auf Threadsicherheit geachtet werden
        int primKey = primKeyGeneratorBier.GetNewPrimaryKey();
        bierTable.CreateNew(primKey);
        return new Bier(primKey);
    }

    @Override
    public Bier Get(int id) throws Exception 
    {
        return bierTable.Get(id);
    }

    @Override
    public void Remove(Bier bier) throws SQLException 
    {
        bierTable.Remove(bier.getId());
    }

    @Override
    public void Update(Bier bier) throws Exception 
    {
        bierTable.Update(bier);
    }

    @Override
    public Collection<Bier> List() throws SQLException {
        /*
        Nicht schön, rechenintensiv...aber sind ja keine größeren Datenmengen zu erwarten.
        Mittlerweile auch unnötig..aber ok
        */
               
        Collection<Integer> IDs = bierTable.GetAllIds();
        ArrayList<Bier> biere = new ArrayList<>();
        
        for(int id : IDs)
        {
            try {
                biere.add(Get(id));
            } catch (Exception ex) {
                Logger.getLogger(BierRepository.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        return biere;
    }
    
    Connection connection;
    IPrimaryKey<Integer> primKeyGeneratorBier;    
    IBierTable bierTable;
}
