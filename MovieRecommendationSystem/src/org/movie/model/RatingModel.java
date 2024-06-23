package org.movie.model;

public class RatingModel {

	private int revId;
	private int umId;
	private float rating;
	private String text;

	public int getRevId() {
		return revId;
	}

	public void setRevId(int revId) {
		this.revId = revId;
	}

	public int getUmId() {
		return umId;
	}

	public void setUmId(int umId) {
		this.umId = umId;
	}

	public float getRating() {
		return rating;
	}

	public void setRating(float rating) {
		this.rating = rating;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

}
