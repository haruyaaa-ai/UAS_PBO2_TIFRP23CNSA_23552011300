package catatan.harian.dao;

import catatan.harian.model.User;
import catatan.harian.util.DatabaseConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class UserDAO {

    public boolean register(User user) {
        String sql = "INSERT INTO users (username, password, email, nama_lengkap) VALUES (?, ?, ?, ?)";
        
        try (Connection conn = DatabaseConnection.getInstance();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, user.getUsername());
            pstmt.setString(2, user.getPassword()); 
            pstmt.setString(3, user.getEmail());
            pstmt.setString(4, user.getNamaLengkap());
            
            int affectedRows = pstmt.executeUpdate();
            return affectedRows > 0;
            
        } catch (SQLException e) {
            System.err.println("Gagal mendaftarkan user: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }
    public User login(String username, String password) {
        String sql = "SELECT * FROM users WHERE username = ? AND password = ?";
        
        try (Connection conn = DatabaseConnection.getInstance();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, username);
            pstmt.setString(2, password);
            
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    User user = new User();
                    user.setId(rs.getInt("id"));
                    user.setUsername(rs.getString("username"));
                    user.setEmail(rs.getString("email"));
                    user.setNamaLengkap(rs.getString("nama_lengkap"));
                    user.setStatus(rs.getString("status"));
                    if (rs.getTimestamp("tanggal_daftar") != null) {
                        user.setTanggalDaftar(rs.getTimestamp("tanggal_daftar").toLocalDateTime());
                    }
                    return user;
                }
            }
        } catch (SQLException e) {
            System.err.println("Gagal login: " + e.getMessage());
            e.printStackTrace();
        }
        
        return null;
    }
}