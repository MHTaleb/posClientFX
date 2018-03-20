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
public class UserState {
    private boolean active;

    public UserState() {
    }

    public UserState(boolean active) {
        this.active = active;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }
    
    
}
