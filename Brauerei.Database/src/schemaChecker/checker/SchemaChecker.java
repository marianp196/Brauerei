/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package schemaChecker.checker;

import datenbank.IDatabase;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.Collection;
import schemaChecker.commands.ISchemaInfo;
import schemaChecker.commands.ISqlBuilder;
import schemaChecker.tables.ITable;

/**
 *
 * @author marian
 */
public class SchemaChecker implements ISchemaChecker {

    public SchemaChecker(ISchemaInfo schemaInfo, ISqlBuilder sqlBuilder, IDatabase database) throws Exception {
        this.schemaInfo = schemaInfo;
        this.sqlBuilder = sqlBuilder;
        this.connection = database.GetConnection();
        
        tables = new ArrayList<>();
    }
    
    /**
     * Dient zur Definition des Schemas. Angwandt wird es durch aufruf von CheckAndGet
     * @param table
     * @throws Exception 
     */
    @Override
    public void AddTable(ITable table) throws Exception {
        if(tables.stream().filter(t -> t.GetTableName().equals(table.GetTableName())).count() > 0)
            throw new Exception("Tabelle existiert in Schema bereits");
        
        tables.add(table);
    }

    @Override
    public void CheckAndCreate() throws Exception 
    {
        
    }
    
    private Collection<ITable> tables;
    
    private ISchemaInfo schemaInfo;
    private ISqlBuilder sqlBuilder;
    private Connection connection;
}
