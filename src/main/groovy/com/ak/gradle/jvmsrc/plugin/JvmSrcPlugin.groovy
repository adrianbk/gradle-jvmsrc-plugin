package com.ak.gradle.jvmsrc.plugin

import com.ak.gradle.jvmsrc.tasks.CreateJvmSourceDirs
import org.gradle.api.Plugin
import org.gradle.api.Project

class JvmSrcPlugin implements Plugin<Project> {
    @Override
    void apply(Project project) {
        project.extensions.create("jvmsrc", JvmSrcExtension)
        project.task("createJvmSrcDirs", type: CreateJvmSourceDirs)
    }
}
