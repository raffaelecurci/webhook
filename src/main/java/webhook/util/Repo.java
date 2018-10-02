package webhook.util;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;

import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.api.errors.InvalidRemoteException;
import org.eclipse.jgit.api.errors.TransportException;
import org.eclipse.jgit.lib.Constants;
import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.transport.UsernamePasswordCredentialsProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;



public class Repo {
//	ch.qos.logback.classic.Logger root = (ch.qos.logback.classic.Logger) org.slf4j.LoggerFactory.getLogger(ch.qos.logback.classic.Logger.ROOT_LOGGER_NAME);
	private Logger root=null;
	
	public void setLogger(Logger root) {
		this.root=root;
	}
	private Repo cloneRepo(String localRepo,String repoUrl) {
		try {
			root.info("Cloning " + repoUrl + " into " + localRepo);
			Git.cloneRepository().setURI(repoUrl).setDirectory(Paths.get(localRepo).toFile()).call();
			root.info("Completed Cloning");
		} catch (GitAPIException e) {
			root.info("Exception occurred while cloning repo");
			e.printStackTrace();
		}
		return this;
	}
	
	//return last commit id
	private String rebaseRemoteBuffer(String localRepo,String remoteRepo,String user,String passwd) throws IOException, InvalidRemoteException, TransportException, GitAPIException {
		File localDirectory=new File(localRepo);
		Git git = Git.open( localDirectory );
		  git.push()
		    .setCredentialsProvider( new UsernamePasswordCredentialsProvider( user,  passwd ) )
		    .setRemote( remoteRepo )
		    .setForce( true )
		    .setPushAll()
		    .setPushTags()
		    .call();
		  Repository repository = git.getRepository();
		  ObjectId lastCommitId = repository.resolve(Constants.HEAD);
		  String last=lastCommitId.toString();
		  root.info(last);
		  root.info(last.substring(12, last.length()-1));
		  return last.substring(12, last.length()-1);
	}

	void test() throws InvalidRemoteException, TransportException, IOException, GitAPIException {
//		root.setLevel(ch.qos.logback.classic.Level.DEBUG);
//		String repoUrl = "https://github.com/fastfoodcoding/SpringBootMongoDbCRUD.git";
		String repoUrl = "https://github.com/raffaelecurci/MSBuild.git";
		String localRepo="/home/fefe/git/jgitexample";
		String remoteRepo="https://github.com/raffaelecurci/Buffer";
		String user="raffaelecurci";
		String passwd="Lillotauro1";

		
		cloneRepo(localRepo, repoUrl);
		rebaseRemoteBuffer(localRepo, remoteRepo, user, passwd);
	}
	
	public void cloneAndPush(String srcRemoteRepo,String localRepo,String dstRemoteRepo, String dstRemoteUser,String dstRemotePasswd) throws InvalidRemoteException, TransportException, IOException, GitAPIException {
		cloneRepo(localRepo, srcRemoteRepo);
		rebaseRemoteBuffer(localRepo, dstRemoteRepo, dstRemoteUser, dstRemotePasswd);
	}
	
	public static void main1(String[] args) throws InvalidRemoteException, TransportException, IOException, GitAPIException {
		// TODO Auto-generated method stub		
		new Repo().test();
	}

}
