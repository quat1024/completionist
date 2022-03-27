package agency.highlysuspect.completionist;

import agency.highlysuspect.completionist.platform.CompletionistXplat;
import agency.highlysuspect.completionist.platform.ServiceHelper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Completionist {
	public static final String MODID = "completionist";
	public static final Logger LOGGER = LogManager.getLogger(MODID);
	
	public static final Completionist INSTANCE = new Completionist(ServiceHelper.loadSingletonService(CompletionistXplat.class));
	
	private Completionist(CompletionistXplat xplat) {
		this.xplat = xplat;
	}
	
	public final CompletionistXplat xplat;
	
	public void onInitialize() {
		xplat.markClientOnly();
	}
}
