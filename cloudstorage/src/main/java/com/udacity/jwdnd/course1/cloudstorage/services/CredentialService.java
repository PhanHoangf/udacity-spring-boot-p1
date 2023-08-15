package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.mapper.CredentialMapper;
import com.udacity.jwdnd.course1.cloudstorage.mapper.UserMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.Credential;
import com.udacity.jwdnd.course1.cloudstorage.model.User;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CredentialService {
    private CredentialMapper credentialMapper;
    private UserMapper userMapper;
    private EncryptionService encryptionService;
    private HashService hashService;

    public CredentialService(
            CredentialMapper credentialMapper,
            UserMapper userMapper,
            EncryptionService encryptionService,
            HashService hashService
    ) {
        this.credentialMapper = credentialMapper;
        this.userMapper = userMapper;
        this.encryptionService = encryptionService;
        this.hashService = hashService;
    }

    public List<Credential> getCredentials(String username) {
        User user = userMapper.getUser(username);

        if (user != null) {
            return credentialMapper.getCredentialsByUserId(user.getUserId());
        }

        return new ArrayList<>();
    }

    public Integer insertCredential(Credential credential, String username) {
        User user = this.userMapper.getUser(username);

        String encodedSalt = hashService.generateSalt();
        String encryptedPassword = encryptionService.encryptValue(credential.getPassword_credential(), encodedSalt);

        Credential newCredential = new Credential(null, credential.getUrl(), credential.getUsername(), encodedSalt, encryptedPassword, user.getUserId());
        return credentialMapper.insertCredential(newCredential);
    }

    public String getDecryptedPassword(Integer credentialId) {
        Credential credential = credentialMapper.getCredentialById(credentialId);
        return encryptionService.decryptValue(credential.getPassword_credential(), credential.getSalt());
    }

    public Integer updateCredential(Credential credential) {
        String encodedSalt = hashService.generateSalt();
        String encryptedPassword = encryptionService.encryptValue(credential.getPassword_credential(), encodedSalt);

        Credential updateCredential = new Credential(credential.getCredentialid(), credential.getUrl(), credential.getUsername(), encodedSalt, encryptedPassword, credential.getUserid());
        return credentialMapper.update(updateCredential);
    }

    public Integer deleteCredential(Integer credentialid) {
        return credentialMapper.deleteCredential( credentialid );
    }
}
