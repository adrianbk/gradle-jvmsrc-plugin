package com.github.adrianbk.jvmsrc.tasks

import com.github.adrianbk.jvmsrc.plugin.JvmSrcExtension
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

  def "Should create android source sets with variants"() {
    given:
      testProject.apply plugin: com.android.build.gradle.AppPlugin

    when:
      testProject.android {
        productFlavors {
          free {}
          paid {}
        }
      }

      task.createJVMProjectSource()

    then:
      assert assertVariantSourceSet('androidTest')
      assert assertVariantSourceSet('debug')
      assert assertVariantSourceSet('main')
      assert assertVariantSourceSet('release')

      assert assertVariantSourceSet('paid')
      assert assertVariantSourceSet('androidTestPaid')

      assert assertVariantSourceSet('free')
      assert assertVariantSourceSet('androidTestFree')
  }

  def assertVariantSourceSet = { String variant ->
    fileExists(testProject.rootDir.path + "/src/$variant/java") &&
            fileExists(testProject.rootDir.path + "/src/$variant/res")
  }
}
