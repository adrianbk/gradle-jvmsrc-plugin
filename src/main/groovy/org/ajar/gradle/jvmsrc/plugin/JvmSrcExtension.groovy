package org.ajar.gradle.jvmsrc.plugin

import org.ajar.gradle.jvmsrc.providers.AndroidSourceProvider
import org.ajar.gradle.jvmsrc.providers.SourceProvider
import org.gradle.api.Project

class JvmSrcExtension {

  public static final String DEFAULT_PACKAGE = "com.nopackage"

  Map additionalSourceContainers = ['android' : new AndroidSourceProvider()]
  String packageName = DEFAULT_PACKAGE
  Boolean includeKeep = true

  void setPackageName(String packageName) {
    this.packageName = packageName ?: DEFAULT_PACKAGE
  }

  void setIncludeKeep(boolean includeKeep) {
    this.includeKeep = includeKeep
  }

  void setAdditionalSourceContainers(Map additionalSourceContainers) {
    this.additionalSourceContainers.putAll(additionalSourceContainers)
  }

  def allSourceDirs(Project p) {
    def dirs = allSource(p) ?: []
    additionalSourceContainers.each { String srcContainer, SourceProvider provider ->
      if (p.hasProperty(srcContainer)) {
        dirs += provider.getSources(p."$srcContainer")
      }
    }
    dirs
  }

  def allSource(obj){
    obj.sourceSets*.allSource.srcDirTrees.flatten()
  }

}
