package org.example;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;

@SuppressWarnings("unchecked")
public class FirstTest {
    public void returnTheMostDownloadedPodcast() throws IOException, ParseException {
        // parsing file "downloads.json"
        Object jsonContent = new JSONParser().parse(new FileReader("src/test/resources/downloads.json"));

        // typecasting obj to JSONObject
        JSONArray podcastEntries = (JSONArray) jsonContent;

        // declaring a podcastNames list
        List<String> podcastNames = new ArrayList<>();
        for (Object podcastEntry : podcastEntries) {
            JSONObject element = (JSONObject) podcastEntry;
            String city = element.get("city").toString();
            if (city.equals("san francisco")) {
                Map<String, String> downloadIdentifier = (Map<String, String>) element.get("downloadIdentifier");
                // iterating podcastNames map
                for (Map.Entry<String, String> downloadIdentifierObject : downloadIdentifier.entrySet()) {
                    if (downloadIdentifierObject.getKey().equals("showId")) {
                        podcastNames.add(downloadIdentifierObject.getValue());
                    }
                }
            }

        }

        //print the result
        Functions functions = new Functions();
        String podcastName = functions.returnNameOfTheMostVisibleElement(podcastNames);
        int noOfDownloads = functions.returnNumberOfAppearancesForTheMostVisibleElement(podcastNames);
        assertEquals("Who Trolled Amber", podcastName);
        assertEquals(24, noOfDownloads);
        System.out.println("Most popular show is: " + podcastName);
        System.out.println("Number of downloads is: " + noOfDownloads);
    }
}
