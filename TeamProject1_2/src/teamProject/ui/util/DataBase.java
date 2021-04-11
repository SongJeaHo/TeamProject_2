package teamProject.ui.util;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;

import javax.swing.JOptionPane;

public class DataBase {
	public static final int NAME = 0;
	public static final int DATE = 1;
	public static final int MONEY_OR_STARTTIME = 2;
	public static final int CATEGORY_OR_ENDTIME = 3;
	public static final int ID = 4;

	public static void writeData(String nav, String contents) {
		FileWriter writer;
		try {
			writer = new FileWriter("res/data/" + nav, false);
			writer.write(contents);
			writer.close();
		} catch (IOException e) {
			// TODO 자동 생성된 catch 블록
			e.printStackTrace();
		}
	}

	public static void removeData(String userID, String ID, String option) {
		ArrayList<Data> previous = getData(userID + "/" + option + ".txt");
		ArrayList<Data> removeData = new ArrayList<Data>();

		if(previous==null) {
			previous = new ArrayList<Data>();
		}

		for(Data data : previous) {
			if(data.ID.equals(ID)) {
				removeData.add(data);
			}		
		}

		for(int i=0; i<removeData.size();i++) {
			previous.remove(removeData.get(i));
		}

		writeData(userID + "/" + option + ".txt", setFileContents(previous));
	}

	public static void addData(String userID, String name, String date, String money_or_startTime, String category_or_endTime, String option) {
		ArrayList<Data> previous = getData(userID + "/" + option + ".txt");
		if(previous==null) {
			previous = new ArrayList<Data>();
		}
		Data input = new Data(name, date, money_or_startTime, category_or_endTime, userID);
		input.generateID(userID);

		previous.add(input);

		writeData(userID + "/" + option + ".txt", setFileContents(previous));
	}

	public static void addData(String userID, String name, String date, String money_or_startTime, String category_or_endTime, String ID, String option) {
		ArrayList<Data> previous = getData(userID + "/" + option + ".txt");
		if(previous==null) {
			previous = new ArrayList<Data>();
		}
		Data input = new Data(name, date, money_or_startTime, category_or_endTime, ID);

		previous.add(input);

		writeData(userID + "/" + option + ".txt", setFileContents(previous));
	}

	public static void addIncomeData(String userID, String name, String date, String money) {
		ArrayList<Data> previous = getData(userID + "/income.txt");
		if(previous==null) {
			previous = new ArrayList<Data>();
		}
		Data input = new Data(name, date, money, "income", userID);
		input.generateID(userID);

		previous.add(input);

		writeData(userID + "/income.txt", setFileContents(previous));
	}


	public static <T>String setFileContents(ArrayList<T> contents) {
		String buffer = "";

		for(T Data : contents) {
			buffer += Data.toString() + ",";
		}

		System.out.println(buffer);
		if (buffer.length() > 0 && buffer.charAt(buffer.length()-1)==',') {
			buffer = buffer.substring(0, buffer.length()-1);
		}

		return buffer;
	}

	public static String setFileContents(String[] contents) {
		String buffer = "";

		for(int i=0; i<contents.length;i++) {
			buffer+= (contents[i] + ",");
		}

		if (buffer.length() > 0 && buffer.charAt(buffer.length()-1)==',') {
			buffer = buffer.substring(0, buffer.length()-1);
		}

		return buffer;
	}

	public static String[] getStringData(String nav) {
		File file = new File("res/Data/" + nav);
		FileReader fileReader;
		try {
			fileReader = new FileReader(file);

			int cur = 0;
			String contents = "";
			while((cur = fileReader.read())!=-1) {
				contents += (char)cur;
			}

			String[] buffer = contents.split(",");
			fileReader.close();
			return buffer;
		} catch (FileNotFoundException e) {
			// TODO 자동 생성된 catch 블록
			e.printStackTrace();
		} catch (IOException e) {
			// TODO 자동 생성된 catch 블록
			e.printStackTrace();
		}
		return null;
	}


