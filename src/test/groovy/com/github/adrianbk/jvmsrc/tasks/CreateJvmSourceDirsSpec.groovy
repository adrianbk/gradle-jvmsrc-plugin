package com.github.adrianbk.jvmsrc.tasks

import com.github.adrianbk.jvmsrc.plugin.JvmSrcExtension
import org.gradle.api.internal.project.DefaultProject
import org.gradle.testfixtures.ProjectBuilder
import spock.lang.Specification
import spock.lang.Unroll

import static org.apache.maven.artifact.ant.shaded.FileUtils.deleteDirectory
import static org.apache.maven.artifact.ant.shaded.FileUtils.fileExists

class CreateJvmSourceDirsSpec extends Specification {

  final DefaultProject testProject = new ProjectBuilder().build()
  final CreateJvmSourceDirs task = testProject.tasks.create('createJvmSrcDirs', CreateJvmSourceDirs.class)

  def setup() {
    testProject.extensions.create("jvmsrc", JvmSrcExtension)
  }

  def cleanup() {
    deleteDirectory(testProject.rootDir.path + '/src')
  }

  def "Should create a new JVM project"() {
    given:
      testProject.apply plugin: 'java'
      testProject.apply plugin: 'groovy'
      testProject.apply plugin: 'scala'

      testProject.jvmsrc.packageName = 'org.ajar.gradle.plugin.develop'

    when:
      task.createJVMProjectSource()

    then:
      fileExists(testProject.rootDir.path + '/src/main/resources')
      fileExists(testProject.rootDir.path + '/src/test/resources')

      assertJvmSourceSet 'groovy'
      assertJvmSourceSet 'java'
      assertJvmSourceSet 'scala'

      assertKeep 'groovy'
      assertKeep 'java'
      assertKeep 'scala'
  }

  @Unroll
  def "should not include keeps"() {
    given:
      testProject.apply plugin: 'java'

      testProject.jvmsrc.packageName = 'org.ajar.gradle.plugin.develop'
      testProject.jvmsrc.includeKeep = included

    when:
      deleteDirectory(testProject.rootDir.path + '/src')
      task.createJVMProjectSource()

    then:
      fileExists(testProject.rootDir.path + "/src/main/java/org/ajar/gradle/plugin/develop/.gitkeep") == included

    where:
      included << [true, false]
  }

  private void assertJvmSourceSet(String lang) {
    assert fileExists(testProject.rootDir.path + "/src/main/$lang/org/ajar/gradle/plugin/develop")
    assert fileExists(testProject.rootDir.path + "/src/test/$lang/org/ajar/gradle/plugin/develop")
  }

  private void assertKeep(String lang) {
    assert fileExists(testProject.rootDir.path + "/src/main/$lang/org/ajar/gradle/plugin/develop/.gitkeep")
    assert fileExists(testProject.rootDir.path + "/src/test/$lang/org/ajar/gradle/plugin/develop/.gitkeep")
    assert fileExists(testProject.rootDir.path + "/src/main/resources/.gitkeep")
    assert fileExists(testProject.rootDir.path + "/src/test/resources/.gitkeep")
  }

  def "should resolve package name"() {
    given:
      testProject.jvmsrc.packageName = packageName

    expect:
      task.resolvePackageName.call() == expected

    where:
      packageName                   || expected
      'org.ajar.gradle.plugin.test' || 'org.ajar.gradle.plugin.test'
      ''                            || 'com.nopackage'
      null                          || 'com.nopackage'

  }

  def "should convert package names to directory paths"() {
    String sep = File.separator
    expect:
      task.packageToDirectoryPath.call('org.ajar.gradle.plugin') == "org${sep}ajar${sep}gradle${sep}plugin"

  }

  def "should not create keep files on directory with contents"() {
    given:
      File dir = new File(testProject.rootDir.path + "/nokeeps")
      dir.mkdirs()
      new File(dir.getAbsolutePath() + '/someExistingFile.txt').createNewFile()
      testProject.jvmsrc.includeKeep == true

    when:
      task.maybeCreateKeep(dir.getAbsolutePath())

    then:
      dir.list().size() == 1

  }

}
