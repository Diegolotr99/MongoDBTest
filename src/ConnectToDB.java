import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
//import com.mongodb.diagnostics.logging.Logger;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Enumeration;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;

import javax.management.Query;

import org.bson.Document;
import com.mongodb.BasicDBObject;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;

public class ConnectToDB {
   private static Logger log = Logger.getLogger(ConnectToDB.class.getName());
   static {
	      System.setProperty("java.util.logging.SimpleFormatter.format",
	              "[%1$tF %1$tT %1$tL] [%4$-7s] %5$s %n");
	  }
   public static void setLevel(Level targetLevel) {
	      Logger root = Logger.getLogger("");
	      root.setLevel(targetLevel);
	      for (Handler handler : root.getHandlers()) {
	          handler.setLevel(targetLevel);
	      }
	      System.out.println("level set: " + targetLevel.getName());
	  }

   public static Set<Level> getAllLevels() throws IllegalAccessException {
       Class<Level> levelClass = Level.class;

       Set<Level> allLevels = new TreeSet<>(
               Comparator.comparingInt(Level::intValue));

       for (Field field : levelClass.getDeclaredFields()) {
           if (field.getType() == Level.class) {
               allLevels.add((Level) field.get(null));
           }
       }
       return allLevels;
   }
   public static void main( String args[] ) throws Exception {  
	   	  setLevel(Level.WARNING);
	      Set<Level> levels = getAllLevels();
	      int i = 1;
	      for (Level level : levels) {
	          log.log(level, level.getName() + " - " + (i++));
	      }
	  
      // Creating a Mongo client 
      //MongoClient mongo = new MongoClient( "localhost" , 27017 );  
	  //Instead of local, just use the connection string that Mongo provides for your instance.
      MongoClientURI uri = new MongoClientURI("mongodb+srv://dbuser:14490Mongo*@cluster0-wxzgx.mongodb.net/test?retryWrites=true");
      
      MongoClient mongoClient = new MongoClient(uri); //Instantiate the Mongo Client.
      
      List<Integer> books = Arrays.asList(27464, 23423423,45545);
      //Create a mongo document object and append information, including an array of number(books above this line)...
      Document person = new Document("_id", "dg")
                                  .append("name", "Diego Gutierrez")
                                  .append("address", new BasicDBObject("street", "123 One Stree")
                                                               .append("city", "Cambridge")
                                                               .append("state", "MA")
                                                               	.append("zip", 12342))
                                  .append("books", books);
      
      
      MongoDatabase database = mongoClient.getDatabase("test"); //Get database/ or create one if not existent.
     
      MongoCollection<Document> collection = database.getCollection("people"); //Get the Collection Person
          
      collection.insertOne(person); //Insert the document object to that collection "people"
      
      System.out.println("======================================"); 
      System.out.println("Documents found: " + collection.count());
      System.out.println("======================================"); 
      for(Document d:collection.find()) { //Loop through all the documents found in the collection.
    	  
    	  System.out.println("Id: " + d.get("_id")); //Get the field named "_id" of the document
    	  System.out.println("Name:" + d.get("name")); //Get the field named "name" of the document
    	  System.out.println("======================================"); //Get the field named "name" of the document
    	  
      }
     
      mongoClient.close(); // Close mongo connection just for this simple example. Since its not being used anymore.
   } 

}
