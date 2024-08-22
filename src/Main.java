import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.MongoCollection;
import org.bson.Document;


public class Main {
    public static void main(String[] args) {
        String uri = "mongodb://localhost:27017";
        MongoClient mongoClient = MongoClients.create(uri);

        MongoDatabase database = mongoClient.getDatabase("Library_Application");
        MongoCollection<Document> booksCollection = database.getCollection("books");
        MongoCollection<Document> usersCollection = database.getCollection("users");
        MongoCollection<Document> reservationsCollection = database.getCollection("reservations");

        new Library(booksCollection, usersCollection, reservationsCollection);
    }
}