package teamProject.ui.panel;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Insets;
import java.util.ArrayList;
import java.util.Calendar;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingConstants;
import javax.swing.border.LineBorder;

import teamProject.constant.Colors;
import teamProject.ui.constant.Font;
import teamProject.ui.constant.Panels;
import teamProject.ui.util.Data;
import teamProject.ui.util.DataBase;

public class HomePanel extends BasePanel{
	private static final long serialVersionUID = 1L;
	JLabel[] moneyLabel = new JLabel[3];
	JLabel[] moneyTitle = new JLabel[3];
	String[] money = new String[3];
	JPanel calPanel, todoPanel, incomePanel, expensePanel;
	JLabel todoTitle, incomeTitle, expenseTitle;
	TodoRecordPanel todoRecordPanel;
	IncomeRecordPanel incomeRecordPanel;
	ExpenseRecordPanel expenseRecordPanel;
	final JScrollPane incomeSc, expenseSc, todoSc;

	private int lastYear, lastMonth, date;
	private Integer[] years = new Integer[50], months = new Integer[12];
	//private JLabel yearLabel, monthLabel;
	private JPanel weekPanel, dayPanel;
	private JButton dayButton[];
	Calendar calendar = Calendar.getInstance();

	public HomePanel() {
		super();

		todoRecordPanel = new TodoRecordPanel();
		todoRecordPanel.setVisible(false);
		add(todoRecordPanel);
		expenseRecordPanel = new ExpenseRecordPanel();
		expenseRecordPanel.setVisible(false);
		add(expenseRecordPanel);
		incomeRecordPanel = new IncomeRecordPanel();
		incomeRecordPanel.setVisible(false);
		add(incomeRecordPanel);

		menuButton[Panels.HOME-1].setVisible(false);
		menuButton[Panels.HOME-1].setForeground(Colors.myGreen);
		menuButton[Panels.HOME-1].setVisible(true);

		setCalPanel();

		todoPanel = new JPanel();
		todoPanel.setBackground(Color.white);
		incomePanel = new JPanel();
		incomePanel.setBackground(Color.white);
		expensePanel = new JPanel();
		expensePanel.setBackground(Color.white);

		incomeSc = new JScrollPane(incomePanel);
		incomeSc.setBounds(720, 540, 440, 200);
		incomeSc.setBorder(null);
		incomeSc.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
		incomeSc.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

		expenseSc = new JScrollPane(expensePanel);
		expenseSc.setBounds(720, 200, 440, 280);
		expenseSc.setBorder(null);
		expenseSc.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
		expenseSc.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

		todoSc = new JScrollPane(todoPanel);
		todoSc.setBounds(240, 540, 440, 200);
		todoSc.setBorder(null);
		todoSc.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
		todoSc.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

		add(todoSc);
		add(incomeSc);
		add(expenseSc);

		moneyTitle[0] = new JLabel(lastMonth + "월 잔고");
		moneyTitle[1] = new JLabel(lastMonth + "월 수입");
		moneyTitle[2] = new JLabel(lastMonth + "월 소비");

		for(int i=0; i<money.length; i++) {
			moneyTitle[i].setBounds(720, 50 + (40*i), 160, 40);
			moneyTitle[i].setFont(Font.GOTHIC_PLAIN_28);
			transparentLabel(moneyTitle[i]);
			moneyTitle[i].setHorizontalAlignment(SwingConstants.LEFT);
			add(moneyTitle[i]);
		}
		moneyTitle[0].setFont(Font.GOTHIC_PLAIN_36);
		moneyTitle[0].setForeground(Colors.myGreen);

		for(int i=0; i<money.length; i++) {
			moneyLabel[i] = new JLabel("");
			moneyLabel[i].setBounds(880, 50 + (40*i), 280, 40);
			moneyLabel[i].setFont(Font.GOTHIC_PLAIN_28);
			transparentLabel(moneyLabel[i]);
			moneyLabel[i].setHorizontalAlignment(SwingConstants.RIGHT);
			add(moneyLabel[i]);
		}
		moneyLabel[0].setFont(Font.GOTHIC_PLAIN_36);
		moneyLabel[0].setForeground(Colors.myGreen);

		add(backGround);
	}

