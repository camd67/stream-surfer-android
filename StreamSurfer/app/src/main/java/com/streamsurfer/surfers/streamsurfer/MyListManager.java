package com.streamsurfer.surfers.streamsurfer;

import android.content.Context;
import android.util.Log;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class MyListManager {
    private static final String FILENAME = "myListData.dat";
    private List<ListEntry> myList;
    private static final String TAG = "MyListManager";
    private static final String DATA_SEPARATOR = ";";


    public MyListManager(Context context) {
        myList = new ArrayList<>();
        loadMyListData(context);
    }

    private void loadMyListData(Context context){
        File dataFile = new File(context.getFilesDir(), FILENAME);
        if(!dataFile.exists()){
            return;
        }

        try{
            BufferedReader reader = new BufferedReader(new FileReader(dataFile));
            String line = reader.readLine();
            while(line != null){
                String[] data = line.split(DATA_SEPARATOR);
                ListEntry entry = new ListEntry(data[0], data[1], data[2], data[3], data[4], data[5]);
                myList.add(entry);
                line = reader.readLine();
            }
        } catch (IOException ex) {
            Log.e(TAG, "loadMyListData: IO error reading data");
        }
    }

    public void saveMyListData(Context context){
        StringBuilder output = new StringBuilder();
        for(int i = 0; i < myList.size(); i++){
            ListEntry e = myList.get(i);
            output.append(e.getTitle());
            output.append(DATA_SEPARATOR);
            output.append(e.getStatus().toString());
            output.append(DATA_SEPARATOR);
            output.append(e.getEpsSeen());
            output.append(DATA_SEPARATOR);
            output.append(e.getTotalEps());
            output.append(DATA_SEPARATOR);
            output.append(e.getRating());
            output.append(DATA_SEPARATOR);
            output.append(e.getFilename());
            output.append("\n");
        }
        File dataFile = new File(context.getFilesDir(), FILENAME);
        try {
            FileWriter writer = new FileWriter(dataFile);
            writer.write(output.toString());
            writer.close();
        } catch (IOException ex){
            Log.e(TAG, "Error saving myList data");
        }
    }

    public boolean listContainsTitle(String title){
        for (ListEntry e: myList) {
            if(e.getTitle().equals(title)){
                return  true;
            }
        }
        return false;
    }

    public void addEntryToList(ListEntry toAdd){
        myList.add(toAdd);
    }

    public ListEntry getListEntryAt(int index){
        return  myList.get(index);
    }

    public ListEntry[] getAllEntries(){
        ListEntry[] toReturn = new ListEntry[myList.size()];
        return myList.toArray(toReturn);
    }
}
