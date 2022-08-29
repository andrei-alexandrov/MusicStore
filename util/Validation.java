package util;

public abstract class Validation {

    public static boolean textValidation(String text) {
        return text != null && text.length() > 0;
    }

}
