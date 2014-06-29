package com.github.adrianbk.jvmsrc.providers

import org.gradle.api.internal.file.collections.DirectoryFileTree

class AndroidSourceProvider implements SourceProvider {

  List getSources(Object container) {
    sourceSetFinder(container.sourceSets) {
      it.getJavaDirectories()
    }
  }

  @Override
  List getResources(Object container) {
    sourceSetFinder(container.sourceSets) {
      it.getResDirectories()
    }
  }

  def sourceSetFinder = { sourceSets, Closure closure ->
    List dirs = []
    sourceSets?.each { sourceSet ->
      dirs += closure(sourceSet)
    }
    return dirs.collect { new DirectoryFileTree(it) }
  }
}
