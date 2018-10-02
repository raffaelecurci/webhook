package webhook.conf;

import java.util.LinkedList;

public class JsonConf {
	private LinkedList<JenkinsInstance> jenkins;
	private LinkedList<Repo4Jenkins> repo;
	private String userRepo;
	private String passwdRepo;
	private String userJenkins;
	private String passwdJenkins;
	private String localPath;
	private String storagePath;
	public LinkedList<JenkinsInstance> getJenkins() {
		return jenkins;
	}
	public void setJenkins(LinkedList<JenkinsInstance> jenkins) {
		this.jenkins = jenkins;
	}
	public LinkedList<Repo4Jenkins> getRepo() {
		return repo;
	}
	public void setRepo(LinkedList<Repo4Jenkins> repo) {
		this.repo = repo;
	}
	public String getUserRepo() {
		return userRepo;
	}
	public void setUserRepo(String userRepo) {
		this.userRepo = userRepo;
	}
	public String getPasswdRepo() {
		return passwdRepo;
	}
	public void setPasswdRepo(String passwdRepo) {
		this.passwdRepo = passwdRepo;
	}
	public String getUserJenkins() {
		return userJenkins;
	}
	public void setUserJenkins(String userJenkins) {
		this.userJenkins = userJenkins;
	}
	public String getPasswdJenkins() {
		return passwdJenkins;
	}
	public void setPasswdJenkins(String passwdJenkins) {
		this.passwdJenkins = passwdJenkins;
	}
	public String getLocalPath() {
		return localPath;
	}
	public void setLocalPath(String localPath) {
		this.localPath = localPath;
	}
	public String getStoragePath() {
		return storagePath;
	}
	public void setStoragePath(String storagePath) {
		this.storagePath = storagePath;
	}
	
}




