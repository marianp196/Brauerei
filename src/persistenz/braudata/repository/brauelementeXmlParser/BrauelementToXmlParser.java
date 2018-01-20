/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package persistenz.braudata.repository.brauelementeXmlParser;

import brauhaus.bierData.brauelemente.IBrauelement;
import com.sun.corba.se.impl.io.ObjectStreamField;
import com.sun.corba.se.impl.io.OutputStreamHook;
import java.io.CharArrayWriter;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collection;
import javax.naming.spi.DirStateFactory.Result;
import javax.xml.bind.JAXB;

/**
 *
 * @author marian
 */
public class BrauelementToXmlParser 
{
    public String BrauelemteToXml(Collection<IBrauelement> brauelemente) throws Exception
    {
        BrauelementDto[] ar = new BrauelementDto[brauelemente.size()];
        int i =0;
        for(IBrauelement b : brauelemente)
        {
            ar[i] = b.GetBrauelementDto();
            i++;
        }
        
        XmlArrayDto brauelementXml = new XmlArrayDto();
        brauelementXml.Brauelemente = ar;
        
        CharArrayWriter charArrayWriter = new CharArrayWriter();
        
        JAXB.marshal(brauelementXml, charArrayWriter);
        return charArrayWriter.toString();
    }
}
