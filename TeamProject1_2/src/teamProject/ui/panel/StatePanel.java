package teamProject.ui.panel;

import java.awt.Color;
import java.awt.Graphics;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Locale;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;

import teamProject.constant.Colors;
import teamProject.constant.Resources;
import teamProject.ui.constant.Font;
import teamProject.ui.util.Data;
import teamProject.ui.util.DataBase;

public class StatePanel extends BasePanel {
	private static final long serialVersionUID = 1L;
	SimpleDateFormat sdf;
	Calendar calendar;
	ChartPanel chart;
	int maxMonth, maxWeek;
	int[][] weeks;
	int[] months;
	int lastMonth;
	String[] categorys;
	String[][] weekList;
	JLabel[] weekLabel = new JLabel[5];
	JLabel[] weekDate = new JLabel[5];
	JLabel[] weekMoney = new JLabel[5];
	JLabel chartShadow = new JLabel();
	JLabel[] noDatas = new JLabel[5];
	JLabel monthLabel, monthMoney;
	JTextArea expenseInfo;

	public StatePanel() {
		super();

		sdf = new SimpleDateFormat("yyyymmdd");
		calendar = new GregorianCalendar(Locale.KOREA);

		setImageLabel(chartShadow, Resources.SHADOW);
		chartShadow.setBounds(760, 186, 240, 240);
		add(chartShadow);
		
		monthLabel = new JLabel();
		transparentLabel(monthLabel);
		monthLabel.setFont(Font.GOTHIC_PLAIN_36);
		monthLabel.setBounds(760, 65, 100, 54);
		monthLabel.setHorizontalAlignment(SwingConstants.LEFT);
		add(monthLabel);
		
		monthMoney = new JLabel();
		transparentLabel(monthMoney);
		monthMoney.setFont(Font.GOTHIC_PLAIN_20);
		monthMoney.setBounds(760, 122, 160, 24);
		monthMoney.setHorizontalAlignment(SwingConstants.LEFT);
		add(monthMoney);

		expenseInfo = new JTextArea();
		expenseInfo.setBounds(770, 555, 400, 160);
		expenseInfo.setFont(Font.GOTHIC_PLAIN_20);
		expenseInfo.setEditable(false);
		add(expenseInfo);
		
		for(int i=0;i<noDatas.length;i++) {
			noDatas[i] = new JLabel("데이터가 없습니다");
			transparentLabel(noDatas[i]);
			noDatas[i].setFont(Font.GOTHIC_PLAIN_20);
			noDatas[i].setHorizontalAlignment(SwingConstants.LEFT);
			noDatas[i].setBounds(230, 113 +(140*i), 440, 32);
			add(noDatas[i]);
		}

		for(int i=0; i<weekLabel.length;i++) {
			if(i==0) {
				weekLabel[i] = new JLabel("이번 주");
			} else {
				weekLabel[i] = new JLabel(i + "주 전");
			}
			transparentLabel(weekLabel[i]);
			weekLabel[i].setFont(Font.GOTHIC_PLAIN_18);
			weekLabel[i].setBounds(230, 65 + (140*i), 55, 30);
			weekLabel[i].setHorizontalAlignment(SwingConstants.LEFT);
			add(weekLabel[i]);
		}

		for(int i=0; i<weekDate.length;i++) {
			weekDate[i] = new JLabel("");
			transparentLabel(weekDate[i]);
			weekDate[i].setFont(Font.GOTHIC_PLAIN_18);
			weekDate[i].setBounds(303, 65 + (140*i), 220, 30);
			weekDate[i].setHorizontalAlignment(SwingConstants.LEFT);
			add(weekDate[i]);
		}

		for(int i=0; i<weekMoney.length;i++) {
			weekMoney[i] = new JLabel("");
			transparentLabel(weekMoney[i]);
			weekMoney[i].setFont(Font.GOTHIC_PLAIN_18);
			weekMoney[i].setBounds(544, 65 + (140*i), 130, 30);
			weekMoney[i].setHorizontalAlignment(SwingConstants.RIGHT);
			add(weekMoney[i]);
		}
		
		chart = new ChartPanel();
		chart.setBounds(0, 0, 1280, 800);
		add(chart);

		remove(contentsPanel);
		lastMonth = calendar.get(Calendar.MONTH)+1;
		add(backGround);
	}

