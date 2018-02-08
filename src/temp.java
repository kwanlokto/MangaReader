//package frontend;
//import backend.*;
//import javax.swing.*;
//import java.awt.*;
//import java.awt.event.*;
//import java.util.*;
//import java.awt.image.*;
//
//public class BookShelf extends JFrame implements MouseListener{
//    private GridBagConstraints c = new GridBagConstraints();
//    private JScrollPane scrollPane;
//    private JPanel desktop;
//    private JMenuItem exitMenuItem;
//    private JMenu fileMenu;
//    private JSeparator jSeparator1;
//    private JMenuBar mainMenuBar;
//    private JMenuItem openMenuItem;
//    private JFileChooser chooser;
//    private ArrayList<JLabel> labels;
//    private ArrayList<JPanel> panels;
//    private ArrayList<String> folders;
//    public static final int iconDimension = 250;
//    private int numbFolders = 0;
//    private PopUp popUpLabel = new PopUp();
//    public static boolean openViewer = true;
//    private boolean popUpOpen = false;
//    public static final int numbCols = 3;
//    public static int numbRows;
//
//
//    public BookShelf() {
//        labels = new ArrayList<>();
//        panels = new ArrayList<>();
//        SavedFolders.loadFile("./src/serial_files/savedFolders.ser");
//        folders = SavedFolders.getFolders();
//        numbRows = (int) Math.ceil((double)folders.size()/numbCols);
//
//        initComponents();
//        pack();
//        addMouseListener(this);
//        setBounds( 100, 100, 1000, 700 );
//    }
//
//    /**
//     * Initialize all the components
//     */
//    public void initComponents() {
//        desktop = new JPanel();
//
//        mainMenuBar = new JMenuBar();
//        fileMenu = new JMenu();
//        openMenuItem = new JMenuItem();
//        jSeparator1 = new JSeparator();
//        exitMenuItem = new JMenuItem();
//        setTitle("BookShelf");
//        addWindowListener(new java.awt.event.WindowAdapter() {
//            public void windowClosing(java.awt.event.WindowEvent evt) {
//                exitForm();
//            }
//        });
//
//        initializeDesktop();
//        add(scrollPane, BorderLayout.NORTH);
//        getContentPane().add(desktop, java.awt.BorderLayout.NORTH);
//
//        initializeMenu();
//        setJMenuBar(mainMenuBar);
//    }
//
//    /**
//     * This method is called when File -> Exit menu item is invoked.
//     * It closes the application.
//     * @param evt ActionEvent instance passed from actionPerformed event.
//     */
//    private void exitMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_exitMenuItemActionPerformed
//        System.exit( 0 );
//    }//GEN-LAST:event_exitMenuItemActionPerformed
//
//    /**
//     * This method is called when File -> Open menu item is invoked. Saves the folder to the bookshelf
//     * It displays a dialog to choose the image file to be opened and displayed.
//     */
//    private void openMenuItemActionPerformed() {//GEN-FIRST:event_openMenuItemActionPerformed
//        chooser = new JFileChooser();
//        chooser.setCurrentDirectory(new java.io.File("."));
//        chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
//        //
//        // disable the "All files" option.
//        //
//        chooser.setAcceptAllFileFilterUsed(false);
//        //
//        if (chooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
//            String newFile = chooser.getSelectedFile().toString();
//            SavedFolders.addFolder(newFile);
//            addImageToGrid(newFile);
//        }
//        repaint();
//        revalidate();
//    }//GEN-LAST:event_openMenuItemActionPerformed
//
//    /**
//     * This method is called when the application frame is closed.
//     */
//    private void exitForm() {//GEN-FIRST:event_exitForm
//        System.exit(0);
//    }//GEN-LAST:event_exitForm
//
//    /**
//     * Resize image and crop the image to fit a 250 x 250 box
//     */
//    private ImageIcon resizeImage(ImageIcon icon) {
//        int imageH = icon.getIconHeight();
//        int imageW = icon.getIconWidth();
//        BufferedImage temp = new BufferedImage(imageW, imageH, BufferedImage.TYPE_INT_ARGB);
//
//        if (imageH > imageW) {
//            imageH = (iconDimension * imageH)/imageW;
//            imageW = iconDimension;
//        }
//        else {
//            imageW = (iconDimension * imageW)/imageH;
//            imageH = iconDimension;
//        }
//        Image image = icon.getImage();
//        System.out.println("width: " + imageW + ", height: "+imageH);
//
//
//        Graphics bGr = temp.createGraphics();
//        bGr.drawImage(image, 0, 0, imageW, imageH,null, null);
//        bGr.dispose();
//
//        int x = imageW/2 - iconDimension/2;
//        int y = imageH/2 - iconDimension/2;
//        BufferedImage bufferedImage = temp.getSubimage(x,y,iconDimension, iconDimension);
//        return new ImageIcon(bufferedImage);
//    }
//
//    @Override
//    public void mouseClicked(MouseEvent e) {
//        if (labels.size() != 0) {
//            for (int i = 0; i < labels.size(); i++) {
//                if (e.getSource() == labels.get(i)) {
//                    if (SwingUtilities.isRightMouseButton(e)) {
//                        System.out.println(e.getX() + ": " + e.getY());
//                        int deltaX = i%numbCols;
//                        int deltaY = i/numbCols;
//                        popUpLabel.index = i;
//                        popUpLabel.popup.show(this,
//                                deltaX * (iconDimension + 40) + e.getX(),
//                                deltaY*(iconDimension + 20) + e.getY());
//                        popUpOpen = true;
//                    }
//
//                    else if (openViewer && !popUpOpen) {
//                        String folder = folders.get(i);
//                        Pictures pictures = new Pictures(folder);
//                        if (!pictures.isEmpty()) {
//                            openViewer = false;
//                            new Viewer(pictures).show();
//                        }
//                    }
//
//                    else {
//                        popUpOpen = false;
//                    }
//
//                }
//            }
//        }
//
//    }
//
//    @Override
//    public void mousePressed(MouseEvent e) {
//
//    }
//
//    @Override
//    public void mouseReleased(MouseEvent e) {
//
//    }
//
//    @Override
//    public void mouseEntered(MouseEvent e) {
//
//    }
//
//    @Override
//    public void mouseExited(MouseEvent e) {
//
//    }
//
//    public static void main(String[] args) {
//        new BookShelf().show();
//    }
//
//    private void addImageToGrid(String folder){
//        if (numbFolders == numbCols*numbRows) {
//            for (int i = 0; i < numbCols; i++) {
//                c.fill = GridBagConstraints.HORIZONTAL;
//                c.gridx = i;
//                c.gridy = numbRows;
//                c.insets = new Insets(10,20,10,20);
//                JPanel tempPanel = new JPanel();
//                tempPanel.setLayout(new BoxLayout(tempPanel, BoxLayout.PAGE_AXIS));
//                panels.add(tempPanel);
//                desktop.add(tempPanel, c);
//            }
//            numbRows++;
//        }
//        ImageIcon icon = new ImageIcon("./src/pictures/blank.png"); //blank.jpg
//        Pictures tempPictures = new Pictures(folder);
//        if (tempPictures.getPic() != null) {
//            icon = new ImageIcon(tempPictures.getPic());
//        }
//        icon = resizeImage(icon); //resize the icon to be the desired size
//        JLabel tempLabel = new JLabel();
//
//        tempLabel.setBorder(BorderFactory.createLineBorder(Color.black)); //black border
//        tempLabel.setIcon(icon);
//        tempLabel.setMinimumSize(new Dimension(iconDimension,iconDimension));
//        tempLabel.setPreferredSize(new Dimension(iconDimension,iconDimension));
//        tempLabel.setMaximumSize(new Dimension(iconDimension,iconDimension));
//
//        labels.add(tempLabel);
//        panels.get(numbFolders).add(tempLabel);
//        String[] folderInfo = folder.split("/");
//        panels.get(numbFolders).add(new JLabel(folderInfo[folderInfo.length-1]));
//        tempLabel.addMouseListener(this);
//
//        numbFolders ++;
//        repaint();
//        revalidate();
//
//    }
//
//    private void initializeDesktop(){
//        desktop.setLayout(new GridBagLayout());
//
//        for (int i = 0; i < numbRows * numbCols; i++) {
//            c.fill = GridBagConstraints.HORIZONTAL;
//            c.gridx = i % numbCols;
//            c.gridy = i / numbCols;
//            c.insets = new Insets(10,20,10,20);
//
//            JPanel tempPanel = new JPanel();
//            tempPanel.setLayout(new BoxLayout(tempPanel, BoxLayout.PAGE_AXIS));
//            panels.add(tempPanel);
//            desktop.add(tempPanel, c);
//        }
//
//        for (String folder : SavedFolders.getFolders()) {
//            addImageToGrid(folder);
//        }
//
//        desktop.addMouseListener(this);
//        scrollPane = new JScrollPane(desktop, ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
//        scrollPane.getVerticalScrollBar().setUnitIncrement(10);
//    }
//
//    private void initializeMenu(){
//        fileMenu.setMnemonic('f');
//        fileMenu.setText("File");
//        openMenuItem.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_O, java.awt.event.InputEvent.CTRL_MASK));
//        openMenuItem.setMnemonic('o');
//        openMenuItem.setText("Open");
//        openMenuItem.addActionListener(new java.awt.event.ActionListener() {
//            public void actionPerformed(java.awt.event.ActionEvent evt) {
//                openMenuItemActionPerformed();
//            }
//        });
//
//        fileMenu.add(openMenuItem);
//
//        fileMenu.add(jSeparator1);
//
//        exitMenuItem.setMnemonic('x');
//        exitMenuItem.setText("Exit");
//        exitMenuItem.addActionListener(new java.awt.event.ActionListener() {
//            public void actionPerformed(java.awt.event.ActionEvent evt) {
//                exitMenuItemActionPerformed(evt);
//            }
//        });
//
//        fileMenu.add(exitMenuItem);
//
//        mainMenuBar.add(fileMenu);
//    }
//}

