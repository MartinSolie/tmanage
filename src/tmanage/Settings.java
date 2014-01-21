/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tmanage;

/**
 *
 * @author martin
 */
public class Settings {
    private static String login = "antonymartynov@gmail.com";
    private static int smtpPort = 587;
    private static String password = "87Maradiel9587";
    private static String host = "smtp.gmail.com";
    
    public static String getLogin (){
        return login;
    }
    
    public static String getPassword(){
        return password;
    }
    
    public static int getPort(){
        return smtpPort;
    }
    
    public static String getHost(){
        return host;
    }
}
