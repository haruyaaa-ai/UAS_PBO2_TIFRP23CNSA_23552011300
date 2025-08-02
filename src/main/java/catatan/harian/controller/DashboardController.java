package catatan.harian.controller;

import catatan.harian.dao.RingkasanDAO;
import catatan.harian.dao.TransaksiDAO;
import catatan.harian.model.RingkasanKeuangan;
import catatan.harian.model.Transaksi;
import catatan.harian.model.User;
import java.net.URL;
import java.text.NumberFormat;
import java.time.LocalDate;
import java.util.Locale;
import java.util.ResourceBundle;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import java.io.IOException;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;
import java.util.Optional;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.XYChart;
import catatan.harian.dao.LaporanDAO;
import catatan.harian.model.LaporanHarian;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.List;

public class DashboardController implements Initializable {

    @FXML
    private Label welcomeLabel;
    @FXML
    private Button tambahButton;
    @FXML
    private Button hapusButton;
    @FXML
    private Button editButton;
    @FXML
    private TableView<Transaksi> tabelTransaksi;
    @FXML
    private TableColumn<Transaksi, LocalDate> kolomTanggal;
    @FXML
    private TableColumn<Transaksi, String> kolomKategori;
    @FXML
    private TableColumn<Transaksi, String> kolomDeskripsi;
    @FXML
    private TableColumn<Transaksi, String> kolomPemasukan;
    @FXML
    private TableColumn<Transaksi, String> kolomPengeluaran;
    @FXML
    private Label pemasukanLabel;
    @FXML
    private Label pengeluaranLabel;
    @FXML
    private Label saldoLabel;
    @FXML
    private Button logoutButton;
    @FXML
    private BarChart<String, Number> barChart;

    private User currentUser;
    private final RingkasanDAO ringkasanDAO = new RingkasanDAO();
    private final TransaksiDAO transaksiDAO = new TransaksiDAO();
    private final ObservableList<Transaksi> listTransaksi = FXCollections.observableArrayList();
    private final NumberFormat currencyFormat = NumberFormat.getCurrencyInstance(new Locale("id", "ID"));

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // Menghubungkan setiap kolom tabel dengan properti di objek Transaksi
        kolomTanggal.setCellValueFactory(new PropertyValueFactory<>("tanggalTransaksi"));
        kolomDeskripsi.setCellValueFactory(new PropertyValueFactory<>("deskripsi"));

        // Kolom Kategori memerlukan penanganan khusus karena berasal dari objek Kategori
        kolomKategori.setCellValueFactory(cellData -> 
            new SimpleStringProperty(cellData.getValue().getKategori().getNamaKategori())
        );

        // Kolom Pemasukan dan Pengeluaran juga memerlukan format mata uang
        currencyFormat.setMaximumFractionDigits(0); // Menghilangkan desimal
        
        kolomPemasukan.setCellValueFactory(cellData -> {
            Transaksi trx = cellData.getValue();
            if ("pemasukan".equals(trx.getJenisTransaksi())) {
                return new SimpleStringProperty(currencyFormat.format(trx.getJumlah()));
            }
            return new SimpleStringProperty("");
        });
        
        kolomPengeluaran.setCellValueFactory(cellData -> {
            Transaksi trx = cellData.getValue();
            if ("pengeluaran".equals(trx.getJenisTransaksi())) {
                return new SimpleStringProperty(currencyFormat.format(trx.getJumlah()));
            }
            return new SimpleStringProperty("");
        });

