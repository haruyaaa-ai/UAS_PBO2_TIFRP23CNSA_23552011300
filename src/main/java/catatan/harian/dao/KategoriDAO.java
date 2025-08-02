package catatan.harian.dao;

import catatan.harian.model.Kategori;
import catatan.harian.util.DatabaseConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class KategoriDAO {

    public List<Kategori> findByJenis(String jenis) {
        List<Kategori> listKategori = new ArrayList<>();
        String sql = "SELECT * FROM kategori_transaksi WHERE jenis = ?";

        try (Connection conn = DatabaseConnection.getInstance();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, jenis);
            
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    Kategori kategori = new Kategori();
                    kategori.setId(rs.getInt("id"));
                    kategori.setNamaKategori(rs.getString("nama_kategori"));
                    kategori.setJenis(rs.getString("jenis"));
                    kategori.setDeskripsi(rs.getString("deskripsi"));
                    
                    listKategori.add(kategori);
                }
            }
        } catch (SQLException e) {
            System.err.println("Gagal mengambil data kategori: " + e.getMessage());
            e.printStackTrace();
        }
        
        return listKategori;
    }
}