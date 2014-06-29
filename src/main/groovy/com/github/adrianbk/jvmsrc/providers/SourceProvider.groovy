package com.github.adrianbk.jvmsrc.providers

interface SourceProvider {
  List getSources(Object container)
  List getResources(Object container)
}
