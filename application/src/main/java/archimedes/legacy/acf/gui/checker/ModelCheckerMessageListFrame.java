/*
 * ModelCheckerMessageListFrame.java
 *
 * 19.05.2016
 *
 * (c) by HO.Lieshoff
 *
 */

package archimedes.legacy.acf.gui.checker;

import static corentx.util.Checks.ensure;

import archimedes.legacy.acf.checker.ModelCheckerMessage;
import baccara.gui.GUIBundle;
import baccara.gui.components.BaccaraFrameWithListener;

/**
 * A frame which shows the model checker messages in a list view.
 *
 * @author O.Lieshoff
 *
 * @changed OLI 19.05.2016 - Added.
 */

public class ModelCheckerMessageListFrame extends
		BaccaraFrameWithListener<ModelCheckerMessageListFrameListener, ModelCheckerMessageListFrameListener.Event>
		implements ModelCheckerMessageListEventFirer {

	private static final String RES_MODEL_CHECKER_LIST_FRAME_TITLE = "ModelChecker.list.frame.title";

	private ModelCheckerMessageListPanel panel = null;

	/**
	 * Creates a new frame for model checker message view with the passed parameters.
	 *
	 * @param guiBundle           A bundle with GUI information.
	 * @param mcms                The list of model checker messages to show.
	 * @param checkBeforeGenerate Set this flag if the frame is called as check before generating the code.
	 * @param listeners           Which should observe the frame.
	 * @throws IllegalArgumentException Passing a null pointer.
	 *
	 * @changed OLI 19.05.2016 - Added.
	 */
	public ModelCheckerMessageListFrame(GUIBundle guiBundle, ModelCheckerMessage[] mcms, boolean checkBeforeGenerate,
			ModelCheckerMessageListFrameListener... listeners) {
		super(guiBundle, RES_MODEL_CHECKER_LIST_FRAME_TITLE);
		ensure(mcms != null, "list of model checker messages cannot be null.");
		for (ModelCheckerMessageListFrameListener listener : listeners) {
			this.addBaccaraFrameListener(listener);
		}
		try {
			this.panel = new ModelCheckerMessageListPanel(guiBundle, mcms, checkBeforeGenerate, this, listeners);
			this.setContentPane(this.panel);
			this.pack();
			this.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * @changed OLI 11.07.2016 - Added.
	 */
	@Override
	public void fireAction(ModelCheckerMessageListFrameListener.Event e) {
		if ((e.getType() == ModelCheckerMessageListFrameListener.Event.Type.CLOSED)
				|| (e.getType() == ModelCheckerMessageListFrameListener.Event.Type.GENERATE)) {
			this.setVisible(false);
			this.dispose();
		}
		this.fireBaccaraFrameEvent(e);
	}

	/**
	 * @changed OLI 19.05.2016 - Added.
	 */
	@Override
	public void fireEvent(ModelCheckerMessageListFrameListener l, ModelCheckerMessageListFrameListener.Event e) {
		l.eventDetected(e);
	}

	/**
	 * Updates the messages in the list frame.
	 *
	 * @param mcms The messages to show in the frame.
	 *
	 * @changed OLI 18.08.2016 - Added.
	 */
	public void updateMessages(ModelCheckerMessage[] mcms) {
		this.panel.updateMessages(mcms);
		if (mcms.length == 0) {
			this.setVisible(false);
		}
	}

}