	private void update(int lastMonth) {
		DecimalFormat df = new DecimalFormat("00");

		ArrayList<Data> totalList = new ArrayList<Data>();
		categorys = DataBase.getCategory(userID);

		System.out.println(categorys.length);

		totalList = DataBase.getData(userID + "/expense.txt");

		lastMonth = calendar.get(Calendar.MONTH)+1;

		weeks = new int[5][categorys.length/2 + 1];
		months = new int[categorys.length/2+1];

		for(int i=0; i<weeks.length;i++) {
			for(int j=0; j<weeks[i].length ;j++) {
				weeks[i][j] = 0;
			}
		}

		for(int i=0; i<months.length;i++) {
			months[i] = 0;
		}
		weekList = new String[5][2];

		calendar = Calendar.getInstance();

		calendar.add(Calendar.DATE, 1);
		for(int i=0; i<weekList.length;i++) {
			calendar.add(Calendar.DATE, -1);
			String strYear = Integer.toString(calendar.get(Calendar.YEAR));
			String strMonth = df.format(calendar.get(Calendar.MONTH) + 1);
			String strDay = df.format(calendar.get(Calendar.DATE));
			String strDate = strYear + strMonth + strDay;
			weekList[i][1] = strDate;

			calendar.add(Calendar.DATE, -6);
			strYear = Integer.toString(calendar.get(Calendar.YEAR));
			strMonth = df.format(calendar.get(Calendar.MONTH) + 1);
			strDay = df.format(calendar.get(Calendar.DATE));
			strDate = strYear + strMonth + strDay;
			weekList[i][0] =strDate;

			System.out.println(weekList[i][1]);

			weekDate[i].setVisible(false);
			weekDate[i].setText(weekList[i][0] + "~" + weekList[i][1]);
			weekDate[i].setVisible(true);
		}

		for(int i=0;i<weeks.length;i++) {
			for(Data d : totalList) {
				if(Integer.parseInt(d.date) >= (Integer.parseInt(weekList[i][0]))
						&& Integer.parseInt(d.date) <= (Integer.parseInt(weekList[i][1]))) {
					weeks[i][0] += Integer.parseInt(d.money_or_startTime);
					for(int j=1; j<weeks[0].length;j++) {
						if(d.category_or_endTime.equals(categorys[(j-1)*2]))
							weeks[i][j]+=Integer.parseInt(d.money_or_startTime);
					}
				}
			}
			weekMoney[i].setText(weeks[i][0] + "원");
		}

		for(Data d : totalList) {
			if(d.date.substring(0, 6).equals(2020+Integer.toString(lastMonth))) {
				months[0] += Integer.parseInt(d.money_or_startTime);
				for(int i=1; i<months.length;i++) {
					if(d.category_or_endTime.equals(categorys[(i-1)*2]))
						months[i]+=Integer.parseInt(d.money_or_startTime);
				}
			}
		}
		maxMonth = months[1];
		maxWeek = weeks[0][0];

		for(int i=0;i<weeks.length;i++) {
			if(i!=0 && weeks[i][0] >= maxWeek) {
				maxWeek = weeks[i][0];
			}
		}

		for(int i=0; i<months.length;i++) {
			if(i!=0 && months[i] >= maxMonth) {
				maxMonth = months[i];
			}
		}

		for(int i=0;i<weeks.length;i++) {
			for(int j=0;j<weeks[0].length;j++) {
				System.out.print(weeks[i][j] + " ");
			}
		}
		
		monthLabel.setVisible(false);
		monthMoney.setVisible(false);
		monthLabel.setText(lastMonth +"월");
		monthMoney.setText(months[0] +"원");
		monthLabel.setVisible(true);
		monthMoney.setVisible(true);
		
		String info = "";
		
		if(months[0]==0) {
			info += "데이터가 아직 없습니다";
		} else {
			for(int i=1; i<months.length; i++) {
				if(maxMonth==months[i]) {
					info += "가장 소비가 높은 카테고리는 \n" + categorys[2*(i-1)] + "입니다.";
				}
			}
		}
		
		if(weeks[0][0] != 0 && weeks[1][0]!=0) {
			int persent = (int)(((double) weeks[0][0] / weeks[1][0])*100) - 100;
			if(persent >= 0) {
				info += "\n\n지난주에 비해 소비가\n" + persent + "% 증가했습니다.";
			} else {
				info += "\n\n지난주에 비해 소비가\n" + persent + "% 감소했습니다.";
			}
		}
		
		expenseInfo.setVisible(false);
		expenseInfo.setText(info);
		expenseInfo.setVisible(true);
		
		chart.repaint();
		
		for(int i=0; i<noDatas.length;i++) {
			noDatas[i].setVisible(false);
		}
		
		for(int i=0; i<noDatas.length;i++) {
			if(weeks[i][0]==0) {
				noDatas[i].setVisible(true);
			}
		}
	}

