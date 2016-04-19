package Library;

import javafx.beans.property.SimpleStringProperty;

public class Project {
	private final SimpleStringProperty name; // Project Name
	private final SimpleStringProperty cdate;// Created Date
	private final SimpleStringProperty mdate; // Modified Date
	private final SimpleStringProperty wspace; // Work Space

	public Project(String name, String cdate, String mdate, String wspace) {
		this.name = new SimpleStringProperty(name);
		this.cdate = new SimpleStringProperty(cdate);
		this.mdate = new SimpleStringProperty(mdate);
		this.wspace = new SimpleStringProperty(wspace);
	}

	public String getName() {
		return name.get();
	}

	public void setName(String hname) {
		name.set(hname);
	}

	public String getCdate() {
		return cdate.get();
	}

	public void setCdate(String cdateS) {
		cdate.set(cdateS);
	}

	public String getMdate() {
		return mdate.get();
	}

	public void setMdate(String mdateS) {
		mdate.set(mdateS);
	}
	
	public String getWspace() {
		return wspace.get();
	}

	public void setWspace(String wspaceS) {
		wspace.set(wspaceS);
	}

}
