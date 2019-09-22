import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document; // bson to biblioteka MongoDB

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class App {

    public static void main(String[] args) {

       //  MongoClient mongoClient = new MongoClient("localhost", 27017); //  27017 to domyślny port dla każdego serwera MongoDB

        MongoClient mongoClient = new MongoClient("localhost", 27017);
        MongoDatabase mongoDatabase = mongoClient.getDatabase("Toys");
        MongoCollection mongoCollection = mongoDatabase.getCollection("Doll");

        // Save information to MongoDB
        save(mongoCollection);
        read(mongoCollection);

    }

    private static void read(MongoCollection mongoCollection){
        Document first = (Document) mongoCollection.find().first();
        System.out.println(first.toJson());
    }

    private static void save(MongoCollection mongoCollection) {
        Document document1 = new Document();
        document1.put("Name", "Eva");
        document1.put("Age", 18);

        Document document2 = new Document();
        document2.put("Name", "Jessica");
        document2.put("Age", 20);

        List<Document> dollList = new ArrayList<>();
        dollList.add(document1);
        dollList.add(document2);
        mongoCollection.insertMany(dollList);
    }

}
