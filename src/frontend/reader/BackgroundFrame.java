package frontend.reader;
import javax.swing.*;
import java.awt.image.*;
import java.awt.*;

class BackgroundFrame extends JPanel{
    private Image image;
    private int width;
    private int height;
    public BackgroundFrame() {
        image = null;
        setBackground(Color.BLACK);
    }

    /**
     * Assign an image to this JPanel
     * @param image
     */
    public void setImage(BufferedImage image) {
        this.image = null;
        if (image != null) {

            int tempWidth = image.getWidth();
            int tempHeight = image.getHeight();
            height = (getWidth() * tempHeight) / tempWidth;
            width = getWidth() - 10;
            setPreferredSize(new Dimension(width, height));
            this.image = image.getScaledInstance(width, height, Image.SCALE_SMOOTH);
        }
        repaint();
    }

    /**
     * Update the image on the JPanel
     * @param g
     */
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        if (image != null) {
            if (height > getHeight()) {
                g.drawImage(image, 0, 0, this);
            }
            else {
                g.drawImage(image, 0, (getHeight() - height)/2, this);
            }
        }
    }
}