	private void setCalPanel() {
		calPanel = new JPanel();

		calPanel.setBounds(240, 50, 440, 480);
		calPanel.setLayout(null);
		calPanel.setBackground(Color.white);
		calPanel.setBorder(new LineBorder(Colors.myGreen, 2));

		for(int i=0; i<50; i++) {
			years[i] = 2010 + i;
		}
		for(int i=0; i<12; i++) {
			months[i] = i+1;
		}

		lastYear = calendar.get(Calendar.YEAR);
		lastMonth = calendar.get(Calendar.MONTH)+1;
		date = calendar.get(Calendar.DATE);
		System.out.println(lastYear);
		System.out.println(lastMonth);

		JComboBox<Integer> yearList = new JComboBox<>(years);
		yearList.setSelectedIndex(calendar.get(Calendar.YEAR)-2010);
		yearList.addActionListener(e-> {
			lastYear = Integer.parseInt(yearList.getSelectedItem().toString());
			System.out.println("update");
			updateDayPanel();
		});
		yearList.setBounds(110, 20, 120, 36);
		yearList.setPreferredSize(new Dimension(110, 36));
		yearList.setFont(Font.GOTHIC_BOLD_20);
		yearList.setBackground(Color.WHITE);

		JComboBox<Integer> monthList = new JComboBox<>(months);
		monthList.setSelectedIndex(calendar.get(Calendar.MONTH));
		monthList.addActionListener(e->{
			lastMonth = Integer.parseInt(monthList.getSelectedItem().toString());
			System.out.println("update");
			updateDayPanel();
		});
		monthList.setBounds(250, 20, 80, 36);
		monthList.setPreferredSize(new Dimension(80, 36));
		monthList.setFont(Font.GOTHIC_BOLD_20);
		monthList.setBackground(Color.WHITE);

		weekPanel = new JPanel(new GridLayout(1, 7));
		weekPanel.setBackground(Color.white);
		JLabel[] weekLabel = {new JLabel("일"), new JLabel("월"), new JLabel("화"), 
				new JLabel("수"), new JLabel("목"), new JLabel("금"), new JLabel("토")};
		for(int i=0; i<7; i++) {
			weekLabel[i].setFont(Font.GOTHIC_PLAIN_28);
			weekLabel[i].setForeground(Colors.myFontColor);
			weekLabel[i].setHorizontalAlignment(SwingConstants.CENTER);
			transparentLabel(weekLabel[i]);
			weekPanel.add(weekLabel[i]);
		}
		weekPanel.setBounds(2, 70, 436, 30);

		dayPanel = new JPanel(new GridLayout(6, 7));
		dayPanel.setBounds(2, 120, 436, 358);
		dayPanel.setBackground(Color.WHITE);

		dayButton = new JButton[42];
		updateDayPanel();

		calPanel.add(yearList);
		calPanel.add(monthList);
		calPanel.add(weekPanel);
		calPanel.add(dayPanel);

		add(calPanel);
	}

