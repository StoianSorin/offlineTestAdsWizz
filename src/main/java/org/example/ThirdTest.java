package org.example;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileReader;
import java.io.IOException;
import java.util.*;

import static org.junit.Assert.assertEquals;

@SuppressWarnings("unchecked")
public class ThirdTest {
    public void returnNoOfOpportunitiesToInsertAds() throws IOException, ParseException {
        int prerollCounter;
        int newValue;

        Object jsonContent = new JSONParser().parse(new FileReader("src/test/resources/downloads.json"));
        JSONArray podcastEntries = (JSONArray) jsonContent;
        Map<String, Integer> prerollCounterList = new HashMap<>();

        for(Object podcastEntry : podcastEntries) {
            prerollCounter = 0;
            JSONObject podcastSubEntry = (JSONObject) podcastEntry;
            JSONArray opportunities = (JSONArray) podcastSubEntry.get("opportunities");

            // iterating opportunities map
            for (Object opportunity : opportunities){
                JSONObject opportunitySubEntry = (JSONObject) opportunity;
                Map<String, List<String>> positionUrlSegments = ((Map<String, List<String>>) opportunitySubEntry.get("positionUrlSegments"));
                Iterator<Map.Entry<String, List<String>>> positionUrlSegmentsIterator = positionUrlSegments.entrySet().iterator();
                Map.Entry<String, List<String>> positionUrlSegmentsPair = positionUrlSegmentsIterator.next();
                if(positionUrlSegmentsPair.getKey().equals("aw_0_ais.adBreakIndex")){
                    List<String> positionUrlSegmentsValues = positionUrlSegments.get("aw_0_ais.adBreakIndex");
                    for (Object positionUrlSegmentsValue : positionUrlSegmentsValues){
                        if(positionUrlSegmentsValue.equals("preroll")){
                            prerollCounter++;
                        }
                    }
                }
            }
            Map<String, String> downloadIdentifier = ((Map<String, String>) podcastSubEntry.get("downloadIdentifier"));
            // iterating downloadIdentifier Map
            for(Map.Entry<String, String> dodownloadIdentifierPair : downloadIdentifier.entrySet()){
                if(dodownloadIdentifierPair.getKey().equals("showId")){
                    if(!prerollCounterList.containsKey(dodownloadIdentifierPair.getValue())){
                        prerollCounterList.put(dodownloadIdentifierPair.getValue(), prerollCounter);
                    }
                    else {
                        for(Map.Entry<String, Integer> prerollCounterListPair : prerollCounterList.entrySet()){
                            if(prerollCounterListPair.getKey().equals(dodownloadIdentifierPair.getValue())){
                                newValue = prerollCounterListPair.getValue() + prerollCounter;
                                prerollCounterList.put(prerollCounterListPair.getKey(), newValue);
                            }
                        }
                    }
                }
            }
        }
        LinkedHashMap<String, Integer> prerollCounterSortedMap = new LinkedHashMap<>();
        ArrayList<Integer> noOfOpportunitiesList = new ArrayList<>();
        for(Map.Entry<String, Integer> entry : prerollCounterList.entrySet()){
            noOfOpportunitiesList.add(entry.getValue());
        }
        noOfOpportunitiesList.sort(Collections.reverseOrder());
        for(Integer noOfOpportunityValue : noOfOpportunitiesList){
            for(Map.Entry<String, Integer> entry : prerollCounterList.entrySet()){
                if(entry.getValue().equals(noOfOpportunityValue)){
                    prerollCounterSortedMap.put(entry.getKey(), noOfOpportunityValue);
                }
            }
        }
        prerollCounterSortedMap.forEach((key, value) -> System.out.println("Show Id: " + key + ":" + ", Preroll Opportunity Number: " + value));
        for(Map.Entry<String, Integer> entry : prerollCounterSortedMap.entrySet()){
            if(entry.getKey().equals("Stuff You Should Know")){
                assertEquals(40, (int) entry.getValue());
            }
            if(entry.getKey().equals("Who Trolled Amber")){
                assertEquals(40, (int) entry.getValue());
            }
            if(entry.getKey().equals("Crime Junkie")){
                assertEquals(30, (int) entry.getValue());
            }
            if(entry.getKey().equals("The Joe Rogan Experience")){
                assertEquals(10, (int) entry.getValue());
            }
        }
    }
}
