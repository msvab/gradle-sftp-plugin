package cz.msvab.gradle.sftp


import cz.msvab.gradle.util.CustomFileSystemFactory
import cz.msvab.gradle.util.MapBasedPasswordAuthenticator
import org.apache.sshd.SshServer
import org.apache.sshd.server.command.ScpCommandFactory
import org.apache.sshd.server.keyprovider.SimpleGeneratorHostKeyProvider
import org.apache.sshd.server.sftp.SftpSubsystem
import org.gradle.api.DefaultTask
import org.gradle.api.tasks.TaskAction

class StartSftpTask extends DefaultTask {

    @TaskAction
    void startSftp() {
        def config = project.sftp

        def server = SshServer.setUpDefaultServer()
        server.port = config.port
        server.keyPairProvider = new SimpleGeneratorHostKeyProvider(System.properties['java.io.tmpdir'] + '/gradle-sftp-plugin-hostkey.ser')
        server.commandFactory = new ScpCommandFactory()
        server.subsystemFactories = [new SftpSubsystem.Factory()]
        server.passwordAuthenticator = new MapBasedPasswordAuthenticator(config.users)
        server.fileSystemFactory = new CustomFileSystemFactory(config.rootDir)
        server.start()
    }
}
