import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.logging.Level;

import org.eclipse.birt.core.exception.BirtException;
import org.eclipse.birt.core.framework.Platform;
import org.eclipse.birt.report.engine.api.EngineConfig;
import org.eclipse.birt.report.engine.api.EngineException;
import org.eclipse.birt.report.engine.api.IReportEngine;
import org.eclipse.birt.report.engine.api.IReportEngineFactory;
import org.eclipse.birt.report.engine.api.IReportRunnable;
import org.eclipse.birt.report.engine.api.IRunAndRenderTask;
import org.eclipse.birt.report.engine.api.PDFRenderOption;

import com.lowagie.text.DocumentException;
import com.lowagie.text.pdf.PdfCopyFields;
import com.lowagie.text.pdf.PdfReader;
// https://stackoverflow.com/questions/890966/what-is-string-args-parameter-in-main-method-java
public class RunApplication_medFordeling {
	
	public static void main(String[] args) {
		List<String> reportFilePathsToMerge = getReportFilePathsToMerge();
		try {
			RunApplication_medFordeling mergeReports = new RunApplication_medFordeling();
			mergeReports.runReport(reportFilePathsToMerge);
			System.out.println("Done!");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void runReport(List<String> reportFilePathsToMerge) throws Exception {
		List<ByteArrayOutputStream> byteArrayOutputStreams = new ArrayList<ByteArrayOutputStream>();
		IReportEngine engine = initBirtEngine();
		for (String filePath : reportFilePathsToMerge) {
			byteArrayOutputStreams.add(runTaskAndGetOutputStream(filePath, engine));
		}
		renderCombinedReportFromOutputStreams(byteArrayOutputStreams);
		engine.destroy();
		Platform.shutdown();
	}

	private void renderCombinedReportFromOutputStreams(List<ByteArrayOutputStream> byteArrayOutputStreams)
			throws DocumentException, IOException {
		List<PdfReader> pdfReaders = new ArrayList<PdfReader>();
		PdfCopyFields pdfCopyFields = new PdfCopyFields(new FileOutputStream(
				"C:/users/jastk/Desktop/Malerifakta-Filer/Ackordräkning-output/Ackord_med_fordelning.pdf"));

		for (ByteArrayOutputStream byteArrayOutputStream : byteArrayOutputStreams) {
			PdfReader pdfReader = new PdfReader(byteArrayOutputStream.toByteArray());
			pdfReaders.add(pdfReader);
		}

		for (PdfReader pdfReader : pdfReaders) {
			pdfCopyFields.addDocument(pdfReader);
		}
		pdfCopyFields.close();
	}

	private IReportEngine initBirtEngine() throws BirtException {
		EngineConfig config = new EngineConfig();
		config.setBIRTHome("C:/Users/jastk/Desktop/Malerifakta-Filer/BIRT/birt-runtime-4.7.0-20170622");
		config.setLogConfig(null, Level.OFF);

		Platform.startup(config);
		IReportEngineFactory factory = (IReportEngineFactory) Platform
				.createFactoryObject(IReportEngineFactory.EXTENSION_REPORT_ENGINE_FACTORY);
		IReportEngine engine = factory.createReportEngine(config);
		return engine;
	}

	private ByteArrayOutputStream runTaskAndGetOutputStream(String filePath, IReportEngine engine)
			throws EngineException {
		IReportRunnable design = engine.openReportDesign(filePath);
		IRunAndRenderTask task = engine.createRunAndRenderTask(design);
		
		String uppdragsId = "1295998"; //1295985   1295998   1295242
		String individTyp = "MAT";
		task.setParameterValue("IN_UPPDRAG_ID", uppdragsId); 
		task.setParameterValue("IN_TYP_ID", individTyp); 

		PDFRenderOption options = new PDFRenderOption();
		ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
		options.setOutputStream(byteArrayOutputStream);
		options.setOutputFormat("pdf");

		task.setRenderOption(options);
		task.run();
		task.close();

		return byteArrayOutputStream;
	}

	private static List<String> getReportFilePathsToMerge() {
		List<String> filePaths = new ArrayList<String>();
		filePaths.add("del1_med_fordelning.rptdesign");
		filePaths.add("del2_med_fordelning.rptdesign");
		return filePaths;
	}

}
