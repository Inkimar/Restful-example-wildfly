/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mjtoolbox.ws;

import javax.ws.rs.FormParam;

/**
 *
 * @author ingimar
 */
public class Form {
    @FormParam("owner")
    private String owner;

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }
    
}
