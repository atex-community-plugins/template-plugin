template
================

This plugin give an easy to use template service based on https://github.com/spullara/mustache.java.

In order for the plugin to be available you need to add the configuration dependency to your top POM.
```
  <dependency>
    <groupId>com.atex.plugins</groupId>
    <artifactId>template-plugin</artifactId>
    <version>1.0</version>
  </dependency>

```

Then you simple create an instance of the service and pass a template name:

```
final TemplateService service = new TemplateServiceBuilder().create();
final String templateName = "/mustache/simpleScope.hbs";
final Map<String, String> scope = Maps.newHashMap();
scope.put("name", "marco");

final String result = service.execute(templateName, scope);
```

where `/mustache/simpleScope.hbs` is stored in your resources folder and it may contain a mustache template, for example:

```
Hello {{name}}
```
## Polopoly Version
10.16.3-fp3

## Code Status
The code in this repository is provided with the following status: **EXAMPLE**

Under the open source initiative, Atex provides source code for plugin with different levels of support. There are three different levels of support used. These are:

- EXAMPLE  
The code is provided as an illustration of a pattern or blueprint for how to use a specific feature. Code provided as is.

- PROJECT  
The code has been identified in an implementation project to be generic enough to be useful also in other projects. This means that it has actually been used in production somewhere, but it comes "as is", with no support attached. The idea is to promote code reuse and to provide a convenient starting point for customization if needed.

- PRODUCT  
The code is provided with full product support, just as the core Polopoly product itself.
If you modify the code (outside of configuraton files), the support is voided.


## License
Atex Polopoly Source Code License
Version 1.0 February 2012

See file **LICENSE** for details
