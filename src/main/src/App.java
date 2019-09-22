import com.mongodb.ClientSessionOptions;
import com.mongodb.MongoClientOptions;
import com.mongodb.client.*;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Projections;
import org.bson.Document; // bson to biblioteka MongoDB
import org.bson.codecs.DocumentCodec;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.codecs.pojo.PojoCodecProvider;
import org.bson.conversions.Bson;

import javax.print.Doc;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.mongodb.client.model.Updates.*;
import static org.bson.codecs.configuration.CodecRegistries.fromProviders;
import static org.bson.codecs.configuration.CodecRegistries.fromRegistries;

public class App {

    public static void main(String[] args) {

       //  MongoClient mongoClient = new MongoClient("localhost", 27017); //  27017 to domyślny port dla każdego serwera MongoDB

       //  MongoClient mongoClient = new MongoClient("localhost", 27017);

        CodecRegistry pojoCodecRegistry = fromRegistries(MongoClient.getDefaultCodecRegistry(),
                fromProviders(PojoCodecProvider.builder().automatic(true).build()));
        MongoClient mongoClient = new MongoClient("localhost", MongoClientOptions.builder().codecRegistry(pojoCodecRegistry).build());

        MongoDatabase mongoDatabase = mongoClient.getDatabase("Toys");
        MongoCollection mongoCollection = mongoDatabase.getCollection("Doll");

        // Save information to MongoDB
        save(mongoCollection);
        read(mongoCollection);
        readByParam(mongoCollection, "Mark","BMW");
        delete(mongoCollection,"Mark","BMW");
        update(mongoCollection);

    }

    private static void update(MongoCollection mongoCollection){

       /* Document document = new Document();
        document.put("Mark","Audi");
        Document documentFound = (Document) mongoCollection.find(document).first();

        Document documentUpdated = new Document();
        documentUpdated.put("Model", "A2");
        documentUpdated.put("Color","Black"); */

        Bson eq = Filters.eq("Mark","Audi");
        Bson newDocument = combine(set("Model","A2"), set("Colour","Black"));
        mongoCollection.updateOne(eq,newDocument);
    }

    private static void delete(MongoCollection mongoCollection, String param, Object value) {
        Document document = new Document();
        document.put(param,value);
        mongoCollection.deleteOne(document);
    }

    private static void readByParam(MongoCollection mongoCollection, String param, Object value){
        Document document = new Document();
        document.put(param,value);
        MongoCursor iterator = mongoCollection.find(document).iterator();
        while (iterator.hasNext())
        {
            Document next = (Document) iterator.next();
            System.out.println(next.toJson());
        }

        //Document first = (Document) mongoCollection.find(document).first();
        System.out.println(first.toJson());
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
