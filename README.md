HYPERLOGLOG CARDİNALİTY ESTİMATİON (Java Implementation)

PROJE TANIMI 

Bu projede büyük veri analitiğinde kullanılan olasılıksal veri yapılarından biri olan HyperLogLog algoritması Java programlama dili ile sıfırdan gerçekleştirilmiştir. HyperLogLog algoritması, çok büyük veri kümelerinde bulunan benzersiz eleman sayısını (cardinality) düşük bellek kullanarak yaklaşık olarak tahmin etmek için kullanılan bir yöntemdir.

Büyük veri sistemlerinde tüm verileri saklayarak benzersiz eleman sayısını hesaplamak oldukça maliyetli olabilir. HyperLogLog algoritması ise verileri saklamak yerine hash fonksiyonları ve istatistiksel yöntemler kullanarak tahmin yapar. Bu sayede milyonlarca veri üzerinde bile oldukça düşük bellek kullanımı ile tahmin yapılabilir.

PROJENİN AMACI

Bu çalışmanın amacı HyperLogLog algoritmasının temel bileşenlerini tasarlamak ve gerçekleştirmektir.

Proje kapsamında:

-yüksek kaliteli bir hash fonksiyonu kullanılmıştır

-veri elemanları bucket yapısına ayrılmıştır

-her bucket için register yapısı oluşturulmuştur

-harmonik ortalama tabanlı cardinality tahmini uygulanmıştır

-küçük ve büyük veri kümeleri için düzeltme mekanizmaları eklenmiştir

-iki farklı HyperLogLog yapısının merge edilmesi gösterilmiştir

-algoritmanın teorik hata analizi yapılmıştır

KULLANILAN TEKNOLOJİLER

-Java

-IntelliJ IDEA

-Git & GitHub

-HyperLogLog algoritması

-MurmurHash3 hash fonksiyonu
