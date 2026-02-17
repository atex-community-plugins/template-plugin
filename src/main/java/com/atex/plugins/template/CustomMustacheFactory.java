package com.atex.plugins.template;

import java.util.List;
import java.util.logging.Logger;

import com.github.mustachejava.DefaultMustacheFactory;
import com.github.mustachejava.reflect.ReflectionObjectHandler;
import com.github.mustachejava.util.Wrapper;

/**
 * CustomMustacheFactory
 *
 * @author mnova
 */
public class CustomMustacheFactory extends DefaultMustacheFactory {

    private static final Logger LOGGER = Logger.getLogger(CustomMustacheFactory.class.getName());

    private final TemplateResolver resolver;

    public CustomMustacheFactory() {
        super();
        this.resolver = null;
        setObjectHandler(new ContextAwareObjectHandler());
    }

    public CustomMustacheFactory(final TemplateResolver resolver) {
        super(resolver::getReader);
        this.resolver = resolver;
        setObjectHandler(new ContextAwareObjectHandler());
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

    static final class ContextAwareObjectHandler extends ReflectionObjectHandler {

        @Override
        public Wrapper find(final String name, final List<Object> scopes) {
            final Wrapper delegate = super.find(name, scopes);

            return scopes1 -> {
                final Object prev = TemplateCurrentContext.get();
                final Object current = (scopes1 != null && !scopes1.isEmpty()) ? scopes1.get(scopes1.size() - 1) : null;

                TemplateCurrentContext.set(current);
                try {
                    return delegate.call(scopes1);
                } finally {
                    if (prev != null) {
                        TemplateCurrentContext.set(prev);
                    } else {
                        TemplateCurrentContext.clear();
                    }
                }
            };
        }

    }
}
