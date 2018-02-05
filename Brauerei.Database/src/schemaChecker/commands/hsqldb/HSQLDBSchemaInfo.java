/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package schemaChecker.commands.hsqldb;

import datenbank.IDatabase;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
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
    {   //Irgendwie alles unschön
        String sql = "SELECT * FROM :name";
        
        sql = sql.replaceAll(":name", name);
        
        boolean found = true;
        try
        {
            con.createStatement().execute(sql);
        }catch(Exception e)
        {
            found = false;
        }
        
        return found;
    }

    @Override
    public Field[] GetFields(String name) throws SQLException, Exception 
    {
        if(! TableExists(name))
            throw new Exception("Tabelle existiert nicht");
        
       Collection<Field> result = new ArrayList<>();
        
       ResultSet rs = getResultSet(name);
       
       result = getFields(rs.getMetaData());
       
       return Arrays.copyOf(result.toArray(), result.toArray().length, Field[].class);
    }

    private ResultSet getResultSet(String name) throws SQLException {
        String sql = "select * from :name";
        sql = sql.replaceAll(":name", name);
        ResultSet rs = con.createStatement().executeQuery(sql);
        return rs;
    }
    
    private Collection<Field> getFields(ResultSetMetaData metaData) throws SQLException, Exception {
       ArrayList<Field> result = new ArrayList<>();
       for(int i =1; i <= metaData.getColumnCount(); i++)
       {
           String name = metaData.getColumnName(i);
           String typ = metaData.getColumnTypeName(i);
           int length = metaData.getPrecision(i);
           
           result.add(new Field(name, getDataType(typ), length));
       }
       
       return result;
    }
    
    private EDataType getDataType(String type)
    {
        if(type.equals("VARCHAR"))
            return EDataType.charString;
        else if(type.equals("INTEGER"))
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
