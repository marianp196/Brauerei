/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package schema;

import brauhaus.bierData.Bier;
import checker.ISchemaChecker;
import schema.tables.BierSchema;
import tables.Table;

/**
 *
 * @author marian
 */
public class Schema {

    public Schema(ISchemaChecker schemaChecker) 
    {
        this.schemaChecker = schemaChecker;
    }  
    
    public void Create() throws Exception
    {
        addTables();        
        schemaChecker.CheckAndCreate();
    }

    private void addTables() throws Exception {
        Table bier = new BierSchema().GetTable();
        schemaChecker.AddTable(bier);
    }

    private ISchemaChecker schemaChecker;
}
