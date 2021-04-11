package teamProject.ui.panel;

import java.awt.Color;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import teamProject.constant.Colors;
import teamProject.constant.Resources;
import teamProject.ui.MainFrame;
import teamProject.ui.constant.Font;
import teamProject.ui.constant.Panels;

// 모든 페이지에 기본적으로 들어갈 내용들을 포함. 추후 다른 페이지들이 이 클래스를 상속받음 
public class BasePanel extends JPanel{
	private static final long serialVersionUID = 1L;
	protected JLabel backGround;
	protected String userID = "";
	protected JButton[] menuButton = new JButton[4];
	JPanel contentsPanel;
	public BasePanel() {
		super();
		setLayout(null);
		
		backGround = new JLabel();
		setImageLabel(backGround, Resources.BACKGROUND);
		backGround.setBounds(0, 0, 1280, 800);
		
		menuButton[0] = new JButton("홈");
		menuButton[0].addActionListener(e -> {
        	MainFrame.getInstance().switchPanel(Panels.HOME);
        });
		menuButton[1] = new JButton("통계");
		menuButton[1].addActionListener(e -> {
        	MainFrame.getInstance().switchPanel(Panels.STATE);
        });
		menuButton[2] = new JButton("저축");
		menuButton[2].addActionListener(e -> {
        	MainFrame.getInstance().switchPanel(Panels.WISH);
        });
		menuButton[3] = new JButton("설정");
		menuButton[3].addActionListener(e -> {
        	MainFrame.getInstance().switchPanel(Panels.SETTING);
        });
		
		for(int i=0; i<menuButton.length; i++) {
			menuButton[i].setBounds(20, 50 + 80*i, 160, 60);
			menuButton[i].setFont(Font.GOTHIC_PLAIN_36);
			menuButton[i].setForeground(Colors.myFontColor);
			transparentButton(menuButton[i]);
			add(menuButton[i]);
		}
		
		contentsPanel = new JPanel();
		contentsPanel.setLayout(null);
		contentsPanel.setBackground(Color.WHITE);
		contentsPanel.setBounds(240, 60, 980, 680);
	}
	
	protected void transparentButton(JButton button) {
		button.setForeground(Colors.myFontColor);
		button.setHorizontalAlignment(SwingConstants.CENTER);
		button.setOpaque(false);
		button.setBorderPainted(false);
		button.setContentAreaFilled(false);
	};

	protected void transparentLabel(JLabel label) {
		label.setForeground(Colors.myFontColor);
		label.setOpaque(false);
		label.setHorizontalAlignment(SwingConstants.CENTER);
	};
	
	protected void setImageLabel(JLabel label, String imageNav) {
		ImageIcon imageIcon = new ImageIcon(imageNav);
		label.setIcon(imageIcon);
	};
	
	public String getID() {
		return userID;
	};
	
	public void setID(String userID) {
		this.userID = userID;
	};
	
	public void refresh() {
		revalidate();
		repaint();
	};
}
