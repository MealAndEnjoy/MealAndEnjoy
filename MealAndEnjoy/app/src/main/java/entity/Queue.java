package entity;

import java.util.List;

//队列类
public class Queue {

	private int queueId;
	private String qName;//队列名称（中卓B）
	
	private List<Numberr> quequ;
	private QueueList queueList;
	public int getQueueId() {
		return queueId;
	}
	public void setQueueId(int queueId) {
		this.queueId = queueId;
	}
	public String getqName() {
		return qName;
	}
	public void setqName(String qName) {
		this.qName = qName;
	}
	public List<Numberr> getQuequ() {
		return quequ;
	}
	public void setQuequ(List<Numberr> quequ) {
		this.quequ = quequ;
	}
	public QueueList getQueueList() {
		return queueList;
	}
	public void setQueueList(QueueList queueList) {
		this.queueList = queueList;
	}
	
	
}
