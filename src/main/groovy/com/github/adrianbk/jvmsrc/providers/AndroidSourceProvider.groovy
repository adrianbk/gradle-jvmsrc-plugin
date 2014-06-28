package com.github.adrianbk.jvmsrc.providers

import org.gradle.api.internal.file.collections.DirectoryFileTree

class AndroidSourceProvider implements SourceProvider {

  List getSources(Object container) {
    List srcDirs = []
    container.sourceSets?.each {
      def javaDirs = it.getJavaDirectories()
      def resourceDirs = it.getResDirectories()
      srcDirs += javaDirs
      srcDirs += resourceDirs
    }
    srcDirs.collect { new DirectoryFileTree(it) }
  }
}
