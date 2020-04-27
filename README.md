# Projekt do zarządzania pracami na mapie.
Projekt wykorzystuje Google Sheets do zbierania danych z arkusza kalkulacyjnego, gdzie w specyficzny
sposób są oznaczone komórki np. zielony kolor komórki oznacza "praca została zakończona".
W dwóch kolumnach występują linki do dysku oraz lokalizacji google.
Projekt ma za zadanie wyciągnąć z komórek "lokalizacji" link oraz przekonwertować go na współrzędne.

Poniżej znajduje się skrypt usługi Google Apps, dzięki któremu będziemy mogli pobrać dane z komórek
i wysłać je w postaci JSONa.

```
function doGet(e) {
 var ss = SpreadsheetApp.openByUrl("<PASTE_URL_GOOGLE_SHEETS>");
 var sheet = ss.getSheetByName("Robione");

 var b2Color = [];
 var range = sheet.getRange("B1:B");
 var bgColors = range.getBackgrounds();
for (var i in bgColors) {
     b2Color[i] = bgColors[i][0];
//     Logger.log(bgColors[i][0]);
}

 var eColor = [];
 var range = sheet.getRange("E1:E");
 var bgColors = range.getBackgrounds();
for (var i in bgColors) {
     eColor[i] = bgColors[i][0];
//     Logger.log(bgColors[i][0]);
}

 var urlDysk = [];
 var range = sheet.getRange("F1:F");
 var bgColors = range.getFormulas();
for (var i in bgColors) {
   urlDysk[i] = bgColors[i][0].slice(12).split("\"")[0];
//   Logger.log(urlDysk[i]);
}

 var urlLocation = [];
 var range = sheet.getRange("H1:H");
 var bgColors = range.getFormulas();
for (var i in bgColors) {
   urlLocation[i] = bgColors[i][0].slice(12).split("\"")[0];
}

  var jo = {};
  var dataArray = [];

  var rows = sheet.getRange(1,1,sheet.getLastRow()-1, sheet.getLastColumn()).getValues();

  for(var i = 0, l= rows.length; i<l ; i++){
    var dataRow = rows[i];
    var record = {};
    record['id'] = i+1;
    record['position'] = dataRow[0];
    record['name'] = dataRow[1];
    record['status'] = dataRow[2];
    record['date'] = dataRow[3];
    record['comments'] = dataRow[4];
    record['UrlDysk'] = urlDysk[i];
    record['UrlLocation'] = urlLocation[i];
    record['nameColor'] = b2Color[i];
    record['CommentsColor'] = eColor[i];
    dataArray.push(record);

  }

  jo.user = dataArray;
  var result = JSON.stringify(jo);
  return ContentService.createTextOutput(result).setMimeType(ContentService.MimeType.JSON);

}
```
## Pierwsze kroki
Przed uruchomieniem powinniśmy dodać odpowiednie ścieżki w zmiennych środowiskowych.
* spring.datasource.username=${DB_LOGIN}
* spring.datasource.password=${DB_PASSWORD}
* urlCSV=${URL_CSV_GOOGLE}
* urlJson=${URL_JSON_GOOGLE}
