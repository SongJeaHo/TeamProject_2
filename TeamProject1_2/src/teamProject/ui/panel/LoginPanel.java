package teamProject.ui.panel;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import teamProject.constant.Resources;
import teamProject.ui.MainFrame;
import teamProject.ui.constant.Font;
import teamProject.ui.constant.Panels;
import teamProject.ui.util.DataBase;

public class LoginPanel extends BasePanel{
	private static final long serialVersionUID = 1L;
	JButton signupButton, loginButton;
	JLabel idState, pwState;
	JTextField idText, pwText;
	String ID, PW;
	ImageIcon login, signup, loginError, emptyState;

	public LoginPanel() {
		super();

		for(int i=0;i<menuButton.length;i++) {
			remove(menuButton[i]);
		}

		setImageLabel(backGround, Resources.LOGIN_BACKGROUND);
		login = new ImageIcon(Resources.LOGIN_BUTTON);
		signup = new ImageIcon(Resources.SIGNUP_BUTTON);
		loginError = new ImageIcon(Resources.LOGIN_ERROR);
		emptyState = new ImageIcon();

		backGround.setBounds(0, -20, 1280, 800);

		signupButton = new JButton(signup);
		signupButton.setBounds(384, 588, 250, 60);
		signupButton.addActionListener(e -> {
			// 기존 데이터베이스 불러오기 
			System.out.println("SIGNUP_NEW_ID");
			signUp();
		});

		loginButton = new JButton(login);
		loginButton.setBounds(646, 588, 250, 60);
		loginButton.addActionListener(e -> {
			// 기존 데이터베이스 불러오기 
			System.out.println("LOGIN");
			logIn();
		});


		idText = new JTextField(10);
		idText.setBounds(491, 438, 336, 40);
		idText.setFont(Font.GOTHIC_PLAIN_20);

		pwText = new JTextField(10);
		pwText.setBounds(491, 518, 336, 40);
		pwText.setFont(Font.GOTHIC_PLAIN_20);

		idState = new JLabel();
		idState.setBounds(827, 438, 40, 40);
		idState.setIcon(emptyState);

		pwState = new JLabel();
		pwState.setBounds(827, 518, 40, 40);
		pwState.setIcon(emptyState);

		add(idState);
		add(pwState);
		add(idText);
		add(pwText);
		add(loginButton);
		add(signupButton);
		add(backGround);
	}

	private void setIcon(JLabel label, ImageIcon icon) {
		label.setVisible(false);
		label.setIcon(icon);
		label.setVisible(true);
	}

	private boolean isEmpty() {
		boolean flag = true;

		ID = idText.getText();
		PW = pwText.getText();

		if(ID.equals("")) {
			JOptionPane.showMessageDialog(null, "아이디가 입력되지 않았습니다.","ERROR_MESSAGE", JOptionPane.ERROR_MESSAGE);
			setIcon(idState, loginError);
			flag = false;
		} else {
			setIcon(idState, emptyState);
		}

		if(PW.equals("")) {
			JOptionPane.showMessageDialog(null, "비밀번호가 입력되지 않았습니다.","ERROR_MESSAGE", JOptionPane.ERROR_MESSAGE);
			setIcon(pwState, loginError);
			flag = false;
		} else {
			setIcon(pwState, emptyState);
		}

		if(flag) return true; else return false;
	}

	private void signUp() {
		if(isEmpty()) {
			try {
				FileReader fileReader = new FileReader("res/data/" + ID + "/password.txt");
				fileReader.close();
				JOptionPane.showMessageDialog(null, "이미 등록된 아이디입니다.","ERROR_MESSAGE", JOptionPane.ERROR_MESSAGE);
				setIcon(idState, loginError);
			} catch (FileNotFoundException e) {
				int result = JOptionPane.showConfirmDialog(null, "새로운 계정을 등록할까요?", 
						"", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
				if(result == JOptionPane.YES_OPTION) {
					if(DataBase.setNewDataBase(ID, PW))
						JOptionPane.showMessageDialog(null, "새로운 계정이 등록되었습니다!");
				}
			} catch (IOException e) {
				e.getStackTrace();
			}

		}
	}

	private void logIn() {
		if(isEmpty()) {
			try {
				BufferedReader reader = new BufferedReader(new FileReader("res/data/" + ID + "/password.txt"));
				String s, buffer = "";
				while((s = reader.readLine()) != null) {
					buffer += s;
				}

				if(buffer.equals(PW)) {
					JOptionPane.showMessageDialog(null, "로그인 되었습니다!");
					System.out.println("LOGIN COMPLETE");
					System.out.println(ID);
					MainFrame.getInstance().switchPanel(Panels.HOME);
				} else {
					JOptionPane.showMessageDialog(null, "비밀번호가 틀렸습니다.","ERROR_MESSAGE", JOptionPane.ERROR_MESSAGE);
					setIcon(pwState, loginError);
				}

				reader.close();
			} catch (FileNotFoundException e) {
				try {
					JOptionPane.showMessageDialog(null, "등록된 사용자가 아닙니다.","ERROR_MESSAGE", JOptionPane.ERROR_MESSAGE);
					setIcon(idState, loginError);
				} catch (Exception exception) {
					exception.getStackTrace();
				}
			} catch (IOException e) {
				e.getStackTrace();
			}
		}
	}

	public void setUserID(BasePanel[] panels) {
		for(int i=0; i<panels.length;i++) {
			panels[i].setID(ID);
		}
	}
}
