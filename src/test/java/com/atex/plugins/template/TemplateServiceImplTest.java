package com.atex.plugins.template;

import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.util.List;
import java.util.Map;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

@RunWith(MockitoJUnitRunner.class)
public class TemplateServiceImplTest {

    private TemplateService service = new TemplateServiceImpl();

    @Test
    public void testSimpleScope() throws IOException {
        final String templateName = "/mustache/simpleScope.hbs";
        final Map<String, String> scope = new HashMap<>();
        scope.put("name", "marco");

        final String result = service.execute(templateName, scope);
        Assert.assertEquals("Hello marco", result);
    }

    @Test
    public void testComplexScope() throws IOException {
        final String templateName = "/mustache/complexScope.hbs";
        final UserList userList = new UserList();
        userList.users.add(new User("user1", "user1@email.it"));
        userList.users.add(new User("user2", "user2@email.it"));
        userList.users.add(new User("user3", "user3@email.it"));
        final Site site = new Site("arena");

        final String result = service.execute(templateName, new Object[] { site, userList });
        final String expected = "Hello arena\n" +
                "<b>user1-user1@email.it</b>\n" +
                "<b>user2-user2@email.it</b>\n" +
                "<b>user3-user3@email.it</b>\n";
        Assert.assertEquals(expected, result);
    }

    @Test
    public void testPartialScope() throws IOException {
        final String templateName = "/mustache/partialParent.hbs";
        final UserList userList = new UserList();
        userList.users.add(new User("user1", "user1@email.it"));
        userList.users.add(new User("user2", "user2@email.it"));
        userList.users.add(new User("user3", "user3@email.it"));
        final Site site = new Site("arena");

        final String result = service.execute(templateName, new Object[] { site, userList });
        final String expected = "Hello arena\n" +
                "<b>user1-user1@email.it</b>\n" +
                "<b>user2-user2@email.it</b>\n" +
                "<b>user3-user3@email.it</b>\n";
        Assert.assertEquals(expected, result);
    }

    @Test
    public void testCustomResolver() throws IOException {
        final String result = service.execute(new TestTemplateResolver(), "sendto.mail.default", new Object());
        Assert.assertEquals("pippo pluto", result);
    }

    private class TestTemplateResolver implements TemplateResolver {

        Map<String, String> contents = new HashMap<>();

        public TestTemplateResolver() {
            contents.put("sendto.mail.default", "pippo {{>sendto.mail.collection}}");
            contents.put("sendto.mail.collection", "pluto");
        }

        @Override
        public String resolvePartialPath(final String dir, final String name, final String extension) {
            return name;
        }

        @Override
        public Reader getReader(final String templateName) {
            final String s = contents.get(templateName);
            if (s != null) {
                return new StringReader(s);
            } else {
                return null;
            }
        }
    }

    private class User {
        private String name;
        private String email;

        public User(final String name, final String email) {
            this.name = name;
            this.email = email;
        }

        public String getName() {
            return name;
        }

        public String getEmail() {
            return email;
        }
    }

    private class UserList {
        private List<User> users = new ArrayList<>();

        public List<User> getUsers() {
            return users;
        }
    }

    private class Site {
        private String name;

        public Site(final String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }
    }

}