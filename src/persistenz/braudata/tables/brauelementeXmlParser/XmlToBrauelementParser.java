/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package persistenz.braudata.tables.brauelementeXmlParser;

import brauhaus.bierData.brauelemente.HopfenKochenElement;
import brauhaus.bierData.brauelemente.IBrauelement;
import brauhaus.bierData.brauelemente.PauseElement;
import brauhaus.bierData.brauelemente.TemperaturRastElement;
import java.io.CharArrayReader;
import java.util.ArrayList;
import java.util.Collection;
import javax.xml.bind.JAXB;

/**
 *
 * @author marian
 */
public class XmlToBrauelementParser {
    public Collection<IBrauelement> getBrauelemente(String xml) throws Exception
    {
        CharArrayReader charArrayReader = new CharArrayReader(xml.toCharArray());
        XmlArrayDto xmlArrayDto = JAXB.<XmlArrayDto>unmarshal(charArrayReader, XmlArrayDto.class);
        
        BrauelementDto[] brauelemenDTOs = xmlArrayDto.Brauelemente;
        
        return getBrauelemente(brauelemenDTOs);
    }

    private Collection<IBrauelement> getBrauelemente(BrauelementDto[] brauelemenDTOs) throws Exception {
        ArrayList<IBrauelement> result = new ArrayList<>();
        
        for(BrauelementDto dto : brauelemenDTOs)
        {
            if(dto.Type.equals(HopfenKochenElement.class.getTypeName()))
            {
                result.add(new HopfenKochenElement(dto.Time, dto.OrderNumber));
            }
            
            if(dto.Type.equals(PauseElement.class.getTypeName()))
            {
                result.add(new PauseElement(dto.OrderNumber));
            }
            
            if(dto.Type.equals(TemperaturRastElement.class.getTypeName()))
            {
                result.add(new TemperaturRastElement(dto.Time, dto.OrderNumber, dto.MinTemp, dto.MaxTemp));
            }
            
        }
        return result;
    }
}
