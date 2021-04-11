package teamProject.ui.util;

import java.awt.event.ActionEvent;

import javax.swing.JButton;

public class ActionEventUtil {
	public static JButton getSource(ActionEvent event) {
        if (event.getSource() instanceof JButton) {
            return (JButton) event.getSource();
        } else {
            throw new ClassCastException();
        }
    }
}