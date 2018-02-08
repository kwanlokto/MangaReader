package backend.listeners;

import frontend.bookShelf.*;
import backend.SavedFolders;

import javax.swing.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import backend.Folder;
public class mouseListener implements MouseListener {
    private BookShelf bookShelf;


    public mouseListener(BookShelf bookShelf) {
        new PopUp(bookShelf);
        this.bookShelf = bookShelf;
    }
    @Override
    public void mouseClicked(MouseEvent e) {
        if (DisplayBooksPanel.pictureIcons.size() != 0) {
            for (int i = 0; i < DisplayBooksPanel.pictureIcons.size(); i++) { //iterate through all panels till the icon clicked is found
                if (e.getSource() == DisplayBooksPanel.pictureIcons.get(i)) {
                    if (SwingUtilities.isRightMouseButton(e)) {
                        rightButtonClicked(i, e);
                    }
                    else if (SwingUtilities.isLeftMouseButton(e)) {
                        leftButtonClicked(i);
                    }
                }
            }
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    private void rightButtonClicked(int i, MouseEvent e) {
        PopUp.folderClicked = SavedFolders.getSavedFolders().get(i);
        PopUp.getPopUp().show(bookShelf,
                e.getXOnScreen() - BookShelf.xDisplacement,
                e.getYOnScreen() - BookShelf.yDisplacement);
        BookShelf.popUpOpen = true;
    }

    private void leftButtonClicked(int i) {
        if (BookShelf.openViewer && !BookShelf.popUpOpen) {
            Folder folder = SavedFolders.getSavedFolders().get(i);
            DisplayBooksPanel.createViewer(folder, SavedFolders.getPageNumb(folder));
        }

        else {
            BookShelf.popUpOpen = false;
        }
    }
}
