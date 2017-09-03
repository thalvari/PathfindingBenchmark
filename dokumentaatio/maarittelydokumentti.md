# Määrittelydokumentti

## Algoritmit ja tietorakenteet
Tarkoituksena on toteuttaa Dijkstran algoritmi, A* ja JPS sekä vertailla näiden suorituskykyä. Koska A* ja JPS ovat Dijkstran optimoituja versioita, on näiden kolmen vertailu tosiasiassa erilaisten optimointien vertailua. Tarkoituksena on myös toteuttaa itse kyseisten algojen tarvitsemat tietorakenteet, kuten lista ja minimikeko, projektin edetessä.

## Ratkaistava ongelma
Ongelmana on mahdollisimman tehokas reitinhaku verkossa kahden pisteen välillä. Tarkoituksena on vertailla pelien tekoälyn usein käyttämiä reitinhakualgoritmeja liikuttaessa kaksiulotteisella pelikartalla, jossa kustakin solmusta voidaan edetä parhaimmillaan kahdeksaan eri suuntaan, pysty- tai vaakasuuntaisten kaarien painojen ollessa 1 ja viistojen sqrt(2). Kulmien leikkaaminen on myös kiellettyä. Käytettävät tietorakenteet valitaan niiden tehokkuuden perusteella.

## Syötteet
Syötteenä ohjelma käyttää [Moving AI Labin](http://movingai.com/benchmarks/) testausta varten tarjoamia ruudukkopohjaisia kaksiulotteisia pelikarttoja. Ohjelma ottaa syötteenä tietyn kartan tai joukon karttoja, ajaa tuetut algoritmit ja antaa tuloksena dataa, jonka pohjalta algoritmeja voidaan vertailla. Tarkoituksena on esittää tulokset graafisen käyttöliittymän avulla ja visualisoida algoritmien kartan läpikäymistä.

## Vaativuustavoitteet
Vaativuuksien osalta tavoitteena on Dijkstran ja A*:n kohdalla TiRa-kurssilta tutut aikavaativuus O((|E| + |V|) log |V|) ja tilavaativuus O(|V|). JPS:n tekijöiden [mukaan](https://harablog.wordpress.com/2011/09/07/jump-point-search/), sen vaativuudet pysyvät samoina, mutta algoritmi on käytännössä tehokkaampi, joten vaativuustavoitteet pysyvät samoina JPS:n osalta.
