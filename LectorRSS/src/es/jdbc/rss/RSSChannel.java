package es.jdbc.rss;

import java.util.HashMap;
import java.util.List;

/**
 * 
 * @author jdbc
 *
 */
public class RSSChannel {

	protected String title;
	protected String link;
	protected String description;
	protected String pubDate;
	protected List<HashMap<String, String>> itemList;
	
	public String getTitle() {
		return title;
	}
	
	public void setTitle(String title) {
		this.title = title;
	}
	
	public String getLink() {
		return link;
	}
	
	public void setLink(String link) {
		this.link = link;
	}
	
	public String getDescription() {
		return description;
	}
	
	public void setDescription(String description) {
		this.description = description;
	}
	
	public String getPubDate() {
		return pubDate;
	}

	public void setPubDate(String pubDate) {
		this.pubDate = pubDate;
	}

	public List<HashMap<String, String>> getItemList() {
		return itemList;
	}
	
	public void setItemList(List<HashMap<String, String>> itemList) {
		this.itemList = itemList;
	}
	
	
}
