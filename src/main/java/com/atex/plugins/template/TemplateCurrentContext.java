package com.atex.plugins.template;

/**
 * TemplateCurrentContext.get() returns the current context object.
 * The context is set by the ContextAwareObjectHandler when evaluating a template.
 * This allows to access the current context object from within a template helper.
 *
 * @author mnova
 */
public class TemplateCurrentContext {

    private static final ThreadLocal<Object> currentContext = new ThreadLocal<>();

    private TemplateCurrentContext() {
    }

    /**
     * Get the current context object. The context is set by the ContextAwareObjectHandler when evaluating a template.
     * @return a context object or null if not set
     */
    public static Object get() {
        return currentContext.get();
    }

    /**
     * Set the current context object. The context is set by the ContextAwareObjectHandler when evaluating a template.
     * @param context a context object to set
     */
    public static void set(final Object context) {
        currentContext.set(context);
    }

    /**
     * Clear the current context. This should be called after evaluating a template to avoid memory leaks.
     */
    public static void clear() {
        currentContext.remove();
    }

}
