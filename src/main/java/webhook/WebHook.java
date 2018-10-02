package webhook;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.Optional;

import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.api.errors.InvalidRemoteException;
import org.eclipse.jgit.api.errors.TransportException;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.bazaarvoice.jolt.Chainr;
import com.bazaarvoice.jolt.JsonUtils;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import net.sf.json.JSONObject;
import webhook.conf.JenkinsInstance;
import webhook.conf.JsonConf;
import webhook.conf.Repo4Jenkins;
import webhook.util.Repo;

@RestController
@EnableAutoConfiguration
public class WebHook {
	static ch.qos.logback.classic.Logger root = (ch.qos.logback.classic.Logger) org.slf4j.LoggerFactory.getLogger(ch.qos.logback.classic.Logger.ROOT_LOGGER_NAME);
	
	
	
	// serves the webhook request from the repository
	@RequestMapping(path = "/webhook", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.TEXT_MARKDOWN_VALUE)
	private void handleWebHook(@RequestBody String request) {
		// System.out.println(getRequest());
		try {
			processRequest(request);
		} catch (IOException | GitAPIException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	private void processRequest(String request) throws JsonParseException, JsonMappingException, IOException, InvalidRemoteException, TransportException, GitAPIException {
		ClassLoader classLoader = getClass().getClassLoader();

		
		//extract information from the post coming from the repository [start]
		List chainrSpecJSON = JsonUtils
				.filepathToList(new File(classLoader.getResource("spec.json").getFile()).getPath());
		Chainr chainr = Chainr.fromSpec(chainrSpecJSON);

		Object inputJSON = JsonUtils.jsonToObject(request);

		Object transformedOutput = chainr.transform(inputJSON);
		JSONObject jsonInput = JSONObject.fromObject(transformedOutput);
		//extract information from the post coming from the repository [stop]
		
		root.info("Received message: '"+jsonInput.toString()+"'");
		
		
		JsonConf jsonConf = null;
		ObjectMapper mapper = new ObjectMapper();
		
		jsonConf = mapper.readValue(new File(classLoader.getResource("repo.json").getFile()),JsonConf.class);
		Optional<JenkinsInstance> ist = jsonConf.getJenkins().stream().filter(j->j.getLanguage().equals((String)jsonInput.get("language"))).findFirst();
		
		JenkinsInstance jenkins=null;
		if(ist.isPresent()) {
			jenkins=ist.get();
			root.info(ist.toString());
		}
		
		Repo4Jenkins repo4jenkins=null;
		Optional<Repo4Jenkins> rp = jsonConf.getRepo().stream().filter(j->j.getLanguage().equals((String)jsonInput.get("language"))).findFirst();
		if(rp.isPresent()) {
			repo4jenkins=rp.get();
			root.info(rp.toString());
		}
		
		
		String 
		userRepo=jsonConf.getUserRepo(),
		passwdRepo=jsonConf.getPasswdRepo(),
		userJenkins=jsonConf.getUserJenkins(),
		passwdJenkins=jsonConf.getPasswdJenkins(),
		localPath=jsonConf.getLocalPath(),
		storagePath=jsonConf.getStoragePath();
		
		Repo repoHandler=new Repo();
		repoHandler.setLogger(root);
		//repository
		String localRepo=localPath+jsonInput.get("project").toString();
		repoHandler.cloneAndPush(jsonInput.get("repository").toString(), localRepo, repo4jenkins.getUrl(), userRepo, passwdRepo);
		
		Files.move(new File(localRepo).toPath(), new File(storagePath+jsonInput.get("project").toString()).toPath(), StandardCopyOption.REPLACE_EXISTING);
		
	}

	public static void main(String[] args) throws Exception {
		root.setLevel(ch.qos.logback.classic.Level.INFO);
		SpringApplication.run(WebHook.class, args);
	}

}
