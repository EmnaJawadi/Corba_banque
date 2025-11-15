package corbaClient;

import org.omg.CORBA.ORB;
import org.omg.CosNaming.*;
import service.corbaBanque.Compte;
import service.corbaBanque.IBanqueRemote;
import service.corbaBanque.IBanqueRemoteHelper;

public class BanqueClient {
    public static void main(String[] args) {
        try {
            // Initialisation de l’ORB
            ORB orb = ORB.init(args, null);

            // Accès au service de nommage
            org.omg.CORBA.Object objRef = orb.resolve_initial_references("NameService");
            NamingContextExt ncRef = NamingContextExtHelper.narrow(objRef);

            // Recherche du service distant
            IBanqueRemote banque = IBanqueRemoteHelper.narrow(ncRef.resolve_str("BanqueService"));

            System.out.println("✅ Connexion au service Banque réussie.");

            // --- Tests des méthodes distantes ---
            Compte c1 = new Compte(1, 1000);
            banque.creerCompte(c1);

            banque.verser(500, 1);
            banque.retirer(200, 1);

            Compte c = banque.getCompte(1);
            System.out.println("Compte code: " + c.code + ", Solde: " + c.solde);

            System.out.println("Conversion 10€ = " + banque.conversion(10) + " DT");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
