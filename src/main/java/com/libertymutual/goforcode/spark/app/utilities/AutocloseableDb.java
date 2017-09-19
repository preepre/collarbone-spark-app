package com.libertymutual.goforcode.spark.app.utilities;

import java.io.Closeable;
import org.javalite.activejdbc.Base;

public class AutocloseableDb implements Closeable, AutoCloseable {

	public AutocloseableDb() {
		Base.open("org.postgresql.Driver", 
				"jdbc:postgresql://localhost:5432/rental", "rental", 
				"rental");
	}

	@Override
	public void close() {
		Base.close();

	}

}
