# Toteutusdokumentti

## Luokkakaavio
![Luokkakaavio](luokkakaavio.png)

## Ohjelman rakenne
Ohjelma on jaettu viiteen pakkaukseen, jotka ovat algoritmit, tietorakenteet, verkko, käyttöliittymä ja muut apuluokat. Toteutuksessa on pyritty siihen, että algoritmeillä olisi kunkin suoritusvuoron alussa mahdollisimman tasapuoliset lähtökohdat, eli käytännössä pelkkä taulukkoon luettu verkko ilman esim. valmiiksi tehtyjä vieruslistoja, koska tällöin ei juurikaan vieruslistoja hyödyntävä JPS olisi alakynnessä.

Algorithms-pakkauksessa sijaitsevat abstrakti AStarAbstract, joka toimii pohjana kaikille kolmelle algoritmille, sekä kolmea toteutettua algoritmia kuvaavat luokat AStar, Dijkstra ja JPS. Algoritmiuokat on kirjoitettu siten, että mahdollisimman suuri osa algoista on yhteistä ja sijaitsee AStarAbstract-luokassa. Dijksta-luokassa on täten vain luvun 0 palauttava heuristinen funktio. JPS-luokka sisältää metodin, joka etsii keosta poistetun solmun seuraajat, sekä tämän lukuisat apumetodit. Muuten sekään ei eroa AStarAbstract-luokasta.

Datastructures-pakkauksesta löytyy minimikeko NodeMinHeap ja kokonaislukulista IntList. NodeMinHeap on toteutettu binäärikekona. Keon alkioina ovat Node-oliot ja avaimena toimii solmujen etäisyyden ja heuristisen arvon summana saatu prioriteetti. Keon toteutus tukee myös heap-dec-key-operaatiota. Samassa pakkauksessa oleva IntList tukee vain tarvittavia operaatioita add, get ja size.

Grid-pakkaus sisältää luokan samannimisen Grid, joka puolestaan sisältää tiedostosta luetun kartan esityksen kaksiulotteisena taulukkona ja tukee esim. tietyn indeksin läpikuljettavuuden selvittämistä sekä tietyssä indeksissä sijaitsevan solmun vieruslistan etsimistä.

GUI-pakkaus tulee sisältämään graafisen käyttöliittymän.

Util-pakkauksen sisältöön kuuluu JPS:n käyttämä Direction, joka on käytännössä suuntavektori, sekä kekoon laitettava Node-olio. Muussa ohjelmassa solmua kuvataan pelkällä indeksillä, mutta kekoon laitettaessa mukana täytyy olla myös etäisyys sekä heuristinen arvo, jotta solmut saadaan ulos keosta oikeassa järjestyksessä, joten nämä pakataan samaan olioon kekoon laitettaessa.

## Vaativuusanalyysi ja vaativuuksien vertailu
Kokonaislukulistan osalta get ja size operaatiot ovat vakioaikaisia ja add-operaatio tasoitetusti vakioaikainen, koska raskas yksiulotteisen taulukon koon kasvatusoperaatio suoritetaan harvoin.

IntListin tilavaativuus on lineaarinen lukujen määrän suhteen. Koska minimikeko on toteutettu binäärikekona, on sen kaikkien toteutettujen operaatioiden aikavaativuus O(log |V|). Koska keon koko riippuu sen sisältämien solmujen määrästä on sen tilavaativuusO(|V|).

Koska Dijkstran algoritmi lisää ja poistaa jokaisen solmun keosta korkeintaan kerran, suorittaa heap-dec-key-operaation korkeintaan kaarien määrän verran ja keko-operaatioilla on edellä mainittu logaritminen aikavaativuus, niin Dijkstran toteutuksen aikavaativuus on O((|E| + |V|) log |V|).

Koska A*:n toteutus eroaa Dijkstrasta vain valkioaikaisen heuristisen funktion osalta, on sen toteutuksen aikavaativuus myös O((|E| + |V|) log |V|).

JPS:n toteutus eroaa A*:n toteutuksesta vain funktion, joka etsii keosta poistetulle solmulle sen seuraajat, osalta. JPS:n toteutuksessa tämän funktion ensimmäinen osa käy läpi kerran kaikki maksimissaan kahdeksan vierussolmua karsien, joten tämä vaihe on vakioaikainen. Toinen vaihe on rekursiivinen, alkaen jokaisesta jäljelle jääneestä naapurista, edetään suoraa pitkin kunnes vastaan tulee sääntöjen mukainen hyppysolmu, maalisolmu tai seinä. Hyppysolmun löytäminen merkitsee sitä, ettei ylihypättyjä solmuja koskaan edes laiteta kekoon. Jos taas törmätään seinään, naapurille ei löydy seuraajaa, joten se hylätään.

Koska pahimmillaan kaikki solmut voivat olla keossa samanaikaisesti ja koska aputietorakenteiden koko on myös lineaarinen solmujen määrän suhteen, on kaikkien kolmen algoritmin tilavaativuus O(|V|).

## Suorituskykyvertailu
### Yleistä
Algoritmeja tullaan vertailemaan graafisen käyttöliittymän avulla, joka näyttää käyttäjälle mm. kuvan kartasta, johon on merkitty laajennettavat, eli keosta poistetut, solmut punaisella sekä lyhimmällä polulla olevat solmut sinisellä. Muut tavat vertailla algoritmien suorituskykyä on toteutettu erilaisten laskureiden ja järjestelmäkutsujen avulla. Nämä ovat lyhimmän polun pituus, keosta poistettuja solmuja laajennettaessa luodun vieras- tai seuraajalistan keskimääräinen koko, keon insert, del-min ja dec-key operaatioiden määrät, suoritusaika sekä käytetyn muistin määrä. Edellisten on tarkoitus myös näkyä GUI:ssa.

