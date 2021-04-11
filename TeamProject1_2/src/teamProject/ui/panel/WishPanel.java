package teamProject.ui.panel;

import java.awt.Color;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import teamProject.constant.Colors;
import teamProject.ui.constant.Font;
import teamProject.ui.util.Data;
import teamProject.ui.util.DataBase;

public class WishPanel extends BasePanel {
	private static final long serialVersionUID = 1L;
	JLabel noDataLabel1, noDataLabel2;
	WishContents[] wishContents = new WishContents[7];
	JButton btnAdd;
	int cnt=0;

	public WishPanel() {
		super();

		noDataLabel1 = new JLabel("데이터가 없습니다");
		transparentLabel(noDataLabel1);
		noDataLabel1.setBounds(240, 100, 500, 60);
		noDataLabel1.setFont(Font.GOTHIC_PLAIN_36);

		noDataLabel2 = new JLabel("아래 추가버튼으로 데이터를 추가해보세요");
		transparentLabel(noDataLabel2);
		noDataLabel2.setBounds(140, 180, 700, 60);
		noDataLabel2.setFont(Font.GOTHIC_PLAIN_36);

		contentsPanel.add(noDataLabel1);
		contentsPanel.add(noDataLabel2);

		RecordPanel recordPanel = new RecordPanel();
		recordPanel.setVisible(false);

		btnAdd = new JButton("추가하기");
		btnAdd.setBounds(900, 600, 80, 80);
		btnAdd.addActionListener(e->{
			if(cnt >= 7) {
				JOptionPane.showMessageDialog(null, "위시리스트는 7개까지 생성 가능합니다.","ERROR_MESSAGE", JOptionPane.ERROR_MESSAGE);
			} else {
				recordPanel.setVisible(true);
				btnAdd.setVisible(false);
				refresh();
			}
		});
		add(recordPanel);
		contentsPanel.add(btnAdd);

		for(int i=0; i<wishContents.length;i++) {
			wishContents[i] = new WishContents();
			wishContents[i].setBounds(0 + (500) * (i%2), 0 + (170 * (i/2)), 480, 160);
			wishContents[i].setBackground(Color.WHITE);
			wishContents[i].setVisible(false);
			contentsPanel.add(wishContents[i]);
		}

		add(contentsPanel);

		add(backGround);
	}

	private void setContentsPanel() {
		ArrayList<Data> wishList = new ArrayList<Data>();
		wishList = DataBase.getData(userID + "/wish.txt" );

		cnt = wishList.size();

		updateWishPanel(wishList);
	}

	private void updateWishPanel(ArrayList<Data> dataList) {
		for(int i=0; i<wishContents.length;i++) {
			contentsPanel.remove(wishContents[i]);
		}

		for(int i=0; i<wishContents.length;i++) {
			wishContents[i] = new WishContents();
			wishContents[i].setBounds(0 + (500) * (i%2), 0 + (170 * (i/2)), 480, 160);
			wishContents[i].setBackground(Color.WHITE);
			wishContents[i].setVisible(false);
			contentsPanel.add(wishContents[i]);
		}

		System.out.println(dataList.size());
		if(dataList.size()!=0) {
			noDataLabel1.setVisible(false);
			noDataLabel2.setVisible(false);
		} else {
			noDataLabel1.setVisible(true);
			noDataLabel2.setVisible(true);
		}

		int i=0;
		for(Data data : dataList) {
			wishContents[i].setContents(data);
			wishContents[i++].setVisible(true);
		}

		for( ; i<wishContents.length;i++) {
			wishContents[i].setVisible(false);
		}
	}

	@Override
	public void refresh() {
		contentsPanel.setVisible(false);
		setContentsPanel();
		contentsPanel.setVisible(true);
	}

	class WishContents extends JPanel{
		private static final long serialVersionUID = 1L;

		JLabel[] dataLabels = new JLabel[5];
		JLabel[] labels = new JLabel[2];
		JLabel[] bar = new JLabel[2];
		JButton[] buttons = new JButton[2];
		String ID;

