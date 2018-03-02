package com.atex.plugins.template;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.Charset;

/**
 * DefaultTemplateResolver
 *
 * @author mnova
 */
public class DefaultTemplateResolver implements TemplateResolver {

    @Override
    public Reader getReader(final String templateName) {
        final InputStream stream = this.getClass().getResourceAsStream(templateName);
        if (stream != null) {
            return new InputStreamReader(stream, Charset.forName("UTF-8"));
        } else {
            return null;
        }
    }

}
