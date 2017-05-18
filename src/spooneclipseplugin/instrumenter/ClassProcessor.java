package spooneclipseplugin.instrumenter;

import spoon.SpoonException;
import spoon.processing.AbstractProcessor;
import spoon.reflect.code.CtCodeSnippetStatement;
import spoon.reflect.declaration.CtType;

public class ClassProcessor extends AbstractProcessor<CtType<?>> {


	@Override
	public void process(CtType<?> clazz) {
			// test
			clazz.getMethods().forEach(m -> {
				String toAdd = "System.out.println(\""+m.getSimpleName()+" executed!\")";
				CtCodeSnippetStatement ctCodeSnippetStatement = getFactory().Core().createCodeSnippetStatement();
				ctCodeSnippetStatement.setValue(toAdd);
				try {
					if (m.getBody() != null) {
						m.getBody().insertBegin(ctCodeSnippetStatement);	
					}
				} catch (SpoonException e) {
				}
			});
	}
}
