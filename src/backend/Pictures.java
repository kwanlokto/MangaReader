package backend;
import java.util.*;
import java.io.*;

public class Pictures implements Serializable{
    private ArrayList<String> pictureNames = new ArrayList<>();

    private static final ArrayList<String> extPrefs = new ArrayList<>(){{
        add("jpg");
        add("jpeg");
        add("png");
        add("bmp");
        add("gif");
        add("JPG");
        add("JPEG");
        add("PNG");
        add("BMP");
        add("GIF");
    }};
    private int index;
    private Folder directory;
    public static FileSearcher fileSearcher = new FileSearcher(extPrefs);

    /**
     * Initialize a Pictures class which holds all the pictures under the datapath directory
     */
    public Pictures(Folder dataPath){
        index = 0;
        directory = dataPath;
        lookForAllPictures(dataPath);
    }

    /**
     * Find all pictures under that directory
     * @param dataPath
     */
    private void lookForAllPictures(Folder dataPath) {

        File file = new File(dataPath.getFolder());
        if (!file.exists()) {
            SavedFolders.removeFolder(dataPath);
        }

        // code to filter out some of the unnecessary files
        pictureNames = fileSearcher.getFileNames(file, 0);
        Collections.sort(pictureNames);
    }

    /**
     * Change the current index depending on the delta
     * @param change the delta value
     */
    public void changeIndex(int change) {
        index += change;
        if (index == pictureNames.size()) {
            index = 0;
        }
        else if (index == -1) {
            index = pictureNames.size() - 1;
        }
        SavedFolders.changePageNumb(this.directory, this.index);
    }

    /**
     * Get the current picture
     * @return the pictureNames
     */
    public String getPic() {
        if (pictureNames.size() == 0) return null;
        return pictureNames.get(index);
    }

    public void goTo(int index) {
        if (this.index < pictureNames.size() && this.index >= 0) {
            this.index = index;
            SavedFolders.changePageNumb(this.directory, this.index);
        }
    }

    public boolean goTo(String location) {
        int low = 0;
        int high = pictureNames.size() - 1;

        while(high >= low) {
            int middle = (low + high) / 2;
            if(pictureNames.get(middle).equals(location)) {
                goTo(middle);
                return true;
            }
            if(pictureNames.get(middle).compareTo(location) < 0) {
                low = middle + 1;
            }
            if(pictureNames.get(middle).compareTo(location) > 0) {
                high = middle - 1;
                }
            }
        return false;
    }

    public int getIndex() {
        return this.index;
    }

    public String getFirstPic() {
        if (pictureNames.size() == 0) {
            return null;
        }
        return pictureNames.get(0);
    }

    public void goToNextFolder() {
        goToNextOrPreviousFolder(1);
    }

    public void goToPreviousFolder() {
        goToNextOrPreviousFolder(-1);
        goToBeginningOfFolder();
    }

    private void goToNextOrPreviousFolder(int direction) {
        String[] tempArray = getPic().split("/");
        String currentDir = String.join("/", Arrays.copyOfRange(tempArray, 0, tempArray.length - 1));
        String compareDir;

        int newIndex = getIndex();
        int counter = newIndex;
        changeIndex(direction);
        do {
            counter += direction;
            String[] compareArray = getPic().split("/");
            compareDir = String.join("/", Arrays.copyOfRange(compareArray, 0, compareArray.length - 1));
            if (!compareDir.equals(currentDir)) {
                newIndex = getIndex(); // get the index at which there is a difference in directories
            }
            changeIndex(direction);
        } while (counter < pictureNames.size() && counter >= 0 && compareDir.equals(currentDir));
        goTo(newIndex);
    }

    private void goToBeginningOfFolder() {
        goToNextOrPreviousFolder(-1);
        changeIndex(1);
    }

    public String getDirectory() {
        return this.directory.getFolder();
    }
}
