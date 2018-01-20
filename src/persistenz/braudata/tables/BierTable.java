/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package persistenz.braudata.tables;

import brauhaus.bierData.Bier;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collection;

/**
 *
 * @author marian
 */
public class BierTable {
        
    public BierTable(Connection connection) {
        if(connection == null)
            throw new NullPointerException("connection");
        this.connection = connection;
    }
    
    public void CreateNew(int primaryKey) throws SQLException
    {
        Statement query = connection.createStatement();
        String vorlage = "INSERT INTO BIER(ID) Values (:ID)";
        vorlage = vorlage.replaceAll(":ID", String.valueOf(primaryKey));
        query.execute(vorlage);
    }
    
    public void Remove(int primaryKey) throws SQLException
    {
        Statement query = connection.createStatement();
        String vorlage = "INSERT INTO BIER(ID) Values (:ID)";
        vorlage = vorlage.replaceAll(":ID", String.valueOf(primaryKey));
        query.execute(vorlage);
    }
    
    public void Update(Bier bier) throws Exception
    {
        if(!Exists(bier.getId()))
            throw new Exception("Bier existiert nicht in DB");
        
        Statement query = connection.createStatement();
        String vorlage = "UPDATE Bier SET name = :name WHERE id = :id";
        
        vorlage = vorlage.replaceAll(":name", bier.getName());
        vorlage = vorlage.replaceAll(":id", String.valueOf(bier.getId()));
        
        query.execute(vorlage);
    }   
    
    public Bier Get(int primaryKey) throws Exception
    {
        Statement query = connection.createStatement();
        
        String vorlage = "SELECT * FROM bier WHERE id = :id";
        vorlage = vorlage.replaceAll(":id", String.valueOf(primaryKey));
        
        ResultSet rs =  query.executeQuery(vorlage);
        
        if(!rs.first())
            throw new Exception("Bier mit dem Primarykey " + primaryKey + " kann nicht gelesen werden");               
        
        Bier bier = new Bier(rs.getInt("id"));
        bier.setName(rs.getString("name"));
        
        return bier;
    }
    
    public Collection<Integer> GetAllIds() throws SQLException
    {
        Statement queryAllePrimaryKeys = connection.createStatement();
        ResultSet rs = queryAllePrimaryKeys.executeQuery("Select id from bier");
        
        ArrayList<Integer> result = new ArrayList<>();
        
        while(rs.next())
        {
            result.add(rs.getInt(1));
        }
        
        return result;
    }
    
    public boolean Exists(int primaryKey) throws SQLException
    {
        Statement query = connection.createStatement();
        String vorlage = "SELECT COUNT(*) FROM BIER WHERE ID = :ID";
        vorlage = vorlage.replaceAll(":ID", String.valueOf(primaryKey));
        return query.executeQuery(vorlage).getInt(1) != 0;
    }
  
    private Connection connection;

}
