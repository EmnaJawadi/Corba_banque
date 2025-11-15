package corbaServer;

import org.omg.CORBA.ORB;
import org.omg.CosNaming.*;
import org.omg.PortableServer.*;

import service.corbaBanque.*;     // Package contenant IBanqueRemote, IBanqueRemoteHelper...
import service.BanqueImpl;       // Ton implémentation du service

public class BanqueServer {

    public static void main(String[] args) {
        try {
            // 1. Initialisation de l'ORB
            ORB orb = ORB.init(args, null);

            // 2. Activer le POA (Portable Object Adapter)
            POA rootPOA = POAHelper.narrow(
                    orb.resolve_initial_references("RootPOA")
            );
            rootPOA.the_POAManager().activate();

            // 3. Créer le servant (implémentation du service)
            BanqueImpl banqueImpl = new BanqueImpl();

            // 4. Récupérer une référence CORBA depuis le servant
            org.omg.CORBA.Object ref = rootPOA.servant_to_reference(banqueImpl);
            IBanqueRemote href = IBanqueRemoteHelper.narrow(ref);

            // 5. Récupérer le service de nommage
            org.omg.CORBA.Object objRef = orb.resolve_initial_references("NameService");
            NamingContextExt namingContext = NamingContextExtHelper.narrow(objRef);

            // 6. Enregistrer l'objet sous le nom "BanqueService"
            NameComponent[] path = namingContext.to_name("BanqueService");
            namingContext.rebind(path, href);

            System.out.println("✅ Serveur Banque prêt. En attente des clients...");

            // 7. Lancer le serveur
            orb.run();

        } catch (Exception e) {
            System.err.println("❌ Erreur dans le serveur CORBA : " + e.getMessage());
            e.printStackTrace();
        }
    }
}
