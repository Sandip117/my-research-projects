package cs5500.plagiarism.schema;

import java.io.File;

/**
 * @since 02.10.2018
 * @author team211
 * Operations that can be performed on PlagarismDetector table. 
 */
public interface PlagarismDetectorSchema {
	void plagarismScore(File f1, File f2);
}
