/*
 * BaseCodeFactory.java
 *
 * 31.03.2018
 *
 * (c) by O.Lieshoff
 *
 */

package archimedes.legacy.cf;

import java.io.File;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import org.apache.log4j.Logger;

import archimedes.legacy.acf.event.CodeFactoryListener;
import archimedes.legacy.acf.gui.checker.ModelCheckerMessageListFrameListener;
import archimedes.legacy.acf.io.FileBaseCodePathReader;
import archimedes.legacy.acf.io.FileExistenceChecker;
import archimedes.legacy.cf.gui.ArtifactGeneratorListConfigurationDialog;
import archimedes.legacy.cf.gui.ProgressFrame;
import archimedes.legacy.model.CodeFactory;
import archimedes.legacy.model.DataModel;
import baccara.gui.GUIBundle;
import corentx.io.FileUtil;
import gengen.IndividualPreferences;
import gengen.IndividualPreferencesReader;

/**
 * An implementation of the code factory as base class for other code factories.
 *
 * @author O. Lieshoff
 *
 * @changed OLI 31.03.2018 - Added.
 */

abstract public class BaseCodeFactory implements CodeFactory {

	private static Logger log = Logger.getLogger(BaseCodeFactory.class);

	private DataModel dm = null;
	private FileExistenceChecker fileExistenceChecker = new FileExistenceChecker();
	private GUIBundle guiBundle = null;
	private List<CodeFactoryListener> listeners = new LinkedList<CodeFactoryListener>();

	protected ModelCheckerMessageListFrameListener[] modelCheckerMessagelisteners = new ModelCheckerMessageListFrameListener[0];

	@Override
	public void addCodeFactoryListener(CodeFactoryListener l) {
		this.listeners.add(l);
	}

	@Override
	public boolean generate(String basePath) {
		IndividualPreferences ip = loadIndividualPreferences(basePath);
		List<ArtifactGenerator<?>> artifactGenerators = this.getArtifactGenerators();
		ObjectsToGenerateCollection otgc = configureGenerationProcess(ip, artifactGenerators);
		if (otgc != null) {
			ProgressFrame progressFrame = createProgressFrame();
			List<Artifact> artifacts = generateArtifacts(progressFrame, ip, otgc, artifactGenerators);
			writeArtifacts(artifacts, progressFrame);
			generateReport(artifacts, progressFrame);
			progressFrame.close();
			return true;
		}
		return false;
	}

	private ProgressFrame createProgressFrame() {
		return new ProgressFrame(null, this.getGUIBundle().getResourceText("BaseCodeFactory.ProgressFrame.title"), 3);
	}

	private IndividualPreferences loadIndividualPreferences(String basePath) {
		IndividualPreferences ip = new IndividualPreferences(basePath, "COMPANYNAME", "USER NAME", "USR");
		String configFilePath = getConfigFilePath();
		if (this.fileExistenceChecker.exists(configFilePath)) {
			try {
				IndividualPreferencesReader baseCodePathReader = new FileBaseCodePathReader(configFilePath);
				/*
				 * if (this.individualPreferencesReader != null) { baseCodePathReader =
				 * this.individualPreferencesReader; }
				 */
				ip = baseCodePathReader.read(basePath);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return ip;
	}

	private String getConfigFilePath() {
		return FileUtil.completePath(System.getProperty("user.home")).concat(".bcf.properties");
	}

	private ObjectsToGenerateCollection configureGenerationProcess(IndividualPreferences ip,
			List<ArtifactGenerator<?>> artifactGenerators) {
		String configFilePath = FileUtil.completePath(System.getProperty("user.home")).concat(".bcf.properties");
		ArtifactGeneratorListConfigurationDialog d = new ArtifactGeneratorListConfigurationDialog(this.guiBundle,
				artifactGenerators.toArray(new ArtifactGenerator<?>[0]), ip, this.dm, configFilePath,
				this.getVersion());
		if (d.getClosingState() != ArtifactGeneratorListConfigurationDialog.ClosingState.GENERATE) {
			return null;
		}
		return d.getObjectToGenerateCodeForCollection();
	}

	private List<Artifact> generateArtifacts(ProgressFrame progressFrame, IndividualPreferences ip,
			ObjectsToGenerateCollection otgc, List<ArtifactGenerator<?>> generators) {
		progressFrame
				.setCommentSteps(this.getGUIBundle().getResourceText("BaseCodeFactory.step.generateArtifacts.name"));
		List<Artifact> artifacts = new LinkedList<Artifact>();
		progressFrame.resetGenerators(generators.size());
		for (ArtifactGenerator ag : generators) {
			if (otgc.contains(ag)) {
				log.debug("starting generation process for: " + ag.getName());
				progressFrame.setCommentGenerators(ag.getName());
				ag.generate(this.dm, artifacts, progressFrame, ip, otgc);
				progressFrame.incrementGenerators();
			}
		}
		progressFrame.incrementSteps();
		return artifacts;
	}

	private void writeArtifacts(List<Artifact> artifacts, ProgressFrame progressFrame) {
		progressFrame
				.setCommentSteps(this.getGUIBundle().getResourceText("BaseCodeFactory.step.generateArtifacts.name"));
		progressFrame.resetGenerators(1);
		progressFrame.setCommentGenerators(this.getGUIBundle().getResourceText("BaseCodeFactory.ArtifactWriter.title"));
		progressFrame.resetObjects(artifacts.size());
		for (Artifact a : artifacts) {
			if (isArtifactToWrite(a)) {
				writeArtifactToFile(a);
			}
			progressFrame.incrementObjects();
		}
		progressFrame.incrementGenerators();
		progressFrame.incrementSteps();
	}

	private boolean isArtifactToWrite(Artifact a) {
		return !new File(a.getSourceFileName()).exists();
	}

	private void writeArtifactToFile(Artifact a) {
		try {
			log.debug("writing artifact to file: " + a.getSourceFileName());
			FileUtil.writeTextToFile(a.getSourceFileName(), false, a.getContent());
		} catch (IOException e) {
			log.error("while writing artifact file: " + a.getSourceFileName() + ", exception: " + e.getMessage());
			e.printStackTrace();
		}
	}

	private void generateReport(List<Artifact> artifacts, ProgressFrame progressFrame) {
		progressFrame
				.setCommentSteps(this.getGUIBundle().getResourceText("BaseCodeFactory.step.generateArtifacts.name"));
		progressFrame.resetGenerators(1);
		progressFrame.setCommentGenerators(this.getGUIBundle().getResourceText("BaseCodeFactory.ArtifactWriter.title"));
		progressFrame.resetObjects(artifacts.size());
		for (Artifact a : artifacts) {
			// TODO Do something for the report.
			progressFrame.incrementObjects();
		}
		progressFrame.incrementGenerators();
		progressFrame.incrementSteps();
	}

	/**
	 * Returns the artifact generators which are used by the code factory.
	 *
	 * @return The artifact generators which are used by the code factory.
	 */
	abstract public List<ArtifactGenerator<?>> getArtifactGenerators();

	@Override
	public GUIBundle getGUIBundle() {
		return this.guiBundle;
	}

	@Override
	public void removeCodeFactoryListener(CodeFactoryListener l) {
		this.listeners.remove(l);
	}

	@Override
	public void setDataModel(DataModel dataModel) {
		this.dm = dataModel;
	}

	@Override
	public void setGUIBundle(GUIBundle guiBundle) {
		this.guiBundle = guiBundle;
	}

	@Override
	public void setModelCheckerMessageListFrameListeners(ModelCheckerMessageListFrameListener... listeners) {
		this.modelCheckerMessagelisteners = listeners;
	}

}