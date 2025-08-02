package catatan.harian.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {

    private static final String DB_URL = "jdbc:mysql://localhost:3306/catatan_keuangan";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "";
    private static Connection connection;

    private DatabaseConnection() {
    }

    public static Connection getInstance() {
        try {
            if (connection == null || connection.isClosed()) {
                connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
                System.out.println("Koneksi ke database berhasil dibuat!");
            }
        } catch (SQLException e) {
            System.err.println("Gagal terhubung ke database: " + e.getMessage());
            e.printStackTrace();
        }
        return connection;
    }
}