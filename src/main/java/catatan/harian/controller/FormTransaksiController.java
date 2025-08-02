package catatan.harian.controller;

import catatan.harian.dao.KategoriDAO;
import catatan.harian.dao.TransaksiDAO;
import catatan.harian.model.Kategori;
import catatan.harian.model.Transaksi;
import catatan.harian.model.User;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.time.LocalDate;
import java.util.List;

public class FormTransaksiController {

    @FXML
    private ComboBox<String> jenisComboBox;
    @FXML
    private ComboBox<Kategori> kategoriComboBox;
    @FXML
    private DatePicker tanggalPicker;
    @FXML
    private TextField jumlahField;
    @FXML
    private TextArea deskripsiArea;
    @FXML
    private Button simpanButton;
    @FXML
    private Button batalButton;
    @FXML
    private Label errorLabel;

    private User currentUser;
    private Transaksi transaksiToEdit;
    private DashboardController dashboardController;
    private final KategoriDAO kategoriDAO = new KategoriDAO();
    private final TransaksiDAO transaksiDAO = new TransaksiDAO();

    @FXML
    public void initialize() {
        // Isi pilihan untuk Jenis Transaksi
        jenisComboBox.setItems(FXCollections.observableArrayList("pemasukan", "pengeluaran"));
        
        // Tambahkan listener untuk mengubah pilihan kategori saat jenis diganti
        jenisComboBox.getSelectionModel().selectedItemProperty().addListener((options, oldValue, newValue) -> {
            if (newValue != null) {
                // Ambil daftar kategori dari database
                List<Kategori> kategoriList = kategoriDAO.findByJenis(newValue);
                kategoriComboBox.setItems(FXCollections.observableArrayList(kategoriList));
                
                // HANYA pilih item pertama JIKA daftarnya tidak kosong
                if (!kategoriList.isEmpty()) {
                    kategoriComboBox.getSelectionModel().selectFirst();
                } else {
                    kategoriComboBox.getSelectionModel().clearSelection();
                }
            }
        });

        tanggalPicker.setValue(LocalDate.now());
        // Pilih "pemasukan" sebagai default, ini akan otomatis memicu listener di atas
        jenisComboBox.getSelectionModel().selectFirst(); 
    }

    public void initData(User user, DashboardController controller) {
        this.currentUser = user;
        this.dashboardController = controller;
        // Tidak perlu mengisi form karena ini untuk data baru
    }

    // Metode ini untuk mode EDIT (3 parameter)
    public void initData(User user, Transaksi transaksi, DashboardController controller) {
        this.currentUser = user;
        this.transaksiToEdit = transaksi;
        this.dashboardController = controller;

        // Mengisi form dengan data dari transaksi yang akan diedit
        jenisComboBox.setValue(transaksi.getJenisTransaksi());
        kategoriComboBox.setItems(FXCollections.observableArrayList(kategoriDAO.findByJenis(transaksi.getJenisTransaksi())));
        kategoriComboBox.setValue(transaksi.getKategori());
        tanggalPicker.setValue(transaksi.getTanggalTransaksi());
        jumlahField.setText(String.valueOf(transaksi.getJumlah()));
        deskripsiArea.setText(transaksi.getDeskripsi());
    }

    @FXML
    private void handleSimpanButton(ActionEvent event) {
        // 1. Validasi input
        if (kategoriComboBox.getValue() == null || tanggalPicker.getValue() == null || jumlahField.getText().isEmpty()) {
            errorLabel.setText("Harap isi semua field yang wajib diisi.");
            return;
        }

        try {
            double jumlah = Double.parseDouble(jumlahField.getText());
            boolean sukses;

            // 2. Cek apakah ini mode 'Edit' atau 'Tambah Baru'
            if (transaksiToEdit == null) {
                // Ini adalah mode TAMBAH BARU
                Transaksi transaksiBaru = new Transaksi();
                transaksiBaru.setUser(currentUser);
                transaksiBaru.setKategori(kategoriComboBox.getValue());
                transaksiBaru.setJenisTransaksi(jenisComboBox.getValue());
                transaksiBaru.setJumlah(jumlah);
                transaksiBaru.setDeskripsi(deskripsiArea.getText());
                transaksiBaru.setTanggalTransaksi(tanggalPicker.getValue());

                // Panggil metode save dari DAO
                sukses = transaksiDAO.save(transaksiBaru);

            } else {
                // Ini adalah mode EDIT
                transaksiToEdit.setKategori(kategoriComboBox.getValue());
                transaksiToEdit.setJenisTransaksi(jenisComboBox.getValue());
                transaksiToEdit.setJumlah(jumlah);
                transaksiToEdit.setDeskripsi(deskripsiArea.getText());
                transaksiToEdit.setTanggalTransaksi(tanggalPicker.getValue());

                // Panggil metode update dari DAO
                sukses = transaksiDAO.update(transaksiToEdit);
            }

            // 3. Jika operasi database berhasil
            if (sukses) {
                dashboardController.loadData();
                handleBatalButton(event);
            } else {
                errorLabel.setText("Gagal menyimpan perubahan ke database.");
            }

        } catch (NumberFormatException e) {
            errorLabel.setText("Jumlah harus berupa angka yang valid.");
        }
    }

    @FXML
    private void handleBatalButton(ActionEvent event) {
        Stage stage = (Stage) batalButton.getScene().getWindow();
        stage.close();
    }
}