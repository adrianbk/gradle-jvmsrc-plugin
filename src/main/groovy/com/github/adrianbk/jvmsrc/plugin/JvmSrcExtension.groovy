package com.github.adrianbk.jvmsrc.plugin

import com.github.adrianbk.jvmsrc.providers.AndroidSourceProvider
import com.github.adrianbk.jvmsrc.providers.DefaultSourceProvider
import com.github.adrianbk.jvmsrc.providers.SourceProvider
import org.gradle.api.Project

class JvmSrcExtension {

  public static final String DEFAULT_PACKAGE = "com.nopackage"
  SourceProvider defaultSourceProvider = new DefaultSourceProvider()
  Map additionalSourceContainers = ['android': new AndroidSourceProvider()]
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
    def dirs = defaultSourceProvider.getSources(p)
    additionalSourceContainers.each { String srcContainer, SourceProvider provider ->
      if (p.hasProperty(srcContainer)) {
        dirs += provider.getSources(p."$srcContainer")
      }
    }
    dirs
  }

  def allResourceDirs(Project p) {
    def dirs = defaultSourceProvider.getResources(p)
    additionalSourceContainers.each { String srcContainer, SourceProvider provider ->
      if (p.hasProperty(srcContainer)) {
        dirs += provider.getResources(p."$srcContainer")
      }
    }
    dirs
  }
}
