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
public interface IBrauelement {
    int GetOrderNumber();
    BrauelementDto GetBrauelementDto();
}
