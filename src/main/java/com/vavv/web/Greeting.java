package com.vavv.web;

/**
 *  * Created by sbollam on 11/9/17.
 *   */
public class Greeting {
	private long id;
	private String text;

	public Greeting(long id, String text) {
		this.id = id;
		this.text = text;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (!(o instanceof Greeting)) return false;

		Greeting greeting = (Greeting) o;

		if (id != greeting.id) return false;
		return text != null ? text.equals(greeting.text) : greeting.text == null;
	}

	@Override
	public int hashCode() {
		int result = (int) (id ^ (id >>> 32));
		result = 31 * result + (text != null ? text.hashCode() : 0);
		return result;
	}
}

