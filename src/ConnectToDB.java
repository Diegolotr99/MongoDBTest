import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

import java.util.Arrays;
import java.util.List;

import javax.management.Query;

import org.bson.Document;
import com.mongodb.BasicDBObject;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;

public class ConnectToDB {
   
   public static void main( String args[] ) {  
      
      // Creating a Mongo client 
      //MongoClient mongo = new MongoClient( "localhost" , 27017 );  
	  //Instead of local, just use the connection string that Mongo provides for your instance.
      MongoClientURI uri = new MongoClientURI("mongodb+srv://dbuser:14490Mongo*@cluster0-wxzgx.mongodb.net/test?retryWrites=true");
      
      MongoClient mongoClient = new MongoClient(uri); //Instantiate the Mongo Client.
      
      List<Integer> books = Arrays.asList(27464, 747854);
      //Create a mongo document object and append information, including an array of number(books above this line)...
      Document person = new Document("_id", "jo")
                                  .append("name", "Jo Bloggs")
                                  .append("address", new BasicDBObject("street", "123 Fake St")
                                                               .append("city", "Faketon")
                                                               .append("state", "MA")
                                                               	.append("zip", 12345))
                                  .append("books", books);
      
      
      MongoDatabase database = mongoClient.getDatabase("test"); //Get database/ or create one if not existent.
      
      
      MongoCollection<Document> collection = database.getCollection("people"); //Get the Collection Person
          
      //collection.insertOne(person); //Insert the document object to that collection "people"
     
      System.out.println("Documents found: " + collection.count());
      
      for(Document d:collection.find()) { //Loop through all the documents found in the collection.
    	  
    	  System.out.println("Id: " + d.get("_id")); //Get the field named "_id" of the document
    	  System.out.println("Name:" + d.get("name")); //Get the field named "name" of the document
    	  
      }
     
      mongoClient.close(); // Close mongo connection just for this simple example. Since its not being used anymore.
   } 

}
