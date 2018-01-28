package schemaChecker.tables;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author marian
 */
public class Table implements ITable
{

    public Table(String tableName) {
        this.tableName = tableName;
        
        fields = new ArrayList<>();
        primaryKeys = new ArrayList<>();
    }    
    
    @Override
    public String GetTableName() 
    {
        return tableName;
    }

    @Override
    public Field[] GetPrimaryKeys() {
        return Arrays.copyOf(primaryKeys.toArray(), primaryKeys.toArray().length, Field[].class); 
    }

    @Override
    public Field[] GetFields() {
        return Arrays.copyOf(fields.toArray(), fields.toArray().length, Field[].class); 
    }
    
    public void AddPrimaryKey(Field field) throws Exception
    {
        if(field == null)
            throw new NullPointerException("field");
        if(fieldExists(field))
            throw new Exception("Das Feld existiert bereits");
     
        primaryKeys.add(field);                
    }
    
    public void AddField(Field field) throws Exception
    {
         if(field == null)
            throw new NullPointerException("field");
        if(fieldExists(field))
            throw new Exception("Das Feld existiert bereits");
        
        fields.add(field);
    }
    
    @Deprecated
    public void AddPrimaryKey(String name, EDataType typ) throws Exception
    {
        AddPrimaryKey(new Field(name, typ,500));
    }
    
    @Deprecated
    public void AddField(String name, EDataType typ) throws Exception
    {
        AddField(new Field(name, typ,500));
    }
    
    private boolean fieldExists(Field field)
    {
        if(fields.stream().filter(f -> f.getName().equals(field.getName())).count() > 0)
            return true;
        
        if(primaryKeys.stream().filter(f -> f.getName().equals(field.getName())).count() > 0)
            return true;
        
        return false;
    }
    
    private String tableName;
    private Collection<Field> fields;
    private Collection<Field> primaryKeys;
}
