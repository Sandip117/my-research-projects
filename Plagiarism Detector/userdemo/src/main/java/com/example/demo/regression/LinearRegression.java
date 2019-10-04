package com.example.demo.regression;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.List;

public class LinearRegression {
	private final double intercept;
	private final double slope;
	private final double r2;
	private final double svar0;
	private final double svar1;

	/**
	 * Performs a linear regression on the data points
	 * @param  astTrainData the values of the predictor variable.
	 * @param  mossTrainData the corresponding values of the response variable.
	 * @throws IllegalArgumentException if the lengths of training set are
	 * 		   not the same. 
	 */
	public LinearRegression(double[] astTrainData, double[] mossTrainData) {
		if (astTrainData.length != mossTrainData.length) {
			throw new IllegalArgumentException("array lengths are not equal");
		}
		int n = astTrainData.length;

		// first pass
		double sumx = 0.0, sumy = 0.0, sumx2 = 0.0;
		for (int i = 0; i < n; i++) {
			sumx  += astTrainData[i];
			sumx2 += astTrainData[i]*astTrainData[i];
			sumy  += mossTrainData[i];
		}
		double xbar = sumx / n;
		double ybar = sumy / n;

		// second pass: compute summary statistics
		double xxbar = 0.0, yybar = 0.0, xybar = 0.0;
		for (int i = 0; i < n; i++) {
			xxbar += (astTrainData[i] - xbar) * (astTrainData[i] - xbar);
			yybar += (mossTrainData[i] - ybar) * (mossTrainData[i] - ybar);
			xybar += (astTrainData[i] - xbar) * (mossTrainData[i] - ybar);
		}
		slope  = xybar / xxbar;
		intercept = ybar - slope * xbar;

		// more statistical analysis
		double rss = 0.0;      
		double ssr = 0.0; 
		for (int i = 0; i < n; i++) {
			double fit = slope*astTrainData[i] + intercept;
			rss += (fit - mossTrainData[i]) * (fit - mossTrainData[i]);
			ssr += (fit - ybar) * (fit - ybar);
		}
		int degreesOfFreedom = n-2;
		r2    = ssr / yybar;
		double svar  = rss / degreesOfFreedom;
		svar1 = svar / xxbar;
		svar0 = svar/n + xbar*xbar*svar1;
		System.out.println(svar1);
		System.out.println(svar0);
	}

	/**
	 * Returns the intercept of the best of the best-fit line 
	 *
	 * @return the intercept of the best-fit line 
	 */
	public double intercept() {
		return intercept;
	}

	/**
	 * Returns the slope of the best of the best-fit line 
	 *
	 * @return the slope of the best-fit line 
	 */
	public double slope() {
		return slope;
	}

	/**
	 * Returns the coefficient of determination 
	 *
	 * @return the coefficient of determination 
	 *         which is a real number between 0 and 1
	 */
	public double R2() {
		return r2;
	}

	/**
	 * Returns the standard error of the estimate for the intercept.
	 *
	 * @return the standard error of the estimate for the intercept
	 */
	public double interceptStdErr() {
		return Math.sqrt(svar0);
	}

	/**
	 * Returns the standard error of the estimate for the slope.
	 *
	 * @return the standard error of the estimate for the slope
	 */
	public double slopeError() {
		return Math.sqrt(svar1);
	}


	/**
	 * Returns a string representation of the simple linear regression model.
	 *
	 * @return a string representation of the simple linear regression model,
	 *         including the best-fit line and the coefficient of determination       
	 */
	public String toString() {
		StringBuilder s = new StringBuilder();
		s.append(String.format("%.2f n + %.2f", slope(), intercept()));
		s.append("  (R^2 = " + String.format("%.3f", R2()) + ")");
		return s.toString();
	}
	/**
	 * Predicts the accurate plagiarism percentage based on moss standards/
	 * @param x is the percentage. 
	 * @return returns the predicted percentage. 
	 */
	public double predict(double x) {
		return (slope * x) + intercept;
	}
	
	public static FileOperationsAndOutput loadDataSet() throws IOException {
		File file = new File("../../team-211/userdemo/datasets/LR_dataset.csv"); 
		List<String> lines = Files.readAllLines(file.toPath(), StandardCharsets.UTF_8);
		int size = lines.size();
		double astOutput[] = new double[size];
		double mossOutput[] = new double[size];
		int index = 0;
		for (String line : lines) { 
		   String[] array = line.split(","); 
		   astOutput[index] = Double.parseDouble(array[0]);
		   mossOutput[index] = Double.parseDouble(array[array.length-1]);
		   index += 1;
		}
		return new FileOperationsAndOutput(astOutput, mossOutput);
	}

	public static void main(String...args) throws IOException {
		
		LinearRegression.loadDataSet();
		
		
	}

}