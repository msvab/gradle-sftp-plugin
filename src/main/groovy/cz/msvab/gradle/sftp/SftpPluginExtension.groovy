package cz.msvab.gradle.sftp

class SftpPluginExtension {
    int port = 22
    Map<String, String> users = ['anonymous': '']
    String rootDir = System.properties['java.io.tmpdir'] + '/gradle-sftp-plugin-home'
}
