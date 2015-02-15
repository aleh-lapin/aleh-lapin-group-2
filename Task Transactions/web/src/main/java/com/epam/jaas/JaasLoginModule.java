package com.epam.jaas;

import java.security.acl.Group;
import java.util.Map;

import javax.security.auth.Subject;
import javax.security.auth.callback.CallbackHandler;
import javax.security.auth.login.LoginException;

import org.jboss.security.SimpleGroup;
import org.jboss.security.SimplePrincipal;
import org.jboss.security.auth.spi.UsernamePasswordLoginModule;

public class JaasLoginModule extends UsernamePasswordLoginModule {

    @SuppressWarnings("rawtypes")
    public void initialize(Subject subject, CallbackHandler callbackHandler,
            Map sharedState,
            Map options) {
        super.initialize(subject, callbackHandler, sharedState, options);
    }

    @Override
    protected String getUsersPassword() throws LoginException {
        // Lets pretend we got the password from somewhere and that it's, by a chance, same as the username
        String password = super.getUsername();
        // Let's also pretend that we haven't got it in plain text but encrypted
        password = password.toUpperCase();
        return password;
    }

    @Override
    protected boolean validatePassword(String inputPassword, String expectedPassword) {
        // Let's encrypt the password typed by the user in the same way as the stored password
        // so that they can be compared for equality.
        String encryptedInputPassword = (inputPassword == null)? null : inputPassword.toUpperCase();
        return super.validatePassword(encryptedInputPassword, expectedPassword);
    }

    @Override
    protected Group[] getRoleSets() throws LoginException {
        SimpleGroup group = new SimpleGroup("Roles");
        try {
            group.addMember(new SimplePrincipal("user_role"));
        } catch (Exception e) {
            throw new LoginException("Failed to create group member for " + group);
        }
        return new Group[] { group };
    }

}