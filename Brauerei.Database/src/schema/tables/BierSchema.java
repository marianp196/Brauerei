/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package schema.tables;

import tables.ITableDefinition;
import tables.Table;
import tables.field.EDataType;
import tables.field.Field;

/**
 *
 * @author marian
 */
public class BierSchema implements ITableDefinition{

    @Override
    public Table GetTable() throws Exception {
        Table result = new Table("Bier");
        
        result.AddPrimaryKey("id", EDataType.integer);
        result.AddField(new Field("name", EDataType.charString, 300));
        result.AddField(new Field("XML_Brauelemnte", EDataType.charString, 3000));
        
        return result;
    }
    
}
