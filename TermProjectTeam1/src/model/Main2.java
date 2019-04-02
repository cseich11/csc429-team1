package model;

import java.awt.*;
import java.util.Properties;

public class Main2 {
    public static void main(String[] args)
    {
        try {
            Properties prop = new Properties();
            prop.setProperty("vName", "Felix Grellard");
            prop.setProperty("vPhone", "5857752004");
            prop.setProperty("vStatus", "Active");
            Vendor vendor = new Vendor(prop);
            vendor.update();
        } catch (Exception e) {
            System.out.println("ERROR: " + e);
        }
    }
}
