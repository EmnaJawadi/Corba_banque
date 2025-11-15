package corbaServer;

import org.omg.CORBA.ORB;
import org.omg.CosNaming.*;
import org.omg.PortableServer.*;

import service.corbaBanque.*;
import service.BanqueImpl;       

public class BanqueServer {

    public static void main(String[] args) {
        try {
            ORB orb = ORB.init(args, null);

            POA rootPOA = POAHelper.narrow(
                    orb.resolve_initial_references("RootPOA")
            );
            rootPOA.the_POAManager().activate();

            BanqueImpl banqueImpl = new BanqueImpl();

            org.omg.CORBA.Object ref = rootPOA.servant_to_reference(banqueImpl);
            IBanqueRemote href = IBanqueRemoteHelper.narrow(ref);

            org.omg.CORBA.Object objRef = orb.resolve_initial_references("NameService");
            NamingContextExt namingContext = NamingContextExtHelper.narrow(objRef);

            NameComponent[] path = namingContext.to_name("BanqueService");
            namingContext.rebind(path, href);

            System.out.println("Serveur Banque prêt. En attente des clients...");

            // 7. Lancer le serveur
            orb.run();

        } catch (Exception e) {
            System.err.println("Erreur dans le serveur CORBA : " + e.getMessage());
            e.printStackTrace();
        }
    }
}
