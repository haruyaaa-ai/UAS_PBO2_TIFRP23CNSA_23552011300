package catatan.harian.dao;

import catatan.harian.model.LaporanHarian;
import catatan.harian.util.DatabaseConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class LaporanDAO {
    public List<LaporanHarian> getLaporanHarian(int userId, int limit) {
        List<LaporanHarian> laporan = new ArrayList<>();
        String sql = "SELECT * FROM v_laporan_harian WHERE user_id = ? ORDER BY tanggal_transaksi DESC LIMIT ?";

        try (Connection conn = DatabaseConnection.getInstance();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, userId);
            pstmt.setInt(2, limit);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                LaporanHarian lap = new LaporanHarian();
                lap.setTanggal(rs.getDate("tanggal_transaksi").toLocalDate());
                lap.setPemasukanHarian(rs.getDouble("pemasukan_harian"));
                lap.setPengeluaranHarian(rs.getDouble("pengeluaran_harian"));
                laporan.add(lap);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return laporan;
    }
}