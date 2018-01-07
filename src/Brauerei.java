
import brauhaus.BrauKessel;
import brauhaus.IBrauKessel;
import brauhaus.startup.ConsolePrinter;
import brauhaus.startup.IPrinter;
import brauhaus.startup.StandardStartupOneKessel;
import gui.GStandardSteuerFenster;
import gui.konsole.BrauereiShell;

/**
 *
 * @author marian
 */
public class Brauerei {
    public static void main(String[] args) throws Exception
    {
        IPrinter printer = new ConsolePrinter();
        StandardStartupOneKessel startup = new StandardStartupOneKessel(printer);
        IBrauKessel braukessel = startup.BuildBrauKessel();
        
        BrauereiShell brauereiShell = new BrauereiShell(braukessel,printer);
    }

    
}
