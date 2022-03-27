package agency.highlysuspect.completionist;

import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.AdvancementProgress;

import java.util.Arrays;

public class ProgressSummary {
	public ProgressSummary(int completed, int total) {
		this.completed = completed;
		this.total = total;
	}
	
	public ProgressSummary() {
		this(0, 0);
	}
	
	public static ProgressSummary sum(ProgressSummary... summaries) {
		return sum(Arrays.asList(summaries));
	}
	
	public static ProgressSummary sum(Iterable<ProgressSummary> summaries) {
		int completedSum = 0;
		int totalSum = 0;
		
		for(ProgressSummary summary : summaries) {
			completedSum += summary.completed;
			totalSum += summary.total;
		}
		
		return new ProgressSummary(completedSum, totalSum);
	}
	
	///
	
	public int completed;
	public int total;
	
	public void visit(Advancement advancement, AdvancementProgress progress) {
		if(AdvancementHelper.isInternal(advancement)) return;
		
		total++;
		if(progress.isDone()) completed++;
	}
	
	@Override
	public String toString() {
		return completed + " / " + total;
	}
	
	@Override
	public boolean equals(Object o) {
		if(this == o) return true;
		if(o == null || getClass() != o.getClass()) return false;
		
		ProgressSummary that = (ProgressSummary) o;
		
		if(completed != that.completed) return false;
		return total == that.total;
	}
	
	@Override
	public int hashCode() {
		int result = completed;
		result = 31 * result + total;
		return result;
	}
}
