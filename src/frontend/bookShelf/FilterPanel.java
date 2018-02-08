package frontend.bookShelf;

import javax.swing.*;
import backend.*;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.TreeSet;
import java.util.*;

public class FilterPanel extends JPanel implements ActionListener{
    public static ArrayList<String> selectedTags = new ArrayList<>();
    private static ArrayList<JRadioButton> filters = new ArrayList<>();
    private JButton filterButton = new JButton("Filter");
    private static JPanel filterPanel = new JPanel();
    private JScrollPane scrollPane;
    /**
     * Create a new Panel
     */
    public FilterPanel() {
        setMaximumSize(new Dimension(BookShelf.windowWidth,60));
        setPreferredSize(new Dimension(BookShelf.windowWidth,60));
        setSize(new Dimension(BookShelf.windowWidth,60));

        setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
        filterButton.addActionListener(this);

        updatePanel();
        scrollPane = new JScrollPane(filterPanel, ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scrollPane.getVerticalScrollBar().setUnitIncrement(10);
        scrollPane.getHorizontalScrollBar().setUnitIncrement(10);
        scrollPane.getViewport().setOpaque(false);
        add(scrollPane);
        add(filterButton);
    }

    /**
     * Updates the panel
     */
    public void updatePanel() {
        for (int i = filters.size(); i < Tag.allTags.size(); i++){
            JRadioButton newFilter = new JRadioButton(Tag.allTags.get(i));
            filterPanel.add(newFilter);
            filters.add(newFilter);
        }
        repaint();
        revalidate();
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        if (e.getSource() == filterButton){
            for (int i = 0; i < filters.size(); i++) {
                if (filters.get(i).isSelected()) {
                    if (!selectedTags.contains(filters.get(i).getText())) {
                        selectedTags.add(filters.get(i).getText());
                    }
                    filters.get(i).doClick();
                }
                else {
                    selectedTags.remove(filters.get(i).getText());
                }
            }
            DisplayBooksPanel.clearDesktop();
            DisplayBooksPanel.initializeDesktop(selectedTags);
        }
    }
}
