package com.example.vmo1.service.impl;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;

import javax.mail.Session;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

import static org.junit.Assert.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class EmailServiceImplTest {

    @Mock
    private JavaMailSender mockMailSender;

    @InjectMocks
    private EmailServiceImpl emailServiceImplUnderTest;

    @Test
    public void testSendEmail() {
        // Setup
        // Configure JavaMailSender.createMimeMessage(...).
        final MimeMessage mimeMessage = new MimeMessage(Session.getInstance(new Properties()));
        when(mockMailSender.createMimeMessage()).thenReturn(mimeMessage);

        // Run the test
        emailServiceImplUnderTest.sendEmail("huent1@vmodev.com", "nguyenhue11052001@gmail.com");

        // Verify the results
        verify(mockMailSender).send(any(MimeMessage.class));
    }

    @Test
    public void testSendEmail_JavaMailSenderSendThrowsMailException() {
        // Setup
        // Configure JavaMailSender.createMimeMessage(...).
        final MimeMessage mimeMessage = new MimeMessage(Session.getInstance(new Properties()));
        when(mockMailSender.createMimeMessage()).thenReturn(mimeMessage);

        doThrow(MailException.class).when(mockMailSender).send(any(MimeMessage.class));

        // Run the test
        assertThrows(MailException.class, () -> emailServiceImplUnderTest.sendEmail("to", "email"));
    }
}
