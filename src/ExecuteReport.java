import java.util.HashMap;
import java.util.logging.Level;
 
import org.eclipse.birt.core.framework.Platform;
import org.eclipse.birt.report.engine.api.EngineConfig;
import org.eclipse.birt.report.engine.api.EngineConstants;
import org.eclipse.birt.report.engine.api.EngineException;
import org.eclipse.birt.report.engine.api.HTMLActionHandler;
import org.eclipse.birt.report.engine.api.HTMLEmitterConfig;
import org.eclipse.birt.report.engine.api.HTMLRenderContext;
import org.eclipse.birt.report.engine.api.HTMLRenderOption;
import org.eclipse.birt.report.engine.api.HTMLServerImageHandler;
import org.eclipse.birt.report.engine.api.IReportEngine;
import org.eclipse.birt.report.engine.api.IReportEngineFactory;
import org.eclipse.birt.report.engine.api.IReportRunnable;
import org.eclipse.birt.report.engine.api.IRunAndRenderTask;
import org.eclipse.birt.report.engine.api.PDFRenderOption;
 
public class ExecuteReport {
// Source : https://wiki.eclipse.org/Simple_Execute_(BIRT)_2.1

//    static void executeReport() throws EngineException {
//        final HashMap<String, Integer> PARAMETERS = new HashMap<String, Integer>();
// 
//        final String NAME = "Top Count";
//        final Integer VALUE = new Integer(4);
// 
//        PARAMETERS.put(NAME, VALUE);
// 
//        IReportEngine engine = null;
//        EngineConfig config = null;
// 
//        try {
// 
//           // Configure the Engine and start the Platform
//           config = new EngineConfig();
//           config.setEngineHome("C:/Users/jastk/Desktop/Malerifakta-Filer/BIRT/birt-runtime-4.7.0-20170622/ReportEngine");
//           // set log config using (null, Level) if you do not want a log file
//           config.setLogConfig(null, Level.FINE);
// 
//           Platform.startup(config);
//           final IReportEngineFactory FACTORY = (IReportEngineFactory) Platform.
//                 createFactoryObject(IReportEngineFactory.EXTENSION_REPORT_ENGINE_FACTORY);
// 
//           engine = FACTORY.createReportEngine(config);
//           engine.changeLogLevel(Level.WARNING);
// 
//        } catch(Exception ex) {
//           ex.printStackTrace();
//        }
// 
//        // Configure the emitter to handle actions and images
//        final HTMLEmitterConfig EMITTER_CONFIGURATION = new HTMLEmitterConfig();
// 
//        EMITTER_CONFIGURATION.setActionHandler(new HTMLActionHandler());
//        HTMLServerImageHandler imageHandler = new HTMLServerImageHandler();
//        EMITTER_CONFIGURATION.setImageHandler(imageHandler);
//        config.getEmitterConfigs().put("html", EMITTER_CONFIGURATION); // $NON-NLS-1$
// 
//        // Open the report design
//        final IReportRunnable DESIGN =
//              engine.openReportDesign("C:/Users/jastk/workspace/MFakta/Mfakta_rapport.rptdesign"); 
//        
// 
//        // Create task to run and render the report:
//        final IRunAndRenderTask TASK = engine.createRunAndRenderTask(DESIGN); 
// 
//        // Set Render context to handle URL and image locations
//        final HTMLRenderContext RENDER_CONTEXT = new HTMLRenderContext();
//        // Set the Base URL for all actions
//        RENDER_CONTEXT.setBaseURL(<nowiki>"http:// localhost/"</nowiki>);
//        // Tell the Engine to prepend all images with this URL - Note this requires using 
//        // the HTMLServerImageHandler
//        RENDER_CONTEXT.setBaseImageURL(<nowiki>"http:// localhost/myimages"</nowiki>);
//        // Tell the Engine where to write the images to
//        RENDER_CONTEXT.setImageDirectory("C:/xampplite/htdocs/myimages");
//        // Tell the Engine what image formats are supported.  Note you must have SVG in the string 
//        // to render charts in SVG.
//        RENDER_CONTEXT.setSupportedImageFormats("JPG;PNG;BMP;SVG");
//        final HashMap<String, HTMLRenderContext> CONTEXT =
//              new HashMap<String, HTMLRenderContext>();
//        CONTEXT.put(EngineConstants.APPCONTEXT_HTML_RENDER_CONTEXT, RENDER_CONTEXT);
//        TASK.setAppContext(CONTEXT);
//        // Set PARAMETERS for the report
//        TASK.setParameterValues(PARAMETERS);
//        // Alternatively set each separately
//        // TASK.setParameterValue("Top Count", new Integer(12));
//        TASK.validateParameters();
// 
//        // Add a scriptable object, which will allow the report developer to put
//        // script in the report that references this Java object, e.g. in script 
//        // pFilter.myjavamethod()
//        // final ProcessFilter PF = new ProcessFilter();
//        // TASK.addScriptableJavaObject("pFilter", PF);
// 
//        // Set rendering options - such as file or stream output, 
//        // output format, whether it is embeddable, etc
//        final HTMLRenderOption HTML_OPTIONS = new HTMLRenderOption();
// 
//        // Remove HTML and Body tags
//        // HTML_OPTIONS.setEmbeddable(true);
// 
//        // Set output location
//       HTML_OPTIONS.setOutputFileName("C:/Users/jastk/Desktop/Malerifakta-Filer/Ackordräkning-output/index.html");
// 
//        // Set output format
//        HTML_OPTIONS.setOutputFormat("html");
//        TASK.setRenderOption(HTML_OPTIONS);
// 
//        // run the report and destroy the engine
//        // Note - If the program stays resident do not shutdown the Platform or the Engine
//        TASK.run();
//        TASK.close();
//        engine.shutdown();
//        Platform.shutdown();
//        System.out.println("Finished");
//    }
//    
    
