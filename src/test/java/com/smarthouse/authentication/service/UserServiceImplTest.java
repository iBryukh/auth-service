package com.smarthouse.authentication.service;

import com.smarthouse.authentication.repository.UserRepository;
import com.smarthouse.commonutil.entities.Role;
import com.smarthouse.commonutil.entities.User;
import junit.framework.TestCase;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RunWith(MockitoJUnitRunner.class)
public class UserServiceImplTest extends TestCase {
    @Mock
    private static UserRepository userRepository;

    @InjectMocks
    private static UserServiceImpl service;

    @Test
    public void testFindRoleByID(){
        service = new UserServiceImpl(userRepository);
        Role role = new Role();
        role.setName("Ake");
        role.setAccessLevel(3);
        role.setId((long)1);
        User user = new User();
        user.setId((long)1);
        user.setName("Ake");
        user.setEmail("133@mail.ua");
        user.setPassword("12345");
        user.setRole(role);
        Long id = user.getId();
        Mockito.when(userRepository.findOne(id)).thenReturn(user);
        Optional<User> optionalUser = service.getById(id);
        User user1 = optionalUser.isPresent() ? optionalUser.get() : new User();


        Assert.assertEquals(user, user1);

    }
    @Test
    public void testFindAll(){
        service = new UserServiceImpl(userRepository);
        Role role = new Role();
        role.setName("Ake");
        role.setAccessLevel(3);
        role.setId((long)1);
        User user = new User();
        user.setId((long)1);
        user.setName("Ake");
        user.setEmail("133@mail.ua");
        user.setPassword("12345");
        user.setRole(role);
        Long id = user.getId();
        List<User> users = new ArrayList<User>();
        users.add(user);
        when(userRepository.findAll()).thenReturn(users);
        List<User> op1 =  service.getAll();

        Assert.assertEquals(1, op1.size());

    }
    @Test
    public void testDeleteById(){
        service = new UserServiceImpl(userRepository);
        Role role = new Role();
        role.setName("Ake");
        role.setAccessLevel(3);
        role.setId((long)1);
        User user = new User();
        user.setId((long)1);
        user.setName("Ake");
        user.setEmail("133@mail.ua");
        user.setPassword("12345");
        user.setRole(role);
        Long id = user.getId();
        service.deleteById(id);
        Mockito.verify(userRepository, times(1)).delete(id);

    }

    @Test
    public void testDeleteByRole(){
        service = new UserServiceImpl(userRepository);
        Role role = new Role();
        role.setId((long)4);
        role.setName("Ake");
        role.setAccessLevel(3);
        User user = new User();
        user.setId((long)1);
        user.setName("Ake");
        user.setEmail("133@mail.ua");
        user.setPassword("12345");
        user.setRole(role);
        service.delete(user);
        Mockito.verify(userRepository, times(1)).delete(user);

    }

    @Test
    public void testBySave(){
        service = new UserServiceImpl(userRepository);
        Role role = new Role();
        role.setName("Ake");
        role.setAccessLevel(3);
        User user = new User();
        user.setId((long)1);
        user.setName("Ake");
        user.setEmail("133@mail.ua");
        user.setPassword("12345");
        user.setRole(role);
        Mockito.when(userRepository.saveAndFlush(user)).thenReturn(user);
        User op = service.save(user);


        Assert.assertEquals(op, user);

    }
}
