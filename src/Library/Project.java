package Library;

import javafx.beans.property.SimpleStringProperty;

public class Project {
	private final SimpleStringProperty name;
	private final SimpleStringProperty cdate;
	private final SimpleStringProperty mdate;

	public Project(String name, String cdate, String mdate) {
		this.name = new SimpleStringProperty(name);
		this.cdate = new SimpleStringProperty(cdate);
		this.mdate = new SimpleStringProperty(mdate);
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

}
