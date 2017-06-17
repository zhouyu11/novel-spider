package com.yu.repository;

import com.mongodb.*;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.Morphia;

/**
 * Created by zhouyu on 07/06/2017.
 */

public class MongoClientInstance {

    private static String clientDbUrl = "mongodb://localhost";
    private static MongoClientInstance instance = null;
    private final Datastore client;

    public static Datastore instance() {
        if (instance == null) {
            instance = new MongoClientInstance();
        }
        return instance.client;
    }

    private MongoClientInstance() {
        client = clientClient();
    }

    private Datastore clientClient() {
        MongoClientOptions.Builder clientOptionsBuilder = MongoClientOptions.builder()
                .connectionsPerHost(100)
                .connectTimeout(10 * 1000)
                .cursorFinalizerEnabled(false)
                .socketKeepAlive(true)
                .readConcern(ReadConcern.LOCAL)
                .readPreference(ReadPreference.primaryPreferred())
                .writeConcern(WriteConcern.MAJORITY)
                .codecRegistry(MongoClient.getDefaultCodecRegistry());

        MongoClientURI clientClientURI = new MongoClientURI(clientDbUrl, clientOptionsBuilder);

        MongoClient mongoClient = new MongoClient(clientClientURI);

        Morphia morphia = new Morphia();

        Datastore datastore = morphia.createDatastore(mongoClient, "novels");
        datastore.ensureIndexes();

        return datastore;
    }

}
