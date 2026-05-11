package utils;

import java.awt.Window;
import javax.swing.SwingUtilities;
import com.formdev.flatlaf.FlatDarkLaf;
import com.formdev.flatlaf.FlatLightLaf;

public class ThemeManager {

	public static void applySavedTheme() {
		String theme = Config.get("ui.theme", "light");
		apply(theme);
	}
	
	public static void apply(String theme) {
		try {
			if(theme.equalsIgnoreCase("dark")) {
				FlatDarkLaf.setup();
			}else {
				FlatLightLaf.setup();
			}
			
			Config.set("ui.theme", theme);
			
			for(Window w: Window.getWindows()) {
				SwingUtilities.updateComponentTreeUI(w);
			}
		}catch(Exception ex) {
			ex.printStackTrace();
		}
	}
	
	public static void toggle() {
		String current = Config.get("ui.theme", "light");
		
		if(current.equalsIgnoreCase("light")) {
			apply("dark");
		}else {
			apply("light");
		}
	}
}