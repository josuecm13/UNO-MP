package com.uno.server.server;

/**
 * Created by ${gaboq} on 21/9/2017.
 */

public class Utils {

    private static final String CODEBASE = "java.rmi.server.codebase";

    public static void setCodeBase(Class<?> c) {
        String ruta = c.getProtectionDomain().getCodeSource().getLocation().toString();

        String path = System.getProperty(CODEBASE);
        if(path != null && !path.isEmpty()) {
            ruta = path + " " + ruta;
        }
        System.setProperty(CODEBASE, ruta);

    }

}