	public static ArrayList<Data> getData(String nav) {
		ArrayList<Data> DataList = new ArrayList<Data>();

		String[] buffer = getStringData(nav);

		if(buffer==null)
			return null;
		
		for(int i=0; i<buffer.length/5 ;i++) {		
			DataList.add(new Data(
					buffer[5*i+NAME], 
					buffer[5*i+DATE], 
					buffer[5*i+MONEY_OR_STARTTIME], 
					buffer[5*i+CATEGORY_OR_ENDTIME],
					buffer[5*i+ID]));
		}
		return DataList;
	}

	public static ArrayList<Data> getData(String nav, int option, String option2){
		ArrayList<Data> dataList = new ArrayList<Data>();
		ArrayList<Data> totalDataList = new ArrayList<Data>();
		totalDataList = getData(nav);
		
		if(totalDataList==null)
			return null;
		
		switch(option) {
		case DATE :
			for(Data data : totalDataList) {
				if(data.date.equals(option2)) {
					dataList.add(data);
				}
			}
			break;
		case CATEGORY_OR_ENDTIME :
			for(Data data : totalDataList) {
				if(data.category_or_endTime.equals(option2)) {
					dataList.add(data);
				}
			}
			break;
		}

		return dataList;
	}

	public static String[] getMoney(String userID, String date) {
		int _income = 0, _expense = 0;
		String[] money = new String[3];
		
		ArrayList<Data> income = new ArrayList<Data>();
		income = getData(userID + "/income.txt");
		for(Data data : income) {
			if(data.date.substring(0, 6).equals(date.substring(0, 6)))
				_income += Integer.parseInt(data.money_or_startTime);
		}

		ArrayList<Data> expense = new ArrayList<Data>();
		expense = getData(userID + "/expense.txt");
		for(Data data : expense) {
			if(data.date.substring(0, 6).equals(date.substring(0, 6)))
				_expense += Integer.parseInt(data.money_or_startTime);
		}

		money[0] = "" + (_income-_expense);
		money[1] = "" + _income;
		money[2] = "" + _expense;

		System.out.println(money[0] + "" + money[1] + "" + money[2]);
		return money;
	}

	public static int ChangePassword(String userID, String prevPassword, String newPassword, String newPasswordConfirm) {
		String[] data = getStringData(userID +"/password.txt");
		String pw = data[0];
		int flag = 1;
		if(!pw.equals(prevPassword))
			flag *= 2;

		if(!newPassword.equals(newPasswordConfirm))
			flag *= 3;

		if(prevPassword.equals("") || newPassword.equals("") || newPasswordConfirm.equals(""))
			flag *= 5;

		if(flag==1) {
			pw = newPassword;
			writeData(userID +"/password.txt", pw);
		}

		return flag;
	}

	public static void updateCategory(String userID, String cat1, String cat2) {
		String[] catList = getStringData(userID + "/category.txt");

		for(int i=0; i<catList.length;i++) {
			if(catList[i].equals(cat1))
				catList[i] = cat2;
		}

		writeData(userID + "/category.txt", setFileContents(catList));

		ArrayList<Data> DataList = new ArrayList<Data>();
		DataList = getData(userID + "/expense.txt");

		for(Data data : DataList) {
			if(data.category_or_endTime.equals(cat1)) {
				data.category_or_endTime = cat2;
			}
		}
		writeData(userID + "/expense.txt", setFileContents(DataList));
	}

	public static void updateData(String option, String userID, String ID, int cat, String contents) {
		ArrayList<Data> dataList = getData(userID + "/" + option + ".txt");

		int i=0;
		for(Data data : dataList) {
			if(data.ID.equals(ID)) {
				System.out.println(data.toString() + contents);
				Data input = new Data(data.name, data.date, data.money_or_startTime, data.category_or_endTime, data.ID);
				switch(cat) {
				case 0:
					input.name = contents;
					break;
				case 1:
					input.date = contents;
					break;
				case 2:
					input.money_or_startTime = contents;
					break;
				case 3:
					input.category_or_endTime = contents;
					break;
				default:
					return;
				}
				dataList.set(i, input);
			}
			i++;
		}
		writeData(userID + "/" + option + ".txt", setFileContents(dataList));
	}

