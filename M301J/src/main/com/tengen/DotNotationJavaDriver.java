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

public class DotNotationJavaDriver {

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
			coll.insert(new BasicDBObject("_id", i)
							.append("start", 
									new BasicDBObject("x", random.nextInt(90)+10)
										    .append("y", random.nextInt(90)+10)
							)
							.append("end", 
									new BasicDBObject("x", random.nextInt(90)+10)
									          .append("y", random.nextInt(90)+10)
				                  )
                             );
		}

		//variante 2
		QueryBuilder qb = QueryBuilder.start("start.x").greaterThan(50);
		
		// find all
		

		DBCursor cur = coll.find(qb.get(), new BasicDBObject("start.y", true).append("_id", false)); 
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
