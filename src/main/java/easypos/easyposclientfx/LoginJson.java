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
public class LoginJson {
    
    String us_pwdusr;
    String us_username;

    public LoginJson() {
    }

    public LoginJson(String us_pwdusr, String us_username) {
        this.us_pwdusr = us_pwdusr;
        this.us_username = us_username;
    }

    public String getUs_pwdusr() {
        return us_pwdusr;
    }

    public String getUs_username() {
        return us_username;
    }

    public void setUs_pwdusr(String us_pwdusr) {
        this.us_pwdusr = us_pwdusr;
    }

    public void setUs_username(String us_username) {
        this.us_username = us_username;
    }

    @Override
    public String toString() {
        return "{" + "us_pwdusr:" + us_pwdusr + ", us_username:" + us_username + '}';
    }
    
    
    
}
