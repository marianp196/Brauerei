/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package schemaChecker.createCommand;

import brauhaus.brauprozess.EState;
import schemaChecker.tables.EDataType;
import schemaChecker.tables.Field;
import schemaChecker.tables.ITable;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

/**
 *
 * @author marian
 */
public class HSQLDBCreateCommand implements ISqlCreateBuilder
{

    @Override
    public String CreateCommand(ITable table) throws Exception {
        String SQL = "";
        
        if((table.GetFields().length + table.GetPrimaryKeys().length) == 0)
            throw new Exception("keine Felder");
        
        SQL = "CREATE TABLE " + table.GetTableName() + "( :FieldListe );";
        
        String fields = getFieldListe(table.GetPrimaryKeys(), table.GetFields());
        String primaryKeyFields = getPrimaryKeys(table.GetPrimaryKeys());
        
        if(!primaryKeyFields.isEmpty())
            fields += " , " + primaryKeyFields;
        
        SQL = SQL.replaceAll(":FeldListe", fields);
        
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
    
    private String getFieldListe(Field[] primKeys, Field[] fields)
    {
        String result = " ";
        
        for(Field primKeyField : primKeys)
        {
            result += getField(primKeyField, true) + " , ";
        }        
        for(Field field : primKeys)
        {
            result += getField(field, false) + " , ";
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
