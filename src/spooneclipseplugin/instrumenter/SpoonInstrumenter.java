package spooneclipseplugin.instrumenter;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

import spoon.Launcher;

public class SpoonInstrumenter {

	private static final String SRC_DIRECTORY = "/src/main/java/";
	private static final String TEST_DIRECTORY = "/src/test/java/";
	private static final String SRC_OUT_DIRECTORY = "/srcOut/";
	private static final String BIN_OUT_DIRECTORY = "/out/";
	private static final String LIB_DIRECTORY = "/lib/";
	
	public void instrument(String absoluteUri) throws IOException {
		Launcher launcher = new Launcher();
		launcher.getEnvironment().setShouldCompile(true);
		launcher.getEnvironment().setAutoImports(true);
		launcher.getEnvironment().setNoClasspath(false);		
		
		List<String> jars = new ArrayList<>();
		
		
		Files.walk(new File(absoluteUri.concat(LIB_DIRECTORY)).toPath()).forEach(p -> jars.add(p.toString()));
		
		jars.forEach(s -> System.out.println(s));
		
		launcher.getEnvironment().setSourceClasspath(jars.toArray(new String[jars.size()]));
		
		launcher.addInputResource(absoluteUri.concat(SRC_DIRECTORY));
		launcher.addInputResource(absoluteUri.concat(TEST_DIRECTORY));
		
		launcher.setBinaryOutputDirectory(absoluteUri.concat(BIN_OUT_DIRECTORY)); 
		launcher.setSourceOutputDirectory(absoluteUri.concat(SRC_OUT_DIRECTORY));

		launcher.addProcessor(new ClassProcessor());
		
		launcher.run();
	}
}
