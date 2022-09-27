package com.example.vmo1.service.impl;

import com.example.vmo1.commons.exceptions.ResourceNotFoundException;
import com.example.vmo1.model.entity.Account;
import com.example.vmo1.model.entity.PasswordResetToken;
import com.example.vmo1.model.entity.Role;
import com.example.vmo1.model.request.PasswordResetLinkRequest;
import com.example.vmo1.model.request.PasswordResetRequest;
import com.example.vmo1.model.response.MessageResponse;
import com.example.vmo1.repository.AccountRepository;
import com.example.vmo1.repository.PasswordResetTokenRepository;
import com.example.vmo1.service.EmailSender;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDateTime;
import java.util.*;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ForgotPasswordServiceImplTest {

    @Mock
    private PasswordResetTokenServiceImpl mockPasswordResetTokenServiceImpl;
    @Mock
    private PasswordResetTokenRepository mockPasswordResetTokenRepository;
    @Mock
    private AccountRepository mockAccountRepository;
    @Mock
    private EmailSender mockEmailSender;
    @Mock
    private PasswordEncoder mockPasswordEncoder;

    @InjectMocks
    private ForgotPasswordServiceImpl forgotPasswordServiceImplUnderTest;

    @Test
    public void testConfirmTokenResetPassword() {
        // Setup
        // Configure PasswordResetTokenRepository.findByToken(...).
        final Account account = new Account();
        account.setId(0L);
        account.setFullname("fullname");
        account.setUsername("username");
        account.setPassword("password");
        account.setEmail("email");
        account.setPhone("phone");
        account.setCreated_at(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        account.setUpdated_at(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        account.set_deleted(false);
        account.setEnable(false);
        final Role role = new Role();
        role.setId(0L);
        role.setName("name");
        role.setCreated_at(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        role.setUpdated_at(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        account.setRoles(new HashSet<>(Arrays.asList(role)));
        final Optional<PasswordResetToken> passwordResetToken = Optional.of(
                new PasswordResetToken(0L, "token", LocalDateTime.now().plusMinutes(15), null, false,
                        new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime(),
                        new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime(), false, account));
        when(mockPasswordResetTokenRepository.findByToken("token")).thenReturn(passwordResetToken);

        // Run the test
        final String result = forgotPasswordServiceImplUnderTest.confirmTokenResetPassword("token");

        // Verify the results
        assertEquals("Your email is confirmed. Please input new password to reset password!!!", result);
        verify(mockPasswordResetTokenRepository).updateConfirmedAt("token", LocalDateTime.now());
    }

    @Test
    public void testConfirmTokenResetPassword_PasswordResetTokenRepositoryFindByTokenReturnsAbsent() {
        // Setup
        when(mockPasswordResetTokenRepository.findByToken("token")).thenReturn(Optional.empty());

        // Run the test
        assertThrows(NoSuchElementException.class,
                () -> forgotPasswordServiceImplUnderTest.confirmTokenResetPassword("token"));
    }

    @Test
    public void testSendTokenToChangePassword() {
        // Setup
        final PasswordResetLinkRequest request = new PasswordResetLinkRequest("email");
        final MessageResponse expectedResult = new MessageResponse(200, "Success: Token send successfully!");

        // Configure AccountRepository.findByEmail(...).
        final Account account1 = new Account();
        account1.setId(0L);
        account1.setFullname("fullname");
        account1.setUsername("username");
        account1.setPassword("password");
        account1.setEmail("email");
        account1.setPhone("phone");
        account1.setCreated_at(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        account1.setUpdated_at(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        account1.set_deleted(false);
        account1.setEnable(false);
        final Role role = new Role();
        role.setId(0L);
        role.setName("name");
        role.setCreated_at(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        role.setUpdated_at(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        account1.setRoles(new HashSet<>(Arrays.asList(role)));
        final Optional<Account> account = Optional.of(account1);
        when(mockAccountRepository.findByEmail("email")).thenReturn(account);

        // Configure PasswordResetTokenRepository.save(...).
        final Account account2 = new Account();
        account2.setId(0L);
        account2.setFullname("fullname");
        account2.setUsername("username");
        account2.setPassword("password");
        account2.setEmail("email");
        account2.setPhone("phone");
        account2.setCreated_at(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        account2.setUpdated_at(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        account2.set_deleted(false);
        account2.setEnable(false);
        final Role role1 = new Role();
        role1.setId(0L);
        role1.setName("name");
        role1.setCreated_at(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        role1.setUpdated_at(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        account2.setRoles(new HashSet<>(Arrays.asList(role1)));
        final PasswordResetToken passwordResetToken = new PasswordResetToken(0L, "token",
                LocalDateTime.of(2020, 1, 1, 0, 0, 0), LocalDateTime.of(2020, 1, 1, 0, 0, 0), false,
                new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime(),
                new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime(), false, account2);
        when(mockPasswordResetTokenRepository.save(
                new PasswordResetToken(0L, "token", LocalDateTime.of(2020, 1, 1, 0, 0, 0),
                        LocalDateTime.of(2020, 1, 1, 0, 0, 0), false,
                        new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime(),
                        new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime(), false, new Account())))
                .thenReturn(passwordResetToken);

        // Run the test
        final MessageResponse result = forgotPasswordServiceImplUnderTest.sendTokenToChangePassword(request);

        // Verify the results
        assertEquals(expectedResult, result);
        verify(mockPasswordResetTokenRepository).save(
                new PasswordResetToken(0L, "token", LocalDateTime.of(2020, 1, 1, 0, 0, 0),
                        LocalDateTime.of(2020, 1, 1, 0, 0, 0), false,
                        new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime(),
                        new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime(), false, new Account()));
        verify(mockEmailSender).sendEmail("email", "eee");
    }

    @Test
    public void testSendTokenToChangePassword_AccountRepositoryReturnsAbsent() {
        // Setup
        final PasswordResetLinkRequest request = new PasswordResetLinkRequest("email");
        when(mockAccountRepository.findByEmail("email")).thenReturn(Optional.empty());

        // Run the test
        assertThrows(ResourceNotFoundException.class,
                () -> forgotPasswordServiceImplUnderTest.sendTokenToChangePassword(request));
    }

    @Test
    public void testChangePassword() {
        // Setup
        final PasswordResetRequest request = new PasswordResetRequest("email", "password", "confirmPassword", "token");
        final MessageResponse expectedResult = new MessageResponse(0, "Change password fail!!");

        // Configure PasswordResetTokenServiceImpl.getValidToken(...).
        final Account account = new Account();
        account.setId(0L);
        account.setFullname("fullname");
        account.setUsername("username");
        account.setPassword("password");
        account.setEmail("email");
        account.setPhone("phone");
        account.setCreated_at(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        account.setUpdated_at(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        account.set_deleted(false);
        account.setEnable(false);
        final Role role = new Role();
        role.setId(0L);
        role.setName("name");
        role.setCreated_at(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        role.setUpdated_at(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        account.setRoles(new HashSet<>(Arrays.asList(role)));
        final PasswordResetToken passwordResetToken = new PasswordResetToken(0L, "token",
                LocalDateTime.of(2020, 1, 1, 0, 0, 0), LocalDateTime.of(2020, 1, 1, 0, 0, 0), false,
                new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime(),
                new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime(), false, account);
        when(mockPasswordResetTokenServiceImpl.getValidToken(
                new PasswordResetRequest("email", "password", "confirmPassword", "token")))
                .thenReturn(passwordResetToken);

        // Configure AccountRepository.findByEmail(...).
        final Account account2 = new Account();
        account2.setId(0L);
        account2.setFullname("fullname");
        account2.setUsername("username");
        account2.setPassword("password");
        account2.setEmail("email");
        account2.setPhone("phone");
        account2.setCreated_at(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        account2.setUpdated_at(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        account2.set_deleted(false);
        account2.setEnable(false);
        final Role role1 = new Role();
        role1.setId(0L);
        role1.setName("name");
        role1.setCreated_at(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        role1.setUpdated_at(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        account2.setRoles(new HashSet<>(Arrays.asList(role1)));
        final Optional<Account> account1 = Optional.of(account2);
        when(mockAccountRepository.findByEmail("email")).thenReturn(account1);

        when(mockPasswordEncoder.encode("password")).thenReturn("password");

        // Configure AccountRepository.save(...).
        final Account account3 = new Account();
        account3.setId(0L);
        account3.setFullname("fullname");
        account3.setUsername("username");
        account3.setPassword("password");
        account3.setEmail("email");
        account3.setPhone("phone");
        account3.setCreated_at(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        account3.setUpdated_at(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        account3.set_deleted(false);
        account3.setEnable(false);
        final Role role2 = new Role();
        role2.setId(0L);
        role2.setName("name");
        role2.setCreated_at(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        role2.setUpdated_at(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        account3.setRoles(new HashSet<>(Arrays.asList(role2)));
        when(mockAccountRepository.save(any(Account.class))).thenReturn(account3);

        // Run the test
        final MessageResponse result = forgotPasswordServiceImplUnderTest.changePassword(request);

        // Verify the results
        assertEquals(expectedResult, result);
        verify(mockAccountRepository).save(any(Account.class));
        verify(mockPasswordResetTokenServiceImpl).deleteToken(
                new PasswordResetRequest("email", "password", "confirmPassword", "token"));
    }

    @Test
    public void testChangePassword_AccountRepositoryFindByEmailReturnsAbsent() {
        // Setup
        final PasswordResetRequest request = new PasswordResetRequest("email", "password", "confirmPassword", "token");

        // Configure PasswordResetTokenServiceImpl.getValidToken(...).
        final Account account = new Account();
        account.setId(0L);
        account.setFullname("fullname");
        account.setUsername("username");
        account.setPassword("password");
        account.setEmail("email");
        account.setPhone("phone");
        account.setCreated_at(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        account.setUpdated_at(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        account.set_deleted(false);
        account.setEnable(false);
        final Role role = new Role();
        role.setId(0L);
        role.setName("name");
        role.setCreated_at(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        role.setUpdated_at(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        account.setRoles(new HashSet<>(Arrays.asList(role)));
        final PasswordResetToken passwordResetToken = new PasswordResetToken(0L, "token",
                LocalDateTime.of(2020, 1, 1, 0, 0, 0), LocalDateTime.of(2020, 1, 1, 0, 0, 0), false,
                new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime(),
                new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime(), false, account);
        when(mockPasswordResetTokenServiceImpl.getValidToken(
                new PasswordResetRequest("email", "password", "confirmPassword", "token")))
                .thenReturn(passwordResetToken);

        when(mockAccountRepository.findByEmail("email")).thenReturn(Optional.empty());

        // Run the test
        assertThrows(ResourceNotFoundException.class, () -> forgotPasswordServiceImplUnderTest.changePassword(request));
    }

    @Test
    public void testUpdatePassword() {
        // Setup
        // Configure AccountRepository.findByEmail(...).
        final Account account1 = new Account();
        account1.setId(0L);
        account1.setFullname("fullname");
        account1.setUsername("username");
        account1.setPassword("password");
        account1.setEmail("email");
        account1.setPhone("phone");
        account1.setCreated_at(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        account1.setUpdated_at(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        account1.set_deleted(false);
        account1.setEnable(false);
        final Role role = new Role();
        role.setId(0L);
        role.setName("name");
        role.setCreated_at(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        role.setUpdated_at(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        account1.setRoles(new HashSet<>(Arrays.asList(role)));
        final Optional<Account> account = Optional.of(account1);
        when(mockAccountRepository.findByEmail("email")).thenReturn(account);

        when(mockPasswordEncoder.encode("password")).thenReturn("password");

        // Configure AccountRepository.save(...).
        final Account account2 = new Account();
        account2.setId(0L);
        account2.setFullname("fullname");
        account2.setUsername("username");
        account2.setPassword("password");
        account2.setEmail("email");
        account2.setPhone("phone");
        account2.setCreated_at(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        account2.setUpdated_at(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        account2.set_deleted(false);
        account2.setEnable(false);
        final Role role1 = new Role();
        role1.setId(0L);
        role1.setName("name");
        role1.setCreated_at(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        role1.setUpdated_at(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        account2.setRoles(new HashSet<>(Arrays.asList(role1)));
        when(mockAccountRepository.save(any(Account.class))).thenReturn(account2);

        // Run the test
        final boolean result = forgotPasswordServiceImplUnderTest.updatePassword("email", "password");

        // Verify the results
        assertFalse(result);
        verify(mockAccountRepository).save(any(Account.class));
    }

    @Test
    public void testUpdatePassword_AccountRepositoryFindByEmailReturnsAbsent() {
        // Setup
        when(mockAccountRepository.findByEmail("email")).thenReturn(Optional.empty());

        // Run the test
        assertThrows(ResourceNotFoundException.class,
                () -> forgotPasswordServiceImplUnderTest.updatePassword("email", "password"));
    }

    @Test
    public void testSaveConfirmationToken() {
        // Setup
        final Account account = new Account();
        account.setId(0L);
        account.setFullname("fullname");
        account.setUsername("username");
        account.setPassword("password");
        account.setEmail("email");
        account.setPhone("phone");
        account.setCreated_at(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        account.setUpdated_at(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        account.set_deleted(false);
        account.setEnable(false);
        final Role role = new Role();
        role.setId(0L);
        role.setName("name");
        role.setCreated_at(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        role.setUpdated_at(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        account.setRoles(new HashSet<>(Arrays.asList(role)));

        // Configure PasswordResetTokenRepository.save(...).
        final Account account1 = new Account();
        account1.setId(0L);
        account1.setFullname("fullname");
        account1.setUsername("username");
        account1.setPassword("password");
        account1.setEmail("email");
        account1.setPhone("phone");
        account1.setCreated_at(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        account1.setUpdated_at(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        account1.set_deleted(false);
        account1.setEnable(false);
        final Role role1 = new Role();
        role1.setId(0L);
        role1.setName("name");
        role1.setCreated_at(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        role1.setUpdated_at(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        account1.setRoles(new HashSet<>(Arrays.asList(role1)));
        final PasswordResetToken passwordResetToken = new PasswordResetToken(0L, "token",
                LocalDateTime.of(2020, 1, 1, 0, 0, 0), LocalDateTime.of(2020, 1, 1, 0, 0, 0), false,
                new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime(),
                new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime(), false, account1);
        when(mockPasswordResetTokenRepository.save(
                new PasswordResetToken(0L, "token", LocalDateTime.of(2020, 1, 1, 0, 0, 0),
                        LocalDateTime.of(2020, 1, 1, 0, 0, 0), false,
                        new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime(),
                        new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime(), false, new Account())))
                .thenReturn(passwordResetToken);

        // Run the test
        forgotPasswordServiceImplUnderTest.saveConfirmationToken(account, "token");

        // Verify the results
        verify(mockPasswordResetTokenRepository).save(
                new PasswordResetToken(0L, "token", LocalDateTime.of(2020, 1, 1, 0, 0, 0),
                        LocalDateTime.of(2020, 1, 1, 0, 0, 0), false,
                        new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime(),
                        new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime(), false, new Account()));
    }

    @Test
    public void testBuildEmail() {
        assertEquals("result", forgotPasswordServiceImplUnderTest.buildEmail("name", "link"));
    }
}
