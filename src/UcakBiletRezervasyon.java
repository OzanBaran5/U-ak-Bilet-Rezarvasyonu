import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

// Uçak Entity Class
class Ucak {
    private String model;
    private String marka;
    private String seriNo;
    private int koltukKapasitesi;

    public Ucak(String model, String marka, String seriNo, int koltukKapasitesi) {
        this.model = model;
        this.marka = marka;
        this.seriNo = seriNo;
        this.koltukKapasitesi = koltukKapasitesi;
    }

    public int getKoltukKapasitesi() {
        return koltukKapasitesi;
    }

    @Override
    public String toString() {
        return "Model: " + model + ", Marka: " + marka + ", Seri No: " + seriNo + ", Koltuk Kapasitesi: " + koltukKapasitesi;
    }
}

// Lokasyon Entity Class
class Lokasyon {
    private String ulke;
    private String sehir;
    private String havaalani;
    private boolean aktif;

    public Lokasyon(String ulke, String sehir, String havaalani, boolean aktif) {
        this.ulke = ulke;
        this.sehir = sehir;
        this.havaalani = havaalani;
        this.aktif = aktif;
    }

    @Override
    public String toString() {
        return "Ülke: " + ulke + ", Şehir: " + sehir + ", Havaalanı: " + havaalani + ", Aktif: " + (aktif ? "Evet" : "Hayır");
    }
}

// Uçuş Entity Class
class Ucus {
    private Lokasyon lokasyon;
    private String saat;
    private Ucak ucak;
    private List<Rezervasyon> rezervasyonlar;

    public Ucus(Lokasyon lokasyon, String saat, Ucak ucak) {
        this.lokasyon = lokasyon;
        this.saat = saat;
        this.ucak = ucak;
        this.rezervasyonlar = new ArrayList<>();
    }

    public Ucak getUcak() {
        return ucak;
    }

    public int getRezervasyonSayisi() {
        return rezervasyonlar.size();
    }

    public boolean koltukVarMi() {
        return getRezervasyonSayisi() < ucak.getKoltukKapasitesi();
    }

    public void rezervasyonEkle(Rezervasyon rezervasyon) {
        rezervasyonlar.add(rezervasyon);
    }

    public List<Rezervasyon> getRezervasyonlar() {
        return rezervasyonlar;
    }

    @Override
    public String toString() {
        return "Uçuş Bilgileri:\n" + lokasyon + "\nSaat: " + saat + "\nUçak: " + ucak +
                "\nRezervasyonlar: " + getRezervasyonSayisi() + "/" + ucak.getKoltukKapasitesi();
    }
}

// Rezervasyon Entity Class
class Rezervasyon {
    private Ucus ucus;
    private String ad;
    private String soyad;
    private int yas;

    public Rezervasyon(Ucus ucus, String ad, String soyad, int yas) {
        this.ucus = ucus;
        this.ad = ad;
        this.soyad = soyad;
        this.yas = yas;
    }

    public Ucus getUcus() {
        return ucus;
    }

    @Override
    public String toString() {
        return ad + ";" + soyad + ";" + yas + ";" + ucus.toString().replace("\n", " | ").replace(";", ",");
    }

    public String detayGoster() {
        return "Ad: " + ad + ", Soyad: " + soyad + ", Yaş: " + yas + "\n" + ucus.toString();
    }
}

public class UcakBiletRezervasyon {
    private List<Ucus> ucuslar = new ArrayList<>();

    public void ucusEkle(Ucus ucus) {
        ucuslar.add(ucus);
    }

    public void rezervasyonYap(Ucus ucus, String ad, String soyad, int yas) {
        if (!ucus.koltukVarMi()) {
            System.out.println("Üzgünüz, bu uçuşta boş koltuk kalmadı.");
            return;
        }
        Rezervasyon rezervasyon = new Rezervasyon(ucus, ad, soyad, yas);
        ucus.rezervasyonEkle(rezervasyon);
        System.out.println("Rezervasyon başarıyla yapıldı:");
        System.out.println(rezervasyon.detayGoster());
    }

