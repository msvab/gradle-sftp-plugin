package cz.msvab.gradle.util

import org.apache.sshd.server.FileSystemView
import org.apache.sshd.server.SshFile

class UserFileSystemView implements FileSystemView {

    private String rootDir
    private String userName

    UserFileSystemView(String rootDir, String userName) {
        this.userName = userName
        this.rootDir = rootDir
    }

    @Override
    SshFile getFile(String file) {
        return getFile(rootDir, file)
    }

    @Override
    SshFile getFile(SshFile baseDir, String file) {
        return getFile(baseDir.absolutePath, file)
    }

    SshFile getFile(String baseDir, String file) {
        String physicalName = file.contains(baseDir) ? file : baseDir + '/' + file
        File fileObj = new File(physicalName).getCanonicalFile()
        return new CustomSshFile(stripRoot(fileObj.absolutePath), fileObj, userName);
    }

    String stripRoot(String path) {
        String relativePath = path
        if (relativePath.contains(rootDir)) {
            relativePath = relativePath.substring(relativePath.indexOf(rootDir) + rootDir.length())
        }
        return relativePath.startsWith('/') ? relativePath : '/' + relativePath
    }
}
