package org.example;

import org.json.simple.parser.ParseException;

import java.io.IOException;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) throws IOException, ParseException {

        FirstTest firstTest = new FirstTest();
        firstTest.returnTheMostDownloadedPodcast();
        System.out.println(" ");

        SecondTest secondTest = new SecondTest();
        secondTest.returnTheMostUsedDevice();
        System.out.println(" ");

        ThirdTest thirdTest = new ThirdTest();
        thirdTest.returnNoOfOpportunitiesToInsertAds();
        System.out.println(" ");


    }
}