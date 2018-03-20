/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package easypos.easyposclientfx;

/**
 *
 * @author taleb
 */
import java.util.Date;
import java.util.List;

/**
 *
 * @author taleb
 */
public class LoginResponse {

    /**
     * ID ,nom,prenom,langue,date derniere connexion , list application , num
     * sequence
     *
     * List Application : comlplete chaque application son etat active ou
     * desactiver boolean nom de l application String Id de l application Long
     * LOGO Blob Lien String
     *
     * -----------------------------------------------
     *
     * dans un click sur une application :
     *
     * le client va envoyer le
     *
     * ID user , sequence en cours , Id application -----> List des Menu :
     *
     * chaque menu avec son etat (ID menu ,IdIcon , Libellé , list des fils)
     *
     *
     */
    private Long us_usrint;
    private String us_cextusr;
    private String us_nomusr;
    private String us_prnusr;

    private Date us_datfin;
    private Date us_lastcnx;
    private UserState us_etatusr;
    private List<Application> us_applications;

    public Long getUs_usrint() {
        return us_usrint;
    }

    public void setUs_usrint(Long us_usrint) {
        this.us_usrint = us_usrint;
    }

    public String getUs_cextusr() {
        return us_cextusr;
    }

    public void setUs_cextusr(String us_cextusr) {
        this.us_cextusr = us_cextusr;
    }

    public String getUs_nomusr() {
        return us_nomusr;
    }

    public void setUs_nomusr(String us_nomusr) {
        this.us_nomusr = us_nomusr;
    }

    public String getUs_prnusr() {
        return us_prnusr;
    }

    public void setUs_prnusr(String us_prnusr) {
        this.us_prnusr = us_prnusr;
    }

    public Date getUs_datfin() {
        return us_datfin;
    }

    public void setUs_datfin(Date us_datfin) {
        this.us_datfin = us_datfin;
    }

    public Date getUs_lastcnx() {
        return us_lastcnx;
    }

    public void setUs_lastcnx(Date us_lastcnx) {
        this.us_lastcnx = us_lastcnx;
    }

    public UserState getUs_etatusr() {
        return us_etatusr;
    }

    public void setUs_etatusr(UserState us_etatusr) {
        this.us_etatusr = us_etatusr;
    }

    public List<Application> getUs_applications() {
        return us_applications;
    }

    public void setUs_applications(List<Application> us_applications) {
        this.us_applications = us_applications;
    }

    public LoginResponse(Long us_usrint, String us_cextusr, String us_nomusr, String us_prnusr, Date us_datfin, Date us_lastcnx, UserState us_etatusr, List<Application> us_applications) {
        this.us_usrint = us_usrint;
        this.us_cextusr = us_cextusr;
        this.us_nomusr = us_nomusr;
        this.us_prnusr = us_prnusr;
        this.us_datfin = us_datfin;
        this.us_lastcnx = us_lastcnx;
        this.us_etatusr = us_etatusr;
        this.us_applications = us_applications;
    }

    public LoginResponse() {
    }

    @Override
    public String toString() {
        return "LoginResponse{" + "us_usrint=" + us_usrint + ", us_cextusr=" + us_cextusr + ", us_nomusr=" + us_nomusr + ", us_prnusr=" + us_prnusr + ", us_datfin=" + us_datfin + ", us_lastcnx=" + us_lastcnx + ", us_etatusr=" + us_etatusr + ", us_applications=" + us_applications + '}';
    }

}
