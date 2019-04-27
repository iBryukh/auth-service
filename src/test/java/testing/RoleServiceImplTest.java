package testing;

import com.smarthouse.authentication.repository.RoleRepository;
import com.smarthouse.authentication.service.RoleServiceImpl;
import com.smarthouse.commonutil.entities.Role;
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
public class RoleServiceImplTest extends TestCase {
    @Mock
    private static RoleRepository roleRepository;

    @InjectMocks
    private static RoleServiceImpl service;

    @Test
    public void testFindRoleByID(){
        service = new RoleServiceImpl(roleRepository);
        Role role = new Role();
        role.setName("Ake");
        role.setAccessLevel(3);
        role.setId((long)1);
        Long id = role.getId();
        Mockito.when(roleRepository.findOne(id)).thenReturn(role);
        Optional<Role> optionalUser = service.getById(id);
        Role role1 = optionalUser.isPresent() ? optionalUser.get() : new Role();


        Assert.assertEquals(role, role1);

    }

    @Test
    public void testFindAll(){
        service = new RoleServiceImpl(roleRepository);
        Role role = new Role();
        role.setName("Ake");
        role.setAccessLevel(3);
        role.setId((long)1);
        roleRepository.save(role);
        List<Role> roles = new ArrayList<Role>();
        roles.add(role);
        Mockito.when(roleRepository.findAll()).thenReturn(roles);
        List<Role> op1 =  service.getAll();

        Assert.assertEquals(1, op1.size());

    }

    @Test
    public void testDeleteById(){
        service = new RoleServiceImpl(roleRepository);
        Role role = new Role();
        role.setName("Ake");
        role.setAccessLevel(3);
        role.setId((long)1);
        roleRepository.save(role);
        Long id = role.getId();
        service.deleteById(id);
        Mockito.verify(roleRepository, times(1)).delete(id);

    }

    @Test
    public void testDeleteByRole(){
        service = new RoleServiceImpl(roleRepository);
        Role role = new Role();
        role.setId((long)4);
        role.setName("Ake");
        role.setAccessLevel(3);
        service.delete(role);
        Mockito.verify(roleRepository, times(1)).delete(role);

    }

    @Test
    public void testBySave(){
        service = new RoleServiceImpl(roleRepository);
        Role role = new Role();
        role.setName("Ake");
        role.setAccessLevel(3);
        Mockito.when(roleRepository.saveAndFlush(role)).thenReturn(role);
        Role op = service.save(role);


        Assert.assertEquals(op, role);

    }

}