package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JPanel;

import dao.ArrangementsDAO;
import dao.UsersDAO;
import view.AdminMain_Frame;
import view.AgentMain_Frame;
import view.admin.arrangements.__Admin_Arrangements_CreateNew_Dialog;
import view.admin.arrangements.__Admin_Arrangements_ViewAll_Pane;
import view.admin.users.__Admin_Users_AddUser_Dialog;
import view.admin.users.__Admin_Users_ViewAll_Pane;
import view.agent.arrangements.__Agent_Arrangements_CreateNew_Dialog;
import view.agent.arrangements.__Agent_Arrangements_MyArrangements_Pane;

public class Router {
    public static ActionListener PaneSwitcher(List<JPanel> viewPanels, JPanel newPane, JFrame container) {
        return new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                for (JPanel panel : viewPanels) {
                    panel.setVisible(false);
                }
                newPane.setVisible(true);
                container.setContentPane(newPane);
                newPane.revalidate();
                newPane.repaint();
            }         
        };
    }
    
    public static ActionListener DashboardLoad(List<JPanel> viewPanels, JPanel newPanel, JFrame container)  {
    	return new ActionListener() {
    		@Override
            public void actionPerformed(ActionEvent e) {
            	for (JPanel panel : viewPanels) {
            	    panel.setVisible(false);
            	}
            	newPanel.setVisible(true);
                container.setContentPane(newPanel);
            }
    	};
    }
    
    public static ActionListener Logout(JFrame container) {
    	return new ActionListener() {
    		@Override
            public void actionPerformed(ActionEvent e) {
            	container.dispose();
            	LoginController.tryLogin();
            }
    	};
    }

    // ADMIN
	public static ActionListener PaneReload(JMenuItem menu_viewAll, ArrayList<JPanel> viewPanels, JMenu menuReload, AdminMain_Frame container, String string, String loginId, String username) {
		return new ActionListener() {
			public void actionPerformed(ActionEvent e) {
                JButton fakeBtn = new JButton();
                menu_viewAll.doClick();
                if(string.equals("users")) {
                	__Admin_Users_AddUser_Dialog registrationForm = new __Admin_Users_AddUser_Dialog();
                    registrationForm.addUser();
                    __Admin_Users_ViewAll_Pane reloadPane = new __Admin_Users_ViewAll_Pane();
                    AdminMain_Frame adminGUI = new AdminMain_Frame(loginId, username);
                    ActionListener menuReload = Router.PaneSwitcher(viewPanels, reloadPane, adminGUI);
                    fakeBtn.addActionListener(menuReload);              
        	        UsersDAO.usersHashLoadAll();
        	        adminGUI.setVisible(true);
                }
                else if(string.equals("arrangements")) {
                	__Admin_Arrangements_CreateNew_Dialog registrationForm = new __Admin_Arrangements_CreateNew_Dialog();
                    registrationForm.addArrangement();
                    __Admin_Arrangements_ViewAll_Pane reloadPane = new __Admin_Arrangements_ViewAll_Pane();
                    AdminMain_Frame adminGUI = new AdminMain_Frame(loginId, username);
                    viewPanels.add(reloadPane);
                    ActionListener menuReload = Router.PaneSwitcher(viewPanels, reloadPane, adminGUI);
                    fakeBtn.addActionListener(menuReload); 
                    ArrangementsDAO.arrangementsHashLoad();
                    adminGUI.setVisible(true);
                }
                
                fakeBtn.doClick(); 
            }
		};
	}
	
	// AGENT
	public static ActionListener PaneReload_ag(JMenuItem menu_viewAll, List<JPanel> viewPanels, JMenu menuReload, JFrame container, String string, String loginId, String username) {
		return new ActionListener() {
			public void actionPerformed(ActionEvent e) {
                JButton fakeBtn = new JButton();
                menu_viewAll.doClick();
                 if(string.equals("arrangements")) {
                	__Agent_Arrangements_CreateNew_Dialog registrationForm = new __Agent_Arrangements_CreateNew_Dialog();
                    registrationForm.addArrangement(loginId);
                    __Agent_Arrangements_MyArrangements_Pane reloadPane = new __Agent_Arrangements_MyArrangements_Pane(loginId);
                    AgentMain_Frame agentGUI = new AgentMain_Frame(loginId, username);
                    viewPanels.add(reloadPane);
                    ActionListener menuReload = Router.PaneSwitcher(viewPanels, reloadPane, agentGUI);
                    fakeBtn.addActionListener(menuReload);
                    ArrangementsDAO.arrangementsHashLoad();
                    agentGUI.setVisible(true);
                }
                fakeBtn.doClick(); 
            }
		};
	}

}