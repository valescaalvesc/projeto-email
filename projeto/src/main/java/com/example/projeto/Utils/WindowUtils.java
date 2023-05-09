package com.example.projeto.Utils;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.Window;

public class WindowUtils {
    public static void centerOnScreen(Window window) {
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int centerX = (int) screenSize.getWidth() / 2;
        int centerY = (int) screenSize.getHeight() / 2;
        int windowX = centerX - window.getWidth() / 2;
        int windowY = centerY - window.getHeight() / 2;
        window.setLocation(windowX, windowY);
    }

}

