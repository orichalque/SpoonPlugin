package spooneclipseplugin.handlers;

import java.io.IOException;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.handlers.HandlerUtil;

import spooneclipseplugin.instrumenter.SpoonInstrumenter;

/**
 * Our sample handler extends AbstractHandler, an IHandler base class.
 * @see org.eclipse.core.commands.IHandler
 * @see org.eclipse.core.commands.AbstractHandler
 */
public class SampleHandler extends AbstractHandler {

	private IWorkbenchWindow window;
	
	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {
		window = HandlerUtil.getActiveWorkbenchWindowChecked(event);
		
		IStructuredSelection selection = (IStructuredSelection) window.getSelectionService().getSelection();
		Object firstElement = selection.getFirstElement();
		
		IProject iProject = ((IAdaptable) firstElement).getAdapter(IProject.class);
		
		try {
			new SpoonInstrumenter().instrument(iProject.getLocation().toString());
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return null;
	}
}
