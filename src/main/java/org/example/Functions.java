package org.example;

import java.util.List;

public class Functions {
    public String returnNameOfTheMostVisibleElement(List<String> list){
        int length = list.size();
        int max_count = 0;
        String max_freq = " ";

        for(String string : list){
            int count = 0;
            for (int  j=0; j<length; j++){
                if(string.equals(list.get(j))){
                    count++;
                }
            }
            if (count > max_count){
                max_count = count;
                max_freq = string;
            }
        }
        return max_freq;
    }

    public int returnNumberOfAppearancesForTheMostVisibleElement(List<String> list){
        int length = list.size();
        int max_count = 0;

        for(String string : list){
            int count = 0;
            for (int  j=0; j<length; j++){
                if(string.equals(list.get(j))){
                    count++;
                }
            }
            if (count > max_count){
                max_count = count;
            }
        }
        return max_count;
    }
}
