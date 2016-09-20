package com.btcc.wsm.util;

public enum SystemAuditTrailLevel {
	INFO(0, "INFO"), ERROR(1, "ERROR");
	
	private String level;
	private int levelInt;
	
	private SystemAuditTrailLevel(int levelInt, String level) {
		this.level = level;
		this.levelInt = levelInt;
	}
	
	public String getLevel() {
		return level;
	}
	
	public int getLevelInt() {
		return levelInt;
	}

}
