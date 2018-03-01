package gces;

import javax.swing.*;
import java.io.IOException;
import java.net.InetAddress;

import static javax.swing.JOptionPane.getRootFrame;

public class InternetChecker {

    public static boolean internetAvailable(){
        try {
            return InetAddress.getByName("www.google.com").isReachable(1500);
        }
        catch (IOException e){
            return false;
        }
    }
}
