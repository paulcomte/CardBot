package fr.rqndomhax.cardbot.utils;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.MongoDatabase;

public class MongoDB {
    private MongoDatabase mongoDatabase;
    private final MongoClient mongoClient;

    public MongoDB(String uri) {
        mongoClient = new MongoClient(new MongoClientURI(uri));
    }

    public MongoDB(String user, String password, String host, int port, String database) {
        mongoClient = new MongoClient(new MongoClientURI("mongodb://"+user+":"+password+"@"+host+":"+port+"/"+database));
        mongoDatabase = mongoClient.getDatabase(database);
    }

    public MongoDB(String user, String password, String host, String database){
        mongoClient = new MongoClient(new MongoClientURI("mongodb://"+user+":"+password+"@"+host+":27017/"+database));
        mongoDatabase = mongoClient.getDatabase(database);
    }

    public MongoDB(String host, int port, String database){
        mongoClient = new MongoClient(new MongoClientURI("mongodb://"+host+":"+port+"/"+database));
        mongoDatabase = mongoClient.getDatabase(database);
    }

    public MongoDB(String host, String database){
        mongoClient = new MongoClient(new MongoClientURI("mongodb://"+host+":27017/"+database));
        mongoDatabase = mongoClient.getDatabase(database);
    }

    public void closeConnection(){ mongoClient.close(); }
    public MongoDatabase getMongoDatabase() { return mongoDatabase; }
    public MongoClient getMongoClient() { return mongoClient; }
}
