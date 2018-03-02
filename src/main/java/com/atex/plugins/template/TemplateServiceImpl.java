package com.atex.plugins.template;

import java.io.IOException;
import java.io.Reader;
import java.io.StringWriter;
import java.io.Writer;
import java.util.UUID;

import com.github.mustachejava.Mustache;
import com.github.mustachejava.MustacheFactory;

/**
 * Implementation of {@link TemplateService} using mustache.
 *
 * @author mnova
 */
public class TemplateServiceImpl implements TemplateService {

    @Override
    public Writer execute(final Writer writer, final TemplateResolver resolver, final String templateName, final Object scope)
            throws IOException {
        return execute(writer, resolver, templateName, toArray(scope));
    }

    @Override
    public Writer execute(final Writer writer, final TemplateResolver resolver, final String templateName, final Object[] scopes)
            throws IOException {
        return execute_internal(writer, resolver, templateName, scopes);
    }

    @Override
    public String execute(final TemplateResolver resolver, final String templateName, final Object scope)
            throws IOException {
        return execute(resolver, templateName, toArray(scope));
    }

    @Override
    public String execute(final TemplateResolver resolver, final String templateName, final Object[] scopes)
            throws IOException {
        return execute(new StringWriter(), resolver, templateName, scopes).toString();
    }

    @Override
    public Writer execute(final Writer writer, final String templateName, final Object scope) throws IOException {
        return execute(writer, new DefaultTemplateResolver(), templateName, scope);
    }

    @Override
    public Writer execute(final Writer writer, final String templateName, final Object[] scopes) throws IOException {
        return execute(writer, new DefaultTemplateResolver(), templateName, scopes);
    }

    @Override
    public String execute(final String templateName, final Object scope) throws IOException {
        return execute(templateName, toArray(scope));
    }

    @Override
    public String execute(final String templateName, final Object[] scopes) throws IOException {
        return execute(new StringWriter(), templateName, scopes).toString();
    }

    @Override
    public String execute(final Reader reader, final Object scope) throws IOException {
        return execute(reader, toArray(scope));
    }

    @Override
    public String execute(final Reader reader, final Object[] scopes) throws IOException {
        return execute_internal(new StringWriter(), reader, UUID.randomUUID().toString(), scopes).toString();
    }

    private Writer execute_internal(final Writer writer, final Reader reader, final String templateName, final Object[] scopes) throws IOException {
        final MustacheFactory mf = new CustomMustacheFactory();
        Mustache mustache = mf.compile(reader, templateName);
        mustache.execute(writer, scopes);
        writer.flush();
        return writer;
    }

    private Writer execute_internal(final Writer writer, final TemplateResolver resolver, final String templateName, final Object[] scopes) throws IOException {
        final MustacheFactory mf = new CustomMustacheFactory(resolver);
        Mustache mustache = mf.compile(templateName);
        mustache.execute(writer, scopes);
        writer.flush();
        return writer;
    }

    private Object[] toArray(final Object scope) {
        return (scope != null) ? new Object[] { scope } : null;
    }

}
