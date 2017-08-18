# Määrittelydokumentti
## Algoritmit ja tietorakenteet
Tarkoituksena on toteuttaa aluksi Dijkstra, A* ja JPS algoritmit ja mahdollisuuksien mukaan toteuttaa näiden pohjalta kyseisten algoritmien optimoituja versioita, kuten JPS+. Tarkoituksena on myös toteuttaa itse kyseisten algojen tarvitsemat tietorakenteet, kuten lista ja minimikeko, projektin edetessä.

## Ratkaistava ongelma
Ongelmana on mahdollisimman tehokas reitinhaku verkossa kahden pisteen välillä. Tarkoituksena on vertailla pelien tekoälyn usein käyttämiä reitinhakualgoritmeja liikuttaessa kaksiulotteisella pelikartalla, jossa kustakin solmusta voidaan edetä kahdeksaan eri suuntaan, pysty- tai vaakasuuntaisten kaarien painojen ollessa 1 ja muiden sqrt(2). Käytettävät tietorakenteet valitaan niiden tehokkuuden perusteella.

## Syötteet
Syötteenä ohjelma käyttää [Moving AI Labin](http://movingai.com/benchmarks/) testausta varten tarjoamia ruudukkopohjaisia kaksiulotteisia pelikarttoja. Ohjelmalle ottaa syötteenä tietyn kartan tai joukon karttoja, ajaa tuetut algoritmit ja antaa tuloksena dataa, jonka pohjalta algoritmeja voidaan vertailla. Tarkoituksena on esittää tulokset graafisen käyttöliittymän avulla, ehkä jopa visualisoida algoritmien kartan läpikäymistä.

## Vaativuustavoitteet
Vaativuuksien osalta tavoitteena on Dijkstran ja A*:n kohdalla TiRa-kurssilta tutut aikavaativuus O((|E| + |V|) log |V|) ja tilavaativuus O(|V|). JPS:n tekijöiden [mukaan](https://harablog.wordpress.com/2011/09/07/jump-point-search/), sen vaativuudet pysyvät samoina, mutta algoritmi on käytännössä tehokkaampi, joten vaativuustavoitteet pysyvät samoina.
