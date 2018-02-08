package frontend.bookShelf;
import backend.listeners.*;
import javax.swing.*;

public class MenuBar {
    public static JMenuItem exitMenuItem = new JMenuItem();
    public static JMenu fileMenu = new JMenu();
    public static JSeparator jSeparator1 = new JSeparator();
    public static JMenuItem openMenuItem = new JMenuItem();
    private static BookShelf bookShelf;

    /**
     * Initialize a MenuBar liking it to the corresponding bookshelf
     * @param bookShelf the linked bookshelf
     */
    public MenuBar(BookShelf bookShelf) {
        this.bookShelf = bookShelf;
    }

    /**
     * Create the menu
     */
    public static void initializeMenu(){
        fileMenu.setText("File");

        openMenuItem.setAccelerator(
                javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_O, java.awt.event.InputEvent.CTRL_MASK));
        openMenuItem.setText("Add Manga");
        openMenuItem.addActionListener(new actionListener(bookShelf));

        exitMenuItem.setText("Exit");
        exitMenuItem.addActionListener(new actionListener(bookShelf));

        fileMenu.add(openMenuItem);
        fileMenu.add(jSeparator1);
        fileMenu.add(exitMenuItem);

        BookShelf.mainMenuBar.add(fileMenu);

        BookShelf.mainMenuBar.repaint();
        BookShelf.mainMenuBar.revalidate();
    }
}
