[![Build Status](https://travis-ci.org/adrianbk/gradle-jvmsrc-plugin.png?branch=master)](https://travis-ci.org/adrianbk/gradle-jvmsrc-plugin)

 [ ![Download](https://api.bintray.com/packages/adrianbkelly/maven/gradle-jvmsrc-plugin/images/download.png) ](https://bintray.com/adrianbkelly/maven/gradle-jvmsrc-plugin/_latestVersion)

gradle-jvmsrc-plugin
====================

### Description
A gradle plugin which provides a task to create the default source, test and resource package directories for JVM 
projects (java, groovy, scala, android etc.)

The plugin inspects the existing language plugins applied to your project and creates the appropriate directory and package structures along with optionally adding .gitkeep files to each directory.

Directories and packages are created with the conventional structure e.g:

- src/main/resources
- src/main/java/<your package structure>
- src/main/groovy/<your package structure>
- src/test/java/<your package structure>
- src/test/groovy/<your package structure>
- src/test/resources

The plugin becomes particularly useful for muti-project builds where several sub projects are created.

#### Android
- Supports android variants and flavours
- Requires versions 0.10+ or 0.11+ of androids gradle build tools `com.android.tools.build:gradle`

### Dependencies

Add the plugin repo and classpath dependency to your build script.

```groovy

buildscript {
    repositories {
        jcenter()
    }
    dependencies {
        classpath "com.github.adrianbk:gradle-jvmsrc-plugin:0.6.1"
    }
}

```

#### Snapshot repo:

http://oss.jfrog.org/artifactory/oss-snapshot-local
```
classpath(group: 'com.github.adrianbk', name: 'gradle-jvmsrc-plugin', version: '0.7-SNAPSHOT')

```


### Usage
 - Apply the plugin to your project or sub projects which adds the task `createJvmSrcDirs` to your project/subprojects

```groovy

    apply plugin: 'com.github.adrianbk.jvmsrc'

or

subprojects{
    apply plugin: 'com.github.adrianbk.jvmsrc'
}
```


- Apply one or more jvm language plugins to your gradle project

```groovy
apply plugin: 'java'
apply plugin: 'groovy'

```

- Configure the base package name in your projects build script

```groovy

jvmsrc {
    packageName "com.mycompany.myproject.mymodule"
}

```

- Disable .gitkeep file creation

```
jvmsrc {
    includeKeeps false
}
```

- Execute the task to create directories

```groovy
./gradlew createJvmSrcDirs

```

### Typical workflow to create a new subproject/module
- Create the project dir: /parent-project/my-module
- Create /parent-project/my-module/build.gradle
- Apply the language plugins to /parent-project/my-module/build.gradle e.g. `apply plugin: 'java'`
- Add the new module to settings.gradle (include "my-module")
- Create the directories: `./gradlew :my-module:createJvmSrcDirs`


### License
[Apache-2.0] (http://www.apache.org/licenses/LICENSE-2.0.html)


