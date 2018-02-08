package backend;
import backend.sort.*;

import java.util.*;

public class SavedFolders{

    private static HashMap <Folder, Integer> savedFolders = new HashMap<>();
    private static HashMap <Folder, Integer> favouriteFolders = new HashMap<>();
    private static String savedPath;
    private static String favouritePath;

    /**
     * Get all the information from the serialized file (all the datapaths)
     */
    public static void loadFile(String sPath, String fPath){
        savedPath = sPath;
        favouritePath = fPath;
        savedFolders = (HashMap<Folder, Integer>) SerializableOperator.readFile(savedPath);
        favouriteFolders = (HashMap<Folder, Integer>) SerializableOperator.readFile(favouritePath);
    }

    /**
     * Add folder to saved folders
     * @param pictureFolder the folder to add
     */
    public static void addFolder(Folder pictureFolder) {
        savedFolders.put(pictureFolder,0);
        SerializableOperator.saveFile(savedPath, savedFolders);
    }

    /**
     * Get all the folders (i.e. all the folders presently see)
     * @return an ArrayList<Folder>
     */
    public static ArrayList<Folder> getSavedFolders() {
        return (new sortByName()).sort((HashMap<?, ?>) savedFolders.clone());
    }

    /**
     * Remove folder from the saved folders list (i.e. all the folders presently seen)
     * @param folder the folder to remove
     */
    public static void removeFolder(Folder folder) {
        savedFolders.remove(folder);
        removeFromFavourites(folder);
        SerializableOperator.saveFile(savedPath, savedFolders);
    }

    /**
     * Add the folder to the favourite list
     * @param pictureFolder the folder to add
     */
    public static void addToFavourites(Folder pictureFolder) {

        for (Folder key: savedFolders.keySet()) {
            if (key.equals(pictureFolder)) {
                favouriteFolders.put(key, savedFolders.get(key));
            }
        }
        SerializableOperator.saveFile(favouritePath, favouriteFolders);
    }

    /**
     * Remove the folder from the favourite list
     * @param pictureFolder the folder to remove
     */
    public static void removeFromFavourites(Folder pictureFolder) {
        for (Folder key: favouriteFolders.keySet()) {
            if (key.equals(pictureFolder)) {
                favouriteFolders.remove(key);
            }
        }

        SerializableOperator.saveFile(favouritePath, favouriteFolders);
    }

    /**
     * Return the list of all favourite folders
     * @return return an ArrayList<Folder> of the favourite folders
     */
    public static ArrayList<Folder> getFavouriteFolders() {
        return (new sortByName()).sort((HashMap<?, ?>) favouriteFolders.clone());
    }

    /**
     * Change the pageNumber linked to the folder
     * @param folder the folder we are using
     * @param pageNumb the new pageNumber
     */
    public static void changePageNumb(Folder folder, int pageNumb) {

        for (Folder key : savedFolders.keySet()) {
            if (key.equals(folder)) {
                savedFolders.put(key, pageNumb);
            }
        }
        for (Folder key : favouriteFolders.keySet()) {
            if (key.equals(folder)) {
                favouriteFolders.put(key, pageNumb);
            }
        }
        SerializableOperator.saveFile(savedPath, savedFolders);
        SerializableOperator.saveFile(favouritePath, favouriteFolders);
    }

    /**
     * Get the page number of the folder
     * @param folder the folder you are lookign for
     * @return the page number
     */
    public static int getPageNumb(Folder folder) {
        for (Folder key : savedFolders.keySet()) {
            if (key.equals(folder)) {
                return savedFolders.get(key);
            }
        }
        return 0;
    }

    /**
     * Get the folder linked to this datapath
     * @param dataPath the datapath to this folder
     * @return the folder
     */
    public static Folder getFolder(String dataPath) {
        for (Folder key : savedFolders.keySet()) {
            if (key.getFolder().equals(dataPath)) {
                return key;
            }
        }
        return null;
    }

    /**
     * Return whether or not the favourite folder has this folder
     * @param compareFolder the folder we are checking if it exists
     * @return whether it exists or not
     */
    public static boolean inFavourites(Folder compareFolder) {
        for (Folder folder : SavedFolders.getFavouriteFolders()) {
            if (folder.equals(compareFolder)) {

                return true;
            }
        }
        return false;
    }

    /**
     * Edit the folder
     * @param folderToEdit the folder we want to edit
     */
    public static void editFolder(Folder folderToEdit) {
        for (Folder folder : savedFolders.keySet()) {
            if (folder.equals(folderToEdit)) {
                Integer page = savedFolders.get(folder);
                savedFolders.remove(folder);
                savedFolders.put(folderToEdit,page);
            }
        }

        for (Folder folder : favouriteFolders.keySet()) {
            if (folder.equals(folderToEdit)) {
                Integer page = favouriteFolders.get(folder);
                favouriteFolders.remove(folder);
                favouriteFolders.put(folderToEdit,page);
            }
        }

        SerializableOperator.saveFile(savedPath, savedFolders);
        SerializableOperator.saveFile(favouritePath, favouriteFolders);
    }

    /**
     * Get all Folders which have the tags, tags.
     * @param tags the tags which we are looking for
     * @return all associated Folders
     */
    public static ArrayList<Folder> getTagFolder(Tag tags) {
        ArrayList<Folder> tagFolder = getSavedFolders();
        for (int i = tagFolder.size() - 1; i >= 0; i --) {
            if (!tagFolder.get(i).getAssociatedTag().equals(tags)) {
                tagFolder.remove(i);
            }
        }
        return tagFolder;
    }
}