	private void updateDayPanel() {
		if(!userID.equals("")) {
			for(int i=0; i<money.length;i++) {
				moneyLabel[i].setVisible(false);
				moneyTitle[i].setVisible(false);
			}

			updateMoneyLabel();

			for(int i=0; i<money.length;i++) {
				moneyLabel[i].setVisible(true);
				moneyTitle[i].setVisible(true);
			}
		}

		dayPanel.removeAll();
		dayPanel.setVisible(false);
		int START_DAY_OF_WEEK = 0;
		int END_DAY = 0;

		Calendar start = Calendar.getInstance();
		Calendar end = Calendar.getInstance();

		start.set(lastYear, lastMonth-1, 1);
		end.set(lastYear, lastMonth, 1);
		end.add(Calendar.DATE, -1);

		START_DAY_OF_WEEK = start.get(Calendar.DAY_OF_WEEK);
		END_DAY = end.get(Calendar.DATE);

		for(int i=0; i < (START_DAY_OF_WEEK-1); i++) {
			dayButton[i] = new JButton(" ");
			dayButton[i].setEnabled(false);
		}

		for(int i=0; i < END_DAY; i++) {
			dayButton[START_DAY_OF_WEEK +i-1] = new JButton(("" + (i+1)));
			dayButton[START_DAY_OF_WEEK +i-1].addActionListener(e -> {
				todoPanel.setVisible(false);
				expensePanel.setVisible(false);
				incomePanel.setVisible(false);

				JButton button = (JButton)(e.getSource());
				date = Integer.parseInt(button.getText());
				updateTodoPanel();
				updateExpensePanel();
				updateIncomePanel();

				todoPanel.setVisible(true);
				expensePanel.setVisible(true);
				incomePanel.setVisible(true);
			});
		}

		System.out.println(END_DAY+START_DAY_OF_WEEK);
		for(int i=END_DAY+START_DAY_OF_WEEK-1; i<42; i++) {
			dayButton[i] = new JButton(" ");
			dayButton[i].setEnabled(false);
		}

		Calendar.getInstance();
		for(int i=0; i<42; i++) {
			dayButton[i].setFont(Font.GOTHIC_PLAIN_28);
			dayButton[i].setMargin(new Insets(-10, -10, -10, -10));
			dayButton[i].setOpaque(false);
			dayButton[i].setBorderPainted(false);
			dayButton[i].setContentAreaFilled(false);
			dayButton[i].setForeground(Colors.myFontColor);
			if(!dayButton[i].getText().equals(" ")) {
				if(Integer.parseInt(dayButton[i].getText())==calendar.get(Calendar.DATE)
						&& lastYear==calendar.get(Calendar.YEAR)
						&& lastMonth==(calendar.get(Calendar.MONTH)+1)) {
					dayButton[i].setForeground(Colors.myGreen);
				}
			}
			dayPanel.add(dayButton[i]);
		}
		dayPanel.setVisible(true);
	}

	private void updateIncomePanel() {
		// TODO Auto-generated method stub
		incomePanel.removeAll();

		incomeTitle = new JLabel(lastMonth + "월 " + date + "일 수입");
		incomeTitle.setHorizontalAlignment(SwingConstants.LEFT);
		incomeTitle.setFont(Font.GOTHIC_BOLD_20);
		incomeTitle.setForeground(Colors.myGreen);
		incomePanel.add(incomeTitle);

		ArrayList<Data> dataList = new ArrayList<Data>();

		dataList = DataBase.getData(userID + "/income.txt", DataBase.DATE, DataBase.setDateString(lastYear, lastMonth, date));

		int cnt = dataList.size();

		incomePanel.setPreferredSize(new Dimension(320, 40*(cnt+2)));

		if(dataList.size()==0) {
			JLabel nodata = new JLabel("데이터가 없습니다.");
			nodata.setFont(Font.GOTHIC_PLAIN_20);
			cnt = 1;
			incomePanel.add(nodata);
		}else {
			DataPanel[] dataPanel = new DataPanel[dataList.size()];
			int i=0;
			for(Data data : dataList) {
				dataPanel[i] = new DataPanel(data, 1);
				incomePanel.add(dataPanel[i]);
			}
		}
		JButton btnAdd = new JButton("추가하기");
		btnAdd.setFont(Font.GOTHIC_PLAIN_16);
		btnAdd.addActionListener(e->{
			btnAdd.setVisible(false);
			calPanel.setVisible(false);
			incomePanel.setVisible(false);
			todoPanel.setVisible(false);
			expensePanel.setVisible(false);
			incomeRecordPanel.setVisible(true);
		});
		incomePanel.add(btnAdd);

		incomePanel.setLayout(new GridLayout(cnt+2, 1));
	}

