package agency.highlysuspect.completionist.mixin;

import net.minecraft.advancements.AdvancementList;
import net.minecraft.server.ServerAdvancementManager;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(ServerAdvancementManager.class)
public interface ServerAdvancementManagerAccessor {
	@Accessor("advancements") AdvancementList completionist$getAdvancementList();
}
