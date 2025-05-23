# Uçak Bilet Rezervasyon Konsol Uygulaması

Bu proje, Java ile geliştirilmiş basit bir **Uçak Bilet Rezervasyon Konsol Uygulaması**dır.  
Kullanıcıların uçuşları listeleyip, seçtikleri uçuş için rezervasyon yapabildiği, yapılan rezervasyonları listeleyebildiği ve isteğe bağlı olarak CSV veya JSON formatında dosyaya kaydedebildiği fonksiyonel bir uygulamadır.

---

## Özellikler

- **Uçak, Lokasyon, Uçuş ve Rezervasyon** gibi temel entity sınıfların kullanımı ile nesneye dayalı modelleme.  
- Uçakların model bilgileri, marka, seri numarası ve koltuk kapasitesi tutulmaktadır.  
- Lokasyon bilgileri; ülke, şehir, havaalanı ve aktiflik durumu içerir.  
- Uçuşlar lokasyon, saat ve uçak bilgisi ile oluşturulur.  
- Rezervasyon yapılırken, uçaktaki koltuk kapasitesi kontrol edilerek boş koltuk kalmadıysa uyarı verilir.  
- Konsol arayüzü sayesinde kullanıcı:
  - Mevcut uçuşları listeleyebilir.
  - Seçilen uçuş için rezervasyon yapabilir.
  - Mevcut rezervasyonları listeleyebilir.
  - Rezervasyonları CSV veya JSON formatında dosyaya kaydedebilir.
- JSON dosyası yazımı için ek kütüphane kullanmak yerine standart Java metotları ve `org.json` ya da alternatif olarak Gson kullanılabilir.

---

## Kullanım

1. Projeyi çalıştırın.
2. Menüden uçuşları listeleyin veya doğrudan rezervasyon yap seçeneğini tercih edin.
3. Rezervasyon yapmak için uçuş numarasını seçip istenilen bilgileri girin (Ad, Soyad, Yaş).
4. Rezervasyon işlemi tamamlandıktan sonra isterseniz yapılan tüm rezervasyonları görüntüleyebilir veya dosyaya kaydedebilirsiniz.
5. Dosya kaydetme seçenekleri:
   - CSV formatında `rezervasyonlar.csv`
   - JSON formatında `rezervasyonlar.json`
6. Çıkış yaparak uygulamadan çıkabilirsiniz.

---

## Kurulum ve Gereksinimler

- Java Development Kit (JDK) 8 veya üzeri.
- (Opsiyonel) JSON formatında kaydetmek için Gson kütüphanesi:
  - Maven veya Gradle kullanıyorsanız, proje bağımlılığına `com.google.code.gson:gson` ekleyin.
  - Veya manuel olarak jar dosyasını projeye dahil edin.
- Alternatif olarak JSON işlemlerinde standart `org.json` kullanılabilir.

---

## Kod Yapısı

- `Ucak` : Uçak modelini, markasını, seri numarasını ve koltuk kapasitesini tutar.
- `Lokasyon` : Ülke, şehir, havaalanı ve aktiflik bilgilerini içerir.
- `Ucus` : Lokasyon, saat ve uçak bilgisiyle bir uçuş oluşturur; rezervasyonları tutar ve koltuk durumu kontrolü yapar.
- `Rezervasyon` : Uçuşa bağlı olarak kişi bilgilerini (ad, soyad, yaş) tutar.
- `UcakBiletRezervasyon` : Ana uygulama sınıfı; uçuş ekleme, rezervasyon yapma, listeleme ve dosyaya kaydetme işlemlerini gerçekleştirir.