	private void updateExpensePanel() {
		// TODO Auto-generated method stub
		expensePanel.removeAll();

		expenseTitle = new JLabel(lastMonth + "월 " + date + "일 소비");
		expenseTitle.setHorizontalAlignment(SwingConstants.LEFT);
		expenseTitle.setFont(Font.GOTHIC_BOLD_20);
		expenseTitle.setForeground(Colors.myGreen);
		expensePanel.add(expenseTitle);

		ArrayList<Data> dataList = new ArrayList<Data>();

		dataList = DataBase.getData(userID + "/expense.txt", DataBase.DATE, DataBase.setDateString(lastYear, lastMonth, date));

		int cnt = dataList.size();
		expensePanel.setPreferredSize(new Dimension(320, 40*(cnt+2)));

		if(dataList.size()==0) {
			JLabel nodata = new JLabel("데이터가 없습니다.");
			nodata.setFont(Font.GOTHIC_PLAIN_20);
			cnt=1;
			expensePanel.add(nodata);
		}else {
			DataPanel[] dataPanel = new DataPanel[dataList.size()];
			int i=0;
			for(Data data : dataList) {
				dataPanel[i] = new DataPanel(data, 2);
				expensePanel.add(dataPanel[i]);
			}
		}
		JButton btnAdd = new JButton("추가하기");
		btnAdd.setFont(Font.GOTHIC_PLAIN_16);
		btnAdd.addActionListener(e->{
			btnAdd.setVisible(false);
			calPanel.setVisible(false);
			incomePanel.setVisible(false);
			todoPanel.setVisible(false);
			expensePanel.setVisible(false);
			expenseRecordPanel.setCategory();
			expenseRecordPanel.setVisible(true);
		});
		expensePanel.add(btnAdd);

		expensePanel.setLayout(new GridLayout(cnt+2, 1));
	}

	private void updateTodoPanel() {
		// TODO Auto-generated method stub
		todoPanel.removeAll();

		todoTitle = new JLabel(lastMonth + "월 " + date + "일 할일");
		todoTitle.setHorizontalAlignment(SwingConstants.LEFT);
		todoTitle.setFont(Font.GOTHIC_BOLD_20);
		todoTitle.setForeground(Colors.myGreen);
		todoPanel.add(todoTitle);

		ArrayList<Data> dataList = new ArrayList<Data>();

		dataList = DataBase.getData(userID + "/todo.txt", DataBase.DATE, DataBase.setDateString(lastYear, lastMonth, date));

		int cnt = dataList.size();
		todoPanel.setPreferredSize(new Dimension(320, 40*(cnt+2)));

		if(dataList.size()==0) {
			JLabel nodata = new JLabel("데이터가 없습니다.");
			nodata.setFont(Font.GOTHIC_PLAIN_20);
			cnt=1;
			todoPanel.add(nodata);
		} else {
			DataPanel[] dataPanel = new DataPanel[dataList.size()];
			int i=0;
			for(Data data : dataList) {
				dataPanel[i] = new DataPanel(data, 3);
				todoPanel.add(dataPanel[i]);
			}
		}

		JButton btnAdd = new JButton("추가하기");
		btnAdd.setFont(Font.GOTHIC_PLAIN_16);
		btnAdd.addActionListener(e->{
			btnAdd.setVisible(false);
			calPanel.setVisible(false);
			incomePanel.setVisible(false);
			todoPanel.setVisible(false);
			expensePanel.setVisible(false);
			todoRecordPanel.setVisible(true);
		});
		todoPanel.add(btnAdd);

		todoPanel.setLayout(new GridLayout(cnt+2, 1));
	}

	private void updateMoneyLabel() {
		money  = DataBase.getMoney(userID, DataBase.setDateString(lastYear, lastMonth, date));

		for(int i=0; i<money.length;i++) {
			moneyLabel[i].setVisible(false);
			moneyLabel[i].setText(money[i]);
			moneyLabel[i].setVisible(true);
		}

		moneyTitle[0].setText(lastMonth + "월 잔고");
		moneyTitle[1].setText(lastMonth + "월 수입");
		moneyTitle[2].setText(lastMonth + "월 소비");

		for(int i=0; i<money.length;i++) {
			moneyTitle[i].setVisible(false);
			moneyTitle[i].setVisible(true);
		}
	}

	class DataPanel extends JPanel{
		private static final long serialVersionUID = 1L;
		JLabel[] labels = new JLabel[3];
		JButton btnDelete;
		String ID;
		int option;

