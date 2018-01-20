/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package persistenz.braudata.repository.primaryKeyGenerators;

import datenbank.IDatabase;
import java.sql.Statement;
import java.sql.Connection;
import java.sql.ResultSet;

/**
 *
 * @author marian
 */
public class IntegerPrimaryKeyGenerator implements IPrimaryKey<Integer> {
     
    public IntegerPrimaryKeyGenerator(IDatabase database, String primaryKey, String table) throws Exception {
        if(database == null)
            throw new NullPointerException("database");            
        
        buildSql();
        
        this.connection = database.GetConnection();
        this.primaryKey = primaryKey;
        this.table = table;
    }
    
    @Override
    public Integer GetNewPrimaryKey() throws Exception {
        Statement query = connection.createStatement();
        ResultSet rs = query.executeQuery(SQL);
        return rs.getInt("RESULT") + 1;
    }

    private void buildSql() {
        String vorlage = "SELECT MAX(:PRIMARYKEY) AS RESULT FROM :TABLE";
        vorlage = vorlage.replaceAll(":PRIMARYKEY", primaryKey);
        vorlage = vorlage.replaceAll(":TABLE", table);
        SQL = vorlage;        
    }
    
    private Connection connection;
    private String primaryKey;
    private String table;
    private String SQL;    
}