        // Menetapkan data ke tabel
        tabelTransaksi.setItems(listTransaksi);
    }

    // Metode ini dipanggil dari LoginController untuk mengirim data user
    public void initData(User user) {
        currentUser = user;
        welcomeLabel.setText("Halo, " + currentUser.getNamaLengkap() + "!");
        loadData();
    }
    
    public void loadData() {
        System.out.println("--- Memulai proses loadData() untuk User ID: " + currentUser.getId() + " ---");

        // Muat data tabel
        listTransaksi.clear();
        System.out.println("Memanggil transaksiDAO.findByUser...");
        List<Transaksi> dataTabel = transaksiDAO.findByUser(currentUser.getId());
        System.out.println("Selesai. Jumlah data ditemukan untuk tabel: " + dataTabel.size());
        listTransaksi.addAll(dataTabel);

        // Muat data ringkasan
        System.out.println("Memanggil ringkasanDAO.getRingkasan...");
        RingkasanKeuangan ringkasan = ringkasanDAO.getRingkasan(currentUser.getId());
        System.out.println("Selesai. Saldo total ditemukan: " + ringkasan.getSaldoTotal());
        pemasukanLabel.setText(currencyFormat.format(ringkasan.getTotalPemasukan()));
        pengeluaranLabel.setText(currencyFormat.format(ringkasan.getTotalPengeluaran()));
        saldoLabel.setText(currencyFormat.format(ringkasan.getSaldoTotal()));

        // Muat data untuk Bar Chart
        System.out.println("Memanggil laporanDAO.getLaporanHarian...");
        barChart.getData().clear();
        List<LaporanHarian> laporanList = laporanDAO.getLaporanHarian(currentUser.getId(), 7);
        System.out.println("Selesai. Jumlah data ditemukan untuk chart: " + laporanList.size());

        // ... sisa kode untuk chart ...
        Collections.reverse(laporanList);
        XYChart.Series<String, Number> pemasukanSeries = new XYChart.Series<>();
        pemasukanSeries.setName("Pemasukan");
        XYChart.Series<String, Number> pengeluaranSeries = new XYChart.Series<>();
        pengeluaranSeries.setName("Pengeluaran");
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM");
        for (LaporanHarian laporan : laporanList) {
            String tanggalFormatted = laporan.getTanggal().format(formatter);
            pemasukanSeries.getData().add(new XYChart.Data<>(tanggalFormatted, laporan.getPemasukanHarian()));
            pengeluaranSeries.getData().add(new XYChart.Data<>(tanggalFormatted, laporan.getPengeluaranHarian()));
        }
        barChart.getData().addAll(pemasukanSeries, pengeluaranSeries);
        System.out.println("--- Proses loadData() selesai ---");
    }
    
    private final LaporanDAO laporanDAO = new LaporanDAO();

    @FXML
    private void handleTambahButton(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/catatan/harian/view/FormTransaksi.fxml"));
            Parent root = loader.load();

            // Kirim data user dan controller ini ke FormTransaksiController
            FormTransaksiController controller = loader.getController();
            controller.initData(currentUser, this);

            Stage stage = new Stage();
            stage.setTitle("Tambah Transaksi Baru");
            stage.setScene(new Scene(root));

            // Modality.APPLICATION_MODAL membuat jendela utama tidak bisa diklik selama form ini terbuka
            stage.initModality(Modality.APPLICATION_MODAL);

            stage.showAndWait(); // Gunakan showAndWait untuk jendela modal

        } catch (IOException e) {
            e.printStackTrace();
            // Tampilkan dialog error jika gagal membuka form
        }
    }

    @FXML
    private void handleEditButton(ActionEvent event) {
        Transaksi selectedTransaksi = tabelTransaksi.getSelectionModel().getSelectedItem();

        if (selectedTransaksi == null) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Peringatan");
            alert.setHeaderText(null);
            alert.setContentText("Silakan pilih transaksi yang ingin Anda edit.");
            alert.showAndWait();
            return;
        }

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/catatan/harian/view/FormTransaksi.fxml"));
            Parent root = loader.load();

            // Panggil initData untuk mode EDIT
            FormTransaksiController controller = loader.getController();
            controller.initData(currentUser, selectedTransaksi, this);

            Stage stage = new Stage();
            stage.setTitle("Edit Transaksi");
            stage.setScene(new Scene(root));
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.showAndWait();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    @FXML
    private void handleHapusButton(ActionEvent event) {
        // 1. Ambil item yang dipilih dari tabel
        Transaksi selectedTransaksi = tabelTransaksi.getSelectionModel().getSelectedItem();

        // 2. Pastikan ada item yang dipilih
        if (selectedTransaksi == null) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Peringatan");
            alert.setHeaderText(null);
            alert.setContentText("Silakan pilih transaksi yang ingin Anda hapus terlebih dahulu.");
            alert.showAndWait();
            return;
        }

        // 3. Tampilkan dialog konfirmasi
        Alert confirmAlert = new Alert(Alert.AlertType.CONFIRMATION);
        confirmAlert.setTitle("Konfirmasi Hapus");
        confirmAlert.setHeaderText("Anda akan menghapus transaksi berikut:");
        confirmAlert.setContentText("Tanggal: " + selectedTransaksi.getTanggalTransaksi() + "\nDeskripsi: " + selectedTransaksi.getDeskripsi());

        Optional<ButtonType> result = confirmAlert.showAndWait();

        // 4. Jika pengguna menekan tombol OK
        if (result.isPresent() && result.get() == ButtonType.OK) {
            // Panggil metode delete dari DAO
            boolean sukses = transaksiDAO.delete(selectedTransaksi.getId());

            if (sukses) {
                // Jika berhasil, refresh tabel
                loadData();
            } else {
                // Jika gagal, tampilkan pesan error
                Alert errorAlert = new Alert(Alert.AlertType.ERROR);
                errorAlert.setTitle("Error");
                errorAlert.setHeaderText(null);
                errorAlert.setContentText("Gagal menghapus transaksi dari database.");
                errorAlert.showAndWait();
            }
        }
    }
    @FXML
    private void handleLogoutButton(ActionEvent event) {
        try {
            // Tutup jendela Dashboard saat ini
            Stage currentStage = (Stage) logoutButton.getScene().getWindow();
            currentStage.close();

            // Buka kembali jendela Login
            Parent root = FXMLLoader.load(getClass().getResource("/catatan/harian/view/Login.fxml"));
            Stage loginStage = new Stage();
            loginStage.setTitle("Aplikasi Catatan Keuangan - Login");
            loginStage.setScene(new Scene(root));
            loginStage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
}