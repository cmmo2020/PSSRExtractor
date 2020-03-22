/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
*/
package guis;

import java.net.Authenticator;
import java.net.PasswordAuthentication;

/**
 *
 * @author Alejandro
 */
public class ProxyAuthenticator extends Authenticator{
    
    private final String user;
    private final char[] password;
    
    public ProxyAuthenticator(String user, char[] password) {
        this.user = user;
        this.password = password;
    }
    
    @Override
    protected PasswordAuthentication getPasswordAuthentication() {
        return new PasswordAuthentication(user, password);
    }
}

