package catatan.harian.model;

import java.time.LocalDate;

public class LaporanHarian {
    private LocalDate tanggal;
    private double pemasukanHarian;
    private double pengeluaranHarian;

    // Getters and Setters
    public LocalDate getTanggal() {
        return tanggal;
    }

    public void setTanggal(LocalDate tanggal) {
        this.tanggal = tanggal;
    }

    public double getPemasukanHarian() {
        return pemasukanHarian;
    }

    public void setPemasukanHarian(double pemasukanHarian) {
        this.pemasukanHarian = pemasukanHarian;
    }

    public double getPengeluaranHarian() {
        return pengeluaranHarian;
    }

    public void setPengeluaranHarian(double pengeluaranHarian) {
        this.pengeluaranHarian = pengeluaranHarian;
    }
}