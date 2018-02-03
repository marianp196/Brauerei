/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package schemaChecker.commands.hsqldb;

import datenbank.IDatabase;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import schemaChecker.commands.ISchemaInfo;
import schemaChecker.tables.EDataType;
import schemaChecker.tables.Field;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

/**
 *
 * @author marian
 */
public class HSQLDBSchemaInfo implements ISchemaInfo{

    public HSQLDBSchemaInfo(IDatabase database) throws Exception {
        con = database.GetConnection();
    }
  
    
    @Override
    public boolean TableExists(String name) throws Exception
    {
        String sql = "select * from tables where name like :name";
        
        sql = sql.replaceAll(":name", name);
        
        ResultSet rs = con.createStatement().executeQuery(sql);
        
        return rs.first();
    }

    @Override
    public Field[] GetFields(String name) throws SQLException, Exception 
    {
       Collection<Field> result = new ArrayList<>();
        
       String sql = "select COLUMN_NAME, TYPE_NAME, COLUMN_SIZE from columns where name like :name";        
       sql = sql.replaceAll(":name", name);
       
       ResultSet rs = con.createStatement().executeQuery(sql);
       
       while(rs.next())
       {
           result.add(new Field(rs.getString(1), getDataType(rs.getString(2))));
       }
       
       return Arrays.copyOf(result.toArray(), result.toArray().length, Field[].class);
    }
    
    private EDataType getDataType(String type)
    {
        if(type.equals("VARCHAR"))
            return EDataType.charString;
        else if(type.equals("INT"))
            return EDataType.integer;
        else if(type.equals("FLOAT"))
            return EDataType.doubl;
        else if(type.equals("BIGINT"))
            return EDataType.bigInt;
        else
            throw new NotImplementedException();
    }
    
    private Connection con;
}
