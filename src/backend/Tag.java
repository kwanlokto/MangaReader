package backend;

import java.io.Serializable;
import java.util.*;

public class Tag implements Serializable{
    private final static String[] fixedTags = {"Action", "Adventure", "Comedy", "Romance", "Sport"};
    public static ArrayList<String> allTags = new ArrayList<>(Arrays.asList(fixedTags));
    private ArrayList<String> tags = new ArrayList<>();
    private Folder folder;

    /**
     * This tag is a list of tags which is associated with file
     * @param linkedFolder file which this tag list is associated with
     */
    public Tag(Folder linkedFolder) {
        this.folder = linkedFolder;
    }

    /**
     * Add a tag to the list, if it is a valid tag add it if not then don't add it
     * @param newTag tag to add
     * @return whether it was added or not
     */
    public boolean addTag(String newTag) {
        if (tags.contains(newTag)){
            return false;
        }
        tags.add(newTag);
        saveTags();
        return true;
    }

    /**
     * Remove the tag from the list
     * @param removeTag the tag to remove
     */
    public void removeTag(String removeTag) {
        tags.remove(removeTag);
        saveTags();
    }

    /**
     * Get the list of tags
     * @return the list of tags
     */
    public ArrayList<String> getTags() {
        return (ArrayList<String>)this.tags.clone();
    }

    /**
     * Save all the tags linked to this folder
     */
    private void saveTags() {
        SavedFolders.editFolder(this.folder);
    }

    /**
     * Return whether or not the two Tag have the same tags
     * @param compare the comparing tag
     * @return whether they are the same
     */
    public boolean equals(Tag compare) {
        return this.tags.equals(compare.tags);
    }
}
