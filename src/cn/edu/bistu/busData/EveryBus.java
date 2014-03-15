package cn.edu.bistu.busData;

public class EveryBus {
	private String catBusId;
	private String busName;
	private String departTime;
	private String returnTime;
	private String busUntro;
	private BusLine busLine;

	public EveryBus() {
		super();
		// TODO Auto-generated constructor stub
	}

	public EveryBus(String catBusId, String busName, String departTime,
			String returnTime, String busUntro, BusLine busLine) {
		super();
		this.catBusId = catBusId;
		this.busName = busName;
		this.departTime = departTime;
		this.returnTime = returnTime;
		this.busUntro = busUntro;
		this.busLine = busLine;
	}

	@Override
	public String toString() {
		return "EveryBus [catBusId=" + catBusId + ", busName=" + busName
				+ ", departTime=" + departTime + ", returnTime=" + returnTime
				+ ", busUntro=" + busUntro + ", busLine=" + busLine + "]";
	}

	public String getCatBusId() {
		return catBusId;
	}

	public void setCatBusId(String catBusId) {
		this.catBusId = catBusId;
	}

	public String getBusName() {
		return busName;
	}

	public void setBusName(String busName) {
		this.busName = busName;
	}

	public String getDepartTime() {
		return departTime;
	}

	public void setDepartTime(String departTime) {
		this.departTime = departTime;
	}

	public String getReturnTime() {
		return returnTime;
	}

	public void setReturnTime(String returnTime) {
		this.returnTime = returnTime;
	}

	public String getBusUntro() {
		return busUntro;
	}

	public void setBusUntro(String busUntro) {
		this.busUntro = busUntro;
	}

	public BusLine getBusLine() {
		return busLine;
	}

	public void setBusLine(BusLine busLine) {
		this.busLine = busLine;
	}

}
