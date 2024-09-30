package org.example;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.*;

import static org.junit.Assert.assertEquals;

@SuppressWarnings("unchecked")
public class FourthTest {
    public void returnWeeklyShows() throws IOException, ParseException {

        Object jsonContent = new JSONParser().parse(new FileReader("src/test/resources/downloads.json"));
        JSONArray podcastEntries = (JSONArray) jsonContent;

        Map<String, List<Timestamp>> frequencyCounterList = new HashMap<>();
        boolean checkIfPodcastIsWeekly;
        System.out.println("Weekly shows are: ");

        for (Object podcastEntry : podcastEntries) {
            JSONObject podcastSubEntry = (JSONObject) podcastEntry;
            JSONArray opportunities = (JSONArray) podcastSubEntry.get("opportunities");

            //iterating opportunites map
            JSONObject opportunitiesEntry = (JSONObject) opportunities.getFirst();
            Timestamp originalEventTime = new Timestamp((Long) opportunitiesEntry.get("originalEventTime"));

            Map<String, String> downloadIdentifier = ((Map<String, String>) podcastSubEntry.get("downloadIdentifier"));
            // iterating downloadIdentifier Map
            for (Map.Entry<String, String> downloadIdentifierPair : downloadIdentifier.entrySet()) {
                if (downloadIdentifierPair.getKey().equals("showId")) {
                    if (!frequencyCounterList.containsKey(downloadIdentifierPair.getValue())) {
                        List<Timestamp> timestamps = new ArrayList<>();
                        timestamps.add(originalEventTime);
                        frequencyCounterList.put(downloadIdentifierPair.getValue(), timestamps);
                    } else {
                        for (Map.Entry<String, List<Timestamp>> frequencyCounterListPair : frequencyCounterList.entrySet()) {
                            if (frequencyCounterListPair.getKey().equals(downloadIdentifierPair.getValue())) {
                                List<Timestamp> listTimestamp = frequencyCounterList.get(downloadIdentifierPair.getValue());
                                listTimestamp.add(originalEventTime);
                                frequencyCounterList.put(frequencyCounterListPair.getKey(), listTimestamp);
                            }
                        }
                    }
                }
            }
        }

        for (Map.Entry<String, List<Timestamp>> frequencyCounterListPair : frequencyCounterList.entrySet()) {
            List<Timestamp> listTimestamp = frequencyCounterListPair.getValue();
            checkIfPodcastIsWeekly = true;
            for (int i = 1; i < listTimestamp.size(); i++) {
                if ((listTimestamp.get(i).getTime() - listTimestamp.get(i - 1).getTime()) % 168000 != 0) {
                    checkIfPodcastIsWeekly = false;
                    break;
                }
            }

            if (checkIfPodcastIsWeekly) {
                Date date = new Date(listTimestamp.getLast().getTime());
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("EEEE");
                simpleDateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
                String formattedDay = simpleDateFormat.format(date);

                SimpleDateFormat simpleDateFormat1 = new SimpleDateFormat("HH:mm");
                simpleDateFormat1.setTimeZone(TimeZone.getTimeZone("UTC"));
                String formattedHour = simpleDateFormat1.format(date);

                System.out.println(frequencyCounterListPair.getKey() + " - " + formattedDay.substring(0, 3) + " " + formattedHour);
                if (frequencyCounterListPair.getKey().equals("Stuff You Should Know")) {
                    assertEquals("Wed", formattedDay.substring(0, 3));
                    assertEquals("22:00", formattedHour);
                }
                if (frequencyCounterListPair.getKey().equals("Who Trolled Amber")) {
                    assertEquals("Mon", formattedDay.substring(0, 3));
                    assertEquals("20:00", formattedHour);
                }
                if (frequencyCounterListPair.getKey().equals("Crime Junkie")) {
                    assertEquals("Wed", formattedDay.substring(0, 3));
                    assertEquals("22:00", formattedHour);
                }
                if (frequencyCounterListPair.getKey().equals("The Joe Rogan Experience")) {
                    assertEquals("Wed", formattedDay.substring(0, 3));
                    assertEquals("22:00", formattedHour);
                }
            }
        }
    }
}
