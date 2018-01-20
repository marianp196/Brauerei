
import brauhaus.IBrauKessel;
import brauhaus.bierData.brauelemente.HopfenKochenElement;
import brauhaus.bierData.brauelemente.IBrauelement;
import brauhaus.bierData.brauelemente.TemperaturRastElement;
import java.util.ArrayList;
import persistenz.braudata.repository.brauelementeXmlParser.BrauelementToXmlParser;
import startup.ConsolePrinter;
import startup.IPrinter;
import startup.StandardStartupOneKessel;
import standardFrontend.konsole.BrauereiShell;

/**
 *
 * @author marian
 */
public class Brauerei {
    public static void main(String[] args) throws Exception
    {
       /* IPrinter printer = new ConsolePrinter(); //ToDo: IPrinterKlasse kann raus
        StandardStartupOneKessel startup = new StandardStartupOneKessel(printer);
        IBrauKessel braukessel = startup.BuildBrauKessel();
        
        BrauereiShell brauereiShell = new BrauereiShell(braukessel);*/
        ArrayList<IBrauelement> ar = new ArrayList<>();
        ar.add(new HopfenKochenElement(300, 0));
        ar.add(new TemperaturRastElement(20, 1, 20, 30));
        BrauelementToXmlParser p = new BrauelementToXmlParser();
        System.err.println(p.BrauelemteToXml(ar));
    }

    
}
