package view.tourist.arrangements;

import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

public class IMAGE {
	public static void show(String imgPath) {
	    imgPath = "C:\\Users\\WIN\\Desktop\\IMAGES\\" + imgPath;
	    JFrame frame = new JFrame("Image");
	    frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

	    ImageIcon originalIcon = new ImageIcon(imgPath);
	    Image originalImage = originalIcon.getImage();

	    // Set the desired width and height for the resized image
	    int desiredWidth = 400;
	    int desiredHeight = 400;

	    // Create a scaled version of the image
	    Image resizedImage = originalImage.getScaledInstance(desiredWidth, desiredHeight, Image.SCALE_SMOOTH);

	    // Create a new ImageIcon with the resized image
	    ImageIcon resizedIcon = new ImageIcon(resizedImage);

	    JLabel label = new JLabel(resizedIcon);
	    frame.getContentPane().add(label);

	    frame.pack();
	    frame.setVisible(true);
	    frame.setResizable(false);
	    frame.setLocationRelativeTo(null);
	}

}
