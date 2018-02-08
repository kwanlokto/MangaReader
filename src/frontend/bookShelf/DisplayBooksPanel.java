package frontend.bookShelf;

import backend.listeners.*;
import backend.SavedFolders;
import frontend.reader.*;
import javax.swing.*;
import java.awt.*;
import java.util.*;
import backend.Folder;

public class DisplayBooksPanel extends JPanel{
    private static BookShelf bookShelf;
    private static GridBagConstraints c = new GridBagConstraints();
    private static int numbFolders = 0;
    public static ArrayList<JPanel> panels = new ArrayList<>();
    public static ArrayList<PictureIcon> pictureIcons = new ArrayList<>();

    /**
     * Create a link between the bookshelf and the panel
     * @param bookShelf the bookshelf this panel is linked to
     */
    public DisplayBooksPanel(BookShelf bookShelf) {
        this.bookShelf = bookShelf;
    }

    /**
     * Initialize the desktop
     */
    public static void initializeDesktop(ArrayList<String> filterTags) {
        for (int i = 0; i < BookShelf.numbRows * BookShelf.numbCols; i++) {
            addPanelToGrid(i % BookShelf.numbCols, i / BookShelf.numbCols );
        }
        for (Folder folder : SavedFolders.getSavedFolders()) {
            //If this folder has this tag
            if (filterTags.size() == 0) {
                addImageToGrid(folder);
            }
            else {
                for (String tag : filterTags) {
                    System.out.println("Tag:" + tag);
                    if (folder.getAssociatedTag().getTags().contains(tag)) {
                        addImageToGrid(folder);
                    }
                }
            }

        }
        BookShelf.desktop.addMouseListener(new mouseListener(bookShelf));
        BookShelf.desktop.repaint();
        BookShelf.desktop.revalidate();
    }

    /**
     * Add an image to the grid
     * @param folder
     */
    public static void addImageToGrid(Folder folder){
        // If there aren't enough available panels to use then add a new row of panels
        if (numbFolders == BookShelf.numbCols * BookShelf.numbRows) {
            for (int i = 0; i < BookShelf.numbCols; i++) {
                addPanelToGrid(i, BookShelf.numbRows);
            }
            BookShelf.numbRows++;
        }

        PictureIcon pictureLabel = new PictureIcon(folder);
        pictureIcons.add(pictureLabel);
        panels.get(numbFolders).add(pictureLabel);
        String[] folderInfo = folder.getFolder().split("/");

        JLabel fileName = new JLabel(folderInfo[folderInfo.length-1]);
        fileName.setPreferredSize(new Dimension(BookShelf.iconDimension, 15));
        panels.get(numbFolders).add(fileName);
        pictureLabel.addMouseListener(new mouseListener(bookShelf));

        numbFolders += 1;
    }

    /**
     * Clear all items on the desktop
     */
    public static void clearDesktop() {
        BookShelf.numbRows = (int) Math.ceil((double)SavedFolders.getSavedFolders().size()/BookShelf.numbCols);
        BookShelf.desktop.removeAll();
        pictureIcons.clear();
        panels.clear();
        numbFolders = 0;
    }

    /**
     * Add a new panel to the row
     */
    private static void addPanelToGrid(int cols, int rows) {
        c.fill = GridBagConstraints.BOTH;
        c.gridx = cols;
        c.gridy = rows;
        c.insets = new Insets(10,20,10,20);
        JPanel tempPanel = new JPanel();
        tempPanel.setLayout(new BoxLayout(tempPanel, BoxLayout.PAGE_AXIS));
        tempPanel.setOpaque(false);
        panels.add(tempPanel);
        BookShelf.desktop.add(tempPanel, c);
    }


    /**
     * Create the viewer for the current folder at the correct page
     * @param folder the folder which picture is coming from
     * @param startingPage the starting page
     */
    public static void createViewer(Folder folder, int startingPage){
        for (int i = 0; i < pictureIcons.size(); i++) {
            if (pictureIcons.get(i).getFolder().equals(folder)) {
                pictureIcons.get(i).getPictures().goTo(startingPage);
                BookShelf.openViewer = false;
                new Viewer(pictureIcons.get(i).getPictures()).show();
            }
        }
    }
}