		public WishContents() {
			super();
			setLayout(null);
			dataLabels[0] = new JLabel();
			dataLabels[1] = new JLabel();
			dataLabels[2] = new JLabel();
			dataLabels[3] = new JLabel();
			dataLabels[4] = new JLabel();

			labels[0] = new JLabel("전체금액");
			labels[1] = new JLabel("현재금액");

			for(int i=0; i<labels.length;i++) {
				transparentLabel(labels[i]);
				labels[i].setFont(Font.GOTHIC_PLAIN_20);
				labels[i].setHorizontalAlignment(SwingConstants.LEFT);
				labels[i].setBounds(160, 24 + (32*i), 74, 29);
				add(labels[i]);
			}

			bar[0] = new JLabel();
			bar[1] = new JLabel();

			setImageLabel(bar[1], teamProject.constant.Resources.BAR_EMPTY);
			setImageLabel(bar[0], teamProject.constant.Resources.BAR_FILL);

			for(int i=0; i<bar.length;i++) {
				bar[i].setBounds(20, 100, 440, 16);
				add(bar[i]);
			}
		}

		public void setContents(Data data){
			dataLabels[0].setText(data.name);
			dataLabels[0].setBounds(20, 24, 130, 29);

			dataLabels[1].setText(data.date);
			dataLabels[1].setBounds(20, 56, 130, 29);

			dataLabels[2].setText(data.money_or_startTime);
			dataLabels[2].setBounds(250, 24, 100, 29);

			dataLabels[3].setText(data.category_or_endTime);
			dataLabels[3].setBounds(250, 56, 100, 29);

			int persent = (int)(Float.parseFloat(data.category_or_endTime) 
					/ Float.parseFloat(data.money_or_startTime) * 100);
			dataLabels[4].setText(Integer.toString(persent) +"%");
			dataLabels[4].setBounds(360, 27, 100, 59);
			bar[0].setBounds(20, 100, 440 * persent / 100, 16);
			ID = data.ID;

			for(int i=0;i<5;i++) {
				transparentLabel(dataLabels[i]);
				dataLabels[i].setFont(Font.GOTHIC_PLAIN_20);
				dataLabels[i].setHorizontalAlignment(SwingConstants.LEFT);
				if(i==(dataLabels.length-1))
					dataLabels[i].setHorizontalAlignment(SwingConstants.RIGHT);
				add(dataLabels[i]);
			}
			dataLabels[4].setFont(Font.GOTHIC_BOLD_36);

			int totalMoney  = Integer.parseInt(data.money_or_startTime);
			int presentMoney = Integer.parseInt(data.category_or_endTime);

			buttons[0] = new JButton("삭제하기");
			buttons[1] = new JButton("저금하기");

			buttons[1].addActionListener(e -> {
				DataBase.saveMoney(userID, data.name, data.date, data.ID, totalMoney, presentMoney);
				refresh();
			});

			buttons[0].addActionListener(e->{
				removeWish();
			});

			for(int i=0; i<buttons.length;i++) {
				buttons[i].setBounds(252 +(110*i), 122, 98, 32);
				buttons[i].setFont(Font.GOTHIC_PLAIN_16);
				add(buttons[i]);
			}
		}

		public void removeWish() {
			DataBase.removeData(userID, ID, "wish");
			System.out.println(ID);
			refresh();
		}
	}

	class RecordPanel extends JPanel{
		private static final long serialVersionUID = 1L;
		JLabel[] titleLabels = new JLabel[5];
		JLabel[] explainLabels = new JLabel[4];
		JTextField[] inputField = new JTextField[4];
		JButton[] buttons = new JButton[2];

