package com.acs.activitytracker.dto;

/**
 * @author akhil.reddy
 *
 */
public class DashboardSummary {
	private int onTrack;
	private int delay;
	private int fallenBack;
	private int notAccepted;

	public DashboardSummary(int onTrack, int delay, int fallenBack, int notAccepted) {
		this.onTrack = onTrack;
		this.delay = delay;
		this.fallenBack = fallenBack;
		this.notAccepted = notAccepted;
	}

	public int getOnTrack() {
		return onTrack;
	}

	public void setOnTrack(int onTrack) {
		this.onTrack = onTrack;
	}

	public void incrementOnTrack() {
		this.onTrack += 1;
	}

	public int getDelay() {
		return delay;
	}

	public void setDelay(int delay) {
		this.delay = delay;
	}

	public void incrementDelay() {
		this.delay += 1;
	}

	public int getFallenBack() {
		return fallenBack;
	}

	public void setFallenBack(int fallenBack) {
		this.fallenBack = fallenBack;
	}

	public void incrementFallenBack() {
		this.fallenBack += 1;
	}

	public int getNotAccepted() {
		return notAccepted;
	}

	public void setNotAccepted(int notAccepted) {
		this.notAccepted = notAccepted;
	}

	public void incrementNotAccepted() {
		this.notAccepted += 1;
	}

}
