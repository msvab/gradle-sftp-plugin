package cz.msvab.gradle.util

import org.apache.sshd.common.Session
import org.apache.sshd.server.FileSystemFactory
import org.apache.sshd.server.FileSystemView

class CustomFileSystemFactory implements FileSystemFactory {

    private String rootDir

    CustomFileSystemFactory(String rootDir) {
        this.rootDir = rootDir
    }

    @Override
    FileSystemView createFileSystemView(Session session) throws IOException {
        def userName = session.username
        def userHome = new File(rootDir)

        if (userHome.file) {
            throw new SftpException("Could not create SFTP directory '$userHome.absolutePath', file with the same name already exists");
        }

        if ((!userHome.exists()) && (!userHome.mkdirs())) {
            throw new SftpException("Could not create SFTP directory '$userHome.absolutePath'")
        }

        return new UserFileSystemView(userHome.absolutePath, userName)
    }
}