		public DataPanel(Data data, int option) {
			setLayout(new GridLayout(1, 4));
			setBackground(Color.white);
			labels[0] = new JLabel(data.name);
			labels[1] = new JLabel(data.money_or_startTime);
			labels[2] = new JLabel(data.category_or_endTime);
			this.ID = data.ID;
			this.option = option;
			for(int i=0; i<3; i++) {
				labels[i].setFont(Font.GOTHIC_PLAIN_16);
				labels[i].setHorizontalAlignment(SwingConstants.CENTER);
				add(labels[i]);
			}
			labels[0].setHorizontalAlignment(SwingConstants.LEFT);

			btnDelete = new JButton("삭제");
			btnDelete.setFont(Font.GOTHIC_PLAIN_16);
			btnDelete.addActionListener(e->{
				switch(option) {
				case 1:
					DataBase.removeData(userID, ID, "income");
					break;
				case 2:
					DataBase.removeData(userID, ID, "expense");
					break;

				case 3:
					DataBase.removeData(userID, ID, "todo");
					break;
				}
				refresh();
			});

			add(btnDelete);
		}
	}

	class ExpenseRecordPanel extends JPanel{
		private static final long serialVersionUID = 1L;
		JLabel[] titleLabels = new JLabel[4];
		JLabel[] explainLabels = new JLabel[3];
		JTextField[] inputField = new JTextField[2];
		JComboBox<String> category;
		String[] categorys;
		JButton[] buttons = new JButton[2];
		String cat;

