package catatan.harian.dao;

import catatan.harian.model.Kategori;
import catatan.harian.model.Transaksi;
import catatan.harian.model.User;
import catatan.harian.util.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TransaksiDAO {

    public boolean save(Transaksi transaksi) {
        String sql = "INSERT INTO transaksi (user_id, kategori_id, jenis_transaksi, jumlah, deskripsi, tanggal_transaksi) VALUES (?, ?, ?, ?, ?, ?)";
        
        try (Connection conn = DatabaseConnection.getInstance();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, transaksi.getUser().getId());
            pstmt.setInt(2, transaksi.getKategori().getId());
            pstmt.setString(3, transaksi.getJenisTransaksi());
            pstmt.setDouble(4, transaksi.getJumlah());
            pstmt.setString(5, transaksi.getDeskripsi());
            pstmt.setDate(6, Date.valueOf(transaksi.getTanggalTransaksi()));

            int affectedRows = pstmt.executeUpdate();
            return affectedRows > 0;

        } catch (SQLException e) {
            System.err.println("Gagal menyimpan transaksi: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }
    
    public boolean update(Transaksi transaksi) {
        String sql = "UPDATE transaksi SET kategori_id = ?, jenis_transaksi = ?, jumlah = ?, deskripsi = ?, tanggal_transaksi = ? WHERE id = ?";
        
        try (Connection conn = DatabaseConnection.getInstance();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, transaksi.getKategori().getId());
            pstmt.setString(2, transaksi.getJenisTransaksi());
            pstmt.setDouble(3, transaksi.getJumlah());
            pstmt.setString(4, transaksi.getDeskripsi());
            pstmt.setDate(5, Date.valueOf(transaksi.getTanggalTransaksi()));
            pstmt.setInt(6, transaksi.getId()); // ID transaksi untuk klausa WHERE

            int affectedRows = pstmt.executeUpdate();
            return affectedRows > 0;

        } catch (SQLException e) {
            System.err.println("Gagal memperbarui transaksi: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    public boolean delete(int transaksiId) {
        String sql = "DELETE FROM transaksi WHERE id = ?";

        try (Connection conn = DatabaseConnection.getInstance();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, transaksiId);
            int affectedRows = pstmt.executeUpdate();
            return affectedRows > 0;

        } catch (SQLException e) {
            System.err.println("Gagal menghapus transaksi: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    public List<Transaksi> findByUser(int userId) {
        List<Transaksi> listTransaksi = new ArrayList<>();
        // Query JOIN untuk mengambil data transaksi beserta detail kategori
        String sql = "SELECT t.*, k.nama_kategori, k.jenis FROM transaksi t " +
                     "JOIN kategori_transaksi k ON t.kategori_id = k.id " +
                     "WHERE t.user_id = ? ORDER BY t.tanggal_transaksi DESC, t.waktu_input DESC";

        try (Connection conn = DatabaseConnection.getInstance();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, userId);
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    // Membuat objek Kategori dari hasil join
                    Kategori kategori = new Kategori();
                    kategori.setId(rs.getInt("kategori_id"));
                    kategori.setNamaKategori(rs.getString("nama_kategori"));
                    kategori.setJenis(rs.getString("jenis"));
                    
                    // Membuat objek Transaksi
                    Transaksi transaksi = new Transaksi();
                    transaksi.setId(rs.getInt("id"));
                    transaksi.setJumlah(rs.getDouble("jumlah"));
                    transaksi.setDeskripsi(rs.getString("deskripsi"));
                    transaksi.setTanggalTransaksi(rs.getDate("tanggal_transaksi").toLocalDate());
                    transaksi.setWaktuInput(rs.getTimestamp("waktu_input").toLocalDateTime());
                    transaksi.setJenisTransaksi(rs.getString("jenis_transaksi"));
                    
                    // Menggabungkan objek kategori ke dalam transaksi
                    transaksi.setKategori(kategori);

                    listTransaksi.add(transaksi);
                }
            }
        } catch (SQLException e) {
            System.err.println("Gagal mengambil data transaksi: " + e.getMessage());
            e.printStackTrace();
        }

        return listTransaksi;
    }
}