package frontend.bookShelf;
import backend.SavedFolders;
import backend.listeners.*;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.border.BevelBorder;
import javax.swing.*;
import backend.Folder;
public class PopUp extends JLabel{

    private static JPopupMenu popup;
    private static BookShelf bookShelf;
    public static Folder folderClicked;
    public static JMenuItem startNewItem;
    public static JMenuItem deleteFromFavItem;
    public static JMenuItem addToFavItem;
    public static JMenuItem editTagItem;
    public static JMenuItem deleteItem;

    /**
     * Create a new popup
     * @param bookShelf the bookshelf which this popup is linked to
     */
    public PopUp(BookShelf bookShelf) {
        this.bookShelf = bookShelf;
    }

    public static JPopupMenu getPopUp() {
        popup = new JPopupMenu();

        startNewItem = createNewItem("Start New");
        popup.add(startNewItem);

        deleteFromFavItem = createNewItem("Delete from Favourites");
        addToFavItem = createNewItem("Add to Favourites");
        boolean found = SavedFolders.inFavourites(folderClicked);
        if (found) {
            popup.add(deleteFromFavItem);
        }
        else {
            popup.add(addToFavItem);
        }

        deleteItem = createNewItem("Delete");
        popup.add(deleteItem);

        editTagItem = createNewItem("Edit Tag");
        popup.add(editTagItem);

        popup.setLabel("Justification");
        popup.setBorder(new BevelBorder(BevelBorder.RAISED));
        return popup;
    }

    private static JMenuItem createNewItem(String text) {
        JMenuItem item = new JMenuItem(text);
        item.setHorizontalTextPosition(JMenuItem.RIGHT);
        item.addActionListener(new actionListener(bookShelf));
        return item;
    }
}