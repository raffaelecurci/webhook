package webhook;

import java.nio.charset.StandardCharsets;

import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@EnableAutoConfiguration
public class Test {

	@RequestMapping(path="/webhook", method = RequestMethod.POST,
	          consumes = MediaType.APPLICATION_JSON_VALUE, 
	            produces=MediaType.TEXT_MARKDOWN_VALUE)
	String home(@RequestBody String request) {
		System.out.println(request);
		return request+'\n';
	}

	public static void main(String[] args) throws Exception {
		SpringApplication.run(Test.class, args);
	}

}
