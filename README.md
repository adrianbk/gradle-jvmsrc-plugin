[![Build Status](https://travis-ci.org/adrianbk/gradle-jvmsrc-plugin.png?branch=master)](https://travis-ci.org/adrianbk/gradle-jvmsrc-plugin)

[ ![Download](https://api.bintray.com/packages/adrianbkelly/maven/gradle-jvm-src-plugin/images/download.png) ](https://bintray.com/adrianbkelly/maven/gradle-jvm-src-plugin/_latestVersion)

gradle-jvmsrc-plugin
====================


### Description
A gradle plugin which provides a task to create the default source, test and resource package directories for JVM projects (java, groovy, scala, etc.)
The plugin inspects the existing language plugins applied to your project and creates the appropriate directory and package structures along with adding .gitkeep to each directory.

Directories and packages are created with the maven convention e.g:

- src/main/resources
- src/main/java/<your package structure>
- src/main/groovy/<your package structure>

- src/test/java/<your package structure>
- src/test/groovy/<your package structure>
- src/test/resources

The plugin becomes particularly useful for muti-project builds where several sub projects are created.

### Dependencies

Add the plugin repo and classpath dependency to your build script.

```groovy
buildscript {
    repositories {
        maven { url 'http://dl.bintray.com/adrianbkelly/maven'}
    }
    dependencies {
        classpath 'com.ak:gradle-jvm-src-plugin:0.1'
    }
}

```

### Usage
 - Apply the plugin to your project or sub projects which adds a task `createJvmSrcDirs` to your project/subprojects

```groovy

apply plugin: 'jvmsrc'

or

subprojects{
    apply plugin: 'jvmsrc'
}
```


- Apply one or more jvm plugin to your gradle project

```groovy
apply plugin: 'java'
apply plugin: 'groovy'

```

- Configure the plugin in your projects build script

```groovy

jvmsrc {
    packageName "com.mycompany.myproject.mymodule"
}

```

- Execute the task

```groovy
./gradlew createJvmSrcDirs

```

### Typical workflow to create a new subproject/module
- Create the project dir: /parent-project/my-module
- Create /parent-project/my-module/build.gradle
- Apply the language plugins to /parent-project/my-module/build.gradle e.g. `apply plugin: 'java'`
- Add the new module to settings.gradle (include "my-module")
- Create the directories: `./gradlew :my-module:createJvmSrcDirs`






