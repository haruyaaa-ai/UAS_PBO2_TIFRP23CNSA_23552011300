package catatan.harian.model;

public class Kategori {
    private int id;
    private String namaKategori;
    private String jenis; // "pemasukan" atau "pengeluaran"
    private String deskripsi;

    // Constructors, Getters, and Setters

    public Kategori() {
    }
    
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNamaKategori() {
        return namaKategori;
    }

    public void setNamaKategori(String namaKategori) {
        this.namaKategori = namaKategori;
    }

    public String getJenis() {
        return jenis;
    }

    public void setJenis(String jenis) {
        this.jenis = jenis;
    }

    public String getDeskripsi() {
        return deskripsi;
    }

    public void setDeskripsi(String deskripsi) {
        this.deskripsi = deskripsi;
    }

    // Override toString() agar tampil nama di ComboBox
    @Override
    public String toString() {
        return namaKategori;
    }
}