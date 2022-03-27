package agency.highlysuspect.completionist;

import net.minecraft.advancements.Advancement;

public class AdvancementHelper {
	public static boolean isInternal(Advancement advancement) {
		return advancement.getParent() == null || advancement.getDisplay() == null;
	}
}
