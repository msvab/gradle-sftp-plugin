package cz.msvab.gradle.sftp

import org.gradle.api.Plugin
import org.gradle.api.Project

class SftpPlugin implements Plugin<Project> {

    @Override
    void apply(Project project) {
        project.extensions.create('sftp', SftpPluginExtension)

        project.task(type: StartSftpTask, 'startSftp')
    }
}
