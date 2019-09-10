package de.ollie.archimedes.alexandrian.gui.codegenerator;

import archimedes.legacy.model.DataModel;
import de.ollie.archimedes.alexandrian.codegenerator.CodeFactory;
import de.ollie.archimedes.alexandrian.codegenerator.CodeFactoryListener;

/**
 * A starter for code generation processes.
 *
 * @author ollie
 *
 */
public class CodeGeneratorStarter implements CodeFactoryListener {

	private String codeFactoryClassName;
	private String codePath = null;
	private DataModel dataModel;

	/**
	 * Creates a new code generator for the passed parameters.
	 * 
	 * @param codeFactoryClassName The name of the class which is to use for code generation.
	 * @param codePath             The path to the code folder.
	 * @param dataModel            The data model which the code is to create for.
	 */
	public CodeGeneratorStarter(String codeFactoryClassName, String codePath, DataModel dataModel) {
		super();
		this.codeFactoryClassName = codeFactoryClassName;
		this.codePath = codePath;
		this.dataModel = dataModel;
	}

	/**
	 * Starts the code generation process.
	 */
	public void start() {
		try {
			final CodeFactory codeFactory = createCodeFactory();
			final CodeFactoryListener listener = this;
			Thread t = new Thread(new Runnable() {
				public void run() {
					((CodeFactory) codeFactory).addCodeFactoryListener(listener);
					((CodeFactory) codeFactory).setDataModel(dataModel);
					((CodeFactory) codeFactory).generate(codePath);
				}
			});
			t.start();
		} catch (Exception e) {
			// ERROR MESSAGE TO MESSAGE FIELD OF PROGRESS DIALOG.
		}
	}

	private CodeFactory createCodeFactory() throws Exception {
		Class<?> codeFactoryClass = Class.forName(codeFactoryClassName);
		return (CodeFactory) codeFactoryClass.newInstance();
	}

	@Override
	public void exceptionOccurred(Exception exception) {
		// TODO Auto-generated method stub
	}

	@Override
	public void messageAppended(String message) {
		// TODO Auto-generated method stub
	}

	@Override
	public void progressChanged(int newProgressPercentage) {
		// TODO Auto-generated method stub
	}

}