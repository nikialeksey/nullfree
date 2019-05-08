# Nullfree

![Elegant Objects Respected Here](http://www.elegantobjects.org/badge.svg)

![nullfree status](https://iwillfailyou.com/nullfree/nikialeksey/nullfree)

[![Maven Central](https://maven-badges.herokuapp.com/maven-central/com.nikialeksey/nullfree/badge.svg)](https://maven-badges.herokuapp.com/maven-central/com.nikialeksey/nullfree)
[![Build Status](https://travis-ci.org/nikialeksey/nullfree.svg?branch=master)](https://travis-ci.org/nikialeksey/nullfree)
[![codecov](https://codecov.io/gh/nikialeksey/nullfree/branch/master/graph/badge.svg)](https://codecov.io/gh/nikialeksey/nullfree)

[![License: MIT](https://img.shields.io/badge/License-MIT-yellow.svg)](https://github.com/nikialeksey/nullfree/blob/master/LICENSE)

## What is it?

It is the service for analysing your project contains or not the `null` literal.
With **Nullfree** you can add a badge to your project who looks like 
![without nulls](https://img.shields.io/badge/nullfree-approved-green.svg), if your project
does not contain `null`, else if project contains at least one `null` it will looks like
![with nulls](https://img.shields.io/badge/nullfree-declined-red.svg).

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
    id 'com.nikialeksey.nullfree' version '1.1.0'
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
    <version>1.1.0</version>
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
It's ok, if you use it, `NullPointerException` does not throw in this place, so you can add option to **nullfree**
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
    <version>1.1.0</version>
    <configuration>
        <skipComparisions>true</skipComparisions>
    </configuration>
</plugin>
```

## Changelog
`1.1.0` - Skip nulls in comparisions expressions ability

`1.0.0` - Changed API of nullfree lib, null suppression ability

`0.0.2` - First version of service and plugin, self checking nullfree badge
