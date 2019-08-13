# Nullfree

![Elegant Objects Respected Here](https://www.elegantobjects.org/badge.svg)

![nullfree status](https://iwillfailyou.com/nullfree/nikialeksey/nullfree)

[![Lib version](https://img.shields.io/maven-central/v/com.nikialeksey/nullfree.svg?label=lib)](https://maven-badges.herokuapp.com/maven-central/com.nikialeksey/nullfree)
[![Gradle plugin version](https://img.shields.io/maven-metadata/v/https/plugins.gradle.org/m2/com/nikialeksey/nullfree/com.nikialeksey.nullfree.gradle.plugin/maven-metadata.xml.svg?label=plugin-gradle)](https://plugins.gradle.org/plugin/com.nikialeksey.nullfree)
[![Maven plugin version](https://img.shields.io/bintray/v/nikialeksey/java/nullfree-maven-plugin.svg?label=plugin-maven)](https://bintray.com/nikialeksey/java/nullfree-maven-plugin/_latestVersion)
[![Build Status](https://travis-ci.org/nikialeksey/nullfree.svg?branch=master)](https://travis-ci.org/nikialeksey/nullfree)
[![codecov](https://codecov.io/gh/nikialeksey/nullfree/branch/master/graph/badge.svg)](https://codecov.io/gh/nikialeksey/nullfree)

[![License: MIT](https://img.shields.io/badge/License-MIT-yellow.svg)](https://github.com/nikialeksey/nullfree/blob/master/LICENSE)

![logo](https://raw.githubusercontent.com/nikialeksey/nullfree/master/assets/github-logo.png)

## What is it?

It is the service for analysing your project contains or not the `null` literal.
With **Nullfree** you can add a badge to your project which looks like 
![without nulls](https://img.shields.io/badge/nullfree-approved-green.svg), if your project
does not contain `null`, else if project contains at least one `null` it will looks like
![with nulls](https://img.shields.io/badge/nullfree-declined-red.svg). There's even more, **Nullfree** will fail project
build, if it contains a null.

## For what?

[Based on statistics from Overops](https://blog.overops.com/the-top-10-exceptions-types-in-production-java-applications-based-on-1b-events/)
[`NullPointerException`](https://docs.oracle.com/javase/8/docs/api/?java/lang/NullPointerException.html)
is at the top of all exceptions in Java apps.
It's simple: if there is no one `null` in a codebase, then
[`NullPointerException`](https://docs.oracle.com/javase/8/docs/api/?java/lang/NullPointerException.html)
will be gone. **Let's stop using `null`!**

## Getting started

### Gradle
Add the **Nullfree** plugin:
```groovy
plugins {
    id 'com.nikialeksey.nullfree' version '1.4.1'
}
```

Invoke it:
```bash
./gradlew nullfree
```

### Maven
Add the **Nullfree** plugin:
```xml
<plugin>
    <groupId>com.nikialeksey</groupId>
    <artifactId>nullfree-maven-plugin</artifactId>
    <version>1.4.1</version>
</plugin>
```

Invoke it:
```bash
mvn nullfree:nullfree
```

Add your nullfree badge to the project readme:
```markdown
![nullfree status](https://iwillfailyou.com/nullfree/<your nickname>/<your repo>)
```

## Suppress
You can suppress any null by `@SuppressWarnings("nullfree")` annotation:
```java
@SuppressWarnings("nullfree")
class A {
    private final String a = null;
}
```
Method, field, variable suppresses are all available as well.

## Other ways to ignoring nulls

Sometimes (usually in integrations with foreign libraries) it have to use null in comparisions:
```java
if (some != null) { ... }
if (other == null) { ... }
```
It's ok, if you use it, `NullPointerException` does not throw in this place, so you can add option to **Nullfree**
plugin for skipping such nulls: 

### Gradle plugin
```groovy
nullfree {
    skipComparisions = true
}
```

### Maven plugin
```xml
<plugin>
    <groupId>com.nikialeksey</groupId>
    <artifactId>nullfree-maven-plugin</artifactId>
    <version>1.4.1</version>
    <configuration>
        <skipComparisions>true</skipComparisions>
    </configuration>
</plugin>
```

## Nulls threshold
You can the nulls threshold to allow project has a few nulls, but there would be only few.

### Gradle plugin
```groovy
nullfree {
    threshold = 5
}
```

### Maven plugin
```xml
<plugin>
    <groupId>com.nikialeksey</groupId>
    <artifactId>nullfree-maven-plugin</artifactId>
    <version>1.4.1</version>
    <configuration>
        <threshold>5</threshold>
    </configuration>
</plugin>
```

## Changelog
`1.4.2` - Support all (from 1 to 12) java version syntax when parsing nulls

`1.4.1` - Parsing error tips in exception message

`1.4.0` - Threshold for nulls count

`1.3.0` - Send null descriptions to the API instead of badge url

`1.2.0` - Fail build if it contains a null

`1.1.0` - Skip nulls in comparisions expressions ability

`1.0.0` - Changed API of nullfree lib, null suppression ability

`0.0.2` - First version of service and plugin, self checking nullfree badge
