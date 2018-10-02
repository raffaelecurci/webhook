package webhook.conf;

public class Repo4Jenkins{
	private String url;
	private String language;
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
	public String toString() {
		return "{\"url\":\""+url+"\", \"language\":\""+language+"\"}";
	}
}