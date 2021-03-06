# Toteutusdokumentti

## Luokkakaavio
![Luokkakaavio](kuvat/luokkakaavio.png)

## Ohjelman rakenne
Ohjelma on jaettu kuuteen pakkaukseen, jotka ovat algoritmit, tietorakenteet, verkko, käyttöliittymä, muut apuluokat ja wrapperi. Toteutuksessa on pyritty siihen, että kullakin algoritmilla olisi suorituksensa alussa mahdollisimman tasapuoliset lähtökohdat, joten niillä on käytettävissä vain verkkoa esittävä taulukko solmu-olioita. Käytössä ei ole esim. valmiiksi tehtyjä vieruslistoja, koska tällöin ei juurikaan vieruslistoja hyödyntävä JPS olisi alakynnessä.

Algorithms-pakkauksessa sijaitsevat abstrakti AStarAbstract, joka toimii pohjana kaikille kolmelle algoritmille, sekä kolmea toteutettua algoritmia kuvaavat luokat AStar, Dijkstra ja JPS. Algoritmiluokat on kirjoitettu siten, että mahdollisimman suuri osa algoista on yhteistä ja sijaitsee AStarAbstract-luokassa. Dijksrta-luokassa on täten vain luvun 0 palauttava heuristinen funktio. JPS-luokka sisältää metodin, joka etsii keosta poistetun solmun seuraajat, sekä tämän lukuisat apumetodit. Muuten sekään ei eroa AStarAbstract-luokasta.

Datastructures-pakkauksesta löytyy minimikeko NodeMinHeap ja geneerisen listan toteutus MyList. NodeMinHeap on toteutettu binäärikekona. Keon alkiot ovat Node-olioita ja avaimena toimii solmun etäisyyden lähtösolmusta ja heuristisen arvon summana saatu prioriteetti. Keon toteutus tukee myös heap-dec-key-operaatiota. MyList tukee vain tarvittavia operaatioita add, get, size ja contains.

Grid-pakkaus sisältää samannimisen luokan Grid, joka puolestaan sisältää tiedostosta luetun kartan esityksen kaksiulotteisena, solmu-olioita sisältävänä taulukkona ja tukee esim. annettujen koordinaattien oikeellisuuden selvittämistä, solmun vieruslistan etsimistä sekä tietyssä suunnassa sijaitsevan solmun läpikuljettavuuden selvittämistä. Grid-olio osaa myös kertoa voidaanko solmusta edetä tiettyyn suuntaan, ottaen huomioon että kulmien läpi ei saa oikaista. Pakkauksessa on myös verkon solmua kuvaava Node-olio, jonka luokkamuuttujia ovat mm. solmun etäisyys lähtösolmusta, solmun heuristinen arvo, solmun koordinaatit, sen tyyppi sekä tieto siitä kuuluuko solmu suljettuun joukkoon.

GUI-pakkaus sisältää graafisen käyttöliittymän. Itse graafisten komponenttien määrittely löytyy resurssitiedostosta FXMLDocument, mutta mm. tapahtumankäsittelijät sijaitsevat FXMLDocumentController-luokassa.

Util-pakkauksen sisältöön kuuluu JPS:n käyttämä Direction, joka kertoo mihin suuntaan ollaan etenemässä, sekä kartanlukijaa kuvaava luokka MapReader. MapReader osaa mm. kertoa mitä karttoja on tarjolla maps-kansiossa. Se osaa myös luoda yksittäisestä map-tiedostosta solmuolioita sisältävän taulukon.

Wrapper pakkauksessa sijaitsee käyttöliittymän aktiivisesti hyödyntämä Wrapper-luokka, joka muokkaa algoritmiluokkien tarjoamat tulokset käyttäjälle esitettävään muotoon.

