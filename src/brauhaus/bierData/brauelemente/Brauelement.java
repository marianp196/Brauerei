/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package brauhaus.bierData.brauelemente;

import persistenz.braudata.repository.brauelementeXmlParser.BrauelementDto;

/**
 *
 * @author marian
 */
public abstract class Brauelement implements IBrauelement
{
    public Brauelement(long zeit, int orderNumber) throws Exception
    {
        setOrderNumber(orderNumber);
        setZeit(zeit);
    }

    public long GetZeit() {
        return zeit;
    }
    
    public int GetOrderNumber() {
        return orderNumber;
    }
    
     @Override
    public BrauelementDto GetBrauelementDto()
    {
        BrauelementDto result = new BrauelementDto();
        
        result.OrderNumber = orderNumber;
        result.Time = zeit;                
        
        return result;
    }
    
    private void setZeit(long zeit) throws Exception {
        if(zeit < 0)
            throw new Exception("zeit muss größer 0 sein");
        this.zeit = zeit;
    }
  
    private void setOrderNumber(int orderNumber) throws Exception {
         if(orderNumber < 0)
            throw new Exception("orderNumber muss größer 0 sein");
        this.orderNumber = orderNumber;
    }
    
    private long zeit;
    private int orderNumber;
   
}
