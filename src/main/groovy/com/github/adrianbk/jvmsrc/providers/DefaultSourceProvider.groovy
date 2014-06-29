package com.github.adrianbk.jvmsrc.providers

public class DefaultSourceProvider implements SourceProvider{
  @Override
  public List getSources(Object container) {
    allDirs(container).findAll {it.dir.name != 'resources'}
  }

  @Override
  public List getResources(Object container) {
   allDirs(container).findAll { it.dir.name == 'resources' }
  }

  def allDirs(container){
    container.sourceSets*.allSource.srcDirTrees.flatten()
  }
}
