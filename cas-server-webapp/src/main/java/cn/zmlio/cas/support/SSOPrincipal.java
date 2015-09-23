package cn.zmlio.cas.support;

import org.jasig.cas.authentication.principal.Principal;

import java.util.Map;

/**
 * Created by ZML on 2015/9/22.
 */
public class SSOPrincipal implements Principal {
    @Override
    public String getId() {
        return null;
    }

    private Map<String, Object> attributes;

    private String id;

    public void setId(String id) {
        this.id = id;
    }

    public void setAttributes(Map<String, Object> attributes) {
        this.attributes = attributes;
    }

    @Override
    public Map<String, Object> getAttributes() {
        return this.attributes;
    }
}
