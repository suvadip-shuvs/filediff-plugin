import org.gradle.testkit.runner.GradleRunner
import org.gradle.testkit.runner.TaskOutcome
import spock.lang.Specification
import spock.lang.TempDir

class FileDiffPluginSpec extends Specification{
    @TempDir
    File testProjectDir
    File buildFile

    def setup() {
        buildFile = new File(testProjectDir, 'build.gradle')
        buildFile << """
            plugins {
                id 'com.shuvs.file-diff'
            }
        """
    }

    void 'can diff 2 files of same length'() {
        given:
        File testFile1 = new File(testProjectDir, 'testFile1.txt')
        /*testFile1 << """
        This is a testfile1
        """*/
        testFile1.createNewFile()
        File testFile2 = new File(testProjectDir, 'testFile2.txt')
        testFile2.createNewFile()

        buildFile << """
            filediff { 
                file1 = file('${testFile1.getName()}')
                file2 = file('${testFile2.getName()}')
            }
        """

        when:
        def result = GradleRunner.create()
                .withProjectDir(testProjectDir)
                .withArguments('filediff')
                .withPluginClasspath()
                .build()

        then:
        result.output.contains("Files have the same size")
        result.task(":filediff").outcome == TaskOutcome.SUCCESS
    }
}
