//package backend.sort;
//import java.util.*;
//import backend.Folder;
//
//public class sortByViews implements sortWays{
//    @Override
//    public ArrayList<Folder> sort(HashMap<?, ?> hashMap) {
//        List<Map.Entry<String, Integer>> list = new LinkedList<Map.Entry<String, Integer>>(((HashMap<Folder, Integer>)hashMap).entrySet());
//        Collections.sort( list, new Comparator<Map.Entry<Folder, Integer>>() {
//            public int compare(Map.Entry<Folder, Integer> o1, Map.Entry<Folder, Integer> o2) {
//                return (o1.getValue()).compareTo( o2.getValue() );
//            }
//        });
//
//        ArrayList<Folder> sortedList = new ArrayList<>();
//        for (Map.Entry<String, Integer> entry : list) {
//            sortedList.add(entry.getKey());
//        }
//
//        return sortedList;
//    }
//}
