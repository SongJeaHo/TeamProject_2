package teamProject.ui;

import java.awt.Color;

import javax.swing.JFrame;

import teamProject.ui.constant.Panels;
import teamProject.ui.panel.BasePanel;
import teamProject.ui.panel.HomePanel;
import teamProject.ui.panel.LoginPanel;
import teamProject.ui.panel.WishPanel;
import teamProject.ui.panel.SettingPanel;
import teamProject.ui.panel.StatePanel;

public class MainFrame extends JFrame {
	private static final long serialVersionUID = 1L;
    private static MainFrame instance = null;

    public static MainFrame getInstance() {
        if (instance == null)
            instance = new MainFrame();

        return instance;
    }
    
    private LoginPanel login = new LoginPanel();

    private BasePanel[] panels = {
    		login,
    		new HomePanel(),
    		new StatePanel(),
    		new WishPanel(),
    		new SettingPanel()
    		};
    
    private volatile int currentPanel = Panels.LOGIN;

    private MainFrame() {
        super();
        setBounds(0, 0, 1280, 800);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBackground(Color.WHITE);
        setResizable(false);
        add(panels[0]);
        
        setVisible(true);
    }

    public void switchPanel(int id) {
    	login.setUserID(panels);
        getContentPane().remove(panels[currentPanel]);
        panels[id].refresh();
        getContentPane().add(panels[id]);
        revalidate();
        repaint();

        currentPanel = id;
    }
}
