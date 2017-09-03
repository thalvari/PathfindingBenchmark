# Testausdokumentti

## Yleistä
Ohjelman luokat on testattu käyttäen automaattisia JUnit-yksikkötestejä sekä manuaalisesti käyttöliittymän avulla, tutkimalla algoritmien etenemistä visuaalisesti ja laskureiden avulla. Erityisesti testaamisessa on painotettu itse toteutettujen tietorakenteiden operaatioiden toimivuuden ja reitinhakualgoritmien tuottamien lyhimpien polkujen oikeellisuuden testaamista. Yleisesti testaamisessa on pyritty mahdollisimman hyvään rivi- ja mutaatiokattavuuteen. Testien kattavuutta testataan PIT-työkalun kattavuusraportin avulla.

## Syötteet
Itse toteutettujen tietorakenteiden yksikkötesteissä on käytetty kattavaa joukkoa itse keksittyjä sopivia syötteitä.

Reitinhakualgoritmien testaamisessa syötteenä on käytetty [Moving AI Labin](http://movingai.com/benchmarks/) testausta varten tarjoamia ruudukkopohjaisia kaksiulotteisia peli- ja keinotekoisia karttoja. Sivustolta löytyy myös lähes jokaista karttaa kohden oma scenario-tiedosto, joka sisältää lukuisia reitinhakuongelmia vastauksineen. Testitapaukset ovat siis yhteisiä kaikille toteutetuille algoritmeille, mikä taas helpottaa vertailua. Algoritmien testauksessa on hyödynnetty mahdollisimman erityyppisiä karttoja, valiten usein eri tyyppien monimutkaisimmat edustajat. Yksikkötesteissä näitä karttoja tutkitaan lähes aina käyttäen ongelmia, joiden tuloksena saatu polku on mahdollisimman pitkä. Tällöin, jos koodissa on olennainen bugi, voi sen hyvinkin havaita testitapausten tulosten virheellisyydestä.

Esimerkiksi JPS:n kohdalla löysin parikin bugia koodistani ajaessani Random obstacles -karttojen testitapauksia ja huomatessani tulosten virheellisyyden. Tämän jälkeen tutkin algoritmin toimintaa visuaalisesti tulosteiden avulla ja huomasin tilanteen, jossa koodissa oli puutteita.

## Toistettavuus
Testitapaukset voidaan toistaa ajamalla yksikkötestit. Muita ongelmia voidaan ajaa käyttöliittymän avulla antamalla kartan nimi sekä alku- ja loppukoordinaatit ja vertaamalla tuloksia edellä mainittujen scenario-tiedostojen tuloksiin.

## Tulokset
Yksikkötestit voidaan ajaa Mavenin komennolla `mvn test`, jolloin Maven antaa graafisen esityksen yksikkötestien tuloksista, kunhan repon sisällön lataa ensin omalle koneelle. PIT-raportissa on puolestaan tarkempi analyysi testien rivi- ja mutaatiokattavuudesta sekä testatuista mutaatioista.

## Suorituskykytestaus
Suorituskykytestausta apuna käytetään ensinnäkin erilaisia koodiin sisällytettyjä laskureita, joiden arvoja voidaan lopuksi esittää käyttäjälle. Toteutettuja laskureita ovat keko-operaatioiden määrä, keon maksimikoko, suljettuun joukkoon kuuluvien solmujen määrä sekä keosta poistettuja solmuja laajennettaessa saadun seuraaja- tai vieruslistan keskimääräinen koko. Keko-operaatiot ovat kalliita joten niiden yhteismäärä on mielenkiintoinen. Keon maksimikoko kertoo miten raskaita keko-operaatiot pahimmillaan ovat. Suljettuun joukkoon kuuluvien solmujen määrä taas kertoo miten suuri osa kartasta käydään läpi. Seuraajalistan keskimääräinen koko puolestaan kertoo JPS:n tapauksessa sen karsimissääntöjen tehokkuudesta kyseisessä verkossa. Testauksessa käytetään myös järjestelmämetodeja `Runtime.getRuntime().totalMemory()`, `Runtime.getRuntime().freeMemory()` ja `ManagementFactory.getThreadMXBean().getCurrentThreadCpuTime()` algoritmien suoritusajan ja muistinkulutuksen mittaamiseen.
