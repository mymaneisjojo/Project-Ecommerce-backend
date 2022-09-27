package com.example.vmo1.service.impl;

import com.example.vmo1.commons.exceptions.ResourceNotFoundException;
import com.example.vmo1.model.entity.Account;
import com.example.vmo1.model.entity.ConfirmationToken;
import com.example.vmo1.model.entity.Role;
import com.example.vmo1.model.request.SignupRequest;
import com.example.vmo1.model.response.MessageResponse;
import com.example.vmo1.repository.AccountRepository;
import com.example.vmo1.repository.ConfirmationTokenRepository;
import com.example.vmo1.repository.RoleRepository;
import com.example.vmo1.service.EmailSender;
import com.example.vmo1.validation.validator.EmailValidator;
import com.example.vmo1.validation.validator.PhoneValidator;
import com.example.vmo1.validation.validator.UserNameValidator;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDateTime;
import java.util.*;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class RegistrationServiceImplTest {

    @Mock
    private AccountRepository mockAccountRepository;
    @Mock
    private EmailValidator mockEmailValidator;
    @Mock
    private UserNameValidator mockUserNameValidator;
    @Mock
    private PhoneValidator mockPhoneValidator;
    @Mock
    private ConfirmationTokenRepository mockConfirmationTokenRepository;
    @Mock
    private EmailSender mockEmailSender;
    @Mock
    private PasswordEncoder mockPasswordEncoder;
    @Mock
    private RoleRepository mockRoleRepository;

    @InjectMocks
    private RegistrationServiceImpl registrationServiceImplUnderTest;

    @Test
    public void testRegister() {
        // Setup
        final SignupRequest request = new SignupRequest("username", "fullname", "email", "password", "fieldName",
                new HashSet<>(Arrays.asList(
                        new Role(0L, "name", new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime(),
                                new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime(), false))));
        final MessageResponse expectedResult = new MessageResponse(0, "Success: Token send successfully!");

        // Configure AccountRepository.findByEmail(...).
        final Account account1 = new Account();
        account1.setId(0L);
        account1.setFullname("fullname");
        account1.setUsername("username");
        account1.setPassword("password");
        account1.setEmail("email");
        account1.setPhone("fieldName");
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
        when(mockAccountRepository.findByEmail("email@gmail.com")).thenReturn(account);

        // Configure AccountRepository.findByUsername(...).
        final Account account3 = new Account();
        account3.setId(0L);
        account3.setFullname("fullname");
        account3.setUsername("username");
        account3.setPassword("password");
        account3.setEmail("email");
        account3.setPhone("fieldName");
        account3.setCreated_at(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        account3.setUpdated_at(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        account3.set_deleted(false);
        account3.setEnable(false);
        final Role role1 = new Role();
        role1.setId(0L);
        role1.setName("name");
        role1.setCreated_at(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        role1.setUpdated_at(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        account3.setRoles(new HashSet<>(Arrays.asList(role1)));
        final Optional<Account> account2 = Optional.of(account3);
        when(mockAccountRepository.findByUsername("username")).thenReturn(account2);

        when(mockEmailValidator.test("email")).thenReturn(false);
        when(mockUserNameValidator.test("username")).thenReturn(false);
        when(mockPhoneValidator.test("fieldName")).thenReturn(false);
        when(mockPasswordEncoder.encode("password")).thenReturn("password");

        // Configure RoleRepository.findByName(...).
        final Optional<Role> role2 = Optional.of(
                new Role(0L, "name", new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime(),
                        new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime(), false));
        when(mockRoleRepository.findByName("ROLE_USER")).thenReturn(role2);

        // Configure AccountRepository.save(...).
        final Account account4 = new Account();
        account4.setId(0L);
        account4.setFullname("fullname");
        account4.setUsername("username");
        account4.setPassword("password");
        account4.setEmail("email");
        account4.setPhone("fieldName");
        account4.setCreated_at(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        account4.setUpdated_at(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        account4.set_deleted(false);
        account4.setEnable(false);
        final Role role3 = new Role();
        role3.setId(0L);
        role3.setName("name");
        role3.setCreated_at(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        role3.setUpdated_at(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        account4.setRoles(new HashSet<>(Arrays.asList(role3)));
        when(mockAccountRepository.save(any(Account.class))).thenReturn(account4);

        // Configure ConfirmationTokenRepository.save(...).
        final Account account5 = new Account();
        account5.setId(0L);
        account5.setFullname("fullname");
        account5.setUsername("username");
        account5.setPassword("password");
        account5.setEmail("email");
        account5.setPhone("fieldName");
        account5.setCreated_at(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        account5.setUpdated_at(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        account5.set_deleted(false);
        account5.setEnable(false);
        final Role role4 = new Role();
        role4.setId(0L);
        role4.setName("name");
        role4.setCreated_at(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        role4.setUpdated_at(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        account5.setRoles(new HashSet<>(Arrays.asList(role4)));
        final ConfirmationToken confirmationToken = new ConfirmationToken("token",
                LocalDateTime.of(2020, 1, 1, 0, 0, 0), account5);
        when(mockConfirmationTokenRepository.save(any(ConfirmationToken.class))).thenReturn(confirmationToken);

        // Run the test
        final MessageResponse result = registrationServiceImplUnderTest.register(request);

        // Verify the results
        assertEquals(expectedResult, result);
        verify(mockAccountRepository).save(any(Account.class));
        verify(mockConfirmationTokenRepository).save(any(ConfirmationToken.class));
//        verify(mockEmailSender).sendEmail("email@gmail.com", "email@gmail.com");
    }

    @Test
    public void testRegister_AccountRepositoryFindByEmailReturnsAbsent() {
        // Setup
        final SignupRequest request = new SignupRequest("username", "fullname", "email@gmail.com", "password", "fieldName",
                new HashSet<>(Arrays.asList(
                        new Role(0L, "name", new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime(),
                                new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime(), false))));
        final MessageResponse expectedResult = new MessageResponse(200,"Success: Token send successfully!");
        when(mockAccountRepository.findByEmail("email@gmail.com")).thenReturn(Optional.empty());

        // Configure AccountRepository.findByUsername(...).
        final Account account1 = new Account();
        account1.setId(0L);
        account1.setFullname("fullname");
        account1.setUsername("username");
        account1.setPassword("password");
        account1.setEmail("email@gmail.com");
        account1.setPhone("fieldName");
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
        when(mockAccountRepository.findByUsername("username")).thenReturn(Optional.empty());

        when(mockEmailValidator.test("email@gmail.com")).thenReturn(true);
        when(mockUserNameValidator.test("username")).thenReturn(true);
        when(mockPhoneValidator.test("fieldName")).thenReturn(true);
        when(mockPasswordEncoder.encode("password")).thenReturn("password");

        // Configure RoleRepository.findByName(...).
        final Optional<Role> role1 = Optional.of(
                new Role(0L, "name", new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime(),
                        new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime(), false));
        when(mockRoleRepository.findByName("ROLE_USER")).thenReturn(role1);

        // Configure AccountRepository.save(...).
        final Account account2 = new Account();
        account2.setId(0L);
        account2.setFullname("fullname");
        account2.setUsername("username");
        account2.setPassword("password");
        account2.setEmail("email@gmail.com");
        account2.setPhone("fieldName");
        account2.setCreated_at(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        account2.setUpdated_at(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        account2.set_deleted(false);
        account2.setEnable(false);
        final Role role2 = new Role();
        role2.setId(0L);
        role2.setName("name");
        role2.setCreated_at(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        role2.setUpdated_at(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        account2.setRoles(new HashSet<>(Arrays.asList(role2)));
        when(mockAccountRepository.save(any(Account.class))).thenReturn(account2);

        // Configure ConfirmationTokenRepository.save(...).
        final Account account3 = new Account();
        account3.setId(0L);
        account3.setFullname("fullname");
        account3.setUsername("username");
        account3.setPassword("password");
        account3.setEmail("email");
        account3.setPhone("fieldName");
        account3.setCreated_at(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        account3.setUpdated_at(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        account3.set_deleted(false);
        account3.setEnable(false);
        final Role role3 = new Role();
        role3.setId(0L);
        role3.setName("name");
        role3.setCreated_at(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        role3.setUpdated_at(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        account3.setRoles(new HashSet<>(Arrays.asList(role3)));
        final ConfirmationToken confirmationToken = new ConfirmationToken("token",
                LocalDateTime.of(2020, 1, 1, 0, 0, 0), account3);
        when(mockConfirmationTokenRepository.save(any(ConfirmationToken.class))).thenReturn(confirmationToken);

        // Run the test
        final MessageResponse result = registrationServiceImplUnderTest.register(request);

        // Verify the results
        assertEquals(expectedResult, result);
        verify(mockAccountRepository).save(any(Account.class));
        verify(mockConfirmationTokenRepository).save(any(ConfirmationToken.class));
//        verify(mockEmailSender).sendEmail("email", "email");
    }

    @Test
    public void testRegister_AccountRepositoryFindByUsernameReturnsAbsent() {
        // Setup
        final SignupRequest request = new SignupRequest("username", "fullname", "email", "password", "fieldName",
                new HashSet<>(Arrays.asList(
                        new Role(0L, "name", new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime(),
                                new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime(), false))));
        final MessageResponse expectedResult = new MessageResponse(200, "Success: Token send successfully!");

        // Configure AccountRepository.findByEmail(...).
        final Account account1 = new Account();
        account1.setId(0L);
        account1.setFullname("fullname");
        account1.setUsername("username");
        account1.setPassword("password");
        account1.setEmail("email");
        account1.setPhone("fieldName");
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
        when(mockAccountRepository.findByEmail("email")).thenReturn(Optional.empty());

        when(mockAccountRepository.findByUsername("username")).thenReturn(Optional.empty());
        when(mockEmailValidator.test("email")).thenReturn(true);
        when(mockUserNameValidator.test("username")).thenReturn(true);
        when(mockPhoneValidator.test("fieldName")).thenReturn(true);
        when(mockPasswordEncoder.encode("password")).thenReturn("password");

        // Configure RoleRepository.findByName(...).
        final Optional<Role> role1 = Optional.of(
                new Role(0L, "name", new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime(),
                        new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime(), false));
        when(mockRoleRepository.findByName("ROLE_USER")).thenReturn(role1);

        // Configure AccountRepository.save(...).
        final Account account2 = new Account();
        account2.setId(0L);
        account2.setFullname("fullname");
        account2.setUsername("username");
        account2.setPassword("password");
        account2.setEmail("email");
        account2.setPhone("fieldName");
        account2.setCreated_at(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        account2.setUpdated_at(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        account2.set_deleted(false);
        account2.setEnable(false);
        final Role role2 = new Role();
        role2.setId(0L);
        role2.setName("name");
        role2.setCreated_at(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        role2.setUpdated_at(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        account2.setRoles(new HashSet<>(Arrays.asList(role2)));
        when(mockAccountRepository.save(any(Account.class))).thenReturn(account2);

        // Configure ConfirmationTokenRepository.save(...).
        final Account account3 = new Account();
        account3.setId(0L);
        account3.setFullname("fullname");
        account3.setUsername("username");
        account3.setPassword("password");
        account3.setEmail("email");
        account3.setPhone("fieldName");
        account3.setCreated_at(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        account3.setUpdated_at(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        account3.set_deleted(false);
        account3.setEnable(false);
        final Role role3 = new Role();
        role3.setId(0L);
        role3.setName("name");
        role3.setCreated_at(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        role3.setUpdated_at(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        account3.setRoles(new HashSet<>(Arrays.asList(role3)));
        final ConfirmationToken confirmationToken = new ConfirmationToken("token",
                LocalDateTime.of(2020, 1, 1, 0, 0, 0), account3);
        when(mockConfirmationTokenRepository.save(any(ConfirmationToken.class))).thenReturn(confirmationToken);

        // Run the test
        final MessageResponse result = registrationServiceImplUnderTest.register(request);

        // Verify the results
        assertEquals(expectedResult, result);
        verify(mockAccountRepository).save(any(Account.class));
        verify(mockConfirmationTokenRepository).save(any(ConfirmationToken.class));
//        verify(mockEmailSender).sendEmail("email", "");
    }

    @Test
    public void testRegister_RoleRepositoryReturnsAbsent() {
        // Setup
        final SignupRequest request = new SignupRequest("username", "fullname", "email@gmail.com", "password", "0372612247",
                new HashSet<>(Arrays.asList(
                        new Role(0L, "name", new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime(),
                                new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime(), false))));
        final MessageResponse expectedResult = new MessageResponse(0, "Fail: username or email already use");

        // Configure AccountRepository.findByEmail(...).
        final Account account1 = new Account();
        account1.setId(0L);
        account1.setFullname("fullname");
        account1.setUsername("username");
        account1.setPassword("password");
        account1.setEmail("email@gmail.com");
        account1.setPhone("0372612247");
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
        when(mockAccountRepository.findByEmail("email@gmail.com")).thenReturn(Optional.empty());

        // Configure AccountRepository.findByUsername(...).
        final Account account3 = new Account();
        account3.setId(0L);
        account3.setFullname("fullname");
        account3.setUsername("username");
        account3.setPassword("password");
        account3.setEmail("email@gmail.com");
        account3.setPhone("0372612247");
        account3.setCreated_at(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        account3.setUpdated_at(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        account3.set_deleted(false);
        account3.setEnable(false);
        final Role role1 = new Role();
        role1.setId(0L);
        role1.setName("name");
        role1.setCreated_at(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        role1.setUpdated_at(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        account3.setRoles(new HashSet<>(Arrays.asList(role1)));
        final Optional<Account> account2 = Optional.of(account3);
        when(mockAccountRepository.findByUsername("username")).thenReturn(Optional.empty());

        when(mockEmailValidator.test("email@gmail.com")).thenReturn(true);
        when(mockUserNameValidator.test("username")).thenReturn(true);
        when(mockPhoneValidator.test("0372612247")).thenReturn(true);
        when(mockPasswordEncoder.encode("password")).thenReturn("password");
        when(mockRoleRepository.findByName("ROLE_USER")).thenReturn(Optional.empty());

        // Configure AccountRepository.save(...).
        final Account account4 = new Account();
        account4.setId(0L);
        account4.setFullname("fullname");
        account4.setUsername("username");
        account4.setPassword("password");
        account4.setEmail("email@gmail.com");
        account4.setPhone("0372612247");
        account4.setCreated_at(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        account4.setUpdated_at(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        account4.set_deleted(false);
        account4.setEnable(false);
        final Role role2 = new Role();
        role2.setId(0L);
        role2.setName("name");
        role2.setCreated_at(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        role2.setUpdated_at(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        account4.setRoles(new HashSet<>(Arrays.asList(role2)));
        when(mockAccountRepository.save(any(Account.class))).thenReturn(account4);

        // Configure ConfirmationTokenRepository.save(...).
        final Account account5 = new Account();
        account5.setId(0L);
        account5.setFullname("fullname");
        account5.setUsername("username");
        account5.setPassword("password");
        account5.setEmail("email@gmail.com");
        account5.setPhone("0372612247");
        account5.setCreated_at(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        account5.setUpdated_at(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        account5.set_deleted(false);
        account5.setEnable(false);
        final Role role3 = new Role();
        role3.setId(0L);
        role3.setName("name");
        role3.setCreated_at(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        role3.setUpdated_at(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        account5.setRoles(new HashSet<>(Arrays.asList(role3)));
        final ConfirmationToken confirmationToken = new ConfirmationToken("token",
                LocalDateTime.of(2020, 1, 1, 0, 0, 0), account5);
        when(mockConfirmationTokenRepository.save(any(ConfirmationToken.class))).thenReturn(confirmationToken);

        // Run the test
        final MessageResponse result = registrationServiceImplUnderTest.register(request);

        // Verify the results
        assertEquals(expectedResult, result);
        verify(mockAccountRepository).save(any(Account.class));
        verify(mockConfirmationTokenRepository).save(any(ConfirmationToken.class));
//        verify(mockEmailSender).sendEmail("email@gmail.com", "email@gmail.com");
    }


    @Test
    public void testConfirmToken() {
        // Setup
        // Configure ConfirmationTokenRepository.findByToken(...).
        final Account account = new Account();
        account.setId(0L);
        account.setFullname("fullname");
        account.setUsername("username");
        account.setPassword("password");
        account.setEmail("email@gmail.com");
        account.setPhone("fieldName");
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
        final Optional<ConfirmationToken> confirmationToken = Optional.of(
                new ConfirmationToken("token", LocalDateTime.of(2023, 1, 1, 0, 0, 0), account));
        when(mockConfirmationTokenRepository.findByToken("token")).thenReturn(confirmationToken);

        // Run the test
        final String result = registrationServiceImplUnderTest.confirmToken("token");

        // Verify the results
        assertEquals("Your email is confirmed. Thank you for using our service!", result);
        verify(mockConfirmationTokenRepository).updateConfirmedAt("token", LocalDateTime.of(2023, 1, 1, 0, 0, 0));
        verify(mockAccountRepository).enableAccount("email@gmail.com");
    }

    @Test
    public void testConfirmToken_ConfirmationTokenRepositoryFindByTokenReturnsAbsent() {
        // Setup
        when(mockConfirmationTokenRepository.findByToken("token")).thenReturn(Optional.empty());

        // Run the test
        assertThrows(ResourceNotFoundException.class, () -> registrationServiceImplUnderTest.confirmToken("token"));
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
        account.setPhone("fieldName");
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

        // Configure ConfirmationTokenRepository.save(...).
        final Account account1 = new Account();
        account1.setId(0L);
        account1.setFullname("fullname");
        account1.setUsername("username");
        account1.setPassword("password");
        account1.setEmail("email");
        account1.setPhone("fieldName");
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
        final ConfirmationToken confirmationToken = new ConfirmationToken("token",
                LocalDateTime.of(2020, 1, 1, 0, 0, 0), account1);
        when(mockConfirmationTokenRepository.save(any(ConfirmationToken.class))).thenReturn(confirmationToken);

        // Run the test
        registrationServiceImplUnderTest.saveConfirmationToken(account, "token");

        // Verify the results
        verify(mockConfirmationTokenRepository).save(any(ConfirmationToken.class));
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
                "                                                Hello name,</h1>\n" +
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
                "                                                                            href=\"link\"\n" +
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
                "                    </table>", registrationServiceImplUnderTest.buildEmail("name", "link"));
    }

}
