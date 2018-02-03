/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package schemaChecker.commands;

import schemaChecker.tables.Field;

/**
 *
 * @author marian
 */
public interface ISchemaInfo {
    boolean TableExists(String name) throws Exception;
    Field[] GetFields(String name) throws Exception;
}
