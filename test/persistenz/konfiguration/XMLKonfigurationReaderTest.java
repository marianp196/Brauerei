/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package persistenz.konfiguration;

import java.io.File;
import java.io.FileWriter;
import junit.framework.Assert;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.Text;
import org.jdom2.output.XMLOutputter;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import sensoren.common.oneWire.OneWireKonfiguration;

/**
 *
 * @author marian
 */
public class XMLKonfigurationReaderTest {
    
    @Test
    public void GetThermometerKonfiguration_ShouldReturnValuesFromFile_IfConfiguredRight() throws Exception
    {       
        File testFile = erstelleTestXMLContent(this.adresse, this.pin, 500, heizwerkKonf, ruehrwerkKonf);
        IKonfiguration sut = createSut(testFile);
        
        OneWireKonfiguration konf = (OneWireKonfiguration)sut.GetThermometerKonfiguration();
        Assert.assertEquals(this.adresse, konf.getAdresse());
        Assert.assertEquals(this.pin, konf.getOnewirePin());
        Assert.assertEquals(500, (long)konf.GetResultLifeTimeMilliSeconds());
    }
    
    @Test
    public void GetHeizwerkKonfiguration_ShouldReturnValuesFromFile_IfConfiguredRight() throws Exception
    {       
        File testFile = erstelleTestXMLContent(this.adresse, this.pin, 500, heizwerkKonf, ruehrwerkKonf);
        IKonfiguration sut = createSut(testFile);
        
        SteuerelementKonfiguration konf = sut.GetHeizwerkKonfiguration();
        Assert.assertEquals(heizwerkKonf.getElementPin(), konf.getElementPin());
        Assert.assertEquals(heizwerkKonf.getSignalPin(), konf.getSignalPin());
    }
    
     @Test
    public void GetRuehrwerkKonfiguration_ShouldReturnValuesFromFile_IfConfiguredRight() throws Exception
    {       
        File testFile = erstelleTestXMLContent(this.adresse, this.pin, 500, heizwerkKonf, ruehrwerkKonf);
        IKonfiguration sut = createSut(testFile);
        
        SteuerelementKonfiguration konf = sut.GetRuehrwerkKonfiguration();
        Assert.assertEquals(ruehrwerkKonf.getElementPin(), konf.getElementPin());
        Assert.assertEquals(ruehrwerkKonf.getSignalPin(), konf.getSignalPin());
    }
    
    @Test
    public void GetThermometerKonfiguration_ShouldThrowException_IfTimeConfigFalse() throws Exception
    {       
        File testFile = erstelleTestXMLContent(this.adresse, this.pin, -500, heizwerkKonf, ruehrwerkKonf);
        
        IKonfiguration sut = null;
        try {
            sut = createSut(testFile);
            assertFalse(true);
        } catch (Exception exception) {
        }       
       
    }
    
    @Test
    public void GetThermometerKonfiguration_ShouldThrowException_IfAdressConfigFalse() throws Exception
    {       
        File testFile = erstelleTestXMLContent("", this.pin,500, heizwerkKonf, ruehrwerkKonf);
        
        IKonfiguration sut = null;
        try {
            sut = createSut(testFile);
            assertFalse(true);
        } catch (Exception exception) {
        }      
       
    }

    /*
    ToDo: Tests für falsche XML-Struktur
    */

    private IKonfiguration createSut(File testFile) throws Exception {
        IKonfiguration sut = new XMLKonfigurationReader(testFile);
        return sut;
    }
    
    private File erstelleTestXMLContent(String thermometerAdresse, int oneWirePin, long zeitErgebnis,
        SteuerelementKonfiguration heizwerkKonf, SteuerelementKonfiguration ruehrwerkKonf) throws Exception
    {
        
        /*
        Vorsicht: Pin kann Probleme machen, wenn harcoded aus Sensoren kommt
        //ToDo
        //ToDo: Weniger ToDos verwenden
        */
       File fxml;
       FileWriter io;
              

        Element root = getXMLDoc(thermometerAdresse, oneWirePin, zeitErgebnis, heizwerkKonf, ruehrwerkKonf);
                  
        XMLOutputter out = new XMLOutputter();
        Document xml = new Document(root);
            
        fxml = new File(System.getProperty("user.dir") + "test.xml");
        if(!fxml.exists())
        {
            fxml.createNewFile();
        }
        io = new FileWriter(fxml);
        out.output(xml, io);
        return fxml;
    }

    // TODO add test methods here.
    // The methods must be annotated with annotation @Test. For example:
    //
    // @Test
    // public void hello() {}

    private Element getXMLDoc(String thermometerAdresse, int oneWirePin, long zeitErgebnis,
            SteuerelementKonfiguration heizwerkKonf, SteuerelementKonfiguration ruehrwerkKonf) {
        /*
        <brauhaus>
        <hardware>
        <oneWirePin>4</oneWirePin>
        <thermometer>
        <adresse>28-0516a41aadff</adresse>
        <zeitMilliSekunden>500</zeitMilliSekunden>
        </thermometer>
        </hardware>
        </brauhaus>
        */
        Element root = new Element("brauhaus");
        
        Element hardware = new Element("hardware");
        root.addContent(hardware);
        
        Element pin =  new Element("oneWirePin");
        hardware.addContent(pin);
        
        Element thermometer = new Element("thermometer");
        hardware.addContent(thermometer);
        
        Element adresse = new Element("adresse");
        thermometer.addContent(adresse);
        
        Element zeit = new Element("zeitMilliSekunden");
        thermometer.addContent(zeit);
        
        Element heizwerk = new Element("heizwerk");
        hardware.addContent(heizwerk);
        
        Element hs = new Element("signalPin");
        heizwerk.addContent(hs);
        
        Element he = new Element("elementPin");
        heizwerk.addContent(he);
        
        Element ruehwerk = new Element("ruehrwerk");
        hardware.addContent(ruehwerk);
        
        Element rs = new Element("signalPin");
        ruehwerk.addContent(rs);
        
        Element re = new Element("elementPin");
        ruehwerk.addContent(re);
        
        pin.setContent(new Text(String.valueOf(oneWirePin)));
        adresse.setContent(new Text(thermometerAdresse));
        zeit.setContent(new Text(String.valueOf(zeitErgebnis)));
        
        hs.setContent(new Text(String.valueOf(heizwerkKonf.getSignalPin())));
        he.setContent(new Text(String.valueOf(heizwerkKonf.getElementPin())));
        
        rs.setContent(new Text(String.valueOf(ruehrwerkKonf.getSignalPin())));
        re.setContent(new Text(String.valueOf(ruehrwerkKonf.getElementPin())));
        
        return root;
    }
    
    private String adresse = "28-0516a41aadff";
    private int pin = 4;
    private SteuerelementKonfiguration heizwerkKonf = new SteuerelementKonfiguration(1, 2);
    private SteuerelementKonfiguration ruehrwerkKonf = new SteuerelementKonfiguration(1, 2);
}
