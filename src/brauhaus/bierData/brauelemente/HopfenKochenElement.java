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
public class HopfenKochenElement extends Brauelement
{    
    public HopfenKochenElement(long zeit, int orderNumber) throws Exception {
        super(zeit, orderNumber);
    }

    @Override
    public BrauelementDto GetBrauelementDto() {
        BrauelementDto result =  super.GetBrauelementDto();
        result.Type = this.getClass().getTypeName();
        return result;
    }
    
    
}
