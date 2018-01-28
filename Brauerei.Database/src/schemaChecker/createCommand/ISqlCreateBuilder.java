/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package schemaChecker.createCommand;

import schemaChecker.tables.ITable;

/**
 *
 * @author marian
 */
public interface ISqlCreateBuilder {
    String CreateCommand(ITable table) throws Exception;
}
