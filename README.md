# Exploring Maven Toolchains and how to work with them

The [Maven Toolchains Plugin](https://maven.apache.org/plugins/maven-toolchains-plugin/index.html) is a very neat plugin
that allows auto-discovery of JDKs/JREs without having to deal with `JAVA_HOME` and decouple JDK management from project
setup.

Toolchains come in two flavors:

- self-managed using the `toolchains.xml` file usually located in `~/.m2/`
- auto-discovered using the plugin goals `toolchains:display-discovered-jdk-toolchains`
  and `toolchains:generate-jdk-toolchains-xml` usually located at `~/.m2/discovered-jdk-toolchains-cache.xml`

The auto-discovery mechanism has been available since
version [3.2.0](https://issues.apache.org/jira/browse/MTOOLCHAINS-49).

The guides/explanations I consulted for this demonstration are:

- [Usage](https://maven.apache.org/plugins/maven-toolchains-plugin/usage.html)
- [JDK Standard Toolchain](https://maven.apache.org/plugins/maven-toolchains-plugin/toolchains/jdk.html)
- [Guide to Toolchains](https://maven.apache.org/guides/mini/guide-using-toolchains.html)

All of them were accessed on the 6th of August 2024. Contents may have changed since then.

## What is tested here and what are my assumptions?

I would like to specify that my project uses a particular major version of Java, let's say 21. In order to build this
project with this particular Java version and ensure tests etc. are all run using this version to verify compatibility
with my runtime container I would like to code that into the project definition.

With Maven Toolchains I should be able to:

- define where in my environment (local or remote) available JDKs are located
- specify within my project what the JDK expectations are to build it

Until the 3.2.0 release of Maven Toolchains I had to create my own toolchain definitions, looking like this:

```xml
<?xml version="1.0" encoding="UTF-8"?>
<toolchains
  xmlns="http://maven.apache.org/TOOLCHAINS/1.1.0"
  xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
  xsi:schemaLocation="http://maven.apache.org/TOOLCHAINS/1.1.0 https://maven.apache.org/xsd/toolchains-1.1.0.xsd">
  <toolchain>
    <type>jdk</type>
    <provides>
      <version>17</version>
      <vendor>Adoptium</vendor>
    </provides>
    <configuration>
      <jdkHome>/Library/Java/JavaVirtualMachines/temurin-17.jdk/Contents/Home</jdkHome>
    </configuration>
  </toolchain>
  <toolchain>
    <type>jdk</type>
    <provides>
      <version>21</version>
      <vendor>Adoptium</vendor>
    </provides>
    <configuration>
      <jdkHome>/Library/Java/JavaVirtualMachines/temurin-21.jdk/Contents/Home</jdkHome>
    </configuration>
  </toolchain>
</toolchains>
```

As you can see, the version definition in particular only states the major release version of the defined JDK. When
looking at the auto generated toolchain definition this looks like this:

```xml
<?xml version="1.0" encoding="UTF-8"?>
<toolchains
  xmlns="http://maven.apache.org/TOOLCHAINS/1.1.0"
  xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
  xsi:schemaLocation="http://maven.apache.org/TOOLCHAINS/1.1.0 https://maven.apache.org/xsd/toolchains-1.1.0.xsd">
  <toolchain>
    <type>jdk</type>
    <provides>
      <vendor.version>Temurin-21.0.3+9</vendor.version>
      <lts>true</lts>
      <runtime.name>OpenJDK Runtime Environment</runtime.name>
      <version>21.0.3</version>
      <vendor>Eclipse Adoptium</vendor>
      <runtime.version>21.0.3+9-LTS</runtime.version>
    </provides>
    <configuration>
      <jdkHome>/Library/Java/JavaVirtualMachines/temurin-21.jdk/Contents/Home</jdkHome>
    </configuration>
  </toolchain>
  <toolchain>
    <type>jdk</type>
    <provides>
      <vendor.version>Temurin-17.0.11+9</vendor.version>
      <lts>true</lts>
      <runtime.name>OpenJDK Runtime Environment</runtime.name>
      <version>17.0.11</version>
      <vendor>Eclipse Adoptium</vendor>
      <runtime.version>17.0.11+9</runtime.version>
    </provides>
    <configuration>
      <jdkHome>/Library/Java/JavaVirtualMachines/temurin-17.jdk/Contents/Home</jdkHome>
    </configuration>
  </toolchain>
</toolchains>
```

As you can see there is no equivalent to `version=21` in the generated file. As a result, I am seemingly unable to
target this feature release of Java generically. At the same time using `version=21.0.3` as a toolchain target defeats
the purpose of the whole thing, i.e. being able to say "Java 21 works" because `21.0.1` is not equal to `21.0.3` but
still a release of Java 21.

Following that discovery I built a number of different possible solutions to this problem by using version ranges
instead. 

All in all there are questions the current documentation doesn't appear to answer:

- with only specifying one Java version in my `pom.xml` explicitly, how do I say `21 <= x < 22` as a range?
- can I auto-calculate versions and ranges based on that? How?
- can I use the same declaration syntax in my `pom.xml` for specifying auto-discovered toolchains and self-managed ones?
- can I still use "just 21" with auto-discovered toolchains?
- ...

Have a look at the modules and the Workflows in this repository to see the results.
