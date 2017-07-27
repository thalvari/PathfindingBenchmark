# Määrittelydokumentti
- Tarkoituksena on toteuttaa aluksi A* ja JPS algoritmit ja mahdollisuuksien mukaan toteuttaa näiden pohjalta kyseisten algoritmien optimoituja versioita, kuten JPS+. Tarkoituksena on myös toteuttaa algojen tarvitsemat tietorakenteet itse projektin edetessä.
- Ongelmana on mahdollisimman tehokas reitinhaku verkossa kahden pisteen välillä. Tarkoituksena on testata tietokonepelien tekoälyn usein käyttämiä reitinhakualgoritmeja liikuttaessa pelikartalla.
- Syötteenä ohjelma käyttää [Moving AI Labin](http://movingai.com/benchmarks/) testausta varten tarjoamia ruudukkopohjaisia kaksiulotteisia pelikarttoja. Ohjelmalle ottaa syötteenä tietyn kartan tai joukon karttoja, ajaa tuetut algoritmit ja antaa tuloksena dataa, jonka pohjalta algoritmeja voidaan vertailla.
- Tavoitteena on vähitään exponentiaalinen tila- ja aikavaativuus, mikä on käsittääkseni A*:n ja sen varianttien vaativuus käytettäessä todella huonoa heuristiikkaa.
