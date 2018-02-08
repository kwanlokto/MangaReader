package backend.sort;

import backend.Folder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.*;
public class sortByName implements sortWays{

    @Override
    public ArrayList<Folder> sort(HashMap<?, ?> hashMap) {
        ArrayList<Folder> sortedList = new ArrayList<>();

        for (Folder key : ((HashMap<Folder, Integer>) hashMap).keySet()) {
            sortedList.add(key);
        }
        Collections.sort(sortedList, new Comparator<Folder>() {
            @Override
            public int compare(Folder o1, Folder o2) {
                return (o1.getFolder()).compareTo( o2.getFolder() );
            }
        });

        return sortedList;
    }
}