	class ChartPanel extends JPanel{ // 차트 표시 패널
		private static final long serialVersionUID = 1L;

		public ChartPanel(){
			setOpaque(false);
		}

		public void paintComponent(Graphics g){
			super.paintComponent(g);
			if(userID!=null) {
				g.setColor(Color.white);
				g.fillRect(720, 45, 500, 440);
				System.out.println("test1");
				

				//super.paint(g);//부모 패인트호출
				if(maxWeek!=0) {
					for(int j=0;j<weeks.length;j++) {
						int width = (int)((float)weeks[j][0] / maxWeek * 440);
						if(width==0) {
							width=60;
						}
						int widthSum=0;

						for(int i=1; i<weeks[0].length;i++) {
							int width2;
							g.setColor(Colors.userColor[i-1]);
							if(weeks[j][0]==0) {
								width2=0;
							}else {
								width2 = (int)(((float) width / weeks[j][0]) * weeks[j][i]);
							}
							System.out.println(width2);

							g.fillRect(230 + widthSum, 113 +(140*j), width2, 32);
							widthSum += width2;
						}
						g.setColor(Color.white);
						g.fillRect(230+widthSum, 113 + (140*j), 440-widthSum, 32);
					}
				}

				int startAngle = 0;
				int[] arcAngle = new int[months.length-1];

				for(int i=1; i<months.length;i++) {
					arcAngle[i-1] = (int)Math.round((double)months[i]/(double)months[0]*360);
					startAngle+=arcAngle[i-1];

					System.out.println("test3");
				}

				startAngle=0;

				for(int i=1;i<months.length;i++){
					g.setColor(Colors.userColor[i-1]);
					g.fillArc(760,186,240,240,startAngle,arcAngle[i-1]);
					startAngle = startAngle + arcAngle[i-1];
				}
				g.setColor(Color.WHITE);
				g.fillArc(820, 246, 120, 120, 0, 360);

				for(int i=0; i<categorys.length/2;i++) {
					g.setColor(Colors.userColor[i]);
					g.fillArc(1044, 188 +(35*i), 24, 24, 0, 360);

					g.setColor(Colors.myFontColor);
					g.setFont(Font.GOTHIC_PLAIN_18);
					g.drawString(categorys[i*2], 1080, 207 +(35*i));

					System.out.println("test4");
				}

				g.setColor(Colors.myGreen);
				g.drawRect(720, 45, 500, 440);
				g.drawRect(720, 515, 500, 210);

				for(int i=0; i<weeks.length;i++) {
					g.drawRect(210, 45 + (140*i), 480, 120);
				}
			}
		}
	}

	@Override
	public void refresh() {
		// TODO Auto-generated method stub
		update(lastMonth);
	}
}
