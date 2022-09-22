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
        account.setIs_deleted(false);
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
                        new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime(), account));
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
        account1.setIs_deleted(false);
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
        account2.setIs_deleted(false);
        account2.setEnable(false);
        final Role role1 = new Role();
        role1.setId(0L);
        role1.setName("name");
        role1.setCreated_at(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        role1.setUpdated_at(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        account2.setRoles(new HashSet<>(Arrays.asList(role1)));
        final PasswordResetToken passwordResetToken = new PasswordResetToken("token",
                LocalDateTime.of(2023, 1, 1, 0, 0, 0),  true, account2);
        when(mockPasswordResetTokenRepository.save(
                new PasswordResetToken( "token", LocalDateTime.now().plusMinutes(15), true, account2)))
                .thenReturn(passwordResetToken);

        // Run the test
        final MessageResponse result = forgotPasswordServiceImplUnderTest.sendTokenToChangePassword(request);
        System.out.println("haha" + result);
        // Verify the results
        assertEquals(new MessageResponse(200, "Success: Token send successfully!"), result);
        verify(mockPasswordResetTokenRepository).save(
                new PasswordResetToken( "token", LocalDateTime.of(2023, 1, 1, 0, 0, 0), true, account2));
        verify(mockEmailSender).sendEmail("to", "email");
    }

    @Test
    public void testSendTokenToChangePassword_AccountRepositoryReturnsAbsent() {
        // Setup
        final PasswordResetLinkRequest request = new PasswordResetLinkRequest("email");
        when(mockAccountRepository.findByEmail("email")).thenReturn(Optional.empty());

        // Run the test
        final MessageResponse result = forgotPasswordServiceImplUnderTest.sendTokenToChangePassword(request);

        // Verify the results
        assertEquals(new MessageResponse(404, "Fail: Email is not found"), result);
    }

    @Test
    public void testChangePassword() {
        // Setup
        final PasswordResetRequest request = new PasswordResetRequest();
        request.setEmail("email");
        request.setPassword("password");
        request.setConfirmPassword("confirmPassword");
        request.setToken("token");

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
        account.setIs_deleted(false);
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
                new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime(), account);
        when(mockPasswordResetTokenServiceImpl.getValidToken(request))
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
        account2.setIs_deleted(false);
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
        account3.setIs_deleted(false);
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
        verify(mockAccountRepository).save(any(Account.class));
        verify(mockPasswordResetTokenServiceImpl).deleteToken(new PasswordResetRequest("email", "password", "confirmPassword", "token"));
    }

    @Test
    public void testChangePassword_AccountRepositoryFindByEmailReturnsAbsent() {
        // Setup
        final PasswordResetRequest request = new PasswordResetRequest();
        request.setEmail("email");
        request.setPassword("password");
        request.setConfirmPassword("confirmPassword");
        request.setToken("token");

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
        account.setIs_deleted(false);
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
                new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime(), account);
        when(mockPasswordResetTokenServiceImpl.getValidToken(request))
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
        account1.setIs_deleted(false);
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
        account2.setIs_deleted(false);
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
        assertTrue(result);
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
        account.setIs_deleted(false);
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
        account1.setIs_deleted(false);
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
                new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime(), account1);
        when(mockPasswordResetTokenRepository.save(
                new PasswordResetToken(0L, "token", LocalDateTime.of(2020, 1, 1, 0, 0, 0),
                        LocalDateTime.of(2020, 1, 1, 0, 0, 0), false,
                        new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime(),
                        new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime(), new Account())))
                .thenReturn(passwordResetToken);

        // Run the test
        forgotPasswordServiceImplUnderTest.saveConfirmationToken(account, "token");

        // Verify the results
        verify(mockPasswordResetTokenRepository).save(
                new PasswordResetToken("token", LocalDateTime.now().plusMinutes(15), true ,account));
    }

    @Test
    public void testBuildEmail() {
        assertEquals("<div style=\"display: none; font-size: 1px; line-height: 1px; max-height: 0; max-width: 0; opacity: 0; overflow: hidden; mso-hide: all; font-family: sans-serif;\">\n" +
                "    &nbsp;\n" +
                "</div>\n" +
                "<table style=\"background: #F7F8FA; border: 0; border-radius: 0; width: 100%;\" cellspacing=\"0\" cellpadding=\"0\">\n" +
                "<tbody>\n" +
                "<tr>\n" +
                "    <td class=\"tw-body\" style=\"padding: 15px 15px 0;\" align=\"center\">\n" +
                "        <table style=\"background: #F7F8FA; border: 0; border-radius: 0;\" cellspacing=\"0\" cellpadding=\"0\">\n" +
                "            <tbody>\n" +
                "            <tr>\n" +
                "                <td class=\"\" style=\"width: 600px;\" align=\"center\">\n" +
                "                    <p style=\"padding: 5px 5px 5px; font-size: 13px; margin: 0 0 0px; color: #316fea;\" align=\"right\">\n" +
                "                    </p>\n" +
                "                    <table style=\"background: #ffffff; border: 0px; border-radius: 4px; width: 99.6672%; overflow: hidden;\"\n" +
                "                           cellspacing=\"0\" cellpadding=\"0\">\n" +
                "                        <tbody>\n" +
                "                        <tr>\n" +
                "                            <td class=\"\" style=\"padding: 0px; width: 100%;\" align=\"center\">\n" +
                "                                <table style=\"background: #336f85; border: 0px; border-radius: 0px; width: 599px; height: 53px; margin-left: auto; margin-right: auto;\"\n" +
                "                                       cellspacing=\"0\" cellpadding=\"0\">\n" +
                "                                    <tbody>\n" +
                "                                    <tr>\n" +
                "\n" +
                "                                        <td class=\"tw-card-header\"\n" +
                "                                            style=\"padding: 5px 5px px; width: 366px; color: #ffff; text-decoration: none; font-family: sans-serif;\"\n" +
                "                                            align=\"center\"><span\n" +
                "                                                style=\"font-weight: 600;\">Email Confirmation Required</span></td>\n" +
                "\n" +
                "                                    </tr>\n" +
                "                                    </tbody>\n" +
                "                                </table>\n" +
                "                                <p><br/><br/></p>\n" +
                "                                <table dir=\"ltr\" style=\"border: 0; width: 100%;\" cellspacing=\"0\" cellpadding=\"0\">\n" +
                "                                    <tbody>\n" +
                "                                    <tr>\n" +
                "                                        <td class=\"tw-card-body\"\n" +
                "                                            style=\"padding: 20px 35px; text-align: left; color: #6f6f6f; font-family: sans-serif; border-top: 0;\">\n" +
                "                                            <h1 class=\"tw-h1\"\n" +
                "                                                style=\"font-size: 24px; font-weight: bold; mso-line-height-rule: exactly; line-height: 32px; margin: 0 0 20px; color: #474747;\">\n" +
                "                                                Hello " + "name" + ",</h1>\n" +
                "                                            <p class=\"\"\n" +
                "                                               style=\"margin: 20px 0; font-size: 16px; mso-line-height-rule: exactly; line-height: 24px;\">\n" +
                "                                            <span style=\"font-weight: 400;\">Thank you for joining <strong>Customer Engagement App</strong>, an efficient and smart solution to manage your health!</span><br/><br/><span\n" +
                "                                                style=\"font-weight: 400;\">To complete the registration process, please confirm your email address to activate your account</span>\n" +
                "                                            <table style=\"border: 0; width: 100%;\" cellspacing=\"0\" cellpadding=\"0\">\n" +
                "                                                <tbody>\n" +
                "                                                <tr>\n" +
                "                                                    <td>\n" +
                "                                                        <table class=\"button mobile-w-full\"\n" +
                "                                                               style=\"border: 0px; border-radius: 7px; margin: 0px auto; width: 525px; background-color: #008bcb; height: 50px;\"\n" +
                "                                                               cellspacing=\"0\" cellpadding=\"0\" align=\"center\">\n" +
                "                                                            <tbody>\n" +
                "                                                            <tr>\n" +
                "                                                                <td class=\"button__td \"\n" +
                "                                                                    style=\"border-radius: 7px; text-align: center; width: 523px;\"><!-- [if mso]>\n" +
                "                                                                    <a href=\"\" class=\"button__a\" target=\"_blank\"\n" +
                "                                                                       style=\"border-radius: 4px; color: #FFFFFF; display: block; font-family: sans-serif; font-size: 18px; font-weight: bold; mso-height-rule: exactly; line-height: 1.1; padding: 14px 18px; text-decoration: none; text-transform: none; border: 1px solid #316FEA;\"> </a>\n" +
                "                                                                    <![endif]--> <!-- [if !mso]><!--> <a\n" +
                "                                                                            class=\"button__a\"\n" +
                "                                                                            style=\"border-radius: 4px; color: #ffffff; display: block; font-family: sans-serif; font-size: 18px; font-weight: bold; mso-height-rule: exactly; line-height: 1.1; padding: 14px 18px; text-decoration: none; text-transform: none; border: 0;\"\n" +
                "                                                                            href=\"" + "link" + "\"\n" +
                "                                                                            target=\"_blank\"\n" +
                "                                                                            rel=\"noopener\">Confirm\n" +
                "                                                                        email</a> <!--![endif]--></td>\n" +
                "                                                            </tr>\n" +
                "                                                            </tbody>\n" +
                "                                                        </table>\n" +
                "                                                    </td>\n" +
                "                                                </tr>\n" +
                "                                                </tbody>\n" +
                "                                            </table>\n" +
                "                                            <div class=\"\"\n" +
                "                                                 style=\"border-top: 0; font-size: 1px; mso-line-height-rule: exactly; line-height: 1px; max-height: 0; margin: 20px 0; overflow: hidden;\">\n" +
                "                                                \u200B\n" +
                "                                            </div>\n" +
                "                                            <p class=\"\"\n" +
                "                                               style=\"margin: 20px 0; font-size: 16px; mso-line-height-rule: exactly; line-height: 24px;\">\n" +
                "                                                Contact our support team if you have any questions or concerns.&nbsp;<a\n" +
                "                                                    style=\"color: #316fea; text-decoration: none;\"\n" +
                "                                                    href=\"javascript:void(0);\" target=\"_blank\" rel=\"noopener\">Ask us any\n" +
                "                                                question</a></p>\n" +
                "                                            <p class=\"tw-signoff\"\n" +
                "                                               style=\"margin: 45px 0 5px; font-size: 16px; mso-line-height-rule: exactly; line-height: 24px;\">\n" +
                "                                                Our best, <br/> The Customer Engagement App team</p>\n" +
                "                                        </td>\n" +
                "                                    </tr>\n" +
                "                                    </tbody>\n" +
                "                                </table>\n" +
                "                            </td>\n" +
                "                        </tr>\n" +
                "                        </tbody>\n" +
                "                    </table>", forgotPasswordServiceImplUnderTest.buildEmail("name", "link"));
    }
}
