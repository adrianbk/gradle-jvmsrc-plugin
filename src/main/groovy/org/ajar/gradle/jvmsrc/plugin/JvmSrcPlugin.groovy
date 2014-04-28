package org.ajar.gradle.jvmsrc.plugin

import org.ajar.gradle.jvmsrc.tasks.CreateJvmSourceDirs
import org.gradle.api.Plugin
import org.gradle.api.Project

class JvmSrcPlugin implements Plugin<Project> {
    @Override
    void apply(Project project) {
        project.extensions.create("jvmsrc", JvmSrcExtension)
        project.task("createJvmSrcDirs", type: CreateJvmSourceDirs)
    }
}
