package cs5500.plagiarism.service;

import java.io.File;

/**
 * @since 02.10.2018
 * @author team211
 * required functionalities on PlagarismDetector.
 */
public interface PlagarismService {
	public void saveScore(File f1, File f2);
}
