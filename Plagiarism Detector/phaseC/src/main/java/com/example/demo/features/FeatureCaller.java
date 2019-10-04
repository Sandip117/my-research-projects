package com.example.demo.features;
import com.example.demo.ast.*;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;

import com.example.demo.ast.AstPrinter;
import com.example.demo.ast.ParserFacade;

public class FeatureCaller {
	public ClassLoader classLoader = getClass().getClassLoader();
	private double editDistanceSimilarity(File f1, File f2) throws IOException{
		// AST for file1
		ParserFacade parserFacade = new ParserFacade();
		AstPrinter astPrinter = new AstPrinter();
		String tree1 = astPrinter.getAst(parserFacade.parse(f1));
		int tree1Len = astPrinter.getLength();

		//AST for file2
		ParserFacade parserFacade2 = new ParserFacade();
		AstPrinter astPrinter2 = new AstPrinter();
		String tree2 = astPrinter2.getAst(parserFacade2.parse(f2));
		int tree2Len = astPrinter2.getLength();
		LblTree t1 = LblTree.fromString(tree1);
		LblTree t2 = LblTree.fromString(tree2);


		// Edit distance between two trees. 
		EditDist ed = new EditDist(1, 1, 1, false);
		double dist = ed.treeDist(t2, t1);
		int maxLen = tree1Len > tree2Len ? tree1Len : tree2Len;  
		return ((maxLen - dist)/maxLen) * 100;
	}
	private double jaccardSim(File f1, File f2) throws IOException{
		ParserFacade parserFacade = new ParserFacade();
		AstPrinter astPrinter = new AstPrinter();
		String tree1 = astPrinter.getAst(parserFacade.parse(f1));
		int tree1Len = astPrinter.getLength();

		//AST for file2
		ParserFacade parserFacade2 = new ParserFacade();
		AstPrinter astPrinter2 = new AstPrinter();
		String tree2 = astPrinter2.getAst(parserFacade2.parse(f2));
		int tree2Len = astPrinter2.getLength();
		JaccardSimilarity jaccard = new JaccardSimilarity();
		return jaccard.distance(tree1, tree2) * 100;
	}

	public double finalPlagiarismScore(File f1, File f2) throws IOException {
		FeatureCaller feat = new FeatureCaller();

		//File f1 = new File(feat.classLoader.getResource("a1/simple.py").getFile());
		//File f2 = new File(feat.classLoader.getResource("a2/simple.py").getFile());
		double edSimilarityScore = feat.editDistanceSimilarity(f1, f2);
		double jaccardSimilarityScore = feat.jaccardSim(f1, f2);
		return (0.9*edSimilarityScore) + (0.1*jaccardSimilarityScore);
	}

}
