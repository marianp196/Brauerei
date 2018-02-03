/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package schemaChecker.commands.hsqldb;

import brauhaus.brauprozess.EState;
import schemaChecker.tables.EDataType;
import schemaChecker.tables.Field;
import schemaChecker.tables.ITable;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;
import schemaChecker.commands.ISqlBuilder;

/** 
 * @author marian
 */
public class HSQLDBCreateCommand implements ISqlBuilder
{
    /*
    * ToDo: refactor
    */
     @Override
    public String CreateCommand(String table, Field[] primaryKeys) throws Exception 
    {        
        if(primaryKeys.length == 0)
            throw new Exception("keine PrimKeyFelder");
        
        String SQL = "CREATE TABLE " + table + "( :FieldListe );";
        
        String fields = getFieldListe(primaryKeys);
        String primaryKeyFields = getPrimaryKeys(primaryKeys);
        
       
        fields += " , " + primaryKeyFields;
        
        SQL = SQL.replaceAll(":FeldListe", fields);
        
        return SQL;
    }

    @Override
    public String AddCollumnCommand(String table,Field field) 
    {
        String SQL = "ALTER TABLE " + table + " ADD " + getField(field, false);
        return SQL;
    } 
    
    private String getPrimaryKeys(Field[] primaryKeys)
    {
        if(primaryKeys.length ==0)
            return "";
        String result = "";

        for(Field f : primaryKeys)
        {
            result += " PRIMARY KEY (" + f.getName() + "), ";
        }
        
        return letztesKommaAbschneiden(result);
    }
    
    private String getFieldListe(Field[] fields)
    {
        String result = " ";
        
        for(Field primKeyField : fields)
        {
            result += getField(primKeyField, true) + " , ";
        }        
       
        result = letztesKommaAbschneiden(result);
        
        return result;
    }

    private String letztesKommaAbschneiden(String result) {
        result = result.substring(0, result.length()-3);
        return result;
    }
    
    private String getField(Field f, boolean notNull)
    {
        String result = f.getName();
        
        result += " " + getDatentyp(f.getType(), f.getLaenge());
        
        if(notNull)
            result += " Not Null ";
        
        return result;
    }
    
    private String getDatentyp(EDataType typ, int laenge)
    {
      if(typ == EDataType.charString)
        return "varchar" + "(" + String.valueOf(laenge) + ")";
      else if(typ == EDataType.doubl)
        return "float";
      else if(typ == EDataType.integer)
        return "INT";
      else if(typ == EDataType.bigInt)
        return "BIGINT";
      else       
        throw new NotImplementedException();
    }
  
        
}
