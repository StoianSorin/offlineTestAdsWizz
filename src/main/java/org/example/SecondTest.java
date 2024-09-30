package org.example;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class SecondTest {
    public void returnTheMostUsedDevice() throws IOException, ParseException {
        Object jsonContent = new JSONParser().parse(new FileReader("src/test/resources/downloads.json"));

        // typecasting jsonContent to JSONObject
        JSONArray podcastEntries = (JSONArray) jsonContent;

        List<String> deviceTypes = new ArrayList<>();
        for(Object podcastEntry : podcastEntries){
            JSONObject element = (JSONObject) podcastEntry;
            String deviceType = (String) element.get("deviceType");
            // iterating deviceTypes Map
            deviceTypes.add(deviceType);
        }

        Functions functions = new Functions();
        String deviceType = functions.returnNameOfTheMostVisibleElement(deviceTypes);
        int noOfDevicesUsed = functions.returnNumberOfAppearancesForTheMostVisibleElement(deviceTypes);
        assertEquals("mobiles & tablets", deviceType);
        assertEquals(60, noOfDevicesUsed);
        System.out.println("Most popular device is: " + deviceType);
        System.out.println("Number of devices is: " + noOfDevicesUsed);
    }
}
