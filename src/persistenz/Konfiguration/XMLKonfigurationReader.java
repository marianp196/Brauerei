/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package persistenz.Konfiguration;

import MCPXMLVerarbeitung.IMcpXml;
import MCPXMLVerarbeitung.XmlDoc;
import abstractions.ISensorKonfiguration;
import java.io.File;
import java.io.FileNotFoundException;
import java.nio.file.FileSystemException;
import org.jdom2.Element;
import sensoren.common.oneWire.OneWireKonfiguration;

/**
 *
 * @author marian
 */

//ToDo: Möglicherweise abstrahieren und eigenes Modul???
public class XMLKonfigurationReader implements IKonfiguration {

    public XMLKonfigurationReader(File datei) throws FileNotFoundException, Exception {
        checkDateiInput(datei);        
        this.xmlDoc = new XmlDoc(datei);
        this.datei = datei;    
        
        checkSchema();
    }
  
    @Override
    public ISensorKonfiguration GetThermometerKonfiguration() throws Exception {
        int pin = getOnewirePin(); //Onewirekonf anpassen, damit pin übergeben werden kann
        String thermometerAdresse = getThermometerAdresse();        
        long thermometerZeit = getThermometerZeit();
        
        return (ISensorKonfiguration)OneWireKonfiguration.GetStandardRaspiKonfig(thermometerAdresse, thermometerZeit);
    }
    
    @Override
    public SteuerelementKonfiguration GetHeizwerkKonfiguration() throws Exception {
        return getSteuerElementKonfiguation(xmlAdresse_Heizwerk_elementPin, xmlAdresse_Heizwerk_signalPin);
    }

    @Override
    public SteuerelementKonfiguration GetRuehrwerkKonfiguration() throws Exception {
        return getSteuerElementKonfiguation(xmlAdresse_Ruehrwerk_elementPin, xmlAdresse_Ruehrwerk_signalPin);
    }
    
    private SteuerelementKonfiguration getSteuerElementKonfiguation(String elementPinPfad, String signalPinPfad) throws Exception {
        int element = Integer.valueOf(xmlDoc.getKnoten(elementPinPfad).getValue());
        int signal = Integer.valueOf(xmlDoc.getKnoten(signalPinPfad).getValue());
        return new SteuerelementKonfiguration(element, signal);
    }

    private long getThermometerZeit() throws Exception {
        Element thermometerZeitElement = xmlDoc.getKnoten(xmlAdresse_ThermometerZeit);
        return Long.valueOf(thermometerZeitElement.getValue());
    }

    private String getThermometerAdresse() throws Exception {
        Element adresseElement = xmlDoc.getKnoten(xmlAdresse_ThermometerAdresse);
        return adresseElement.getValue();
    }

    private int getOnewirePin() throws Exception, NumberFormatException {
        Element pinElement = xmlDoc.getKnoten(xmlAdresse_OneWirePin);
        return Integer.valueOf(pinElement.getValue());
    }
    
    private void checkDateiInput(File datei) throws FileNotFoundException, NullPointerException {
        if (datei == null) {
            throw new NullPointerException("datei");
        }
        if (!(datei.exists() && datei.isFile() && datei.canRead())) {
            throw new FileNotFoundException("Datei nicht vorhanden oder Rechte-Problem");
        }
    }    

    private void checkSchema() throws Exception {
        
        checkKonfiguration(xmlAdresse_ThermometerAdresse, false);        
        checkKonfiguration(xmlAdresse_OneWirePin, true);        
        checkKonfiguration(xmlAdresse_ThermometerZeit, true);
        checkKonfiguration(xmlAdresse_Heizwerk_elementPin, true);
        checkKonfiguration(xmlAdresse_Heizwerk_signalPin, true);
        checkKonfiguration(xmlAdresse_Ruehrwerk_elementPin, true);
        checkKonfiguration(xmlAdresse_Ruehrwerk_signalPin, true);        
        
    }
    
    private void checkKonfiguration(String pfad, boolean integerValue) throws Exception
    {
         try {
            Element knoten = xmlDoc.getKnoten(pfad);
            if (knoten.getValue().isEmpty()) {
                throw new Exception("Knoten hat kein text");
            }
            if(integerValue)
            {
                int value = Integer.valueOf(knoten.getValue());
                if(value < 0)
                    throw new Exception("ThermometerZeit muss groeßer 0 sein ");
            }
        } catch (Exception exception) {
            throw new Exception(pfad + " nicht konfiguriert", exception);
        }
    }    
   
    private File datei;
    private IMcpXml xmlDoc;   
    
    private String xmlAdresse_ThermometerAdresse = "/brauhaus/hardware/thermometer/adresse";
    private String xmlAdresse_ThermometerZeit = "/brauhaus/hardware/thermometer/zeitMilliSekunden";
    private String xmlAdresse_OneWirePin = "/brauhaus/hardware/oneWirePin";
    private String xmlAdresse_Ruehrwerk_elementPin = "/brauhaus/hardware/ruehrwerk/elementPin";
    private String xmlAdresse_Ruehrwerk_signalPin = "/brauhaus/hardware/ruehrwerk/signalPin";
    private String xmlAdresse_Heizwerk_elementPin = "/brauhaus/hardware/heizwerk/elementPin";
    private String xmlAdresse_Heizwerk_signalPin = "/brauhaus/hardware/heizwerk/signalPin";

   

    
}
