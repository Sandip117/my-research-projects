package com.example.demo.ast;

import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.RuleContext;
import org.antlr.v4.runtime.tree.ParseTree;

public class AstPrinter {
	//to store child count of prev node
	int prev=-1;
	int len=0;
	String ast="";
	String ast2="";

	private boolean ignoringWrappers = true;
	/**
	 * 
	 * public getters and setters
	 */
	public void setIgnoringWrappers(boolean ignoringWrappers) {
		this.ignoringWrappers = ignoringWrappers;
	}

	public String getAst(RuleContext ctx) {
		return exploreAstAsString(ctx, 0);
	}

	public int getLength() {
		return len;
	}

	private String exploreAstAsString(RuleContext ctx, int indentation) {
		boolean toBeIgnored = ignoringWrappers
				&& ctx.getChildCount() == 1
				&& ctx.getChild(0) instanceof ParserRuleContext;
		if (!toBeIgnored) {
			String ruleName = Python3Parser.ruleNames[ctx.getRuleIndex()];
			if(prev >= indentation) {
				for(int i=0;i<=prev-indentation;i++) {
					ast += "}";
				}
			}
			ast+= "{" + ruleName;
			prev = indentation;
			len++;
		}
		for (int i=0;i<ctx.getChildCount();i++) {
			ParseTree element = ctx.getChild(i);
			if (element instanceof RuleContext) {
				exploreAstAsString((RuleContext)element, indentation + (toBeIgnored ? 0 : 1));
			}
		}
		return astComplete(ast);
	}

	//Helper function to append the remaining parenthesis to the resultant ast. 
	private String astComplete(String ast) {
		int temp = 0;
		for (int i = 0; i < ast.length(); i++){
			if(ast.charAt(i) == '{') {
				temp++;
			}
			if(ast.charAt(i) == '}') {
				temp--;
			}
		}
		while(temp>0) {
			ast += "}";
			temp--;
		}
		return ast;
	}

	/**
	 * print is used to traverse the ast and return the parsed file as a list of rules
	 * @param ctx
	 */
	public String print(RuleContext ctx) {
		return explore(ctx, 0);
	}

	/**
	 * Helper function for print()
	 * @param ctx contains nodes of AST
	 * @param indentation used to append spaces and and visualise AST
	 * @return parsed file as a ast which is represented as a string
	 */
	private String explore(RuleContext ctx, int indentation) {
		boolean toBeIgnored = ignoringWrappers
				&& ctx.getChildCount() == 1
				&& ctx.getChild(0) instanceof ParserRuleContext;
		if (!toBeIgnored) {
			String ruleName = Python3Parser.ruleNames[ctx.getRuleIndex()];
			for (int i = 0; i < indentation; i++) {
				ast2 += " ";
			}
			ast2 += ruleName;
			len++;

		}
		for (int i=0;i<ctx.getChildCount();i++) {
			ParseTree element = ctx.getChild(i);
			if (element instanceof RuleContext) {
				explore((RuleContext)element, indentation + (toBeIgnored ? 0 : 1));
			}
		}
		return ast2;
	}


}