## Vaativuusanalyysi ja vaativuuksien vertailu
Listan osalta get ja size operaatiot ovat vakioaikaisia ja add-operaatio tasoitetusti vakioaikainen, koska raskas yksiulotteisen taulukon koon kasvatusoperaatio suoritetaan vain harvoin. Contains-operaation aikavaativuus on lineaarinen, koska se käy tarvittaessa koko taulukon läpi. Listan tilavaativuus on lineaarinen lukujen määrän suhteen.

Koska minimikeko on toteutettu binäärikekona, on sen kaikkien toteutettujen operaatioiden aikavaativuus O(log |V|). Koska keon koko riippuu sen sisältämien solmujen määrästä on sen tilavaativuus O(|V|).

Koska toteutettu Dijkstran algoritmi lisää ja poistaa jokaisen solmun keosta korkeintaan kerran, suorittaa heap-dec-key-operaation korkeintaan kaarien määrän verran ja keko-operaatioilla on edellämainittu logaritminen aikavaativuus, niin Dijkstran toteutuksen aikavaativuus on O((|E| + |V|) log |V|).

Koska A*:n toteutus eroaa Dijkstrasta vain valkioaikaisen heuristisen funktion osalta, on sen toteutuksen aikavaativuus myös O((|E| + |V|) log |V|).

JPS:n toteutus eroaa A*:n toteutuksesta vain funktion, joka etsii keosta poistetulle solmulle sen seuraajat, osalta. JPS:n toteutuksessa tämän funktion ensimmäinen osa käy läpi kerran kaikki maksimissaan kahdeksan vierussolmua karsien, joten tämä vaihe on vakioaikainen. Toinen vaihe on rekursiivinen, missä alkaen jokaisesta jäljelle jääneestä naapurista, edetään suoraa pitkin kunnes vastaan tulee sääntöjen mukainen hyppysolmu, maalisolmu tai seinä. Hyppysolmun löytäminen merkitsee sitä, ettei ylihypättyjä solmuja koskaan edes laiteta kekoon. Jos taas törmätään seinään, naapurille ei löydy seuraajaa, joten se hylätään. Ajatellaan JPS:n pahimmaksi tapaukseksi tilanne, jossa JPS onnistuu karsimaan mahdollisimman pienen määrän naapureita, myös rekursioiden jäädessä mahdollisimman lyhyeksi, jotta juuri minkään solmun yli ei voida hypätä. Kartta olisi siis jonkinlainen tiheä "metsä". Tällöin keon koko ja keko-operaatioiden määrä olisi mahdollisimman suuri, mutta todella lyhyiden rekursioiden takia seuraajalistan etsiminen olisi vakioaikaista, jolloin JPS:n aikavativuus olisi yhä A*:n O((|E| + |V|) log |V|).

Koska pahimmillaan kaikki solmut voivat olla keossa samanaikaisesti, on kaikkien kolmen algoritmin tilavaativuus O(|V|).

## Suorituskykyvertailu

### Yleistä
Algoritmeja vertaillaan graafisen käyttöliittymän avulla, joka näyttää käyttäjälle mm. kuvan kartasta, johon on merkitty suljettuun joukkoon kuuluvat, eli keosta poistetut, solmut keltaisella sekä lyhimmällä polulla olevat solmut punaisella. Muut tavat vertailla algoritmien suorituskykyä on toteutettu erilaisten laskureiden ja järjestelmäkutsujen avulla. Nämä ovat lyhimmän polun pituus, keosta poistettuja solmuja laajennettaessa luodun vieras- tai seuraajalistan keskimääräinen koko, suljettuun joukkoon kuuluvien solmujen osuus, keko-operaatioiden määrä, keon maksimikoko, suoritusaika sekä käytetyn muistin määrä. Kaksi viimeksi mainittua lasketaan keskiarvona koodissa määritellyn näytekoon mukaisesta määrästä peräkkäisiä algoritmin suoriuksia. Kaikki edellä mainitut näkyvät käyttäjälle GUI:ssa.

