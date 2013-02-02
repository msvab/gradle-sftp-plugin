package cz.msvab.gradle.util

import org.apache.sshd.server.filesystem.NativeSshFile;

public class CustomSshFile extends NativeSshFile {

    public CustomSshFile(String fileName, File file, String userName) {
        super(fileName, file, userName)
    }
}
