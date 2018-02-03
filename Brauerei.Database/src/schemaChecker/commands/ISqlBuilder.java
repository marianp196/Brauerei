/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package schemaChecker.commands;

import schemaChecker.tables.Field;
import schemaChecker.tables.ITable;

/**
 *
 * @author marian
 */
public interface ISqlBuilder {
    String CreateCommand(String table, Field[] primaryKeys) throws Exception;
    String AddCollumnCommand(String table, Field field);  
}
