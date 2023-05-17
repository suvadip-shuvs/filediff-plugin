package com.shuvs.plugins
import org.gradle.api.Plugin
import org.gradle.api.Project


class FileDiffPlugin implements Plugin<Project> {
    @Override
    void apply(Project project) {
        project.extensions.create('filediff',FileDiffExtension)
        project.tasks.register('filediff', FileDiffTask) {
            file1 = project.filediff.file1
            file2 = project.filediff.file2
        }
    }
}
