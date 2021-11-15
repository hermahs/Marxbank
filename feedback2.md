# Tilbakemelding på innlevering 2

- litt lettvint domene, når det brukes som eksempel og øving i oo-prog
- dere har jo tilført en del, men det virker nesten litt overdrevent komplisert...

## Bygging

- testene kjørte greit
- fikk advarsel om at fxml-formatet var nyere enn fxml-versjonen som brukes, det kan potensielt være en kilde til feil

## Dokumentasjon

- diagrammer skal være en abstraksjon, her har dere tatt med for mange detaljer. mange ser ut til å vøære direkte generert fra koden (og i svært liten grad redigert etterpå), og det blir for lettvint (leseren av koden kan like godt gjøre det selv)

## Design

- det så funksjonelt og omfangsrikt ut 

## Kodegjennomgang

### Account

- hva trenger dere id til, når dere har accountNumber?
- generateAccountNumber burde være protected, den er vel ikke men å skulle kalles "utenfra"
- konstruktørene kan også være protected, for å markere at de bare brukes i subklasser

### AccountType

- ser ikke helt hensikten, når det er en en-til-en-sammenheng med klassene

### Bank

- bankInstance: singleton-objekter bør man unngå!

### Transaction

- hva trenger dere id til, objektene har jo identitet?!
- dateString: bør unngå duplisering av informasjon
- bruk en ordentlig tidsklasse i stedet for String i konstruktøren
- commitTransaction: valideringen burde vært gjort i konstruktøre (generelt så tidlig som mulig). hva skjer om receiver utløser unntak, da blir transaksjonen halvveis utført
- kanskje er det mest logisk at det er banken som utfører transaksjonen

### AccountFactory

- humor er bra, men stalin er likevel ikke et bra modulnavn ;-)
- når dere først har AccountType, så burde dere bruke det her, i stedet for String!
- bruk den nye switch-syntaksen i stedet for if med mange greiner
- kunne nesten like gjerne hatt disse metodene i AccountType

### SavingsAccountTest

- assertAll visste jeg ikke om!

### DataManager

- dataInstance: singleton-objekter bør man unngå!

### Array*Serializer

- disse klassene er hjelpeklasser for ObjectMapper, så det virker galt å instansiere ObjectMapper og SimpleModule inni den.

### *Wrapper

- jeg skjønner hvordan de brukes, men jeg klarer ikke å se at de gjør koden ryddigere eller bedre

### DataHandler

- ObjectMapper og SimpleModule er relativt dyre objekter som bør gjengrukes på tvers av kall

### DataManager

- checkIfTransactionExists(Transaction): så vidt jeg kan se så brukes ikke denne noe sted

### ChangePasswordController

- har aldri sett at feilmeldinger gis ved å endre teksten i en knapp, tror ikke det er noen god idé.

### CreateNewAccountController

- handleCreateAccount: sørg heller for at knappen er unaktiv, hvis skjema ikke er fylt inn riktig

### MainController (og en del andre Controller-klasser)

- mye duplisert kode i metodene for å skifte panel
- unngå å lese inn samme panel mer enn én gang (med mindre du trenger flere instanser samtidig)