    public void rezervasyonlariKaydet(String dosyaYolu) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(dosyaYolu))) {
            // Başlık satırı
            writer.write("Ad;Soyad;Yaş;Uçuş Bilgileri");
            writer.newLine();
            for (Ucus ucus : ucuslar) {
                for (Rezervasyon rezervasyon : ucus.getRezervasyonlar()) {
                    writer.write(rezervasyon.toString());
                    writer.newLine();
                }
            }
            System.out.println("Rezervasyonlar başarıyla '" + dosyaYolu + "' dosyasına kaydedildi.");
        } catch (IOException e) {
            System.out.println("Dosya yazılırken hata oluştu: " + e.getMessage());
        }
    }

    public void ucuslariListele() {
        System.out.println("Mevcut Uçuşlar:");
        for (int i = 0; i < ucuslar.size(); i++) {
            System.out.println((i + 1) + ") " + ucuslar.get(i));
        }
    }

    // Yeni eklenen metot: Rezervasyonları listele
    public void rezervasyonlariListele() {
        boolean rezervasyonVar = false;
        System.out.println("Tüm Rezervasyonlar:");
        for (Ucus ucus : ucuslar) {
            List<Rezervasyon> rezervasyonList = ucus.getRezervasyonlar();
            if (!rezervasyonList.isEmpty()) {
                rezervasyonVar = true;
                System.out.println("\n" + ucus.toString());
                for (Rezervasyon r : rezervasyonList) {
                    System.out.println(" - " + r.detayGoster());
                }
            }
        }
        if (!rezervasyonVar) {
            System.out.println("Henüz yapılmış rezervasyon bulunmamaktadır.");
        }
    }

    public static void main(String[] args) {
        UcakBiletRezervasyon sistem = new UcakBiletRezervasyon();
        Scanner scanner = new Scanner(System.in);

        // Örnek Uçaklar ve Lokasyonlar
        Ucak ucak1 = new Ucak("Boeing 737", "Boeing", "737-800", 180);
        Ucak ucak2 = new Ucak("Airbus A320", "Airbus", "A320-200", 150);

        Lokasyon lokasyon1 = new Lokasyon("Türkiye", "İstanbul", "İstanbul Havalimanı", true);
        Lokasyon lokasyon2 = new Lokasyon("Almanya", "Berlin", "Berlin Brandenburg Havalimanı", true);

        // Örnek Uçuşlar
        Ucus ucus1 = new Ucus(lokasyon1, "10:00", ucak1);
        Ucus ucus2 = new Ucus(lokasyon2, "15:30", ucak2);

        sistem.ucusEkle(ucus1);
        sistem.ucusEkle(ucus2);

        System.out.println("=== Uçak Bilet Rezervasyon Sistemi ===");
        boolean devam = true;

        while (devam) {
            System.out.println("\n1) Uçuşları Listele");
            System.out.println("2) Rezervasyon Yap");
            System.out.println("3) Rezervasyonları Kaydet");
            System.out.println("4) Rezervasyonları Listele");
            System.out.println("5) Çıkış");
            System.out.print("Seçiminiz: ");

            int secim = -1;
            try {
                secim = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Lütfen geçerli bir sayı giriniz.");
                continue;
            }

            switch (secim) {
                case 1:
                    sistem.ucuslariListele();
                    break;
                case 2:
                    sistem.ucuslariListele();
                    System.out.print("Bir uçuş seçiniz (numara): ");
                    int ucusNo = -1;
                    try {
                        ucusNo = Integer.parseInt(scanner.nextLine());
                    } catch (NumberFormatException e) {
                        System.out.println("Geçersiz seçim.");
                        break;
                    }
                    if (ucusNo < 1 || ucusNo > sistem.ucuslar.size()) {
                        System.out.println("Yanlış uçuş numarası.");
                        break;
                    }
                    Ucus secilenUcus = sistem.ucuslar.get(ucusNo - 1);

                    System.out.print("Adınız: ");
                    String ad = scanner.nextLine();
                    System.out.print("Soyadınız: ");
                    String soyad = scanner.nextLine();
                    System.out.print("Yaşınız: ");
                    int yas = -1;
                    try {
                        yas = Integer.parseInt(scanner.nextLine());
                    } catch (NumberFormatException e) {
                        System.out.println("Geçersiz yaş.");
                        break;
                    }
                    sistem.rezervasyonYap(secilenUcus, ad, soyad, yas);
                    break;
                case 3:
                    sistem.rezervasyonlariKaydet("rezervasyonlar.csv");
                    break;
                case 4:
                    sistem.rezervasyonlariListele();
                    break;
                case 5:
                    System.out.println("Sistemden çıkılıyor. İyi günler!");
                    devam = false;
                    break;
                default:
                    System.out.println("Lütfen 1-5 arasında bir seçim yapınız.");
            }
        }
        scanner.close();
    }
}

