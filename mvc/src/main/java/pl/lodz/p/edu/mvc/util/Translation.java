package pl.lodz.p.edu.mvc.util;

import jakarta.inject.Named;

import java.util.ResourceBundle;

@Named
public class Translation {
    public static String booleanToString(boolean b) {
        ResourceBundle resourceBundle = ResourceBundle.getBundle("i18n.messages");
        return b ? resourceBundle.getString("types.true")
                 : resourceBundle.getString("types.false");
    }
}
