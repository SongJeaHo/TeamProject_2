package teamProject.ui.util;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class Data {
	public String name;
	public String date;
	public String money_or_startTime;
	public String category_or_endTime;
	public String ID;

	public Data(String name, String date, String money_or_startTime, String category_or_endTime, String ID) {
		this.name = name;
		this.date = date;
		this.money_or_startTime = money_or_startTime;
		this.category_or_endTime = category_or_endTime;
		this.ID = ID;
	}

	public void generateID(String userID) {
		File file = new File("res/Data/" + userID + "/userID.txt");

		try {
			FileReader fileReader  = new FileReader(file);

			int cur = 0;
			String id = "";
			while ((cur = fileReader.read()) != -1) {
				id += (char) cur;
			}
			this.ID = Integer.toString(Integer.parseInt(id) + 1);

			FileWriter fileWriter = new FileWriter(file);
			fileWriter.write(ID);

			fileReader.close();
			fileWriter.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public String toString() {
		return name + "," + date + "," + money_or_startTime + "," + category_or_endTime + "," + ID;
	}
}
