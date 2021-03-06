package com.github.adrianbk.jvmsrc.tasks

import org.gradle.api.DefaultTask
import org.gradle.api.tasks.TaskAction

class CreateJvmSourceDirs extends DefaultTask {

  String description = 'Creates a JVM based project structure based on the plugins applied to the project.'
  String group = "build setup"

  def allSourceDirs = {
    project.jvmsrc.allSourceDirs(project)
  }

  def allResourceDirs = {
    project.jvmsrc.allResourceDirs(project)
  }

  @TaskAction
  def createJVMProjectSource() {
    String packageDir = packageToDirectoryPath.call(resolvePackageName.call())
    logger.info "Creating source structure for project: $project.name with package directory: ${packageDir}"

    allSourceDirs().dir.each { File dir ->
      logger.info("Creating source directory: ${dir}")
      dir.mkdirs()
      String directoryPath = dir.absolutePath + File.separator + packageDir
      logger.info "Creating packge directories: ${directoryPath}"
      def pFile = new File(directoryPath)
      pFile.mkdirs()
      maybeCreateKeep pFile.getAbsolutePath()
    }

    allResourceDirs().dir.each { File dir ->
      logger.info("Creating resource directory: ${dir}")
      dir.mkdirs()
      maybeCreateKeep dir.getAbsolutePath()
    }
  }

  def maybeCreateKeep(String dir) {
    if (project.jvmsrc.includeKeep == true) {
      File container = new File(dir)
      if (!container.list()) {
        new File(dir + '/.gitkeep').createNewFile()
      }
    }
  }

  def resolvePackageName = {
    def name = project.jvmsrc.packageName
    assert name
    name
  }

  def packageToDirectoryPath = { String packageName ->
    packageName.replaceAll('\\.', File.separator)
  }
}
