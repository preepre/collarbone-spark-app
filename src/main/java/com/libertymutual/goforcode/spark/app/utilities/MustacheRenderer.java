package com.libertymutual.goforcode.spark.app.utilities;

import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;

import com.github.mustachejava.DefaultMustacheFactory;
import com.github.mustachejava.Mustache;

public class MustacheRenderer {
	
	private DefaultMustacheFactory factory;
	private static final MustacheRenderer instance = new 
			MustacheRenderer("templates");
	
	//the only class who can call the constructor is the class itself
	private MustacheRenderer(String folderName) {
		factory = new DefaultMustacheFactory(folderName);
		
	}
	
	public static MustacheRenderer getInstance() {
		
		return instance;
		
	}
	
	public String render(String templatPath, Map<String, Object> model) {
		Mustache mustache = factory.compile(templatPath);
		StringWriter writer = new StringWriter();
		mustache.execute(writer, model);
		
		return writer.toString();

	}

}
