package teamProject.ui.panel;

import java.awt.Color;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import teamProject.constant.Colors;
import teamProject.ui.constant.Font;
import teamProject.ui.constant.Panels;
import teamProject.ui.util.DataBase;

public class SettingPanel extends BasePanel{
	private static final long serialVersionUID = 1L;
	JLabel title;
	JButton btnPW, btnCategory, btnInit;
	JPanel panelPW, panelCategory;


	public SettingPanel() {
		super();

		menuButton[Panels.SETTING-1].setVisible(false);
		menuButton[Panels.SETTING-1].setForeground(Colors.myGreen);
		menuButton[Panels.SETTING-1].setVisible(true);

		title = new JLabel("설정");
		transparentLabel(title);
		title.setBounds(20, 10, 400, 60);
		title.setFont(Font.GOTHIC_PLAIN_36);
		title.setHorizontalAlignment(SwingConstants.LEFT);

		btnPW = new JButton("비밀번호 변경");
		transparentButton(btnPW);
		btnPW.setBounds(0,  90,  400, 40);
		btnPW.setFont(Font.GOTHIC_PLAIN_28);
		btnPW.setHorizontalAlignment(SwingConstants.LEFT);
		btnPW.addActionListener(e->{
			panelPW.setVisible(true);;
		});

		btnCategory = new JButton("카테고리 수정");
		transparentButton(btnCategory);
		btnCategory.setBounds(0,  150,  400, 40);
		btnCategory.setFont(Font.GOTHIC_PLAIN_28);
		btnCategory.setHorizontalAlignment(SwingConstants.LEFT);
		btnCategory.addActionListener(e->{
			panelPW.setVisible(false);
			setPanelCategory();
		});

		btnInit = new JButton("데이터 초기화");
		transparentButton(btnInit);
		btnInit.setBounds(0,  210,  400, 40);
		btnInit.setFont(Font.GOTHIC_PLAIN_28);
		btnInit.setHorizontalAlignment(SwingConstants.LEFT);
		btnInit.addActionListener(e->{
			panelPW.setVisible(false);
			panelCategory.setVisible(false);
			int result = JOptionPane.showConfirmDialog(null, 
					"초기화를 진행할까요?\n한번 삭제한 데이터는 다시 복구가 불가능합니다.", 
					"", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
			if(result == JOptionPane.YES_OPTION) {
				JOptionPane.showMessageDialog(null, "새로운 정보 등록을 위해 현재소지금 입력창으로 이동합니다.\n"
						+ "소지금 입력창에서 입력을 취소할 경우 초기화가 중지됩니다.");
				if(DataBase.initialization(userID)) {
					JOptionPane.showMessageDialog(null, "초기화가 완료되었습니다!");
				}
			}
		});

		panelPW = new JPanel();
		panelPW.setBounds(360, 0, 580, 680);
		panelPW.setBackground(Color.white);
		panelPW.setLayout(null);

		panelCategory = new JPanel();
		panelCategory.setBounds(360, 0, 580, 680);
		panelCategory.setBackground(Color.white);
		panelCategory.setLayout(null);

		JLabel pwTitle = new JLabel("비밀번호 변경");
		transparentLabel(pwTitle);
		pwTitle.setFont(Font.GOTHIC_PLAIN_28);
		pwTitle.setBounds(190, 00, 240, 60);
		panelPW.add(pwTitle);

		JLabel[] pwText = {new JLabel("이번 비밀번호"),
				new JLabel("새 비밀번호"),
				new JLabel("새 비밀번호 확인")	};
		for(int i=0; i<pwText.length;i++) {
			transparentLabel(pwText[i]);
			pwText[i].setFont(Font.GOTHIC_PLAIN_20);
			pwText[i].setHorizontalAlignment(SwingConstants.LEFT);
			pwText[i].setBounds(20, 100 + (60*i), 160, 40);
			panelPW.add(pwText[i]);
		}

		JTextField[] pwInput = new JTextField[3];
		for(int i=0; i<pwInput.length;i++) {
			pwInput[i] = new JTextField();
			pwInput[i].setFont(Font.GOTHIC_PLAIN_18);
			pwInput[i].setHorizontalAlignment(SwingConstants.LEFT);
			pwInput[i].setBounds(200, 100 + (60*i), 375, 40);
			pwInput[i].setBackground(Colors.inputGrey);
			panelPW.add(pwInput[i]);
		}

		JLabel[] pwError = {new JLabel("이전 비밀번호가 일치하지 않습니다."),
				new JLabel("새 비밀번호와 새 비밀번호 확인이 일치하지 않습니다."),
				new JLabel("입력되지 않은 값이 있습니다.")};
		for(int i=0; i<pwError.length;i++) {
			transparentLabel(pwError[i]);
			pwError[i].setFont(Font.GOTHIC_PLAIN_16);
			pwError[i].setForeground(Colors.errorColor);
			pwError[i].setHorizontalAlignment(SwingConstants.CENTER);
			pwError[i].setBounds(110, 280 + (24*i), 400, 20);
			pwError[i].setVisible(false);
			panelPW.add(pwError[i]);
		}

		JButton btnChangePW = new JButton("변경하기");
		btnChangePW.setBounds(160,  360,  300, 48);
		btnChangePW.setFont(Font.GOTHIC_PLAIN_28);
		btnChangePW.setHorizontalAlignment(SwingConstants.CENTER);
		btnChangePW.addActionListener(e->{
			int result = JOptionPane.showConfirmDialog(null, "비밀번호를 변경할까요?", 
					"", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
			if(result == JOptionPane.YES_OPTION) {
				int flag = DataBase.ChangePassword(userID, pwInput[0].getText(), 
						pwInput[1].getText(), pwInput[2].getText());

				if(flag==1) {
					JOptionPane.showMessageDialog(null, "비밀번호가 변경되었습니다.");
					for(int i=0; i<pwError.length;i++) {
						pwError[i].setVisible(false);
					}

					for(int i=0; i<pwInput.length; i++) {
						pwInput[i].setText("");
					}
					return;
				}

				if(flag%2==0) {
					pwError[0].setVisible(true);
				}else {
					pwError[0].setVisible(false);
				}

				if(flag%3==0) {
					pwError[1].setVisible(true);
				}else {
					pwError[1].setVisible(false);
				}

				if(flag%5==0) {
					pwError[2].setVisible(true);
				}else {
					pwError[2].setVisible(false);
				}

				JOptionPane.showMessageDialog(null, "비밀번호 변경에 실패하였습니다.",
						"ERROR_MESSAGE", JOptionPane.ERROR_MESSAGE);
			} else {
				JOptionPane.showMessageDialog(null, "취소되었습니다!");
			}
		});

		panelPW.add(btnChangePW);
		panelPW.setVisible(false);
		
		panelCategory.setVisible(false);

		contentsPanel.add(title);
		contentsPanel.add(btnPW);
		contentsPanel.add(btnCategory);
		contentsPanel.add(btnInit);
		contentsPanel.add(panelPW);
		contentsPanel.add(panelCategory);

		add(contentsPanel);
		add(backGround);
	}

	private void setPanelCategory() {
		panelCategory.removeAll();
		String[] data = DataBase.getStringData(userID + "/category.txt");
		JPanel[] categorys = new JPanel[data.length/2];

		for(int i=0; i<(data.length/2); i++){
			categorys[i] = new JPanel();
			categorys[i].setLayout(null);
			categorys[i].setBounds(20, 10 + (70*i), 540, 60);
			categorys[i].setBackground(Color.white);

			JLabel title = new JLabel(data[i*2]);
			transparentLabel(title);
			title.setBounds(10, 10, 300, 40);
			title.setFont(Font.GOTHIC_PLAIN_28);
			title.setHorizontalAlignment(SwingConstants.LEFT);

			JButton[] buttons = new JButton[2];
			buttons[0] = new JButton("수정");
			buttons[1] = new JButton("삭제");
			
			final int cnt = i;
			buttons[0].addActionListener(e->{
				System.out.println(data[cnt*2+1]);
				if(data[cnt*2+1].equals("6") || data[cnt*2+1].equals("7")) {
					JOptionPane.showMessageDialog(null, "수정이 불가능한 카테고리입니다.",
							"ERROR_MESSAGE", JOptionPane.ERROR_MESSAGE);
					return;
				}
				String buffer;
				buffer = JOptionPane.showInputDialog(null, "변경할 이름을 입력해주세요", 
						"", JOptionPane.OK_CANCEL_OPTION);
				if(buffer!=null) {
					for(int j=0; j<data.length/2;j++) {
						if(data[j*2].equals(buffer)) {
							JOptionPane.showMessageDialog(null, "이미 등록된 카테고리입니다.",
									"ERROR_MESSAGE", JOptionPane.ERROR_MESSAGE);
							return;
						}
					}
					DataBase.updateCategory(userID, data[cnt*2], buffer);
					data[cnt*2] = buffer;
					DataBase.writeData(userID +"/categoty.txt", DataBase.setFileContents(data));
					panelCategory.setVisible(false);
					setPanelCategory();
				}
			});

			buttons[1].addActionListener(e->{
				if(data[cnt*2+1].equals("6") || data[cnt*2+1].equals("7")) {
					JOptionPane.showMessageDialog(null, "수정이 불가능한 카테고리입니다.",
							"ERROR_MESSAGE", JOptionPane.ERROR_MESSAGE);
					return;
				}
				
				int result = JOptionPane.showConfirmDialog(null, "카테고리를 삭제할까요?\n"
						+ "삭제한 카테고리에 있던 데이터는 기타 카테고리로 옮겨집니다", 
						"", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
				if(result == JOptionPane.YES_OPTION) {
					DataBase.updateCategory(userID, data[cnt*2], "기타");
					String[] newData = new String[data.length-2];

					for(int k=0, j=0; k<data.length/2 && j<data.length/2; k++, j++) {
						newData[k*2] = data[j*2];
						newData[k*2+1] = Integer.toString(j);
						
						if(newData[k*2].equals("저축"))
							newData[k*2+1] = "6";
						
						if(newData[k*2].equals("기타"))
							newData[k*2+1] = "7";
						
						if(data[cnt*2]==data[j*2]) {
							k-=1;
						}
					}

					DataBase.writeData(userID +"/category.txt", DataBase.setFileContents(newData));
					panelCategory.setVisible(false);
					setPanelCategory();
				} else {
					JOptionPane.showMessageDialog(null, "취소되었습니다!");
					return;
				}
			});

			for(int j=0; j<buttons.length;j++) {
				buttons[j].setFont(Font.GOTHIC_PLAIN_20);
				buttons[j].setBounds(360 + 90*j, 10, 80, 40);
				buttons[j].setHorizontalAlignment(SwingConstants.CENTER);
				categorys[i].add(buttons[j]);
			}

			categorys[i].add(title);
			panelCategory.add(categorys[i]);
		}
		
		JButton btnAdd = new JButton("추가하기");
		btnAdd.setBounds(380, 500, 170, 80);
		btnAdd.setFont(Font.GOTHIC_PLAIN_20);
		
		btnAdd.addActionListener(e->{
			if(data.length>=14){
				JOptionPane.showMessageDialog(null, "카테고리는 최대 7개까지 등록 가능합니다.",
						"ERROR_MESSAGE", JOptionPane.ERROR_MESSAGE);
				return;
			}
			
			String buffer;
			buffer = JOptionPane.showInputDialog(null, "변경할 이름을 입력해주세요", 
					"", JOptionPane.OK_CANCEL_OPTION);
			
			if(buffer!=null) {
				for(int i=0; i<data.length/2;i++) {
					if(data[i*2].equals(buffer)) {
						JOptionPane.showMessageDialog(null, "이미 등록된 카테고리입니다.",
								"ERROR_MESSAGE", JOptionPane.ERROR_MESSAGE);
						return;
					}
				}
				String[] newData = new String[data.length+2];
				for(int i=0; i<newData.length/2; i++) {
					if(data[2*i+1].equals("6")) {
						System.out.println(i);
						newData[2*i] = buffer;
						newData[2*i+1] = Integer.toString(i);
						
						newData[2*(i+1)] = "저축";
						newData[2*(i+1)+1] = "6";
						
						newData[2*(i+2)] = "기타";
						newData[2*(i+2)+1] = "7";
						
						break;
					}
					
					newData[i*2] = data[i*2];
					newData[i*2+1] = Integer.toString(i);
				}
				DataBase.writeData(userID +"/category.txt", DataBase.setFileContents(newData));
				panelCategory.setVisible(false);
				setPanelCategory();
			}
		});

		panelCategory.add(btnAdd);
		panelCategory.setVisible(true);
	}
}
