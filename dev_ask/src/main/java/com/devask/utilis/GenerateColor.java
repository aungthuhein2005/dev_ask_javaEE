package com.devask.utilis;

public class GenerateColor {
    public String getAvatarColor(String username) {
        // Use the absolute value of the hash code to avoid negative colors
        int hash = Math.abs(username.hashCode());
        
        // Extract RGB components
        int r = (hash & 0xFF0000) >> 16;
        int g = (hash & 0x00FF00) >> 8;
        int b = (hash & 0x0000FF);
        
        // Return color in hex format
        return String.format("#%02X%02X%02X", r, g, b);
    }
}
