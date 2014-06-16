package me.armar.plugins.autorank.playerchecker.requirement;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import me.armar.plugins.autorank.Autorank;
import me.armar.plugins.autorank.playerchecker.result.Result;
import me.armar.plugins.autorank.statsmanager.StatsPlugin;

import org.bukkit.entity.Player;

/**
 * Whenever you want to create a new requirement, you'll have to extend this
 * class.
 * Every requirement needs the following:
 * 
 * <p>
 * - Option to check if the requirement is optional.
 * <p>
 * - Results that will be performed when the requirement is completed. These
 * results have to be one of the results registered in Autorank.
 * <p>
 * - Option to check if the requirement will auto complete.
 * 
 * @author Staartvin
 * 
 */
public abstract class Requirement {

	private Autorank autorank;
	private boolean optional = false, autoComplete = false;
	private int reqId;
	private List<Result> results = new ArrayList<Result>();

	public final void setAutorank(final Autorank autorank) {
		this.autorank = autorank;
	}

	public final Autorank getAutorank() {
		return autorank;
	}

	/**
	 * Setup requirement specific objects.
	 * 
	 * This method is called when Autorank sets up its config.
	 * The requirement id, auto completion and optional values are assigned
	 * automatically.
	 * 
	 * @param options String[] containing values of the config
	 * @param optional Is this an optional requirement?
	 * @param results List<Result> containing results
	 * @param autoComplete Will this auto complete?
	 * @param reqId id of the requirement
	 * @return true if everything was setup correctly; false otherwise
	 */
	public abstract boolean setOptions(String[] options/*, boolean optional,
			List<Result> results, boolean autoComplete, int reqId*/);

	/**
	 * Does it meet the requirements?
	 * This method gets called when someone does /ar check or /ar complete.
	 * It should always contain the following line:
	 * 
	 * <p>
	 * if (isCompleted(getReqId(), player.getName())) { return true; }
	 * 
	 * @param player Player to check for
	 * @return true if it meets the requirements; false otherwise
	 */
	public abstract boolean meetsRequirement(Player player);

	/**
	 * Gets the description of the requirement
	 * Make sure this is always a translatable message.
	 * 
	 * @return string containing description (in locale language)
	 */
	public abstract String getDescription();

	/**
	 * Is this an optional requirement?
	 * (Not a main requirement)
	 * 
	 * @return true when optional; false otherwise.
	 */
	public boolean isOptional() {
		return optional;
	}

	/**
	 * Gets the results when this requirement is finished
	 * 
	 * @return A list of results that has to be done.
	 */
	public List<Result> getResults() {
		return results;
	}

	/**
	 * Use auto completion for this?
	 * 
	 * @return true when auto complete; false otherwise
	 */
	public boolean useAutoCompletion() {
		return autoComplete;
	}

	@Override
	public String toString() {
		return this.getClass().getSimpleName();
	}

	/**
	 * Check if the requirement is completed already.
	 * 
	 * @param reqID Requirement id.
	 * @param playerName Player to check for
	 * @return true if completed, false otherwise.
	 */
	public final boolean isCompleted(final int reqID, UUID uuid) {
		return autorank.getRequirementHandler().hasCompletedRequirement(reqID,
				uuid);
	}

	/**
	 * Gets the current progress of a player on a certain requirement.
	 * 
	 * @param player Player to check for
	 * @return String containing the progress
	 */
	public abstract String getProgress(Player player);

	/**
	 * Get the id of this requirement.
	 * This should get assigned automatically at setOptions().
	 * The id should always be dynamic.
	 * 
	 * @return id
	 */
	public int getReqId() {
		return reqId;
	}

	/**
	 * Get the current running stats plugin.
	 * 
	 * @return stats plugin that Autorank uses for stat data
	 */
	public StatsPlugin getStatsPlugin() {
		return autorank.getHookedStatsPlugin();
	}
	
	public void setReqId(int reqId) {
		this.reqId = reqId;
	}
	
	public void setOptional(boolean optional) {
		this.optional = optional;
	}
	
	public void setAutoComplete(boolean autoComplete) {
		this.autoComplete = autoComplete;
	}
	
	public void setResults(List<Result> results) {
		this.results = results;
	}
}
