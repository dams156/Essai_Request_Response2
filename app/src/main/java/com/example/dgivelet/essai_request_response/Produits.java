package com.example.dgivelet.essai_request_response;

/**
 * Created by dgivelet on 19/04/2016.
 */
public class Produits {

    private String nom;
    private int quantite;

    public Produits() {
    }

    public Produits(String nom, int quantite) {

        this.nom = nom;
        this.quantite = quantite;
    }

    public String getNom() {
        return nom;
    }

    public int getQuantite() {
        return quantite;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public void setQuantite(int quantite) {
        this.quantite = quantite;
    }

    @Override
    public String toString() {
        return "Produits{" +
                "nom='" + nom + '\'' +
                '}';
    }
}
