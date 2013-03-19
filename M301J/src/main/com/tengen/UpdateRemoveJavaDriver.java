package com.tengen;

import java.net.UnknownHostException;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;
import com.mongodb.ServerAddress;

public class UpdateRemoveJavaDriver {

	/**
	 * @param args
	 * @throws UnknownHostException
	 */
	public static void main(String[] args) throws UnknownHostException {
		DBCollection coll = createCollection();
		coll.drop();

		List<String> names = Arrays.asList("alice", "bobby", "john", "tim", "blub");
		for (String string : names) {
			coll.insert(new BasicDBObject("_id", string));
		}
		
		//update
		coll.update(new BasicDBObject("_id", "alice"), new BasicDBObject("age", 34));
		coll.update(new BasicDBObject("_id", "alice"), new BasicDBObject("$set", new BasicDBObject("gender", "F")));
		coll.update(new BasicDBObject("_id", "frank"), new BasicDBObject("$set", new BasicDBObject("gender", "M")), true, false);
		coll.update(new BasicDBObject(), new BasicDBObject("$set", new BasicDBObject("title", "Dr")), false, true);
	
		//remove
		coll.remove(new BasicDBObject("_id", "alice"));
		
		printCollection(coll);

	}

	private static void printCollection(DBCollection coll) {
		DBCursor cur = coll.find();
		try {
			while (cur.hasNext()) {
				DBObject obj2 = cur.next();
				System.out.println(obj2);
			}
		} finally {
			cur.close();
		}
	}

	private static DBCollection createCollection() throws UnknownHostException {
		MongoClient client = new MongoClient(new ServerAddress("localhost", 27017));
		DB db = client.getDB("m2");
		DBCollection coll = db.getCollection("aufgabe2");
		return coll;
	}

}
