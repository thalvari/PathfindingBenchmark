# Viikkoraportti 5
- Käytetty tuntimäärä: 18h.
- Tällä viikolla käytin aikaa erityisesti GUI:n toteuttamiseen. Aikaa kului myös koodin refaktorointiin sekä dokumentaatioon.
- Aluksi muutin Solmu-olion roolia siten että solmun indeksin sijaan itse solmuoliota käytetään kaikkialla. Seuraavaksi refaktoroin JPS:ää saaden karsittua noin 50 riviä. Sitten toteutin graafisen käyttöliittymän sekä välikätenä toimivan Wrapper-luokan. Päänvaivaa aiheutti erityisesti graafisten komponenttien muistinkulutus, koska kartan esittämistä varten näitä tarvittiin jopa satojatuhansia. Päädyin lopulta esittämään kartan yhtenä kuvaoliona, jolloin muistiongelmat ratkesivat. Lopuksi tein kartanlukijasta oman luokkansa.
- Opin tällä viikolla erityisesti JavaFX:stä sekä FXML-tiedostoista.
- Seuraavaksi tarkoituksena on hioa koodia lisää, muokata hieman käyttöliittymää ja viimeistellä dokumentaatio.
