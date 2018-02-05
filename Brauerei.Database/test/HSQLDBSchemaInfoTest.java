/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import datenbank.ConnectionInfo;
import datenbank.Database;
import datenbank.IDatabase;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Collection;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import schemaChecker.commands.ISchemaInfo;
import schemaChecker.commands.hsqldb.HSQLDBSchemaInfo;
import schemaChecker.tables.EDataType;
import schemaChecker.tables.Field;

/**
 *
 * @author marian
 */
public class HSQLDBSchemaInfoTest {

    public HSQLDBSchemaInfoTest() throws Exception
    {
        ConnectionInfo info = new ConnectionInfo("jdbc:hsqldb:file:./hallo.test", 
                "SA", "", "org.hsqldb.jdbcDriver");
        database = new Database(info);
        connection = database.GetConnection();
    }
        
    @Before
    public void ClearSchema() throws SQLException
    {
        Statement st = connection.createStatement();
        
        try {
            st.execute("drop table test");
        } catch (SQLException ex) {
            Logger.getLogger(HSQLDBSchemaInfoTest.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    @Test
    public void TableExists_ShouldReturnTrue_IfTableExists() throws Exception
    {
        CreateSchemaForTest();
        ISchemaInfo sut = createSut();
        Assert.assertTrue(sut.TableExists("test"));
    }
    
    @Test
    public void TableExists_ShouldReturnFalse_IfTableDoesntExist() throws Exception
    {
        ISchemaInfo sut = createSut();
        Assert.assertFalse(sut.TableExists("test"));
    }
    
    @Test
    public void GetFields_ShouldReturnFields() throws Exception
    {
        CreateSchemaForTest();
        ISchemaInfo sut = createSut();
        Field[] fields = sut.GetFields("test");
        
        Field[] shouldFelder = new Field[3];
        shouldFelder[0] = new Field("id", EDataType.integer);
        shouldFelder[1] = new Field("text", EDataType.charString);
        shouldFelder[2] = new Field("zahl", EDataType.bigInt);
        
        Assert.assertArrayEquals(shouldFelder, fields);
        
    }
    
    private ISchemaInfo createSut() throws Exception
    {
        return new HSQLDBSchemaInfo(database);
    }
    
     public void CreateSchemaForTest() throws Exception
    {
        Statement query = connection.createStatement();
        try
        {
           //Hack :)
           query.executeQuery("select * from test");
        }catch(Exception e)
        {
            query = connection.createStatement();
            query.execute("create table test(id int not null, text varchar(50), zahl bigint, primary key(id))");
        }
    } 
  
    private IDatabase database;
    private Connection connection;
}