### Havainnot
Todella tiheissä labyrinteissä A*:n heuristiikasta ei ole mitään hyötyä, joten sen suoritusaika on Dijkstran tasoa, mutta JPS osaa hyppiä suorien osuuksien yli.

```
-----------
Kartan nimi: maze512-1-0
Kartan koko: 262144
Solmujen määrä: 131071
Lähtösolmu: (497, 89)
Maalisolmu: (467, 44)
-----------

Dijkstra:
Lyhimmän polun pituus: 4787.
Seuraajalistan koon ka: 2.000.
Keon insert-operaatioiden määrä: 127300.
Keon del-min-operaatioiden määrä: 127292.
Keon dec-key-operaatioiden määrä: 0.
Suoritusaika: 51.406 ms.
Käytetty muisti: 33.564 MB.

A*:
Lyhimmän polun pituus: 4787.
Seuraajalistan koon ka: 2.000.
Keon insert-operaatioiden määrä: 121821.
Keon del-min-operaatioiden määrä: 121795.
Keon dec-key-operaatioiden määrä: 0.
Suoritusaika: 52.969 ms.
Käytetty muisti: 57.949 MB.

JPS:
Lyhimmän polun pituus: 4787.
Seuraajalistan koon ka: 1.001.
Keon insert-operaatioiden määrä: 33747.
Keon del-min-operaatioiden määrä: 33729.
Keon dec-key-operaatioiden määrä: 0.
Suoritusaika: 26.250 ms.
Käytetty muisti: 23.615 MB.
```

Ei niin tiheissä labyrinteissä sama juttu Dijkstran ja A*:n osalta. Nyt heuristiikan hyödyttömyys näkyy erityisesti dec-key-operaatioiden määrässä, koska A* joutuu etsimään vaihtoehtoisia reittejä. JPS taas pystyy hyppimään paljon pidempiä matkoja ja on todella nopea.

```
------------
Kartan nimi: maze512-32-0
Kartan koko: 262144
Solmujen määrä: 253840
Lähtösolmu: (59, 434)
Maalisolmu: (101, 194)
------------

Dijkstra:
Lyhimmän polun pituus: 2306.94.
Seuraajalistan koon ka: 7.805.
Keon insert-operaatioiden määrä: 248450.
Keon del-min-operaatioiden määrä: 248305.
Keon dec-key-operaatioiden määrä: 0.
Suoritusaika: 117.656 ms.
Käytetty muisti: 152.305 MB.

A*:
Lyhimmän polun pituus: 2306.94.
Seuraajalistan koon ka: 7.805.
Keon insert-operaatioiden määrä: 237279.
Keon del-min-operaatioiden määrä: 236897.
Keon dec-key-operaatioiden määrä: 162388.
Suoritusaika: 130.156 ms.
Käytetty muisti: 176.781 MB.

JPS:
Lyhimmän polun pituus: 2306.94.
Seuraajalistan koon ka: 1.013.
Keon insert-operaatioiden määrä: 152.
Keon del-min-operaatioiden määrä: 150.
Keon dec-key-operaatioiden määrä: 0.
Suoritusaika: 18.438 ms.
Käytetty muisti: 10.080 MB.
```

Kartoissa, jotka ovat tyypiltään tiheitä metsiä, heuristiikka nousee arvoonsa ja A* on todella hyvä. Toisaalta nyt JPS ei pysty karsimaan niin monia naapureita koska sen sääntöihin sopivia hyppysolmuja löytyy joka puolelta. Tämä näkyy JPS:n seuraajalistan koon korkeassa keskiarvossa sekä korkeassa määrässä kekoon lisättäviä solmuja suhteessa A*:een. Tämä taitaa olla yksi JPS:n pahimpia tapauksia.

```
--------------
Kartan nimi: random512-10-0
Kartan koko: 262144
Solmujen määrä: 235900
Lähtösolmu: (19, 44)
Maalisolmu: (509, 436)
--------------

Dijkstra:
Lyhimmän polun pituus: 668.188.
Seuraajalistan koon ka: 6.499.
Keon insert-operaatioiden määrä: 233690.
Keon del-min-operaatioiden määrä: 233565.
Keon dec-key-operaatioiden määrä: 3487.
Suoritusaika: 146.094 ms.
Käytetty muisti: 166.329 MB.

A*:
Lyhimmän polun pituus: 668.188.
Seuraajalistan koon ka: 6.562.
Keon insert-operaatioiden määrä: 35951.
Keon del-min-operaatioiden määrä: 34450.
Keon dec-key-operaatioiden määrä: 24095.
Suoritusaika: 27.344 ms.
Käytetty muisti: 40.339 MB.

JPS:
Lyhimmän polun pituus: 668.188.
Seuraajalistan koon ka: 2.174.
Keon insert-operaatioiden määrä: 16025.
Keon del-min-operaatioiden määrä: 15072.
Keon dec-key-operaatioiden määrä: 4185.
Suoritusaika: 21.875 ms.
Käytetty muisti: 25.212 MB.
```

## Lähteet
- TiRa-kurssin materiaali.
- [Implementation of A*](http://www.redblobgames.com/pathfinding/a-star/implementation.html)
- [Heuristics](http://theory.stanford.edu/~amitp/GameProgramming/Heuristics.html)
- [Jump Point Search](https://harablog.wordpress.com/2011/09/07/jump-point-search/)
- [Online Graph Pruning for Pathfinding on Grid Maps](http://users.cecs.anu.edu.au/~dharabor/data/papers/harabor-grastien-aaai11.pdf)
- [The JPS Pathfinding System](http://users.cecs.anu.edu.au/~dharabor/data/papers/harabor-grastien-socs12.pdf)