    public static void executeReport() throws EngineException {
    	 
        IReportEngine engine = null;
        EngineConfig config = null;
     
        try {
            config = new EngineConfig();  
            // Path to birt-runtime instalation 
            config.setBIRTHome("C:/Users/j/Desktop/Ma/BIRT/birt-runtime-4.7.0-20170622/ReportEngine");
            config.setLogConfig(null, Level.FINEST);
            Platform.startup(config);
            final IReportEngineFactory FACTORY = (IReportEngineFactory) Platform
                .createFactoryObject(IReportEngineFactory.EXTENSION_REPORT_ENGINE_FACTORY);
            engine = FACTORY.createReportEngine(config);       
     
            // Open the report design
            IReportRunnable design = null;
            // Path to the .rptdesign file
            design = engine.openReportDesign("C:/Users/j/Copy/Mfakta_rapport.rptdesign"); 
            IRunAndRenderTask task = engine.createRunAndRenderTask(design);        
            // task.setParameterValue("Top Count", (new Integer(5)));
            // task.validateParameters();
            String id= "1295331";  // 1295998 
            task.setParameterValue("RP_param", id);   // Sätter parameter
     
//            final HTMLRenderOption HTML_OPTIONS = new HTMLRenderOption();       
//            HTML_OPTIONS.setOutputFileName("C:/Users/jastk/Desktop/Malerifakta-Filer/Ackordräkning-output/index.html");
//            HTML_OPTIONS.setOutputFormat("html");
//            HTML_OPTIONS.setHtmlRtLFlag(false);
//            HTML_OPTIONS.setEmbeddable(false);
//            HTML_OPTIONS.setImageDirectory("C:/Users/jastk/workspace/MFakta/logo.png");
     
             PDFRenderOption PDF_OPTIONS = new PDFRenderOption();
             // Path to the output folder
             PDF_OPTIONS.setOutputFileName("C:/Users/j/Desktop/Mal/Ackordräkning-output/new.pdf");
             PDF_OPTIONS.setOutputFormat("pdf");
     
            //task.setRenderOption(HTML_OPTIONS);
            task.setRenderOption(PDF_OPTIONS);
            task.run();
            task.close();
            engine.destroy();
        } catch(final Exception EX) {
            EX.printStackTrace();
        } finally {
           Platform.shutdown();
        }
        System.out.println("Finished");
    }

        

    public static void main(final String[] ARGUMENTS) {
        try {
           executeReport();
        } catch (final Exception EX) {
           EX.printStackTrace();
        }
    }
}