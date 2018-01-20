/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package brauhaus.bierData.brauelemente;

import persistenz.braudata.tables.brauelementeXmlParser.BrauelementDto;

/**
 *
 * @author marian
 */
public class PauseElement implements IBrauelement
{
    public PauseElement(int orderNumber) throws Exception {
        setOrderNumber(orderNumber);
    }

    @Override
    public int GetOrderNumber() {
        return orderNumber;
    }
    
     @Override
    public BrauelementDto GetBrauelementDto() {
        BrauelementDto result =  new BrauelementDto();
        result.Type = this.getClass().getSimpleName();
        result.OrderNumber = orderNumber;
        return result;
    }

    private void setOrderNumber(int orderNumber) throws Exception 
    {
        if(orderNumber < 0)
            throw new Exception("OrderNumber muss größer null sein");
        this.orderNumber = orderNumber;
    } 
    
    
    private int orderNumber;    
}
