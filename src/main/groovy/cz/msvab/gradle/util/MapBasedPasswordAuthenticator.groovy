package cz.msvab.gradle.util

import org.apache.sshd.server.PasswordAuthenticator
import org.apache.sshd.server.session.ServerSession

public class MapBasedPasswordAuthenticator implements PasswordAuthenticator {

    private def users = [:]

    MapBasedPasswordAuthenticator(users) {
        this.users = users
    }

    @Override
    public boolean authenticate(String username, String password, ServerSession session) {
        return users.any { user ->
            user.key == username && user.value == password
        }
    }
}
