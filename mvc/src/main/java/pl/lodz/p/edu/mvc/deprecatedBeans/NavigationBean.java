package pl.lodz.p.edu.mvc.deprecatedBeans;

import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Named;

@Named
@RequestScoped
public class NavigationBean {

    public static String navigate(String target) {
        return "";
    }
}
