package webhook.conf;

public class JenkinsInstance{
	private String url;
	private String language;
	private String job;
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getLanguage() {
		return language;
	}
	public void setLanguage(String language) {
		this.language = language;
	}
	public String getJob() {
		return job;
	}
	public void setJob(String job) {
		this.job = job;
	}
	public String toString() {
		return "{\"url\":\""+url+"\", \"language\":\""+language+"\", \"job\":\""+job+"\"}";
	}
}