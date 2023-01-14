package pl.lodz.p.edu.mvc.backingBean;

import jakarta.faces.context.ExternalContext;
import jakarta.faces.context.FacesContext;

import java.io.IOException;
import java.io.Serializable;
import java.util.Map;

public abstract class AbstractBean implements Serializable {
    protected ExternalContext getContext() {
        return FacesContext.getCurrentInstance().getExternalContext();
    }

    protected String getUuidFromParam() {
        ExternalContext context = getContext();
        Map<String, String> params = context.getRequestParameterMap();
        return params.get("uuid");
    }

    protected String getFromParam(String name) {
        ExternalContext context = getContext();
        Map<String, String> params = context.getRequestParameterMap();
        return params.get(name);
    }

    protected void redirect(String page) {
        ExternalContext context = getContext();
        try {
            context.redirect(page);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
