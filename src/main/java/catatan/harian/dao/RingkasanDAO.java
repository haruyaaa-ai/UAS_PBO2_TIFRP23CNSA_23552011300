package catatan.harian.dao;

import catatan.harian.model.RingkasanKeuangan;
import catatan.harian.util.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class RingkasanDAO {

    public RingkasanKeuangan getRingkasan(int userId) {
        String sql = "SELECT * FROM v_ringkasan_keuangan WHERE user_id = ?";
        RingkasanKeuangan ringkasan = new RingkasanKeuangan();

        try (Connection conn = DatabaseConnection.getInstance();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, userId);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    ringkasan.setTotalPemasukan(rs.getDouble("total_pemasukan"));
                    ringkasan.setTotalPengeluaran(rs.getDouble("total_pengeluaran"));
                    ringkasan.setSaldoTotal(rs.getDouble("saldo_total"));
                }
            }
        } catch (SQLException e) {
            System.err.println("Gagal mengambil data ringkasan: " + e.getMessage());
            e.printStackTrace();
        }
        return ringkasan;
    }
}