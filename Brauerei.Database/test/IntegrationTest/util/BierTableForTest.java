/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package IntegrationTest.util;

import datenbank.IDatabase;
import java.sql.Connection;
import schema.tables.BierSchema;

/**
 *
 * @author marian
 */
public class BierTableForTest extends TableForTest {

    public BierTableForTest(IDatabase database) {
        super(database, new BierSchema());
    }

}