		public RecordPanel() {
			setLayout(null);
			setBounds(240, 40, 980, 700);
			setBackground(Color.white);

			titleLabels[0] = new JLabel("저축/위시리스트 등록하기");
			titleLabels[1] = new JLabel("이름");
			titleLabels[2] = new JLabel("날짜");
			titleLabels[3] = new JLabel("총 금액");
			titleLabels[4] = new JLabel("현재 모은 금액");

			titleLabels[0].setBounds(60, 60, 420, 54);

			for(int i=0; i<titleLabels.length;i++) {
				transparentLabel(titleLabels[i]);
				titleLabels[i].setHorizontalAlignment(SwingConstants.LEFT);
				titleLabels[i].setFont(Font.GOTHIC_PLAIN_36);
				if(i!=0) {
					titleLabels[i].setBounds(60, i*100 + 40, 240, 54);
				}
				add(titleLabels[i]);
			}

			explainLabels[0] = new JLabel("이름은 최대 8글자 까지 입력 가능합니다.");
			explainLabels[1] = new JLabel("오늘 날짜가 자동으로 입력됩니다.");
			explainLabels[2] = new JLabel("금액은 최대 99999999원까지 입력 가능합니다.");
			explainLabels[3] = new JLabel("현재 모은 금액이 없다면 0으로 입력해주세요.");

			for(int i=0; i<explainLabels.length; i++) {
				transparentLabel(explainLabels[i]);
				explainLabels[i].setHorizontalAlignment(SwingConstants.LEFT);
				explainLabels[i].setFont(Font.GOTHIC_PLAIN_16);
				explainLabels[i].setBounds(60, i*100 + 200, 480, 24);
				add(explainLabels[i]);
			}

			for(int i=0;i<inputField.length;i++) {
				inputField[i] = new JTextField(7);
				inputField[i].setBackground(Colors.inputGrey);
				inputField[i].setFont(Font.GOTHIC_PLAIN_28);
				inputField[i].setBounds(320, i*100 + 140, 600, 54);
				add(inputField[i]);
			}
			inputField[1].setText(DataBase.getTodayString());
			inputField[1].setEditable(false);

			buttons[0] = new JButton("취소");
			buttons[1] = new JButton("등록하기");
			for(int i=0;i<buttons.length;i++) {
				buttons[i].setBounds(540 +(240*i), 560, 180, 60);
				buttons[i].setFont(Font.GOTHIC_PLAIN_28);
				add(buttons[i]);
			}

			buttons[0].addActionListener(e->{
				this.setVisible(false);
				btnAdd.setVisible(true);
			});
			buttons[1].addActionListener(e->{
				boolean flag = true;
				
				for(int i=0; i<inputField.length;i++) {
					if(inputField[i].getText().equals("")) {
						inputField[i].setText("값이 비어있습니다.");
						inputField[i].setBackground(Colors.errorColor);
						flag = false;
					} else if ((i==2 || i==3)
							&& DataBase.checkDigit(inputField[i].getText())==0) {
						inputField[i].setText("숫자만 입력해주세요");
						inputField[i].setBackground(Colors.errorColor);
						flag = false;
					} else {
						inputField[i].setBackground(Colors.inputGrey);
					}
				}
				
				if(flag == false)
					return;
				
				if(inputField[0].getText().length() > 8) {
					inputField[0].setText("이름이 너무 깁니다");
					inputField[0].setBackground(Colors.errorColor);
					flag = false;
				} else {
					inputField[0].setBackground(Colors.inputGrey);
				}

				if(inputField[2].getText().length() > 8){
					inputField[2].setText("금액이 너무 큽니다");
					inputField[2].setBackground(Colors.errorColor);
					flag = false;
				} else {
					inputField[0].setBackground(Colors.inputGrey);
				}

				if(inputField[2].getText().length() > 8){
					inputField[2].setText("금액이 너무 큽니다");
					inputField[2].setBackground(Colors.errorColor);
					flag = false;
				} else {
					inputField[2].setBackground(Colors.inputGrey);
				}

				if(flag == true){
					DataBase.addData(userID, inputField[0].getText(), inputField[1].getText(), inputField[2].getText(), inputField[3].getText(), "/wish");
					btnAdd.setVisible(true);
					inputField[0].setText("");
					inputField[2].setText("");
					inputField[3].setText("");
					this.setVisible(false);
					refresh();
				}
			});

		}
	}
}