	public static String[] getCategory(String userID) {
		return getStringData(userID + "/category.txt");
	}

	public static String setDateString(int year, int month, int date) {
		String dateString = "";

		dateString += year;
		if(month < 10) {
			dateString += '0';
		}
		dateString += month;
		if(date < 10) {
			dateString += '0';
		}
		dateString += date;

		return dateString;
	};

	public static boolean setNewDataBase(String userID, String PW){
		File newUser = new File("res/data/" + userID);
		newUser.mkdir();
		System.out.println("CREATE NEW USER FOLDER");

		writeData(userID + "/password.txt", PW);
		writeData(userID + "/userID.txt", "1"); 
		writeData(userID + "/wish.txt", "");
		writeData(userID +"/expense.txt", "");
		writeData(userID + "/todo.txt", "");
		writeData(userID +"/income.txt", "");
		writeData(userID + "/category.txt", "저축,6,기타,7");
		return true;
	}

	public static boolean initialization(String userID) {
		writeData(userID + "/wish.txt", "");
		writeData(userID +"/expense.txt", "");
		writeData(userID + "/todo.txt", "");
		writeData(userID +"/income.txt", "");

		return true;
	}

	public static boolean saveMoney(String userID, String name, String date, String ID, int totalMoney, int presentMoney) {
		int maxMoney = totalMoney - presentMoney;
		String moneyData = JOptionPane.showInputDialog(null, "저축할 금액을 입력해주세요.\n"
				+ "최대 금액을 넘어갈 경우 최대로 저축할 수 있는 금액이 저축됩니다.\n"
				+ "현재 저축할 수 있는 최대 금액은 " + maxMoney + "원입니다.", 
				"", JOptionPane.OK_CANCEL_OPTION);
		System.out.println("moneyData is " + moneyData);

		if(checkDigit(moneyData)==-1 || moneyData.equals("0")) {
			JOptionPane.showMessageDialog(null, "취소되었습니다!");
			return false;
		}
		if(checkDigit(moneyData)==0) {
			JOptionPane.showMessageDialog(null, "숫자만 입력해주세요.",
					"ERROR_MESSAGE", JOptionPane.ERROR_MESSAGE);
			return false;
		}

		if(!checkFilled(moneyData)) {
			JOptionPane.showMessageDialog(null, "입력값이 없어 취소되었습니다!");
			return false;
		}

		int money = Integer.parseInt(moneyData);
		if(money > maxMoney) {
			money = maxMoney;
		}

		addData(userID, name, date, Integer.toString(money), "저축", ID, "expense");

		updateData("wish", userID, ID, 3, Integer.toString(money +presentMoney));

		return true;
	}

	public static int checkDigit(String str) {
		try{
			for(int i=0; !str.equals(null) && i<str.length();i++) {
				if(!Character.isDigit(str.charAt(i)))
					return 0;
			}
		}catch(NullPointerException e) {
			return -1;
		}
		return 1;
	};

	public static boolean checkFilled(String str) {
		if(str.equals(""))
			return false;

		if(str.equals(null))
			return false;

		return true;
	};

	public static int[] getToday() {
		int[] days = new int[3];

		Calendar calendar = Calendar.getInstance();

		days[0] = calendar.get(Calendar.YEAR);
		days[1] = calendar.get(Calendar.MONTH)+1;
		days[2] = calendar.get(Calendar.DATE);

		return days;
	}

	public static String getTodayString() {
		int[] days =  getToday();
		String result = setDateString(days[0], days[1], days[2]);

		return result;
	}
}