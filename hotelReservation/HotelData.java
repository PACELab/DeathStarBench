
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.ParseException;
import java.util.Random;

public class HotelData {
    public static void main(String[] args) throws IOException, ParseException {
        BufferedReader csvReader = new BufferedReader(new FileReader("/HotelReservation/data.csv"));
        FileWriter hotelWriter = new FileWriter("/HotelReservation/hotels.json");
        FileWriter localesWriter = new FileWriter("/HotelReservation/locales.json");
        FileWriter inventoryWriter = new FileWriter("HotelReservation/inventory.json");
        FileWriter geoWriter = new FileWriter("/HotelReservation/geo.json");

        String row;
        int hotelNum = 7;
        double lat,lon;
        while ((row = csvReader.readLine()) != null) {
            String[] data = row.split(",");
            if (hotelNum == 7) {
                hotelNum++;
                continue;
            }
            if(!data[17].isEmpty() && !data[18].isEmpty()){
               lat  = Double.valueOf(data[17]);
               lon = Double.valueOf(data[18]);
            }else{
                lat = -37.8014;
                lon = 144.9958;
            }

            writeToGeo(data, hotelNum, geoWriter,lat,lon);
            writeToHotel(data,hotelNum,hotelWriter,lat,lon);
            writeToLocale(hotelNum,localesWriter);
            writeToInventory(data,hotelNum,inventoryWriter);
            // do something with the data
            hotelNum++;
        }

        geoWriter.flush();
        geoWriter.close();
        localesWriter.flush();
        localesWriter.close();
        hotelWriter.flush();
        hotelWriter.close();
        inventoryWriter.flush();
        inventoryWriter.close();
        csvReader.close();

    }


    public static void writeToGeo(String[] data, int hotelNum, FileWriter geoWriter,double lat,double lon) throws IOException {
        JSONObject geoObj = new JSONObject();

        geoObj.put("hotelId", hotelNum);
        geoObj.put("lat", lat);
        geoObj.put("lon", lon);
        geoWriter.write(geoObj.toString());
        geoWriter.write(",");
    }

    public static void writeToHotel(String[] data, int hotelNum, FileWriter hotelWriter,double lat,double lon) throws IOException {
        JSONObject hotelObj = new JSONObject();
        hotelObj.put("id",hotelNum);
        hotelObj.put("name","Cliff"+hotelNum);
        hotelObj.put("phoneNumber","(415) 775-4700");
        hotelObj.put("description","Just another hotel");
        JSONObject addressObj = new JSONObject();
        String[] address = data[1].split(" ");
        addressObj.put("streetNumber", address[0]);
        addressObj.put("streetName", address[1]+address[2]);
        addressObj.put("city", data[19]);
        addressObj.put("state", "Melbourne");
        addressObj.put("country", "Australia");
        addressObj.put("postalCode", "3000");
        addressObj.put("lat", lat);
        addressObj.put("lon",lon);
        hotelObj.put("address",addressObj);
        hotelWriter.write(hotelObj.toString());
        hotelWriter.write(",");
    }
    public static void writeToLocale(int hotelNum, FileWriter localeWriter) throws IOException {
        JSONObject localObj = new JSONObject();
        localObj.put("hotelId", hotelNum);
        localObj.put("description", "Its a locale");
        localObj.put("locale", "en");
        localeWriter.write(localObj.toString());
        localeWriter.write(",");
    }
    /*
    * {
        "hotelId": "1",
        "code": "RACK",
        "inDate": "2015-04-09",
        "outDate": "2015-04-10",
        "roomType": {
          "bookableRate": 109.00,
          "code": "KNG",
          "description": "King sized bed",
          "totalRate": 109.00,
          "totalRateInclusive": 123.17
        }
    * */
    public static void writeToInventory(String[] data, int hotelNum, FileWriter inventoryWriter) throws IOException, ParseException {

        JSONObject inventoryObj = new JSONObject();
        inventoryObj.put("hotelId",hotelNum);
        inventoryObj.put("code","RACK");
        Random random = new Random();
        int inDate = random.nextInt(23) ;
        int outDate=-1;
        while(outDate  <= inDate){
            outDate = random.nextInt(24);
        }

        if(inDate <= 9){
            inventoryObj.put("inDate", "2015-04-0"+inDate);
        }else{
            inventoryObj.put("inDate", "2015-04-"+inDate);
        }
        if(outDate <= 9){
            inventoryObj.put("outDate", "2015-04-0"+outDate);
        }else{
            inventoryObj.put("outDate", "2015-04-"+outDate);
        }

        JSONObject roomType = new JSONObject();

        roomType.put("code", "RACK");
        roomType.put("description", "hotel");
        if(data[4].isEmpty()){
            roomType.put("bookableRate", 109.00);
            roomType.put("totalRate",109.00 );
            roomType.put("totalRateInclusive", 113.00);
        }else{
            roomType.put("bookableRate",  Double.valueOf(data[4])/10000);
            roomType.put("totalRate", Double.valueOf(data[4])/10000 );
            roomType.put("totalRateInclusive",  Double.valueOf(data[4])/10000 + 20);
        }
        inventoryObj.put("roomType",roomType);
        inventoryWriter.write(inventoryObj.toString());
        inventoryWriter.write(",");

    }
}
