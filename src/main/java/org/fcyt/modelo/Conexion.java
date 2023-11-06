package org.fcyt.modelo;

import java.sql.Connection;

import static java.sql.DriverManager.getConnection;

public class Conexion {
    static Connection connection;
    private static final String url = "jdbc:postgresql://localhost:5432/db_hoteles";
    private static final String user = "postgres";
    private static final String password = "onukitaeko78";

    public static Connection Conectar() {
        try {
            connection = getConnection(url, user, password);
            System.out.println("Conexión exitosa.");

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error de conexión.");
        }
        return connection;
    }
}
