package model;

/**
 * Created by 1494778 on 2016-03-07.
 */
public class Client {
    private int id;
    private String nom;
    private String noCivique;
    private String rue;
    private String ville;
    private String codePostal;

    public Client(String nom, String noCivique, String rue, String ville, String codePostal) {
        this.nom = nom;
        this.noCivique = noCivique;
        this.rue = rue;
        this.ville = ville;
        this.codePostal = codePostal;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getNoCivique() {
        return noCivique;
    }

    public void setNoCivique(String noCivique) {
        this.noCivique = noCivique;
    }

    public String getRue() {
        return rue;
    }

    public void setRue(String rue) {
        this.rue = rue;
    }

    public String getVille() {
        return ville;
    }

    public void setVille(String ville) {
        this.ville = ville;
    }

    public String getCodePostal() {
        return codePostal;
    }

    public void setCodePostal(String codePostal) {
        this.codePostal = codePostal;
    }

    @Override
    public String toString() {
        return  nom ;
    }
}
