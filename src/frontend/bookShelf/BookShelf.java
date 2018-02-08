package frontend.bookShelf;

import backend.listeners.*;
import backend.*;

import java.util.ArrayList;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.logging.Filter;


public class BookShelf extends JFrame{
    public static JScrollPane scrollPane;
    public static JPanel desktop;
    public static FilterPanel filterPanel;
    private JFileChooser chooser;
    public static JMenuBar mainMenuBar;
    public static boolean openViewer = true;
    public static boolean popUpOpen = false;
    public static final int numbCols = 3;
    public static int numbRows;
    public static int windowWidth = 1000;
    public static int windowHeight = 700;
    public static final int iconDimension = 250;
    public static int xDisplacement = 100;
    public static int yDisplacement = 50;

    /**
     * Initialize a bookshelf frame
     */
    public BookShelf() {
        new DisplayBooksPanel(this);
        new MenuBar(this);
        SavedFolders.loadFile("savedFolders.ser",
                "favouriteFolders.ser");
        numbRows = (int) Math.ceil((double)SavedFolders.getSavedFolders().size()/numbCols);
        initComponents();
        pack();
        addMouseListener(new mouseListener(this));
        setBounds( xDisplacement, yDisplacement, windowWidth, windowHeight );
    }

    /**
     * Initialize all the components
     */
    public void initComponents() {

        JPanel main = new JPanel();
        main.setLayout(new BoxLayout(main, BoxLayout.Y_AXIS));
        filterPanel = new FilterPanel();
        desktop = new JPanel();
        mainMenuBar = new JMenuBar();

        setTitle("BookShelf");
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                exitForm();
            }
        });

        addComponentListener(new ComponentListener() {
            public void componentResized(ComponentEvent e) {
                changeLocation();
            }

            @Override
            public void componentMoved(ComponentEvent e) {
                changeLocation();
            }

            @Override
            public void componentShown(ComponentEvent e) {
            }

            @Override
            public void componentHidden(ComponentEvent e) {

            }
        });
        main.add(filterPanel);
        desktop.setOpaque(false);
        desktop.setLayout(new GridBagLayout());
        DisplayBooksPanel.initializeDesktop(FilterPanel.selectedTags);

        scrollPane = new JScrollPane(desktop, ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scrollPane.getVerticalScrollBar().setUnitIncrement(10);
        scrollPane.getHorizontalScrollBar().setUnitIncrement(10);
        scrollPane.getViewport().setOpaque(false);
        main.add(scrollPane);
        add(main);
        MenuBar.initializeMenu();
        setJMenuBar(mainMenuBar);
    }

    /**
     * This method is called when File -> Exit menu item is invoked.
     * It closes the application.
     */
    public void exitMenuItemActionPerformed() {//GEN-FIRST:event_exitMenuItemActionPerformed
        System.exit( 0 );
    }//GEN-LAST:event_exitMenuItemActionPerformed

    /**
     * This method is called when File -> Open menu item is invoked. Saves the folder to the bookshelf
     * It displays a dialog to choose the image file to be opened and displayed.
     */
    public void openMenuItemActionPerformed() {//GEN-FIRST:event_openMenuItemActionPerformed
        chooser = new JFileChooser();
        chooser.setCurrentDirectory(new java.io.File("."));
        chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        chooser.setAcceptAllFileFilterUsed(false);
        // when a file is selected get the file that is currently selected
        if (chooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
            String newFile = chooser.getSelectedFile().toString();
            Folder folder = new Folder(newFile);
            if (!SavedFolders.getSavedFolders().contains(folder)) {
                SavedFolders.addFolder(folder);
                DisplayBooksPanel.clearDesktop();
                DisplayBooksPanel.initializeDesktop(FilterPanel.selectedTags);
            }
        }
        repaint();
        revalidate();
    }

    /**
     * This method is called when the application frame is closed.
     */
    private void exitForm() {//GEN-FIRST:event_exitForm
        System.exit(0);
    }//GEN-LAST:event_exitForm

    /**
     * When the frame changes size or is moved then this method is called and it will update the variables
     */
    private void changeLocation() {
        try {
            xDisplacement = getLocationOnScreen().x;
            yDisplacement = getLocationOnScreen().y;
            windowHeight = getHeight();
            windowWidth = getWidth();
        }
        catch(Exception e) {
        }
    }

    /**
     * Remove the unslected tags from the display panel
     * @param notSelectedTags
     */
    public static void removeUnselectedTags(ArrayList<String> notSelectedTags) {

    }



    public static void main(String[] args) {
        new BookShelf().show();
    }
}
