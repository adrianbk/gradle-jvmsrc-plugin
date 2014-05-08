package org.ajar.gradle.jvmsrc.plugin

class JvmSrcExtension {
  public static final String DEFAULT_PACKAGE = "com.nopackage"

  String packageName = DEFAULT_PACKAGE
  Boolean includeKeep = true
  List additionalSourceSetContainers = []

  void setPackageName(String packageName) {
    this.packageName = packageName ?: DEFAULT_PACKAGE
  }

  void setIncludeKeep(boolean includeKeep) {
    this.includeKeep = includeKeep
  }

  void setAdditionalSourceSetContainers(List additionalSourceSetContainers) {
    this.additionalSourceSetContainers = additionalSourceSetContainers
  }
}
