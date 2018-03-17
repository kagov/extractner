package com.extractner;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Properties;

import edu.stanford.nlp.ling.CoreAnnotations;
import edu.stanford.nlp.pipeline.Annotation;
import edu.stanford.nlp.pipeline.AnnotationPipeline;
import edu.stanford.nlp.pipeline.JSONOutputter;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;


public class NerExtractor {
	
	static final DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss",Locale.ENGLISH);
	
	public static void main(String [] args) {
		
		String text = "John is going to New York and I am watching avengers and "
				+ "eating an apple";
		
		AnnotationPipeline pipeline = new AnnotationPipeline();
		pipeline.addAnnotator(new StanfordCoreNLP(getProperties(/* the entities file*/ "entities.txt" )));
		Annotation annotation = new Annotation(text);
		annotation.set(CoreAnnotations.DocDateAnnotation.class, dateFormat.format(new Date()));
		pipeline.annotate(annotation);
		
		try {
			new JSONOutputter().print(annotation,System.out);
		} catch (IOException e) {
			
			e.printStackTrace();
		}
	}
	
	private static Properties getProperties(final String fileName) {
		Properties props = new Properties();
		// the annotators that are required to carry out the extraction process
		// ner is name entity extraction 
		// regexner is the annotator that is responsible for custom entity extraction
		props.put("annotators", "tokenize,ssplit,pos,lemma,ner,regexner");
		// the mapping file that contains the details for the custom entities
		props.put("regexner.mapping",fileName);
	    props.put("regexner.ignorecase","true");
		return props;
	}

}
