package com.atex.plugins.template;

import java.util.logging.Logger;

import com.github.mustachejava.DefaultMustacheFactory;

/**
 * CustomMustacheFactory
 *
 * @author mnova
 */
public class CustomMustacheFactory extends DefaultMustacheFactory {

    private static final Logger LOGGER = Logger.getLogger(CustomMustacheFactory.class.getName());

    private TemplateResolver resolver;

    public CustomMustacheFactory() {
        super();

        this.resolver = null;
    }

    public CustomMustacheFactory(final TemplateResolver resolver) {
        super(resolver::getReader);

        this.resolver = resolver;
    }

    @Override
    public String resolvePartialPath(final String dir, final String name, final String extension) {
        String s = null;
        if (resolver != null) {
            s = resolver.resolvePartialPath(dir, name, extension);
            LOGGER.fine("from resolver: Dir " + dir + " name " + name + " ext " + extension + " = " + s);
        }
        if (s == null) {
            s = super.resolvePartialPath(dir, name, extension);
            LOGGER.fine("from super   : Dir " + dir + " name " + name + " ext " + extension + " = " + s);
        }
        return s;
    }

}