### Havainnot
Allaolevassa tyypillisessä kartassa, jossa lyhin polku maaliin ei ole alusta saakka täysin selvä, Dijkstra etenee tasaiseti joka suuntaan löytäen maalin, kun lähes kaikki solmut ovat jo suljetussa joukossa. A* taas lähtee etenemään suoraan kohti maalia. Kun se havaitsee että suora reitti ei käy, se lähtee etsimään vaihtoehtoisia polkuja. Kun se jossain vaiheessa saa vainun maalista, loppu on varsin suoraviivainen. Tämä näkyy suljetun joukon huomattavasti pienemmässä koossa. JPS puolestaan yrittää samalla lailla ensin suorinta reittiä, kunnes sekin löytää oikean reitin. Avoimella kartalla se kuitenkin pystyy hyppimään pitkälle, jolloin sen tarvitsee läpikäydä vain alle prosentti kaikista solmuista, mikä tekee siitä nopean.

![AR0011SR-dijkstra](kuvat/AR0011SR-dijkstra.png)
![AR0011SR-astar](kuvat/AR0011SR-astar.png)
![AR0011SR-jps](kuvat/AR0011SR-jps.png)

Todella tiheissä labyrinteissä A*:n heuristiikasta ei ole mitään hyötyä, joten sen suoritusaika on Dijkstran tasoa, mutta JPS osaa hyppiä suorien osuuksien yli.

![maze512-1-0-dijkstra](kuvat/maze512-1-0-dijkstra.png)
![maze512-1-0-astar](kuvat/maze512-1-0-astar.png)
![maze512-1-0-jps](kuvat/maze512-1-0-jps.png)

Ei niin tiheissä labyrinteissä sama juttu Dijkstran ja A*:n osalta. Nyt heuristiikan hyödyttömyys näkyy erityisesti keko-operaatioiden määrässä ja keon maksimikoossa, koska A* joutuu etsimään vaihtoehtoisia reittejä. JPS taas pystyy hyppimään paljon pidempiä matkoja ja on todella nopea.

![maze512-32-0-dijkstra](kuvat/maze512-32-0-dijkstra.png)
![maze512-32-0-astar](kuvat/maze512-32-0-astar.png)
![maze512-32-0-jps](kuvat/maze512-32-0-jps.png)

Kartoissa, jotka ovat tyypiltään tiheitä metsiä, heuristiikka nousee arvoonsa ja A* on todella hyvä. Toisaalta nyt JPS ei pysty karsimaan niin monia naapureita koska sen sääntöihin sopivia hyppysolmuja löytyy joka puolelta. Tämä näkyy JPS:n seuraajalistan koon korkeassa keskiarvossa sekä keko-operaatioiden tavallista korkeammassa määrässä suhteessa A*:een. Tämä taitaa olla yksi JPS:n pahimpia tapauksia.

![random512-10-0-dijkstra](kuvat/random512-10-0-dijkstra.png)
![random512-10-0-astar](kuvat/random512-10-0-astar.png)
![random512-10-0-jps](kuvat/random512-10-0-jps.png)

## Puutteet
Lähtö- ja maalisolmujen valitseminen klikkaamalla karttaa käyttöliittymässä.

## Lähteet
- TiRa-kurssin materiaali.
- [Implementation of A*](http://www.redblobgames.com/pathfinding/a-star/implementation.html)
- [Heuristics](http://theory.stanford.edu/~amitp/GameProgramming/Heuristics.html)
- [Jump Point Search](https://harablog.wordpress.com/2011/09/07/jump-point-search/)
- [Online Graph Pruning for Pathfinding on Grid Maps](http://users.cecs.anu.edu.au/~dharabor/data/papers/harabor-grastien-aaai11.pdf)
- [The JPS Pathfinding System](http://users.cecs.anu.edu.au/~dharabor/data/papers/harabor-grastien-socs12.pdf)
