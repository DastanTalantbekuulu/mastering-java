## Common Utils
[![Codacy Badge](https://app.codacy.com/project/badge/Grade/b7fb8b8d6b514b5aa754b86e9bd26e7c)](https://www.codacy.com/gh/spranshu1/common-utils/dashboard?utm_source=github.com&amp;utm_medium=referral&amp;utm_content=spranshu1/common-utils&amp;utm_campaign=Badge_Grade) [![Build Project](https://github.com/spranshu1/common-utils/actions/workflows/build.yml/badge.svg?branch=master)](https://github.com/spranshu1/common-utils/actions/workflows/build.yml)

A small java project holding various utility classes to reduce development effort. 

## Target Version

[![Maven Central](https://img.shields.io/maven-central/v/com.github.spranshu1.common.util/common-utils.svg?label=Maven%20Central)](https://search.maven.org/search?q=g:%22com.github.spranshu1.common.util%22%20AND%20a:%22common-utils%22)

Getting started:

* [Prerequisites](#markdown-header-prerequisites)
* [Setup](#markdown-header-setup)
* [Release Log](#markdown-header-releaselog)
* [Contact](#markdown-header-authors)

## Prerequisites

Ensure local installation of following softwares/tools:

* JDK - 1.8
    ```markdown
    $ java -version
    java version "1.8.0_121"
    ```
* Apache Maven - if using maven dependency 
    ```https://maven.apache.org/install.html```
* Gradle - if using gradle
    ```https://gradle.org/install/```

## Setup

### Apache Maven

* Add dependency in your `pom.xml`

	```markdown
	
	<dependency>
      <groupId>com.github.spranshu1.common.util</groupId>
      <artifactId>common-utils</artifactId>
      <version>${version}</version>
    </dependency>
	
	```
### Gradle Groovy DSL

* ```implementation 'com.github.spranshu1.common.util:common-utils:[<version>]' ```

### Gradle Kotlin DSL

* ```implementation("com.github.spranshu1.common.util:common-utils:[<version>]") ```

## Release Log

`1.0.3`

- **Updates**:
	- Introduced `JsonParser` a generic Serializer/Deserializer utility for parsing Json.
	- Code quality improvements	    
- **Fixes**:
	- Minor code smells

`1.0.2`

- **Updates**:
	- Below utilities are now available
	    - ZipUtil
	    - HexUtil
	    - FileUtil
	- `JSONHandler` now support creating JSON from ResultSet Object	    
- **Fixes**:
	- none

`1.0.1`

- **Updates**:
	- Vulnerability patch `com.fasterxml.jackson` version bump to `2.9.10.3`
	- `Jgitflow` added for release process automation 
- **Fixes**:
	- none
	
`1.0.0`

- First version	

### Support or Contact
```
spranshu1
```
