package frontend.reader;
import backend.SavedFolders;
import backend.listeners.*;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.border.BevelBorder;
import javax.swing.*;

public class ViewerPopUp extends JLabel{
    private Viewer viewer;
    private static JPopupMenu popup;
    public static JMenuItem goToNextFolder;
    public static JMenuItem goToPreviousFolder;
    public static JMenuItem goToFolder;
    public ViewerPopUp(Viewer viewer) {
        this.viewer = viewer;
    }

    public JPopupMenu getPopUp() {
        popup = new JPopupMenu();
        goToNextFolder = createNewItem("Next Folder");
        popup.add(goToNextFolder);

        goToPreviousFolder = createNewItem("Previous Folder");
        popup.add(goToPreviousFolder);

        goToFolder = createNewItem("Go To");
        popup.add(goToFolder);

        popup.setLabel("Justification");
        popup.setBorder(new BevelBorder(BevelBorder.RAISED));
        return popup;
    }

    private JMenuItem createNewItem(String text) {
        JMenuItem item = new JMenuItem(text);
        item.setHorizontalTextPosition(JMenuItem.RIGHT);
        item.addActionListener(viewer);
        return item;
    }
}