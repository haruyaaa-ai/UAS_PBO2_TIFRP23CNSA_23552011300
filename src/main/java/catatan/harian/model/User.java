package catatan.harian.model;

import java.time.LocalDateTime;

public class User {
    private int id;
    private String username;
    private String password;
    private String email;
    private String namaLengkap;
    private LocalDateTime tanggalDaftar;
    private String status;

    // Constructors, Getters, and Setters
    
    public User() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNamaLengkap() {
        return namaLengkap;
    }

    public void setNamaLengkap(String namaLengkap) {
        this.namaLengkap = namaLengkap;
    }

    public LocalDateTime getTanggalDaftar() {
        return tanggalDaftar;
    }

    public void setTanggalDaftar(LocalDateTime tanggalDaftar) {
        this.tanggalDaftar = tanggalDaftar;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}