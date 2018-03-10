/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package IntegrationTest.util;

import factory.SchemaCheckerFactory;
import checker.ISchemaChecker;
import datenbank.IDatabase;
import java.sql.Connection;
import tables.ITableDefinition;

/**
 *
 * @author marian
 */
public  class TableForTest {
   
    public TableForTest(IDatabase database, ITableDefinition tableDefinition) {
        this.database = database;
        this.tableDefinition = tableDefinition;
    }
    
    public void Reset() throws Exception
    {
        Connection connection = database.GetConnection();
        connection.createStatement().execute("delete from " + tableDefinition.GetTable().GetTableName());
    }
    
    public void CreateSchema() throws Exception{
        ISchemaChecker schemachecker = SchemaCheckerFactory.CreateMySqlSchemaChecker(database);
        schemachecker.AddTable(tableDefinition.GetTable());
        schemachecker.CheckAndCreate();
    }
    
    private IDatabase database;
    private ITableDefinition tableDefinition;   
}
