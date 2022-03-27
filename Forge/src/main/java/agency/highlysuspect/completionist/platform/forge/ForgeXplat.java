package agency.highlysuspect.completionist.platform.forge;

import agency.highlysuspect.completionist.platform.CompletionistXplat;
import net.minecraftforge.fml.ExtensionPoint;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.network.FMLNetworkConstants;
import org.apache.commons.lang3.tuple.Pair;

public class ForgeXplat implements CompletionistXplat {
	@Override
	public void markClientOnly() {
		//cool modloader!
		ModLoadingContext.get().registerExtensionPoint(ExtensionPoint.DISPLAYTEST, () -> Pair.of(() -> FMLNetworkConstants.IGNORESERVERONLY, (a, b) -> true));
	}
}
