package controller;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoIterable;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.Iterator;

@SpringBootApplication
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
        /*MongoClient mongoClient = new MongoClient("localhost", 27017);

        MongoIterable li = mongoClient.listDatabaseNames();
        Iterator iter = li.iterator();
        while(iter.hasNext()){
            System.out.println(iter.next());
        }*/
    }

    @Bean
    public MongoClient getMongoClient(){
        return new MongoClient("localhost", 27017);
    }
}
