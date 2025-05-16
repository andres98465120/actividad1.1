package com.iudigital.util;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionUtil {

    private static final String URL = "jdbc:mysql://localhost:3308/funcionariosdb";
    private static final String USER = "root";
    private static final String PASSWORD = "Dener123";

    // Función para obtener la conexión a la base de datos
    public static Connection getConnection() throws SQLException {
        try {
            // Asegúrate de que el driver esté cargado
            Class.forName("com.mysql.cj.jdbc.Driver");
            return DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            throw new SQLException("Driver JDBC de MySQL no encontrado.", e);
        }
    }

    // Función para obtener los detalles de la conexión a la base de datos
    public static String getDatabaseDetails(Connection connection) throws SQLException {
        DatabaseMetaData metaData = connection.getMetaData();
        String databaseName = metaData.getDatabaseProductName();
        String databaseVersion = metaData.getDatabaseProductVersion();

        // Obtener el nombre de la base de datos
        String databaseURL = connection.getMetaData().getURL();
        String dbName = databaseURL.substring(databaseURL.lastIndexOf("/") + 1);

        // Construir un mensaje con la información
        return String.format("Conexión exitosa a la base de datos: %s\nVersión de la base de datos: %s\nBase de datos a la que estás conectado: %s",
                databaseName, databaseVersion, dbName);
    }
}
