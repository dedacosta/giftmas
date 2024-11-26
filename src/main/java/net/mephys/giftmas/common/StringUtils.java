package net.mephys.giftmas.common;

public class StringUtils {

    public static String capitalizeFirstLetter(String input) {
        if (input == null || input.isEmpty()) {
            return input; // Return as is for null or empty strings
        }
        return input.substring(0, 1).toUpperCase() + input.substring(1);
    }

    public static void main(String[] args) {
        String example = "java";
        String result = capitalizeFirstLetter(example);
        System.out.println(result); // Output: Java
    }
}
