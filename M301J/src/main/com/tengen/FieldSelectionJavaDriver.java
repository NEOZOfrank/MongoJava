package com.tengen;

import java.net.UnknownHostException;
import java.util.Random;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;
import com.mongodb.QueryBuilder;
import com.mongodb.ServerAddress;

public class FieldSelectionJavaDriver {

	/**
	 * @param args
	 * @throws UnknownHostException
	 */
	public static void main(String[] args) throws UnknownHostException {
		MongoClient client = new MongoClient(new ServerAddress("localhost", 27017));
		DB db = client.getDB("m2");
		DBCollection coll = db.getCollection("aufgabe2");
		coll.drop();
		
		Random random = new Random();
		for (int i = 0; i < 10; i++) {
			coll.insert(new BasicDBObject("x", random.nextInt(2)).append("y", random.nextInt(100)).append("z", random.nextInt(1000)));
		}
		
		//variante 1
		DBObject query = new BasicDBObject("x", 0).append("y", new BasicDBObject("$gt", 10).append("$lt", 90));
		
		//variante 2
		QueryBuilder qb = QueryBuilder.start("x").is(0).and("y").lessThan(90).greaterThan(10);
		
		// find all
		
		// DBCursor cur = coll.find(query);
//		DBCursor cur = coll.find(qb.get(), new BasicDBObject("x", false)); // ohne x
		DBCursor cur = coll.find(qb.get(), new BasicDBObject("y", true).append("_id", false)); // nur y
		try {
			while (cur.hasNext()) {
				DBObject obj2 = cur.next();
				System.out.println(obj2);
			}
		} finally {
			cur.close();
		}

		// print count
		// System.out.println(coll.count(query));
		System.out.println(coll.count(qb.get()));

	}

}
