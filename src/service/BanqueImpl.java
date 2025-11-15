package service;

import service.corbaBanque.Compte;
import service.corbaBanque.IBanqueRemotePOA;

public class BanqueImpl extends IBanqueRemotePOA {

    @Override
    public void creerCompte(Compte cpte) {

    }

    @Override
    public void verser(float mt, int code) {

    }

    @Override
    public void retirer(float mt, int code) {

    }

    @Override
    public Compte getCompte(int code) {
        return null;
    }

    @Override
    public Compte[] getComptes() {
        return new Compte[0];
    }

    @Override
    public float conversion(float montant) {
        return montant * 3.3f;
    }

    @Override
    public String test() {
        return "Serveur CORBA opérationnel";
    }
}
