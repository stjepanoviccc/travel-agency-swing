package view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.util.ArrayList;

import javax.swing.*;

import controller.Router;
import view.admin.arrangements.__Admin_Arrangements_ViewAll_Pane;
import view.admin.reservations.__Admin_Reservations_ViewAll_Pane;
import view.admin.users.__Admin_Users_ViewAll_Pane;

public class AdminMain_Frame extends JFrame {

    protected static final long serialVersionUID = 1L;
    protected Font font;
    protected Font fontBtn;
    protected JMenuBar menuBar;
    protected JPanel mainPanel;
    protected JPanel buttonPanel;
    protected JPanel newPanel;
    protected JLabel welcomeLabel;
    protected JButton usersBtn;
    protected JButton arrangementsBtn;
    protected JButton reservationsBtn;

    public AdminMain_Frame(String loginId, String username) {
        setTitle("Tourist Agency - Admin Dashboard");

        // full screen
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        setBounds(0, 0, screenSize.width, screenSize.height);
        setMinimumSize(new Dimension(getHeight(), 800));

        // create a content pane for the frame
        mainPanel = new JPanel(new BorderLayout());
        setContentPane(mainPanel);
        menuBar = new JMenuBar();

        welcomeLabel = new JLabel("Welcome, Admin (" + username + ")");
        int fontSize = Math.max(12, getWidth() / 25);
        font = new Font("SansSerif", Font.PLAIN, fontSize);
        fontBtn = new Font("SansSerif", Font.BOLD, 20);
        
        welcomeLabel.setFont(font);
        welcomeLabel.setHorizontalAlignment(SwingConstants.CENTER);
        welcomeLabel.setVerticalAlignment(SwingConstants.CENTER);

        buttonPanel = new JPanel(new GridLayout(0, 1, 0, 50)); // 50px between
        usersBtn = new JButton("All Users");
        usersBtn.setPreferredSize(new Dimension(300, 100));
        usersBtn.setFont(fontBtn);
        arrangementsBtn = new JButton("All Arrangements");
        arrangementsBtn.setPreferredSize(new Dimension(300, 100));
        arrangementsBtn.setFont(fontBtn);
        reservationsBtn = new JButton("All Reservations");
        reservationsBtn.setPreferredSize(new Dimension(300, 100));
        reservationsBtn.setFont(fontBtn);
        buttonPanel.add(usersBtn);
        buttonPanel.add(arrangementsBtn);
        buttonPanel.add(reservationsBtn);

        newPanel = new JPanel(new GridBagLayout());
        GridBagConstraints cWelcome = new GridBagConstraints();
        GridBagConstraints cNested = new GridBagConstraints();
        cWelcome.gridx = 0;
        cWelcome.gridy = 0;
        cWelcome.insets = new Insets(0, 0, 100, 0); // margin 100px welcomeLabel
        cNested.gridx = 0;
        cNested.gridy = 1;

        newPanel.add(welcomeLabel, cWelcome);
        newPanel.add(buttonPanel, cNested);

        mainPanel.add(newPanel, BorderLayout.CENTER);


        // tabs
        JMenu homeMenu = new JMenu("Home");
        JMenu usersMenu = new JMenu("Users");
        JMenu arrangementsMenu = new JMenu("Arrangements");
        JMenu reservationsMenu = new JMenu("Reservations");
        
        // dropdown menus
        JMenuItem homeMenu_dashboard = new JMenuItem("Dashboard");
        JMenuItem homeMenu_logout = new JMenuItem("Log out");
        JMenuItem usersMenu_add = new JMenuItem("Add New User");
        JMenuItem usersMenu_viewAll = new JMenuItem("View All Users");
        JMenuItem arrangementsMenu_create = new JMenuItem("Create New Arrangement");
        JMenuItem arrangementsMenu_viewAll = new JMenuItem("View All Arrangements");
        JMenuItem reservationsMenu_viewAll = new JMenuItem("View All Reservations");
        
        // setting margin
        Insets menuInsets = new Insets(5, 5, 5, 5);
        homeMenu.setMargin(menuInsets);
        usersMenu.setMargin(menuInsets);
        arrangementsMenu.setMargin(menuInsets);
        reservationsMenu.setMargin(menuInsets);
        homeMenu_dashboard.setMargin(menuInsets);
        homeMenu_logout.setMargin(menuInsets);
        usersMenu_add.setMargin(menuInsets);
        usersMenu_viewAll.setMargin(menuInsets);
        arrangementsMenu_create.setMargin(menuInsets);
        arrangementsMenu_viewAll.setMargin(menuInsets);
        reservationsMenu_viewAll.setMargin(menuInsets);
        
        // add sub-tasks to specific main task
        homeMenu.add(homeMenu_dashboard);
        homeMenu.add(homeMenu_logout);
        usersMenu.add(usersMenu_add);
        usersMenu.add(usersMenu_viewAll);
        arrangementsMenu.add(arrangementsMenu_create);
        arrangementsMenu.add(arrangementsMenu_viewAll);
        reservationsMenu.add(reservationsMenu_viewAll);
        
        // add main tasks to navbar
        menuBar.add(homeMenu);
        menuBar.add(usersMenu);
        menuBar.add(arrangementsMenu);
        menuBar.add(reservationsMenu);

        setJMenuBar(menuBar);
        setVisible(true);

        // exit the application when the frame is closed
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // resize the welcome label when the frame is resized
        addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                int fontSize = Math.max(12, getWidth() / 25);
                Font font = new Font("SansSerif", Font.PLAIN, fontSize);
                welcomeLabel.setFont(font);
            }
        });
        
        // ACTION LISTENERS -> ROUTE
        ArrayList<JPanel> viewPanels = new ArrayList<>();
     
        __Admin_Users_ViewAll_Pane viewUsersPane = new __Admin_Users_ViewAll_Pane();
        __Admin_Arrangements_ViewAll_Pane viewArrangementsPane = new __Admin_Arrangements_ViewAll_Pane();
        __Admin_Reservations_ViewAll_Pane viewReservationsPane = new __Admin_Reservations_ViewAll_Pane();
        viewPanels.add(viewUsersPane);
        viewPanels.add(viewArrangementsPane);
        viewPanels.add(viewReservationsPane);
        
        ActionListener dashboard_load = Router.DashboardLoad(viewPanels, mainPanel, this);
        ActionListener users_load = Router.PaneSwitcher(viewPanels, viewUsersPane, this);
        ActionListener arrangements_load = Router.PaneSwitcher(viewPanels, viewArrangementsPane, this);
        ActionListener reservations_load = Router.PaneSwitcher(viewPanels, viewReservationsPane, this);
        ActionListener logout = Router.Logout(this);
        
        // also for reload after updating table state
        ActionListener users_reload = Router.PaneReload(usersMenu_viewAll, viewPanels, usersMenu, this, "users", loginId, username);
        ActionListener arrangements_reload = Router.PaneReload(arrangementsMenu_viewAll, viewPanels, arrangementsMenu, this, "arrangements", loginId, username);
        usersMenu_add.addActionListener(users_reload);
        arrangementsMenu_create.addActionListener(arrangements_reload);
        
        homeMenu_dashboard.addActionListener(dashboard_load);
        usersMenu_viewAll.addActionListener(users_load);
        usersBtn.addActionListener(users_load);
        arrangementsMenu_viewAll.addActionListener(arrangements_load);
        arrangementsBtn.addActionListener(arrangements_load);
        reservationsMenu_viewAll.addActionListener(reservations_load);
        reservationsBtn.addActionListener(reservations_load);  
        homeMenu_logout.addActionListener(logout);
    }
}