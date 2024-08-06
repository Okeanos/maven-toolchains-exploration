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
