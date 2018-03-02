package com.atex.plugins.template;

import java.io.Reader;

/**
 * TemplateResolver
 *
 * @author mnova
 */
public interface TemplateResolver {

    /**
     * Get the given template.
     *
     * @param templateName must be a not null String.
     * @return null if the template does not exists.
     */
    Reader getReader(final String templateName);

    default String getTemplateName(final String name) { return resolvePartialPath("", name, ""); }

    default String resolvePartialPath(final String dir, final String name, final String extension) { return null; }

}
