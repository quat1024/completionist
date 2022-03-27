package agency.highlysuspect.completionist.mixin;

import agency.highlysuspect.completionist.Completionist;
import agency.highlysuspect.completionist.ProgressSummary;
import agency.highlysuspect.completionist.SummaryData;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.AdvancementList;
import net.minecraft.server.PlayerAdvancements;
import net.minecraft.server.level.ServerPlayer;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Set;

/**
 * One PlayerAdvancements exists for each ServerPlayer. It manages the player's set of earned advancements and tracks their progress.
 * The server does not tell the client about all advancements. It only tells the client about advancements that the player has made
 * progress on, and the parents of those advancements (recursively).
 */
@Mixin(PlayerAdvancements.class)
public class PlayerAdvancementsMixin {
	private @Unique @Nullable SummaryData summaryData = null;
	
	@Shadow @Final private Set<Advancement> visible;
	
	@Inject(method = "flushDirty", at = @At(
		value = "INVOKE",
		//This is when it's clearing the set of "dirty" advancements.
		//figure this is a good time, since it doesn't get ran if any advancements are dirty.
		target = "Ljava/util/Set;clear()V",
		ordinal = 0
	))
	private void whenFlushing(ServerPlayer player, CallbackInfo ci) {
		AdvancementList advancementList = ((ServerAdvancementManagerAccessor) player.server.getAdvancements()).completionist$getAdvancementList();
		summaryData = SummaryData.compute(advancementList, ((PlayerAdvancements) (Object) this));
		
		//TODO actually send it to the client lmao.
		Completionist.LOGGER.info(summaryData);
		
		summaryData.pareDownToVisible(visible);
		Completionist.LOGGER.info("stripped: {}", summaryData);
	}
}
