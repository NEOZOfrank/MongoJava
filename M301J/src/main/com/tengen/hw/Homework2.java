package com.tengen.hw;

import java.net.UnknownHostException;
import java.util.HashSet;
import java.util.Set;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;
import com.mongodb.QueryBuilder;
import com.mongodb.ServerAddress;

public class Homework2 {

	/**
	 * @param args
	 * @throws UnknownHostException 
	 */
	public static void main(String[] args) throws UnknownHostException {
		MongoClient client = new MongoClient(new ServerAddress("localhost", 27017));
		DB db = client.getDB("students");
		DBCollection coll = db.getCollection("grades");


		QueryBuilder qb = QueryBuilder.start();
		
		// find all
		Set<Integer> ids = new HashSet<Integer>();
		
		DBCursor cur = coll.find(qb.get(), new BasicDBObject("student_id", true).append("_id", false));
		try {
			while (cur.hasNext()) {
				DBObject obj2 = cur.next();
				ids.add((Integer) obj2.get("student_id"));
				
			}
			
		} finally {
			cur.close();
		}
		
		removeMinHW(db, ids);
	}

	private static void removeMinHW(DB db, Set<Integer> ids) {
		DBCollection coll = db.getCollection("grades");
		
		for (Integer studentId : ids) {
			QueryBuilder qb = QueryBuilder.start("student_id").is(studentId).and("type").is("homework");
			
			DBCursor cur = coll.find(qb.get()).sort(new BasicDBObject("score", 1)).limit(1);
			try {
				while (cur.hasNext()) {
					DBObject obj2 = cur.next();
//					coll.remove(obj2);
					
					System.out.println(obj2);
				}
			} finally {
				cur.close();
			}
		}
		
		
	}

}
