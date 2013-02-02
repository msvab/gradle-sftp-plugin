[![Build Status](https://secure.travis-ci.org/msvab/gradle-sftp-plugin.png)](http://travis-ci.org/msvab/gradle-sftp-plugin)

# Gradle SFTP Plugin

Plugin that starts embedded SFTP server. Useful for integration tests that don't have to rely on external services.

## Usage

To use the plugin, include in your build script:
```groovy
apply plugin: 'sftp'
```

## Configuration options
* `port`: Port on which SFTP server will be started (default: 22)
* `users`: Map of users with passwords that have access to this server (default: ['anonymous': ''])
* `rootDir`: Local directory, that will serve as a root directory for the server (default: System.properties['java.io.tmpdir'] + '/gradle-sftp-plugin-home')