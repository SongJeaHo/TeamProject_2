package teamProject.constant;

import java.awt.*;

// 색을 저장할 클래스, 크게 배경색과 글자색으로 나뉘어짐. 
// 흔히 디자인에 많이 쓰이는 색으로 하여 깉은 회색은 40번대 옅은 회색은 225번대 사용 
public class Colors {
    public static Color myFontColor = new Color(40, 40, 40);
    public static Color myGreen = new Color(70, 223, 142);
    public static Color errorColor = new Color(245, 106, 121);
    public static Color inputGrey = new Color(235, 235, 235);
    
    public static Color[] userColor = { new Color(168, 222, 224), 
    		new Color(133, 203, 204), new Color(249, 226, 174), new Color(251, 199, 184),
    		new Color(167, 214, 118), new Color(161, 168, 191), new Color(70, 223, 142) };
}
