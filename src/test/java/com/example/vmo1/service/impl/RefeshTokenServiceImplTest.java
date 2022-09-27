package com.example.vmo1.service.impl;

import com.example.vmo1.model.entity.Account;
import com.example.vmo1.model.entity.RefreshToken;
import com.example.vmo1.model.entity.Role;
import com.example.vmo1.repository.AccountRepository;
import com.example.vmo1.repository.RefreshTokenRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.time.LocalDateTime;
import java.util.*;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class RefeshTokenServiceImplTest {

    @Mock
    private RefreshTokenRepository mockRefreshTokenRepository;
    @Mock
    private AccountRepository mockAccountRepository;

    @InjectMocks
    private RefeshTokenServiceImpl refeshTokenServiceImplUnderTest;

    @Test
    public void testFindByToken() {
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
        final Optional<RefreshToken> expectedResult = Optional.of(
                new RefreshToken(0L, account, "9a2fc81f-55ec-43cf-8fc0-a5bbb7ce5ce9",
                        LocalDateTime.of(2020, 1, 1, 0, 0, 0)));

        // Configure RefreshTokenRepository.findByToken(...).
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
        final Optional<RefreshToken> refreshToken = Optional.of(
                new RefreshToken(0L, account1, "9a2fc81f-55ec-43cf-8fc0-a5bbb7ce5ce9",
                        LocalDateTime.of(2020, 1, 1, 0, 0, 0)));
        when(mockRefreshTokenRepository.findByToken("token")).thenReturn(refreshToken);

        // Run the test
        final Optional<RefreshToken> result = refeshTokenServiceImplUnderTest.findByToken("token");

        // Verify the results
        assertEquals(expectedResult, result);
    }

    @Test
    public void testFindByToken_RefreshTokenRepositoryReturnsAbsent() {
        // Setup
        when(mockRefreshTokenRepository.findByToken("token")).thenReturn(Optional.empty());

        // Run the test
        final Optional<RefreshToken> result = refeshTokenServiceImplUnderTest.findByToken("token");

        // Verify the results
        assertEquals(Optional.empty(), result);
    }

    @Test
    public void testCreateRefreshToken() {
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
        final RefreshToken expectedResult = new RefreshToken(0L, account, "9a2fc81f-55ec-43cf-8fc0-a5bbb7ce5ce9",
                LocalDateTime.of(2020, 1, 1, 0, 0, 0));

        // Configure AccountRepository.findById(...).
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
        when(mockAccountRepository.findById(0L)).thenReturn(account1);

        // Configure RefreshTokenRepository.save(...).
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
        final RefreshToken refreshToken = new RefreshToken(0L, account3, "9a2fc81f-55ec-43cf-8fc0-a5bbb7ce5ce9",
                LocalDateTime.of(2020, 1, 1, 0, 0, 0));
        when(mockRefreshTokenRepository.save(new RefreshToken(0L, new Account(), "9a2fc81f-55ec-43cf-8fc0-a5bbb7ce5ce9",
                LocalDateTime.of(2020, 1, 1, 0, 0, 0)))).thenReturn(refreshToken);

        // Run the test
        final RefreshToken result = refeshTokenServiceImplUnderTest.createRefreshToken(0L);

        // Verify the results
        assertEquals(expectedResult, result);
        verify(mockRefreshTokenRepository).save(
                new RefreshToken(0L, new Account(), "9a2fc81f-55ec-43cf-8fc0-a5bbb7ce5ce9",
                        LocalDateTime.of(2020, 1, 1, 0, 0, 0)));
    }

    @Test
    public void testCreateRefreshToken_AccountRepositoryReturnsAbsent() {
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
        final RefreshToken expectedResult = new RefreshToken(0L, account, "9a2fc81f-55ec-43cf-8fc0-a5bbb7ce5ce9",
                LocalDateTime.of(2020, 1, 1, 0, 0, 0));
        when(mockAccountRepository.findById(0L)).thenReturn(Optional.empty());

        // Configure RefreshTokenRepository.save(...).
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
        final RefreshToken refreshToken = new RefreshToken(0L, account1, "9a2fc81f-55ec-43cf-8fc0-a5bbb7ce5ce9",
                LocalDateTime.of(2020, 1, 1, 0, 0, 0));
        when(mockRefreshTokenRepository.save(new RefreshToken(0L, new Account(), "9a2fc81f-55ec-43cf-8fc0-a5bbb7ce5ce9",
                LocalDateTime.of(2020, 1, 1, 0, 0, 0)))).thenReturn(refreshToken);

        // Run the test
        final RefreshToken result = refeshTokenServiceImplUnderTest.createRefreshToken(0L);

        // Verify the results
        assertEquals(expectedResult, result);
        verify(mockRefreshTokenRepository).save(
                new RefreshToken(0L, new Account(), "9a2fc81f-55ec-43cf-8fc0-a5bbb7ce5ce9",
                        LocalDateTime.of(2020, 1, 1, 0, 0, 0)));
    }

    @Test
    public void testVerifyExpiration() {
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
        final RefreshToken token = new RefreshToken(0L, account, "9a2fc81f-55ec-43cf-8fc0-a5bbb7ce5ce9",
                LocalDateTime.of(2020, 1, 1, 0, 0, 0));
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
        final RefreshToken expectedResult = new RefreshToken(0L, account1, "9a2fc81f-55ec-43cf-8fc0-a5bbb7ce5ce9",
                LocalDateTime.of(2020, 1, 1, 0, 0, 0));

        // Run the test
        final RefreshToken result = refeshTokenServiceImplUnderTest.verifyExpiration(token);

        // Verify the results
        assertEquals(expectedResult, result);
        verify(mockRefreshTokenRepository).delete(
                new RefreshToken(0L, new Account(), "9a2fc81f-55ec-43cf-8fc0-a5bbb7ce5ce9",
                        LocalDateTime.of(2020, 1, 1, 0, 0, 0)));
    }

    @Test
    public void testDeleteByUserId() {
        // Setup
        // Configure AccountRepository.findById(...).
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
        when(mockAccountRepository.findById(0L)).thenReturn(account);

        when(mockRefreshTokenRepository.deleteByAccount(any(Account.class))).thenReturn(0);

        // Run the test
        final int result = refeshTokenServiceImplUnderTest.deleteByUserId(0L);

        // Verify the results
        assertEquals(0, result);
    }

    @Test
    public void testDeleteByUserId_AccountRepositoryReturnsAbsent() {
        // Setup
        when(mockAccountRepository.findById(0L)).thenReturn(Optional.empty());

        // Run the test
        assertThrows(NoSuchElementException.class, () -> refeshTokenServiceImplUnderTest.deleteByUserId(0L));
    }
}
