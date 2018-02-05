/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import org.junit.Assert;
import org.junit.Test;
import schemaChecker.commands.ISqlBuilder;
import schemaChecker.commands.hsqldb.HSQLDBCreateCommand;
import schemaChecker.tables.EDataType;
import schemaChecker.tables.Field;

/**
 *
 * @author marian
 */
public class HSQLDBCreateCommandTest {
    
    public HSQLDBCreateCommandTest() {
    }
    
    @Test
    public void CreateCommand_ShouldReturnStatement_IfOneField() throws Exception
    {
        ISqlBuilder sut = createSut();
        
        Field[] fields = new Field[1];
        fields[0] = new Field("f", EDataType.integer);
        
        String sql = sut.CreateCommand("test", fields);
        Assert.assertEquals("CREATE TABLE test( f INT Not Null , PRIMARY KEY (f) );",sql);
    }
    
    @Test
    public void CreateCommand_ShouldReturnStatement_IfFewField() throws Exception
    {
        ISqlBuilder sut = createSut();
        
        Field[] fields = new Field[2];
        fields[0] = new Field("f", EDataType.integer);
        fields[1] = new Field("id", EDataType.integer);
        
        String sql = sut.CreateCommand("test", fields);
        Assert.assertEquals("CREATE TABLE test( f INT Not Null, id INT Not Null , PRIMARY KEY (f), PRIMARY KEY (id) );",sql);
    }
    
    @Test
    public void CreateCommand_ShouldReturnStatement_IfFieldVarchar() throws Exception
    {
        ISqlBuilder sut = createSut();
        
        Field[] fields = new Field[1];
        fields[0] = new Field("f", EDataType.charString, 100);
        
        String sql = sut.CreateCommand("test", fields);
        Assert.assertEquals("CREATE TABLE test( f VARCHAR(100) Not Null , PRIMARY KEY (f) );",sql);
    }
    
    @Test
    public void AddColumn_ShouldReturnStatement() throws Exception
    {
        ISqlBuilder sut = createSut();
        
        Field feld = new Field("f", EDataType.integer);
        
        String sql = sut.AddCollumnCommand("test", feld);
        System.out.println(sql);
        Assert.assertEquals("ALTER TABLE test ADD f INT;", sql);
    }
    
     @Test
    public void AddColumn_ShouldReturnStatement_IfVarchar() throws Exception
    {
        ISqlBuilder sut = createSut();
        
        Field feld = new Field("f", EDataType.charString, 40);
        
        String sql = sut.AddCollumnCommand("test", feld);
        System.out.println(sql);
        Assert.assertEquals("ALTER TABLE test ADD f VARCHAR(40);", sql);
    }
    
    private ISqlBuilder createSut()
    {
         return new HSQLDBCreateCommand();
    }
}
