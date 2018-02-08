package backend;

import java.io.Serializable;
import java.util.ArrayList;

public class Folder implements Serializable{

    private String folder;
    private Pictures pictures;
    private Tag tag;

    public Folder(String linkedFolder) {
        this.folder = linkedFolder;
        this.pictures = new Pictures(this);
        this.tag = new Tag(this);
    }

    public String getFolder() {
        return folder;
    }

    public Pictures getPictures() {
        return pictures;
    }

    public Tag getAssociatedTag() {
        return tag;
    }

    public boolean equals(Folder obj) {
        return this.getFolder().equals(obj.getFolder());
    }
}
