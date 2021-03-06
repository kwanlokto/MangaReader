package frontend.reader;

import frontend.bookShelf.*;
import backend.*;
import java.awt.*;
import java.io.File;
import javax.swing.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.util.Arrays;
import javax.imageio.ImageIO;
import javax.swing.JFrame;
import static javax.swing.ScrollPaneConstants.*;
import java.io.FilenameFilter;


public class Viewer extends JFrame implements ActionListener, KeyListener, MouseListener{
    private JScrollPane scrollPane;
    private JScrollBar vertical;
    private BackgroundFrame imageViewer;
    private Pictures pictures;
    private JLabel pageNumber;
    private final int initialH = 700;
    private final int initialW = 1000;
    private boolean popUpShow = false;
    /** Image Viewer constructor.
     * It initializes all GUI components [menu bar, menu items, desktop pane, etc.].
     */
    public Viewer(Pictures pictures) {
        this.pictures = pictures;
        pageNumber = new JLabel();
        initComponents();
        addKeyListener(this);
        addMouseListener(this);
        pack();
        setBounds( 100, 50, initialW, initialH );

        //setResizable(false);
    }

    /**
     * This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the FormEditor.
     */
    private void initComponents() {//GEN-BEGIN:initComponents
        // A specialized layered pane to be used with JInternalFrames
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                BookShelf.openViewer = true;
            }
        });
        imageViewer = new BackgroundFrame();
        imageViewer.setSize(new Dimension(initialW,initialH));
        setDesktopImage(new File(pictures.getPic()));
        setTitle("Viewer");
        setLayout(new BorderLayout());
        imageViewer.addMouseListener(this);
        getAccessibleContext().setAccessibleName("Image Viewer Frame");
        getContentPane().add(imageViewer, java.awt.BorderLayout.CENTER);
        scrollPane = new JScrollPane(imageViewer, ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setHorizontalScrollBarPolicy(HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.getVerticalScrollBar().setUnitIncrement(10);
        vertical = scrollPane.getVerticalScrollBar();
        add(scrollPane, BorderLayout.CENTER);
        add(pageNumber, BorderLayout.PAGE_END);
        repaint();
        revalidate();
    }

    /**
     * This method is called when you need to update the image
     */
    private void setDesktopImage(File file){
        BufferedImage img = null;
        try {
            img = ImageIO.read(file);

        } catch (Exception ex) {
            ex.printStackTrace();
        }
        imageViewer.setImage(img);
        pageNumber.setFont(new Font("Calibri", Font.PLAIN, 16));
        pageNumber.setText("Folder: " +  pictures.getPic());
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_LEFT){
            pictures.changeIndex(-1);
            changePage();
        }
        else if (e.getKeyCode() == KeyEvent.VK_RIGHT){
            pictures.changeIndex(1);
            changePage();
        }
        else if (e.getKeyCode() == KeyEvent.VK_UP){

            vertical.setValue(vertical.getValue() - 30);
        }

        else if (e.getKeyCode() == KeyEvent.VK_DOWN){

            vertical.setValue(vertical.getValue() + 30);
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if (SwingUtilities.isRightMouseButton(e)) {
            rightClick(e);
        }
        else if (SwingUtilities.isLeftMouseButton(e)) {
            if (!popUpShow) {
                pictures.changeIndex(1);
                changePage();
            }
            popUpShow = false;
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

    /**
     * Change the pae
     */
    private void changePage() {
        popUpShow = false;

        String nextPic = pictures.getPic();
        if (nextPic != null) {
            setDesktopImage(new File(nextPic));
        }
        vertical.setValue(0);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == ViewerPopUp.goToNextFolder) {

            pictures.goToNextFolder();
            changePage();
        }
        else if (e.getSource() == ViewerPopUp.goToPreviousFolder) {
            pictures.goToPreviousFolder();
            changePage();
        }
        else if (e.getSource() == ViewerPopUp.goToFolder) {
            choosePicture();
        }

    }

    /**
     * If the user right clicks then a Pop up menu should appear at that location
     * @param e the action performed
     */
    private void rightClick(MouseEvent e) {
        ViewerPopUp viewerPopUp = new ViewerPopUp(this);
        viewerPopUp.getPopUp().show(this, e.getX(), e.getY());
        popUpShow = true;
    }

    /**
     * The user selects the picture they want to go to. If the user enters a valid picture then go to that picture,
     * but if it is not a valid picture then nothing should change.
     */
    private void choosePicture() {
        String picture = getPictureOption(getFolderOption());
        if (picture != null) {
            pictures.goTo(picture);
            changePage();
            repaint();
            revalidate();
        }
    }

    /**
     * Create JDialog which allows the user to select one of the subDirectories.
     * @return the path to the directory the user selects
     */
    private String getFolderOption() {
        JFrame frame = new JFrame();
        String[] listFiles = (new File(pictures.getDirectory())).list(new FilenameFilter() {
            @Override
            public boolean accept(File current, String name) {
                return new File(current, name).isDirectory();
            }
        });
        //If there are no subdirectories
        if (listFiles == null || listFiles.length == 0) {
            return pictures.getDirectory();
        }

        Arrays.sort(listFiles);
        String folder = (String)JOptionPane.showInputDialog(
                frame,
                "Select the folder",
                "Customized Dialog",
                JOptionPane.PLAIN_MESSAGE,
                null,
                listFiles,
                listFiles[0]);
        if ((folder != null) && (folder.length() > 0)) {
            return pictures.getDirectory() + "/" + folder;
        }
        return null;
    }

    /**
     * Create a JDialog where the user can select the picture they want to open. If nothing is clicked nothing should
     * change in the Viewer
     * @param folder The parent folder that holds all the pictures
     * @return the picture the user selected
     */
    private String getPictureOption(String folder) {
        if (folder != null) {
            JFrame frame = new JFrame();
            String[] listPictures = new File(folder).list(new FilenameFilter() {
                @Override
                public boolean accept(File dir, String name) {
                    return Pictures.fileSearcher.isPicture(new File(dir, name));
                }
            });
            //if there are no pictures found
            if (listPictures == null || listPictures.length == 0) {
                return null;
            }
            Arrays.sort(listPictures);
            String picture = (String) JOptionPane.showInputDialog(
                    frame,
                    "Select the picture",
                    "Customized Dialog",
                    JOptionPane.PLAIN_MESSAGE,
                    null,
                    listPictures,
                    listPictures[0]);
            if ((picture != null) && (picture.length() > 0)) {
                return folder + "/" + picture;
            }
        }
        return null;
    }
}