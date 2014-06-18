package org.ajar.gradle.jvmsrc.providers

import org.gradle.api.internal.file.collections.DirectoryFileTree

class AndroidSourceProvider implements SourceProvider {

  List getSources(Object container) {
    List srcDirs = []
    container.sourceSets?.each {
      def javaDirs = it.getJavaDirectories()
      def resourceDirs = it.getResourcesDirectories()
      srcDirs += javaDirs
      srcDirs += resourceDirs
    }
    srcDirs.collect { new DirectoryFileTree(it) }
  }
}