		public ExpenseRecordPanel() {
			setLayout(null);
			setBounds(240, 40, 980, 700);
			setBackground(Color.white);

			titleLabels[0] = new JLabel("소비 등록하기");
			titleLabels[1] = new JLabel("이름");
			titleLabels[2] = new JLabel("금액");
			titleLabels[3] = new JLabel("카테고리");

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

			explainLabels[0] = new JLabel("이름을 입력해주세요.");
			explainLabels[1] = new JLabel("금액을 입력해주세요.");
			explainLabels[2] = new JLabel("카테고리를 선택해주세요.");

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

			category = new JComboBox<String>();
			category.setBounds(320, 440, 600, 54);
			add(category);

			buttons[0] = new JButton("취소");
			buttons[1] = new JButton("등록하기");
			for(int i=0;i<buttons.length;i++) {
				buttons[i].setBounds(540 +(240*i), 560, 180, 60);
				buttons[i].setFont(Font.GOTHIC_PLAIN_28);
				add(buttons[i]);
			}

			buttons[0].addActionListener(e->{
				this.setVisible(false);
				calPanel.setVisible(true);
				incomePanel.setVisible(true);
				todoPanel.setVisible(true);
				expensePanel.setVisible(true);
				refresh();
				refresh();
				//				btnAdd.setVisible(true);
			});

			buttons[1].addActionListener(e->{
				boolean flag = true;

				for(int i=0; i<inputField.length;i++) {
					if(inputField[i].getText().equals("")) {
						inputField[i].setText("값이 비어있습니다.");
						inputField[i].setBackground(Colors.errorColor);
						flag = false;
					} else if ((i==1)
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

				if(flag == true){
					DataBase.addData(userID, inputField[0].getText(), DataBase.setDateString(lastYear, lastMonth, date), inputField[1].getText(), cat, "/expense");
					//					btnAdd.setVisible(true);

					this.setVisible(false);
					calPanel.setVisible(true);
					incomePanel.setVisible(true);
					todoPanel.setVisible(true);
					expensePanel.setVisible(true);
					refresh();
					refresh();
				}
			});
		}

		public void setCategory() {
			category.setVisible(false);
			String[] buffer = DataBase.getStringData(userID + "/category.txt");
			categorys = new String[buffer.length/2];

			category.removeAllItems();
			for(int i=0; i<buffer.length/2;i++) {
				categorys[i] = buffer[i*2];
				category.addItem(categorys[i]);
			}

			category.setSelectedIndex(0);
			category.addActionListener(e->{
				cat = (String)category.getSelectedItem();
				System.out.println(cat);
			});
			category.setVisible(true);
		}
	}

	class IncomeRecordPanel extends JPanel{
		private static final long serialVersionUID = 1L;
		JLabel[] titleLabels = new JLabel[3];
		JLabel[] explainLabels = new JLabel[2];
		JTextField[] inputField = new JTextField[2];
		JButton[] buttons = new JButton[2];

		public IncomeRecordPanel() {
			setLayout(null);
			setBounds(240, 40, 980, 700);
			setBackground(Color.white);

			titleLabels[0] = new JLabel("수입 등록하기");
			titleLabels[1] = new JLabel("이름");
			titleLabels[2] = new JLabel("총 금액");

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

			buttons[0] = new JButton("취소");
			buttons[1] = new JButton("등록하기");
			for(int i=0;i<buttons.length;i++) {
				buttons[i].setBounds(540 +(240*i), 560, 180, 60);
				buttons[i].setFont(Font.GOTHIC_PLAIN_28);
				add(buttons[i]);
			}

			buttons[0].addActionListener(e->{
				this.setVisible(false);
				calPanel.setVisible(true);
				incomePanel.setVisible(true);
				todoPanel.setVisible(true);
				expensePanel.setVisible(true);
				refresh();
				//				btnAdd.setVisible(true);
			});
			buttons[1].addActionListener(e->{
				boolean flag = true;

				for(int i=0; i<inputField.length;i++) {
					if(inputField[i].getText().equals("")) {
						inputField[i].setText("값이 비어있습니다.");
						inputField[i].setBackground(Colors.errorColor);
						flag = false;
					} else if ((i==1)
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

				if(flag == true){
					DataBase.addIncomeData(userID, inputField[0].getText(), DataBase.setDateString(lastYear, lastMonth, date), inputField[1].getText());
					//					btnAdd.setVisible(true);
					this.setVisible(false);
					calPanel.setVisible(true);
					incomePanel.setVisible(true);
					todoPanel.setVisible(true);
					expensePanel.setVisible(true);
					refresh();
				}
			});
		}
	}
	class TodoRecordPanel extends JPanel{
		private static final long serialVersionUID = 1L;
		JLabel[] titleLabels = new JLabel[4];
		JLabel[] explainLabels = new JLabel[3];
		JTextField[] inputField = new JTextField[3];
		JButton[] buttons = new JButton[2];

		public TodoRecordPanel() {
			setLayout(null);
			setBounds(240, 40, 980, 700);
			setBackground(Color.white);

			titleLabels[0] = new JLabel("할일 등록하기");
			titleLabels[1] = new JLabel("이름");
			titleLabels[2] = new JLabel("시작시간");
			titleLabels[3] = new JLabel("종료시간");

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
			explainLabels[1] = new JLabel("시작시.");
			explainLabels[2] = new JLabel("종료시간.");

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

			buttons[0] = new JButton("취소");
			buttons[1] = new JButton("등록하기");
			for(int i=0;i<buttons.length;i++) {
				buttons[i].setBounds(540 +(240*i), 560, 180, 60);
				buttons[i].setFont(Font.GOTHIC_PLAIN_28);
				add(buttons[i]);
			}

			buttons[0].addActionListener(e->{
				this.setVisible(false);
				calPanel.setVisible(true);
				incomePanel.setVisible(true);
				todoPanel.setVisible(true);
				expensePanel.setVisible(true);
				refresh();
				//				btnAdd.setVisible(true);
			});
			buttons[1].addActionListener(e->{
				boolean flag = true;

				for(int i=0; i<inputField.length;i++) {
					if(inputField[i].getText().equals("")) {
						inputField[i].setText("값이 비어있습니다.");
						inputField[i].setBackground(Colors.errorColor);
						flag = false;
					} else if ((i==1 || i==2)
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

				if(flag == true){
					DataBase.addData(userID, inputField[0].getText(), DataBase.setDateString(lastYear, lastMonth, date), inputField[1].getText(), inputField[2].getText(), "/todo");
					//					btnAdd.setVisible(true);

					this.setVisible(false);
					calPanel.setVisible(true);
					incomePanel.setVisible(true);
					todoPanel.setVisible(true);
					expensePanel.setVisible(true);
					refresh();
				}
			});
		}
	}

	@Override
	public void refresh() {
		incomeSc.setVisible(false);
		expenseSc.setVisible(false);
		todoSc.setVisible(false);
		updateMoneyLabel();
		updateIncomePanel();
		updateExpensePanel();
		updateTodoPanel();
		incomeSc.setVisible(true);
		expenseSc.setVisible(true);
		todoSc.setVisible(true);
	}
}
