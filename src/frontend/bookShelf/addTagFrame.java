package frontend.bookShelf;

import javax.swing.*;
import backend.Folder;
import backend.SavedFolders;
import backend.Tag;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.*;
public class addTagFrame extends JFrame implements ActionListener, KeyListener{

    private JButton addTags = new JButton("Add Tags");
    private JButton deleteTags = new JButton("Delete Tags");
    private JButton addNewTag = new JButton("OK");
    private JTextField newTag = new JTextField();
    private Folder folder;
    private JSplitPane panel;
    private JPanel bottomPanel = new JPanel();
    private JPanel mainPanel = new JPanel();
    private JScrollPane scrollPane;
    private JPanel radioPanel = new JPanel();
    private ArrayList<JRadioButton> listOfRadioButton = new ArrayList<>();

    private DefaultListModel<String> model = new DefaultListModel<>();
    private JList<String> jList = new JList<>(model);

    private Font usedFont = new Font("Calibri", Font.PLAIN, 14);
    public addTagFrame(Folder linkedFolder) {
        this.folder = linkedFolder;
        initComponents();
        setBounds(new Rectangle(500, 200,320,250));
        setResizable(false);
    }

    private void initComponents() {
        newTag.addKeyListener(this);

        //LEFT SIDE IS ALL THE CURRENT TAGS
        for (String tag : folder.getAssociatedTag().getTags()) {
            model.addElement(tag);
        }
        jList.setFont(usedFont);

        //MAIN SIDE
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setOpaque(false);

        //CREATE THE TOP LEFT PART OF THE TAGS
        JPanel topPanel = new JPanel();
        topPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 20, 5));
        newTag.setPreferredSize(new Dimension(200,30));
        newTag.setFont(usedFont);
        topPanel.add(newTag);
        addNewTag.addActionListener(this);
        topPanel.add(addNewTag);
        mainPanel.add(topPanel);

        //CREATE THE JRADIOBUTTONS (RIGHT SIDE)
        radioPanel.setLayout(new BoxLayout(radioPanel,BoxLayout.Y_AXIS));
        createJRadioButtons();
        radioPanel.setAlignmentX(Component.RIGHT_ALIGNMENT);
        panel = new JSplitPane( JSplitPane.HORIZONTAL_SPLIT, jList, radioPanel);
        panel.setDividerLocation(160);
        panel.setOpaque(false);

        //CREATE THE SCROLLPANE
        scrollPane = new JScrollPane(panel, ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scrollPane.getVerticalScrollBar().setUnitIncrement(10);
        scrollPane.getHorizontalScrollBar().setUnitIncrement(10);
        scrollPane.getViewport().setOpaque(false);
        mainPanel.add(scrollPane);

        //CREATE THE BOTTOM PANEL
        bottomPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 20, 5));
        addTags.addActionListener(this);
        deleteTags.addActionListener(this);
        bottomPanel.add(deleteTags);
        bottomPanel.add(addTags);
        mainPanel.add(bottomPanel);

        add(mainPanel);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == addTags) {
            for (int i = 0; i < listOfRadioButton.size(); i++) {
                if (listOfRadioButton.get(i).isSelected()) {
                    listOfRadioButton.get(i).doClick();

                    //if there does not exist another one inside this tag list then add it
                    if (folder.getAssociatedTag().addTag(listOfRadioButton.get(i).getText())) {
                        model.addElement(listOfRadioButton.get(i).getText());
                    }
                }
            }
            repaint();
            revalidate();
        }

        else if (e.getSource() == addNewTag) {
            if (!newTag.getText().equals("")) {
                String tagString = newTag.getText();
                newTag.setText(null);

                // if the the tag you are trying to add isn't already inside then add it
                if (!Tag.allTags.contains(tagString)) {
                    Tag.allTags.add(tagString);
                    JRadioButton radioButton = new JRadioButton(tagString);
                    radioButton.setFont(usedFont);
                    listOfRadioButton.add(radioButton);
                    radioPanel.add(radioButton);
                    BookShelf.filterPanel.updatePanel();
                }
            }
            repaint();
            revalidate();
        }

        else if (e.getSource() == deleteTags){
            //REMOVE ALL SELECTED LIST TAGS
            for (int i = 0; i < listOfRadioButton.size(); i++) {
                if (listOfRadioButton.get(i).isSelected()) {
                    listOfRadioButton.get(i).doClick();
                    Tag.allTags.remove(listOfRadioButton.get(i).getText());
                    radioPanel.remove(listOfRadioButton.get(i));
                }
            }
            System.out.println("All Tags: "+ Tag.allTags);

            int[] selectedIndices= jList.getSelectedIndices();
            Arrays.sort(selectedIndices);
            for (int i = selectedIndices.length - 1; i >= 0; i--) {
                String strToRemove = model.get(selectedIndices[i]);
                model.removeElement(strToRemove);
                folder.getAssociatedTag().removeTag(strToRemove);
            }
            jList.clearSelection();
            System.out.println("Folder Tags: "+folder.getAssociatedTag().getTags());
            repaint();
            revalidate();
        }
    }

    private void createJRadioButtons(){
        for (String tag : Tag.allTags) {
            JRadioButton radioButton = new JRadioButton(tag);
            listOfRadioButton.add(radioButton);
            radioButton.setFont(usedFont);
            radioPanel.add(radioButton);
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            addNewTag.doClick();
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }
}
