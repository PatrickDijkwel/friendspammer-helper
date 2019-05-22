package nl.hu.sie.bep.friendspammer;



import org.bson.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.MongoException;
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

}
