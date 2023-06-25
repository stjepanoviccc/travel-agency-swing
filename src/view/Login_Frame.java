package view;
import javax.swing.*;

import dao.ReservationsDAO;
import dao.RoomsDAO;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class Login_Frame extends JFrame {
	protected static final long serialVersionUID = 1L;
	protected JTextField usernameField;
    protected JPasswordField passwordField;
    protected ImageIcon backgroundImage;
    protected JLabel backgroundLabel;
    protected JLabel usernameLabel;
    protected JLabel passwordLabel;
    protected JButton loginButton;

    public Login_Frame() {
    	// create a JLabel with an ImageIcon and add it to the content pane
        backgroundImage = new ImageIcon("C:\\Users\\WIN\\Desktop\\IMAGES\\loginBackground.png");
        backgroundLabel = new JLabel(backgroundImage);
        backgroundLabel.setLayout(new GridBagLayout());
        getContentPane().add(backgroundLabel, BorderLayout.CENTER);
        setTitle("Tourist Agency - Login");
        setSize(450, 325);
        setMinimumSize(new Dimension(getWidth(), 325));
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        RoomsDAO.roomsHashLoad();
        System.out.println(RoomsDAO.roomsHash);
        ReservationsDAO.reservationsHashLoad();
    
        // all components in login form
        usernameLabel = new JLabel("Username:");
        passwordLabel = new JLabel("Password:");
        loginButton = new JButton("Login");
        usernameField = new JTextField(15);
        passwordField = new JPasswordField(15);

        // layout constraints
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.insets = new Insets(5, 5, 5, 5);
        constraints.anchor = GridBagConstraints.CENTER;
        constraints.gridx = 0;
        constraints.gridy = 0;
        backgroundLabel.add(usernameLabel, constraints);
        constraints.gridx = 1;
        backgroundLabel.add(usernameField, constraints);
        constraints.gridx = 0;
        constraints.gridy = 1;
        backgroundLabel.add(passwordLabel, constraints);
        constraints.gridx = 1;
        backgroundLabel.add(passwordField, constraints);
        constraints.gridx = 0;
        constraints.gridy = 2;
        constraints.gridwidth = 2;
        constraints.anchor = GridBagConstraints.CENTER;
        constraints.ipady = 10;
        backgroundLabel.add(loginButton, constraints);

        // add action listener to login button
        loginButton.addActionListener(new ActionListener() {
			@Override
            public void actionPerformed(ActionEvent e) {
                String username = usernameField.getText();
                String password = new String(passwordField.getPassword());
                String id = controller.LoginController.login(username, password);
                if (id != null) {
                    // login successful 
                	controller.LoginController.checkRole(id,username);
                	dispose();
                } else {
                    // error message
                    JOptionPane.showMessageDialog(Login_Frame.this, "Invalid username or password", "Login Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        
        // enabled enter key press on login
        passwordField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    loginButton.doClick();
                }
            }
        });

        // adjust font size, input size, and button size
        addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                int fontSize = Math.max(12, getWidth() / 25);
                Font font = new Font("SansSerif", Font.PLAIN, fontSize);
                usernameLabel.setFont(font);
                passwordLabel.setFont(font);
                usernameField.setFont(font);
                passwordField.setFont(font);
                loginButton.setFont(font);
                int buttonWidth = getWidth() / 3;
                int buttonHeight = Math.max(20, getHeight() / 12);
                loginButton.setPreferredSize(new Dimension(buttonWidth, buttonHeight));
            }
        });
        
    }
}
