package cz.msvab.gradle.util;

import org.junit.Test;

public class MapBasedPasswordAuthenticatorTest {

    @Test
    void shouldAuthenticateKnownUser() {
        def authenticator = new MapBasedPasswordAuthenticator(['user': 'pwd'])

        assert authenticator.authenticate('user', 'pwd', null)
    }

    @Test
    void shouldRejectUnknownUsers() {
        def authenticator = new MapBasedPasswordAuthenticator(['user': 'pwd'])

        assert !authenticator.authenticate('user', 'password', null)
        assert !authenticator.authenticate('user?', 'pwd', null)
        assert !authenticator.authenticate('foo', 'bar', null)
        assert !authenticator.authenticate(null, null, null)
    }
}
