package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.util.ArrayList;

import javax.swing.*;

import controller.Router;
import view.reports.TOURIST_REPORT;
import view.tourist.arrangements.__Tourist_Arrangements_ViewAll_Pane;
import view.tourist.reservations.__Tourist_Reservations_MyReservations_Pane;

public class TouristMain_Frame extends JFrame {

    protected static final long serialVersionUID = 1L;
    protected Font font;
    protected Font fontBtn;
    protected JMenuBar menuBar;
    protected JPanel mainPanel;
    protected JPanel buttonPanel;
    protected JPanel newPanel;
    protected JLabel welcomeLabel;
    protected JButton arrangementsBtn;
    protected JButton reservationsBtn;

    public TouristMain_Frame(String loginId, String username) {
        setTitle("Tourist Agency - Tourist Dashboard");

        // full screen
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        setBounds(0, 0, screenSize.width, screenSize.height);
        setMinimumSize(new Dimension(getHeight(), 800));

        // create a content pane for the frame
        mainPanel = new JPanel(new BorderLayout());
        setContentPane(mainPanel);
        menuBar = new JMenuBar();
        
        welcomeLabel = new JLabel("Welcome, Tourist (" + username + ")");
        int fontSize = Math.max(12, getWidth() / 25);
        font = new Font("SansSerif", Font.PLAIN, fontSize);
        fontBtn = new Font("SansSerif", Font.BOLD, 20);
        welcomeLabel.setFont(font);
        welcomeLabel.setHorizontalAlignment(SwingConstants.CENTER);
        welcomeLabel.setVerticalAlignment(SwingConstants.CENTER);
        
        buttonPanel = new JPanel(new GridLayout(0, 1, 0, 50)); // 50px between
        arrangementsBtn = new JButton("All Arrangements");
        arrangementsBtn.setPreferredSize(new Dimension(300, 100));
        arrangementsBtn.setFont(fontBtn);
        reservationsBtn = new JButton("My Reservations");
        reservationsBtn.setPreferredSize(new Dimension(300, 100));
        reservationsBtn.setFont(fontBtn);
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
        JMenu arrangementsMenu = new JMenu("Arrangements");
        JMenu reservationsMenu = new JMenu("Reservations");
        JMenu reportMenu = new JMenu("REPORT");
        
        // dropdown menus
        JMenuItem homeMenu_dashboard = new JMenuItem("Dashboard");
        JMenuItem homeMenu_logout = new JMenuItem("Log out");
        JMenuItem arrangementsMenu_viewAll = new JMenuItem("View All Arrangements");
        JMenuItem reservationsMenu_viewAll = new JMenuItem("My Reservations");
        JMenuItem reportMenu_viewReport = new JMenuItem("ALL TIME EXPENSES");
        
        // setting margin
        Insets menuInsets = new Insets(5, 5, 5, 5);
        homeMenu.setMargin(menuInsets);
        arrangementsMenu.setMargin(menuInsets);
        reservationsMenu.setMargin(menuInsets);
        reportMenu.setMargin(menuInsets);
        homeMenu_dashboard.setMargin(menuInsets);
        homeMenu_logout.setMargin(menuInsets);
        arrangementsMenu_viewAll.setMargin(menuInsets);
        reservationsMenu_viewAll.setMargin(menuInsets);
        reportMenu_viewReport.setMargin(menuInsets);
        
        // add sub-tasks to specific main task
        homeMenu.add(homeMenu_dashboard);
        homeMenu.add(homeMenu_logout);
        arrangementsMenu.add(arrangementsMenu_viewAll);
        reservationsMenu.add(reservationsMenu_viewAll);
        reportMenu.add(reportMenu_viewReport);
        
        // add main tasks to navbar
        menuBar.add(homeMenu);
        menuBar.add(arrangementsMenu);
        menuBar.add(reservationsMenu);
        menuBar.add(reportMenu);

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
        
        __Tourist_Arrangements_ViewAll_Pane viewArrangementsPane = new __Tourist_Arrangements_ViewAll_Pane();
        __Tourist_Reservations_MyReservations_Pane viewReservationsPane = new __Tourist_Reservations_MyReservations_Pane(loginId);
        viewPanels.add(viewArrangementsPane);
        viewPanels.add(viewReservationsPane);
        
        ActionListener dashboard_load = Router.DashboardLoad(viewPanels, mainPanel, this);
        ActionListener arrangements_load = Router.PaneSwitcher(viewPanels, viewArrangementsPane, this);
        ActionListener reservations_load = Router.PaneSwitcher(viewPanels, viewReservationsPane, this);
        ActionListener logout = Router.Logout(this);
        
        homeMenu_dashboard.addActionListener(dashboard_load);     
        arrangementsMenu_viewAll.addActionListener(arrangements_load);
        arrangementsBtn.addActionListener(arrangements_load);
        reservationsMenu_viewAll.addActionListener(reservations_load);
        reservationsBtn.addActionListener(reservations_load);
        homeMenu_logout.addActionListener(logout);
        
        reportMenu.setForeground(Color.RED);
        reportMenu_viewReport.addActionListener(new ActionListener() {
        	@Override
            public void actionPerformed(ActionEvent e) {
                TOURIST_REPORT report = new TOURIST_REPORT();
                report.report(loginId);
            }
        });
    }
}