//package frontend;
//import backend.*;
//import javax.swing.*;
//import java.awt.*;
//import java.awt.event.*;
//import java.util.*;
//import java.awt.image.*;
//
//public class BookShelf extends JFrame{
//    private GridBagConstraints c = new GridBagConstraints();
//    private JScrollPane scrollPane;
//    private JPanel desktop;
//    private JMenuItem exitMenuItem;
//    private JMenu fileMenu;
//    private JSeparator jSeparator1;
//    private JMenuBar mainMenuBar;
//    private JMenuItem openMenuItem;
//    private JFileChooser chooser;
//    public static ArrayList<String> folders;
//    public static final int iconDimension = 250;
//    public static PopUp popUpLabel = new PopUp();
//    public static boolean openViewer = true;
//    public static boolean popUpOpen = false;
//    public static final int numbCols = 3;
//    public static int numbRows;
//
//
//    public BookShelf() {
//        new DisplayBooksPanel(this);
//        SavedFolders.loadFile("./src/serial_files/savedFolders.ser");
//        folders = SavedFolders.getFolders();
//        numbRows = (int) Math.ceil((double)folders.size()/numbCols);
//
//        initComponents();
//        pack();
//        addMouseListener(new mouseListener(this));
//        setBounds( 100, 100, 1000, 700 );
//    }
//
//    /**
//     * Initialize all the components
//     */
//    public void initComponents() {
//        desktop = new JPanel();
//
//        mainMenuBar = new JMenuBar();
//        fileMenu = new JMenu();
//        openMenuItem = new JMenuItem();
//        jSeparator1 = new JSeparator();
//        exitMenuItem = new JMenuItem();
//        setTitle("BookShelf");
//        addWindowListener(new java.awt.event.WindowAdapter() {
//            public void windowClosing(java.awt.event.WindowEvent evt) {
//                exitForm();
//            }
//        });
//        System.out.println("hello" );
//        scrollPane = DisplayBooksPanel.initializeDesktop();
//
//        add(scrollPane, BorderLayout.NORTH);
//        getContentPane().add(scrollPane, java.awt.BorderLayout.NORTH);
//
//        initializeMenu();
//        setJMenuBar(mainMenuBar);
//    }
//
//    /**
//     * This method is called when File -> Exit menu item is invoked.
//     * It closes the application.
//     * @param evt ActionEvent instance passed from actionPerformed event.
//     */
//    private void exitMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_exitMenuItemActionPerformed
//        System.exit( 0 );
//    }//GEN-LAST:event_exitMenuItemActionPerformed
//
//    /**
//     * This method is called when File -> Open menu item is invoked. Saves the folder to the bookshelf
//     * It displays a dialog to choose the image file to be opened and displayed.
//     */
//    private void openMenuItemActionPerformed() {//GEN-FIRST:event_openMenuItemActionPerformed
//        chooser = new JFileChooser();
//        chooser.setCurrentDirectory(new java.io.File("."));
//        chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
//        //
//        // disable the "All files" option.
//        //
//        chooser.setAcceptAllFileFilterUsed(false);
//        //
//        if (chooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
//            String newFile = chooser.getSelectedFile().toString();
//            SavedFolders.addFolder(newFile);
//            DisplayBooksPanel.addImageToGrid(newFile);
//        }
//        repaint();
//        revalidate();
//    }//GEN-LAST:event_openMenuItemActionPerformed
//
//    /**
//     * This method is called when the application frame is closed.
//     */
//    private void exitForm() {//GEN-FIRST:event_exitForm
//        System.exit(0);
//    }//GEN-LAST:event_exitForm
//
//
//    public static void main(String[] args) {
//        new BookShelf().show();
//    }
//
//
//
//    private void initializeMenu(){
//        fileMenu.setMnemonic('f');
//        fileMenu.setText("File");
//        openMenuItem.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_O, java.awt.event.InputEvent.CTRL_MASK));
//        openMenuItem.setMnemonic('o');
//        openMenuItem.setText("Open");
//        openMenuItem.addActionListener(new java.awt.event.ActionListener() {
//            public void actionPerformed(java.awt.event.ActionEvent evt) {
//                openMenuItemActionPerformed();
//            }
//        });
//
//        fileMenu.add(openMenuItem);
//
//        fileMenu.add(jSeparator1);
//
//        exitMenuItem.setMnemonic('x');
//        exitMenuItem.setText("Exit");
//        exitMenuItem.addActionListener(new java.awt.event.ActionListener() {
//            public void actionPerformed(java.awt.event.ActionEvent evt) {
//                exitMenuItemActionPerformed(evt);
//            }
//        });
//
//        fileMenu.add(exitMenuItem);
//
//        mainMenuBar.add(fileMenu);
//    }
//}
