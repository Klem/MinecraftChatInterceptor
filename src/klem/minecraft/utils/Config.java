package klem.minecraft.utils;

import java.util.Properties;

public class Config {

	private String url;
	private int purgeHistoryThreshold;
	private int maxDisplayedMessages;
	private boolean autoSavePurge;
	private long refreshTime;
	private boolean debugEnabled;
	private final Properties config;
	public Config() {
		this.config = PropertiesManager.getConfig();
	}

	public String getUrl() {
		return url = (String) config.get("URL");
	}

	public int getPurgHistoryThreshold() {
		return purgeHistoryThreshold = Integer.valueOf(config.getProperty("PURGE_HISTORY_TRESHOLD"));
	}

	public int getMaxDisplayedMessages() {
		return maxDisplayedMessages = Integer.valueOf(config.getProperty("MAX_DISPLAYED_MESSAGES"));
	}

	public boolean isAutoSavePurge() {
		return autoSavePurge = new Boolean(config.getProperty("AUTOSAVE_ON_PURGE"));
	}

	public long getRefreshTime() {
		return refreshTime = Long.valueOf(config.getProperty("REFRESH_TIME"));
	}

	public boolean isDebugEnabled() {

		return debugEnabled = new Boolean(config.getProperty("DEBUG_ENABLED"));
	}

	public Properties getConfig() {
		return config;
	}

	public void printProperties() {
		System.out.println("URL : " + getUrl() + "\nPURGE_HISTORY_TRESHOLD : " + getPurgHistoryThreshold()
				+ "\nMAX_DISPLAYED_MESSAGES : " + getMaxDisplayedMessages() + "\nAUTOSAVE_ON_PURGE : "
				+ isAutoSavePurge() + "\nREFRESH_TIME : " + getRefreshTime() + "\nDEBUG_ENABLED : " + isDebugEnabled());
	}
}
