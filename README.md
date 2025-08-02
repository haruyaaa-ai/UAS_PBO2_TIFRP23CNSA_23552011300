# Final Proyek Pemrograman Berorientasi Obyek 2

- **Mata Kuliah:** Pemrograman Berorientasi Obyek 2  
- **Dosen Pengampu:** Muhammad Ikhwan Fathulloh

## Profil

- **Nama:** Damar Satriatama Putra  
- **NIM:** 23552011300  
- **Studi Kasus:** Aplikasi Catatan Keuangan

## Judul Studi Kasus

**Aplikasi Catatan Keuangan Harian Berbasis JavaFX**

## Penjelasan Studi Kasus

Aplikasi ini merupakan sistem pencatatan keuangan pribadi yang memungkinkan pengguna mencatat pemasukan dan pengeluaran secara terstruktur.  
Fitur utama meliputi:
- Login dan Register
- Pencatatan Transaksi
- Ringkasan keuangan dalam bentuk grafik batang

Aplikasi dibangun menggunakan **bahasa Java**, paradigma **Object-Oriented Programming (OOP)**, dan antarmuka berbasis **JavaFX**.

# Penjelasan 4 Pilar OOP dalam Studi Kasus
Penjelasan 4 Pilar OOP dalam Studi Kasus

### 1. Inheritance (Pewarisan)
**Penjelasan:**  
Inheritance memungkinkan sebuah class mewarisi properti dan method dari class lain, sehingga menghindari penulisan kode yang berulang.

**Contoh Studi Kasus:**
```java
public class Admin extends User {
    // mewarisi username, password, method login(), dll dari User
}

2. Encapsulation
Penjelasan:
Enkapsulasi menyembunyikan data (menggunakan private) dan hanya membolehkan akses melalui getter dan setter. Hal ini menjaga keamanan data.

Studi Kasus:
Misalnya pada class Keuangan:

public class Keuangan {
    private int pemasukan;
    private int pengeluaran;

    public int getPemasukan() {
        return pemasukan;
    }

    public void setPemasukan(int pemasukan) {
        this.pemasukan = pemasukan;
    }
}

Field pemasukan dan pengeluaran tidak bisa diakses langsung, melainkan melalui method getter dan setter.

3. Polymorphism
Penjelasan:
Polimorfisme memungkinkan satu method yang sama memiliki perilaku berbeda tergantung objeknya.

Studi Kasus:
Misalnya, ada method tampilkanRingkasan() di class Keuangan dan Laporan, namun dengan implementasi yang berbeda:

public class Keuangan {
    public void tampilkanRingkasan() {
        System.out.println("Ringkasan keuangan harian.");
    }
}

public class Laporan extends Keuangan {
    @Override
    public void tampilkanRingkasan() {
        System.out.println("Ringkasan seluruh laporan keuangan.");
    }
}

Saat dipanggil, method tampilkanRingkasan() akan menyesuaikan dengan objeknya.

4. Abstract
Penjelasan:
Abstraksi menyembunyikan kompleksitas implementasi dan hanya menunjukkan fitur penting dari suatu class.

Studi Kasus:
Jika kamu menggunakan class abstract seperti:

public abstract class Pengguna {
    protected String username;
    protected String password;

    public abstract void login();
}
Kemudian class Admin dan UserBiasa mengimplementasikan method login() dengan cara masing-masing.

# Demo Proyek
Github: Github
Youtube: Youtube
