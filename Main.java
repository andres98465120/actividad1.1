package com.iudigital.view;

import com.iudigital.util.ConnectionUtil;
import java.sql.Connection;
import java.sql.SQLException;

public class Main {

    public static void main(String[] args) {

        try {
            Connection connection = ConnectionUtil.getConnection();

            if (connection != null) {
                // Obtener los detalles de la base de datos
                String dbDetails = ConnectionUtil.getDatabaseDetails(connection);

                // Imprimir la información
                System.out.println(dbDetails);

                connection.close();
            }
        } catch (SQLException e) {
            System.out.println("Error al conectar a la base de datos: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
