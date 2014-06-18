package org.ajar.gradle.jvmsrc.tasks

import org.ajar.gradle.jvmsrc.plugin.JvmSrcExtension
import org.gradle.api.internal.project.DefaultProject
import org.gradle.testfixtures.ProjectBuilder
import spock.lang.Specification

import static org.apache.maven.artifact.ant.shaded.FileUtils.deleteDirectory
import static org.apache.maven.artifact.ant.shaded.FileUtils.fileExists

class CreateJvmSourceDirsAndroidSpec extends Specification {

  final DefaultProject testProject = new ProjectBuilder().build()
  final CreateJvmSourceDirs task = testProject.tasks.create('createJvmSrcDirs', CreateJvmSourceDirs.class)

  def setup() {
    testProject.extensions.create("jvmsrc", JvmSrcExtension)
    testProject.jvmsrc.packageName = 'org.ajar.gradle.plugin.develop'
  }

  def cleanup() {
    deleteDirectory(testProject.rootDir.path + '/src')
  }

  def "Should create a new android project with source"() {
    given:
      testProject.apply plugin: com.android.build.gradle.AppPlugin

    when:
      task.createJVMProjectSource()

    then:
      fileExists(testProject.rootDir.path + '/src/androidTest/resources')
      fileExists(testProject.rootDir.path + '/src/androidTest/java')
      fileExists(testProject.rootDir.path + '/src/debug/resources')
      fileExists(testProject.rootDir.path + '/src/debug/java')
      fileExists(testProject.rootDir.path + '/src/main/resources')
      fileExists(testProject.rootDir.path + '/src/main/java')
      fileExists(testProject.rootDir.path + '/src/release/resources')
      fileExists(testProject.rootDir.path + '/src/release/java')
  }
}
