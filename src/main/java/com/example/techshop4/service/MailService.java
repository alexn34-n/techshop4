package com.example.techshop4.service;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp;
import com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiver;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.util.Base64;
import com.google.api.client.util.store.FileDataStoreFactory;
import com.google.api.services.gmail.Gmail;
import com.google.api.services.gmail.GmailScopes;
import org.mortbay.util.ajax.JSON;
import org.springframework.http.converter.json.Jackson2ObjectMapperFactoryBean;
import org.springframework.stereotype.Service;
import com.google.api.services.gmail.model.Message;

import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.*;
import java.security.GeneralSecurityException;
import java.util.List;
import java.util.Properties;

@Service
public class MailService {
    private static final String APPLICATION_NAME = "Spring shop";
    private static final JsonFactory JSON_FACTORY = JacksonFactory.getDefaultInstance();
    private static final String TOKENS_DIRECTORY_PATH = "tokens";

    private static final List<String> SCOPES = List.of(GmailScopes.GMAIL_SEND, GmailScopes.MAIL_GOOGLE_COM);
    private static final String CREDENTIALS_FILE_PATH = "/credential.json";

    private Credential getCredentials(final NetHttpTransport httpTransport) throws IOException {
        InputStream in = MailService.class.getResourceAsStream(CREDENTIALS_FILE_PATH);
        if (in == null) {
            throw new FileNotFoundException("Не найден файлен с данными о пользователе");
        }

        GoogleClientSecrets clientSecrets = GoogleClientSecrets.load(JSON_FACTORY, new InputStreamReader(in));
        GoogleAuthorizationCodeFlow flow = new GoogleAuthorizationCodeFlow.Builder(
                httpTransport, JSON_FACTORY, clientSecrets, SCOPES)
                .setDataStoreFactory(new FileDataStoreFactory(new File(TOKENS_DIRECTORY_PATH)))
                .setAccessType("offline")
                .build();
        LocalServerReceiver receiver = new LocalServerReceiver.Builder().setPort(8888).build();
        return new AuthorizationCodeInstalledApp(flow, receiver).authorize("user");
    }

    public void sendMessage(String email, String text) {
        NetHttpTransport httpTransport = null;
        try {
            httpTransport = GoogleNetHttpTransport.newTrustedTransport();

            Gmail service = new Gmail.Builder(httpTransport, JSON_FACTORY, getCredentials(httpTransport))
                    .setApplicationName(APPLICATION_NAME)
                    .build();

            String user = "me";
            Session session = Session.getDefaultInstance(new Properties(), null);
            MimeMessage emailContent = new MimeMessage(session);
            emailContent.setFrom(new InternetAddress("me"));
            emailContent.addRecipient(javax.mail.Message.RecipientType.TO, new InternetAddress(email));
            emailContent.setSubject("Test");
            emailContent.setText(text);

            Message message = createMessageWithEmail(emailContent);

            service.users().messages().send(user, message).execute();
        } catch (GeneralSecurityException | IOException | MessagingException e) {
            e.printStackTrace();
        }
    }

    private Message createMessageWithEmail(MimeMessage email) {
        try (ByteArrayOutputStream buffer = new ByteArrayOutputStream()) {
            email.writeTo(buffer);
            byte[] bytes = buffer.toByteArray();
            String encodedEmail = Base64.encodeBase64URLSafeString(bytes);
            Message message = new com.google.api.services.gmail.model.Message();
            message.setRaw(encodedEmail);
            return message;
        } catch (IOException | MessagingException e) {
            e.printStackTrace();
        }

        return null;
    }


}