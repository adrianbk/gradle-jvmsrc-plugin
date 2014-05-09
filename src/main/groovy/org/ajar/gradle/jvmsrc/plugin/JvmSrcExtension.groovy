package org.ajar.gradle.jvmsrc.plugin

import org.gradle.api.Project

class JvmSrcExtension {

  public static final String DEFAULT_PACKAGE = "com.nopackage"

  List additionalSourceContainers = ['android']
  String packageName = DEFAULT_PACKAGE
  Boolean includeKeep = true

  void setPackageName(String packageName) {
    this.packageName = packageName ?: DEFAULT_PACKAGE
  }

  void setIncludeKeep(boolean includeKeep) {
    this.includeKeep = includeKeep
  }

  def allSourceDirs(Project p) {
    def dirs = allSource(p) ?: []
    additionalSourceContainers.each { String srcContainer ->
      if (p.hasProperty(srcContainer)) {
        dirs += allSource(p."$srcContainer")
      }
    }
    dirs
  }

  def allSource(obj){
    obj.sourceSets*.allSource.srcDirTrees.flatten()
  }

}
