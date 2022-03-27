package agency.highlysuspect.completionist;

import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.AdvancementList;
import net.minecraft.server.PlayerAdvancements;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class SummaryData {
	public SummaryData(ProgressSummary totalSummary, Map<Advancement, ProgressSummary> perRootSummaries) {
		this.totalSummary = totalSummary;
		this.perRootSummaries = perRootSummaries;
	}
	
	public ProgressSummary totalSummary;
	public Map<Advancement, ProgressSummary> perRootSummaries;
	
	public void pareDownToVisible(Set<Advancement> visible) {
		for(Advancement root : new ArrayList<>(perRootSummaries.keySet())) { //copy before iterating
			if(!visible.contains(root)) perRootSummaries.remove(root);
		}
	}
	
	public static SummaryData compute(AdvancementList advancementList, PlayerAdvancements playerAdvancements) {
		Map<Advancement, ProgressSummary> perRootSummaries = new HashMap<>();
		
		for(Advancement root : advancementList.getRoots()) {
			ProgressSummary perRootSummary = new ProgressSummary();
			recursivelyVisitAdvancements(playerAdvancements, root, perRootSummary);
			perRootSummaries.put(root, perRootSummary);
		}
		
		ProgressSummary totalSummary = ProgressSummary.sum(perRootSummaries.values());
		
		return new SummaryData(totalSummary, perRootSummaries);
	}
	
	private static void recursivelyVisitAdvancements(PlayerAdvancements playerAdvancements, Advancement advancement, ProgressSummary summary) {
		//TODO: won't this force-start progress on every single advancement? hm
		summary.visit(advancement, playerAdvancements.getOrStartProgress(advancement));
		
		for(Advancement child : advancement.getChildren()) {
			if(AdvancementHelper.isInternal(advancement)) continue;
			recursivelyVisitAdvancements(playerAdvancements, child, summary);
		}
	}
	
	@Override
	public String toString() {
		ToStringBuilder what = new ToStringBuilder(this);
		what.append("Total: ", totalSummary);
		perRootSummaries.forEach((advancement, progressSummary) -> what.append(" - " + advancement.getId().toString(), progressSummary));
		return what.build();
	}
}
