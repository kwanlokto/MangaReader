package frontend.bookShelf;
import backend.*;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class PictureIcon extends JLabel{

    private Pictures pictures;
    private Folder folder;

    public PictureIcon(Folder folder) {
        this.folder = folder;
        this.pictures = folder.getPictures();

        createPictureIcon();
    }

    /**
     * Create an new pictureIcon to put on the grid. This pictureIcon should be linked to the correct file and
     * have an Icon of the first picture in the file.
     */
    private void createPictureIcon() {
        if (this.pictures.getPic() != null) {
            ImageIcon icon = new ImageIcon(this.pictures.getFirstPic());
            icon = resizeImage(icon, BookShelf.iconDimension, BookShelf.iconDimension); //resize the icon to be the desired size
            setIcon(icon);
        }
        boolean found = SavedFolders.inFavourites(this.folder);
        if (found) {
            setBorder(BorderFactory.createLineBorder(Color.YELLOW));
        }
        else {
            setBorder(BorderFactory.createLineBorder(Color.BLACK)); //black border
        }
        setMinimumSize(new Dimension(BookShelf.iconDimension,BookShelf.iconDimension));
        setPreferredSize(new Dimension(BookShelf.iconDimension,BookShelf.iconDimension));
        setMaximumSize(new Dimension(BookShelf.iconDimension,BookShelf.iconDimension));
    }


    /**
     * Get all the pictures linked with this pictureIcon
     * @return the Pictures linked with this pictureIcon
     */
    public Pictures getPictures() {
        return this.pictures;
    }

    /**
     * Resize the image with the correct sizes
     * @param icon the image to resize
     * @param newWidth the new width of the image
     * @param newHeight the new height of the image
     * @return the new image resized to the desired dimensions
     */
    public static ImageIcon resizeImage(ImageIcon icon, int newWidth, int newHeight) {
        int imageH = icon.getIconHeight();
        int imageW = icon.getIconWidth();
        BufferedImage temp = new BufferedImage(imageW, imageH, BufferedImage.TYPE_INT_ARGB);

        if (imageH * newWidth > imageW * newHeight) {
            imageH = (newWidth * imageH)/imageW;
            imageW = newWidth;
        }
        else {
            imageW = (newHeight * imageW)/imageH;
            imageH = newHeight;
        }
        Image image = icon.getImage();

        Graphics bGr = temp.createGraphics();
        bGr.drawImage(image, 0, 0, imageW, imageH,null, null);
        bGr.dispose();

        int x = imageW/2 - BookShelf.iconDimension/2;
        int y = imageH/2 - BookShelf.iconDimension/2;
        BufferedImage bufferedImage = temp.getSubimage(x,y,BookShelf.iconDimension, BookShelf.iconDimension);
        return new ImageIcon(bufferedImage);
    }

    /**
     * Get the folder of this PictureIcon
     * @return the folder of this PictureIcon
     */
    public Folder getFolder(){
        return this.folder;
    }

}
