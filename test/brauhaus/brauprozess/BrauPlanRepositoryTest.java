package brauhaus.brauprozess;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import brauhaus.brauprozess.brauPlanRepository.BrauPlanRepository;
import brauhaus.bierData.brauelemente.Brauelement;
import brauhaus.bierData.brauelemente.HopfenKochenElement;
import brauhaus.bierData.brauelemente.IBrauelement;
import brauhaus.bierData.brauelemente.TemperaturRastElement;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import junit.framework.Assert;
import org.junit.Test;


/**
 *
 * @author marian
 */
public class BrauPlanRepositoryTest {
    
    public BrauPlanRepositoryTest() {
    }
    
   
    private BrauPlanRepository createSut(ArrayList<IBrauelement> ar) throws Exception
    {
        return new BrauPlanRepository(ar);
    }
    
    @Test
    public void Create_ThrowsException_IfNoUniqueIds()
    {
        try {
            BrauPlanRepository sut = createSut(geTestBauelemente_withoutUniqueIds());
            Assert.assertFalse(true);
        } catch (Exception ex) {
            Assert.assertFalse(false);
            Logger.getLogger(BrauPlanRepositoryTest.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    @Test
    public void Next_ReturnAllElementsInRightOrder_IfIsSortedRight() throws Exception
    {
        BrauPlanRepository sut = createSut(getTestBrauelemente());
        IBrauelement brauelement = sut.Next();
        int i =0;
        while(brauelement != null)
        {
            Assert.assertEquals(i, brauelement.GetOrderNumber());
            brauelement = sut.Next();
            i++;
        }
        Assert.assertEquals(4+1, i);
    }
    
    @Test
    public void GetActualElement_Return() throws Exception
    {
        BrauPlanRepository sut = createSut(getTestBrauelemente());
        IBrauelement brauelement = sut.Next();
        int i =0;
        while(brauelement != null)
        {
            Assert.assertEquals(i, brauelement.GetOrderNumber());
            Assert.assertEquals(brauelement.GetOrderNumber(), sut.GetActualElement().GetOrderNumber());
            brauelement = sut.Next();
            i++;
        }
        Assert.assertEquals(4+1, i);
    }
    
     @Test
    public void Next_ReturnNull_IfListEmpty() throws Exception
    {
        BrauPlanRepository sut = createSut(new ArrayList<>());
        
        Assert.assertEquals(null, sut.Next());
    }
    
    private ArrayList<IBrauelement> getTestBrauelemente() throws Exception
    {
        ArrayList<IBrauelement> result = new ArrayList<>();
        result.add(new TemperaturRastElement(100, 0, 10, 20));
        result.add(new TemperaturRastElement(200, 2, 10, 20));
        result.add(new TemperaturRastElement(200, 1, 10, 20));
        result.add(new TemperaturRastElement(100, 3, 10, 20));
        result.add(new HopfenKochenElement(100, 4));
        return result;
    }
    
    private ArrayList<IBrauelement> geTestBauelemente_withoutUniqueIds() throws Exception
    {
        ArrayList<IBrauelement> result = new ArrayList<>();
        result.add(new TemperaturRastElement(100, 0, 10, 20));
        result.add(new TemperaturRastElement(200, 2, 10, 20));
        result.add(new TemperaturRastElement(200, 1, 10, 20));
        result.add(new TemperaturRastElement(100, 0, 10, 20));
        return result;
    }
}
