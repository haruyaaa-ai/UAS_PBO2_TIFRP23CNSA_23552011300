package catatan.harian.model;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class Transaksi {
    private int id;
    private User user;
    private Kategori kategori;
    private String jenisTransaksi;
    private double jumlah;
    private String deskripsi;
    private LocalDate tanggalTransaksi;
    private LocalDateTime waktuInput;

    // Constructors, Getters, and Setters

    public Transaksi() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Kategori getKategori() {
        return kategori;
    }

    public void setKategori(Kategori kategori) {
        this.kategori = kategori;
    }

    public String getJenisTransaksi() {
        return jenisTransaksi;
    }

    public void setJenisTransaksi(String jenisTransaksi) {
        this.jenisTransaksi = jenisTransaksi;
    }

    public double getJumlah() {
        return jumlah;
    }

    public void setJumlah(double jumlah) {
        this.jumlah = jumlah;
    }

    public String getDeskripsi() {
        return deskripsi;
    }

    public void setDeskripsi(String deskripsi) {
        this.deskripsi = deskripsi;
    }

    public LocalDate getTanggalTransaksi() {
        return tanggalTransaksi;
    }

    public void setTanggalTransaksi(LocalDate tanggalTransaksi) {
        this.tanggalTransaksi = tanggalTransaksi;
    }

    public LocalDateTime getWaktuInput() {
        return waktuInput;
    }

    public void setWaktuInput(LocalDateTime waktuInput) {
        this.waktuInput = waktuInput;
    }
}