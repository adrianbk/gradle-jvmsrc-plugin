package org.ajar.gradle.jvmsrc.plugin

class JvmSrcExtension {
  public static final String DEFAULT_PACKAGE = "com.nopackage"

  String packageName = DEFAULT_PACKAGE
  Boolean includeKeep = true

  void setPackageName(String packageName) {
    this.packageName = packageName ?: DEFAULT_PACKAGE
  }

  void setIncludeKeep(boolean includeKeep) {
    this.includeKeep = includeKeep
  }
}
