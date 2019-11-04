package controller;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TinyUrlController {
    public final static String BASE_URL = "https://www.tinyurl.com/";
    public final static String ORIGINAL_URL_KEY = "OriginalUrl";
    public final static String TINY_URL_KEY = "TinyUrl";
    public final static String TINY_URL_COLLECTION_NAME = "tiny-url";

    @Autowired
    MongoClient mongoClient;

    @RequestMapping("/encodeUrl")
    public String getTinyUrl(@RequestParam(value = "url") String url) {
        String encodedUrl = UrlEncoderDecoder.encodeUrl(url);
        String tinyUrl = BASE_URL + encodedUrl.substring(encodedUrl.length()-8);

        saveTinyUrlIntoDB(url,tinyUrl);

        return tinyUrl;
    }

    @RequestMapping("/decodeUrl")
    public String getOriginalUrl(@RequestParam(value = "url") String url) {
        String originalUrl = getOriginalUrlFromDB(url);

        return originalUrl;
    }

    private MongoCollection<Document> getMongoDBCollectionForTinyUrl(){
        MongoDatabase database = mongoClient.getDatabase("shubham");
        MongoCollection<Document> collection = database.getCollection(TINY_URL_COLLECTION_NAME);

        return collection;
    }

    private void saveTinyUrlIntoDB(String originalUrl, String tinyUrl){
        MongoCollection<Document> collection = getMongoDBCollectionForTinyUrl();

        Document doc = new Document(ORIGINAL_URL_KEY,originalUrl)
                .append(TINY_URL_KEY,tinyUrl);

        collection.insertOne(doc);
    }

    private String getOriginalUrlFromDB(String tinyUrl){
        MongoCollection<Document> collection = getMongoDBCollectionForTinyUrl();

        Document resultDoc  = collection.find(new Document(TINY_URL_KEY,tinyUrl)).first();

        if(resultDoc != null){
            return resultDoc.getString(ORIGINAL_URL_KEY);
        }
        else{
            return null;
        }
    }
}
