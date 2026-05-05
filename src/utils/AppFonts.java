package utils;

import java.awt.Font;

public class AppFonts {

    private static final String FONT_NAME = "Segoe UI"; 

    public static Font normal() {
        return new Font(FONT_NAME, Font.PLAIN, 14);
    }

    public static Font bold() {
        return new Font(FONT_NAME, Font.BOLD, 14);
    }
    
    public static Font title() {
        return new Font(FONT_NAME, Font.BOLD, 20);
    }
}