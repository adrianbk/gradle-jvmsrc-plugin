package com.github.adrianbk.jvmsrc.plugin

import com.github.adrianbk.jvmsrc.tasks.CreateJvmSourceDirs
import org.gradle.api.Project
import org.gradle.api.plugins.ExtensionContainer
import spock.lang.Specification

class JvmSrcPluginTest extends Specification {

  def "should add tasks and extensions"() {
    given:
      ExtensionContainer extensionContainer = Mock()
      Project project = Mock()
      project.getExtensions() >> extensionContainer

    when:
      new JvmSrcPlugin().apply(project)

    then:
      1 * extensionContainer.create("jvmsrc", JvmSrcExtension)
      1 * project.task(['type':CreateJvmSourceDirs], 'createJvmSrcDirs')
  }
}
