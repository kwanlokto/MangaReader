package backend.listeners;

import frontend.bookShelf.*;
import backend.SavedFolders;
import frontend.bookShelf.MenuBar;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.logging.Filter;

public class actionListener implements ActionListener{
    private BookShelf bookShelf;
    public actionListener(BookShelf bookShelf) {
        this.bookShelf = bookShelf;
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == MenuBar.openMenuItem) {
            bookShelf.openMenuItemActionPerformed();
        }
        else if (e.getSource() == MenuBar.exitMenuItem) {
            bookShelf.exitMenuItemActionPerformed();
        }

        else if (e.getSource() == PopUp.deleteItem) {
            SavedFolders.removeFolder(PopUp.folderClicked);

            DisplayBooksPanel.clearDesktop();
            DisplayBooksPanel.initializeDesktop(FilterPanel.selectedTags);

            bookShelf.revalidate();
            bookShelf.repaint();
        }

        else if (e.getSource() == PopUp.deleteFromFavItem) {
            SavedFolders.removeFromFavourites(PopUp.folderClicked);
            DisplayBooksPanel.clearDesktop();
            DisplayBooksPanel.initializeDesktop(FilterPanel.selectedTags);

            bookShelf.revalidate();
            bookShelf.repaint();
        }

        else if (e.getSource() == PopUp.addToFavItem) {
            SavedFolders.addToFavourites(PopUp.folderClicked);
            DisplayBooksPanel.clearDesktop();
            DisplayBooksPanel.initializeDesktop(FilterPanel.selectedTags);

            bookShelf.revalidate();
            bookShelf.repaint();
        }

        else if (e.getSource() == PopUp.startNewItem) {
            String datapath = PopUp.folderClicked.getFolder();
            DisplayBooksPanel.createViewer(SavedFolders.getFolder(datapath), 0);
        }

        else if (e.getSource() == PopUp.editTagItem) {
            new addTagFrame(PopUp.folderClicked).show();

        }
    }
}
