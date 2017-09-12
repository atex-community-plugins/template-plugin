package com.atex.plugins.template;

/**
 * TemplateServiceBuilder
 *
 * @author mnova
 */
public class TemplateServiceBuilder {

    public TemplateService create() {
        return new TemplateServiceImpl();
    }

}
