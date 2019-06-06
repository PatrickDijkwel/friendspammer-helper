package nl.hu.sie.bep.friendspammer;



import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.bson.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import com.mongodb.MongoClient;
import com.mongodb.MongoClientOptions;
import com.mongodb.MongoClientURI;
import com.mongodb.MongoCredential;
import com.mongodb.MongoException;
import com.mongodb.ServerAddress;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

public class MongoSaver {
	
	static final Logger logger = LoggerFactory.getLogger(MongoSaver.class);
	
	private MongoSaver() {
		throw new IllegalStateException("Utility class");
	}
	
	public static boolean saveEmail(String to, String from, String subject, String text, Boolean html) {
		String database = "friendspammer";
		
		boolean success = true;
		
		MongoClientURI uri = new MongoClientURI("mongodb+srv://administrator:<password>@cluster0-vaulu.mongodb.net/test?retryWrites=true");
		
		try (MongoClient mongoClient = new MongoClient(uri) ) {
			
			MongoDatabase db = mongoClient.getDatabase( database );
			
			MongoCollection<Document> c = db.getCollection("email");
			
			Document  doc = new Document ("to", to)
			        .append("from", from)
			        .append("subject", subject)
			        .append("text", text)
			        .append("asHtml", html);
			c.insertOne(doc);
		} catch (MongoException mongoException) {
			logger.info("XXXXXXXXXXXXXXXXXX ERROR WHILE SAVING TO MONGO XXXXXXXXXXXXXXXXXXXXXXXXXX");
			mongoException.printStackTrace();
			success = false;
		}
		
		return success;
 		
	} 
	public static List<Email> getAll() {
		String userName = "spammer";
		String passwort = "hamspam";
		String database = "friendspammer";
		
		ArrayList<Email> allEmails = new ArrayList<>();
		MongoCredential credential = MongoCredential.createCredential(userName, database, passwort.toCharArray());

		MongoClient mongoClient = new MongoClient(new ServerAddress("ds227939.mlab.com", 27939), credential, MongoClientOptions.builder().build());
			
			MongoDatabase db = mongoClient.getDatabase( database );
			
			MongoCollection<Document> c = db.getCollection("email");
			Iterator<Document> it = c.find().iterator();
			
			while(it.hasNext()) {
				Email e = new Email();
				Document email = it.next();
				e.to = (String)	email.get("to");
				e.from = (String) email.get("from");
				e.subject = (String) email.get("subject");
				e.text = (String) email.get("text");
				e.asHtml = (boolean) email.get("asHtml");
				allEmails.add(e);
			}
			mongoClient.close();
			return allEmails;
	}
}
