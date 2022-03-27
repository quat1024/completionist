package agency.highlysuspect.completionist.platform.forge;

import agency.highlysuspect.completionist.Completionist;
import net.minecraftforge.fml.common.Mod;

@Mod(Completionist.MODID)
public class ForgeEntrypoint {
	public ForgeEntrypoint() {
		Completionist.INSTANCE.onInitialize();
	}
}
