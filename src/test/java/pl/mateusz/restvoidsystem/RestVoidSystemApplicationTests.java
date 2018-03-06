package pl.mateusz.restvoidsystem;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import pl.mateusz.restvoidsystem.model.entity.Project;
import pl.mateusz.restvoidsystem.model.entity.Voter;
import pl.mateusz.restvoidsystem.model.repository.ProjectRepository;
import pl.mateusz.restvoidsystem.model.repository.VoterRepository;

import java.security.SecureRandom;

import static org.junit.Assert.assertNotNull;

@RunWith(SpringRunner.class)
@SpringBootTest
public class RestVoidSystemApplicationTests {

	@Autowired
	private VoterRepository voterRepository;

	@Autowired
	private ProjectRepository projectRepository;

	/*@Test
	public void contextLoads1() {
		assertNotNull(voterRepository);
	}

	@Test
	public void contextLoads2(){
		assertNotNull(projectRepository);
	}*/

	private SecureRandom secureRandom = new SecureRandom();
	private static Integer random;

	@Test
	@Ignore
	public void contextLoads1() {
		for(int i = 1; i<=10; i++){
			random = secureRandom.nextInt(100);
			voterRepository.save(new Voter("voter"+random));
		}
	}

	@Test
	@Ignore
	public void contextLoads2(){
		for(int i = 1; i<=10; i++){
			random = secureRandom.nextInt(100);
			projectRepository.save(new Project("project"+random,"Descryption"+random,true));
		}
	}

}
