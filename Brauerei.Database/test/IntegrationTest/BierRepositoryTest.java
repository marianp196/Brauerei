package IntegrationTest;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import braudata.BierRepositoryFactory;
import braudata.repository.BierRepository;
import datenbank.ConnectionInfo;
import datenbank.Database;
import datenbank.IDatabase;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import IntegrationTest.util.BierTableForTest;
import braudata.repository.IRepository;
import brauhaus.bierData.Bier;
import brauhaus.bierData.brauelemente.HopfenKochenElement;
import brauhaus.bierData.brauelemente.IBrauelement;
import brauhaus.bierData.brauelemente.TemperaturRastElement;
import java.util.ArrayList;
import org.junit.Assert;

/**
 *
 * @author marian
 */
public class BierRepositoryTest {
    
    public BierRepositoryTest() throws Exception 
    {
        ConnectionInfo connectionInfo = new ConnectionInfo("jdbc:hsqldb:mem:mymemdb", "SA", "",
                "org.hsqldb.jdbcDriver");
        database = new Database(connectionInfo);
        bierTable = new BierTableForTest(database);
    }
   
    @Before
    @After
    public void reset() throws Exception 
    {
        bierTable.CreateSchema();
        bierTable.Reset();
    }
    
    @Test
    public void  Create_ReturnsBierWithPrimKey1_IfFirstBier() throws Exception
    {
        IRepository<Integer, Bier> sut = CreateSut();
        Bier bier = sut.CreateNew();      
        Assert.assertEquals(1, bier.getId());
        
    }
    
    @Test
    public void  Create_ReturnsBierWithIncrementetPrimaryKey_IfCreateTwoTimes() throws Exception
    {
        IRepository<Integer, Bier> sut = CreateSut();
        Bier bier = sut.CreateNew();
        Bier bier2 = sut.CreateNew();
        Assert.assertTrue(bier.getId() != bier2.getId());        
    }
    
    @Test
    public void  Update_ShouldReadWriteNameCorrect() throws Exception
    {
        IRepository<Integer, Bier> sut = CreateSut();
        Bier bier = sut.CreateNew();
        bier.setName("Name");
        sut.Update(bier);
        
        Bier readFromDb = sut.Get(1);
        Assert.assertEquals(bier.getName(), readFromDb.getName());
    }
    
    @Test
    public void  Update_ShouldReadWriteNameCorrect_IfNameIsNull() throws Exception
    {
        IRepository<Integer, Bier> sut = CreateSut();
        Bier bier = sut.CreateNew();       
        sut.Update(bier);
        
        Bier readFromDb = sut.Get(1);
        Assert.assertEquals(bier.getName(), readFromDb.getName());
    }
    
    @Test
    public void  Update_ShouldReadWriteBrauelemente() throws Exception
    {
        IRepository<Integer, Bier> sut = CreateSut();
        Bier bier = sut.CreateNew();       
       
        ArrayList<IBrauelement> brauelemente = new ArrayList<>();
        brauelemente.add(new TemperaturRastElement(300, 0, 10, 20));
        brauelemente.add(new TemperaturRastElement(300, 1, 30, 40));
        brauelemente.add(new HopfenKochenElement(200, 2));
        
        bier.setBrauelemente(brauelemente);
        sut.Update(bier);
        
        Bier readFromDb = sut.Get(1);
        //Hier genauer Testen!!!!!!!!!!!!!!!!!!
        Assert.assertEquals(brauelemente.size(), bier.getBrauelemente().size());
    }
    
     @Test
    public void  Update_ShouldReadWriteBrauelemente_IfUpdatedTwice() throws Exception
    {
        IRepository<Integer, Bier> sut = CreateSut();
        Bier bier = sut.CreateNew();       
       
        ArrayList<IBrauelement> brauelemente = new ArrayList<>();
        brauelemente.add(new TemperaturRastElement(300, 0, 10, 20));
        brauelemente.add(new TemperaturRastElement(300, 1, 30, 40));
        brauelemente.add(new HopfenKochenElement(200, 2));
        
        bier.setBrauelemente(brauelemente);
        sut.Update(bier);
        
        brauelemente.add(new HopfenKochenElement(3000, 3));
        sut.Update(bier);
        
        Bier readFromDb = sut.Get(1);
        //Hier genauer Testen!!!!!!!!!!!!!!!!!!
        Assert.assertEquals(brauelemente.size(), bier.getBrauelemente().size());
    }
    
    private IRepository<Integer, Bier> CreateSut() throws Exception
    {
        return new BierRepositoryFactory(database).CreateBierRepositoy();
    }
 
    private IDatabase database;
    private BierTableForTest bierTable;
}
