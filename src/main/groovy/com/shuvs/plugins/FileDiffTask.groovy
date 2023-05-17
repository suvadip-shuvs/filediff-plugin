package com.shuvs.plugins

import org.gradle.api.DefaultTask
import org.gradle.api.file.RegularFileProperty
import org.gradle.api.tasks.InputFile
import org.gradle.api.tasks.OutputFile
import org.gradle.api.tasks.TaskAction

abstract class FileDiffTask extends DefaultTask {
    @InputFile
    abstract RegularFileProperty getFile1()
    @InputFile
    abstract RegularFileProperty getFile2()
    @OutputFile
    abstract RegularFileProperty getResultFile()

    FileDiffTask() {
        resultFile.convention(project.layout.buildDirectory.file('diff-result.txt'))
    }

    @TaskAction
    def diff() {
        String result
        if(size(file1) == size(file2)) {
            result = "Files have the same size at ${file1.get().asFile.size()} bytes"
        } else {
            File largestFile = size(file1) > size(file2) ? file1.get().asFile : file2.get().asFile
            result = "${largestFile.toString()} was the largest file with ${largestFile.size()} bytes"
        }

        resultFile.get().asFile.write(result)
        println("File written to $resultFile")
        println(result)
    }

    private static long size(RegularFileProperty regularFileProperty) {
        return regularFileProperty.get().asFile.size()
    }
}
