package cn.edu.bistu.newsdata;

import java.io.Serializable;
import java.util.ArrayList;

public class DetailNewsType implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/*
	 * property部分（文章）
	 * 
	 * docid: 文章id
	 * 
	 * docchannel: 栏目id
	 * 
	 * doctype: 文档类型
	 * 
	 * doctitle: 文档标题
	 * 
	 * subdoctitle: 文档子标题
	 * 
	 * docauthor: 文档作者
	 * 
	 * docsourcename:
	 * 
	 * docreltime:
	 * 
	 * opertime:
	 * 
	 * cruser:
	 * 
	 * crtime:
	 * 
	 * docabstract: 文章简介
	 * 
	 * dochtmlcon: 文章内容
	 */
	private String docid;
	private String docchannel;
	private String doctype;
	private String doctitle;
	private String subdoctitle;
	private String docauthor;
	private String docsourcename;
	private String docreltime;
	private String opertime;
	private String cruser;
	private String crtime;
	private String docabstract;
	private String dochtmlcon;
	private ArrayList<DetailResource> detailResources = new ArrayList<DetailResource>();

	public String getDocid() {
		return docid;
	}

	public void setDocid(String docid) {
		this.docid = docid;
	}

	public String getDocchannel() {
		return docchannel;
	}

	public void setDocchannel(String docchannel) {
		this.docchannel = docchannel;
	}

	public String getDoctype() {
		return doctype;
	}

	public void setDoctype(String doctype) {
		this.doctype = doctype;
	}

	public String getDoctitle() {
		return doctitle;
	}

	public void setDoctitle(String doctitle) {
		this.doctitle = doctitle;
	}

	public String getSubdoctitle() {
		return subdoctitle;
	}

	public void setSubdoctitle(String subdoctitle) {
		this.subdoctitle = subdoctitle;
	}

	public String getDocauthor() {
		return docauthor;
	}

	public void setDocauthor(String docauthor) {
		this.docauthor = docauthor;
	}

	public String getDocsourcename() {
		return docsourcename;
	}

	public void setDocsourcename(String docsourcename) {
		this.docsourcename = docsourcename;
	}

	public String getDocreltime() {
		return docreltime;
	}

	public void setDocreltime(String docreltime) {
		this.docreltime = docreltime;
	}

	public String getOpertime() {
		return opertime;
	}

	public void setOpertime(String opertime) {
		this.opertime = opertime;
	}

	public String getCruser() {
		return cruser;
	}

	public void setCruser(String cruser) {
		this.cruser = cruser;
	}

	public String getCrtime() {
		return crtime;
	}

	public void setCrtime(String crtime) {
		this.crtime = crtime;
	}

	public String getDocabstract() {
		return docabstract;
	}

	public void setDocabstract(String docabstract) {
		this.docabstract = docabstract;
	}

	public String getDochtmlcon() {
		return dochtmlcon;
	}

	public void setDochtmlcon(String dochtmlcon) {
		this.dochtmlcon = dochtmlcon;
	}

	public ArrayList<DetailResource> getDetailResources() {
		return detailResources;
	}

	public void setDetailResources(ArrayList<DetailResource> detailResources) {
		this.detailResources = detailResources;
	}

	@Override
	public String toString() {
		return "DetailNewsType [docid=" + docid + ", docchannel=" + docchannel
				+ ", doctype=" + doctype + ", doctitle=" + doctitle
				+ ", subdoctitle=" + subdoctitle + ", docauthor=" + docauthor
				+ ", docsourcename=" + docsourcename + ", docreltime="
				+ docreltime + ", opertime=" + opertime + ", cruser=" + cruser
				+ ", crtime=" + crtime + ", docabstract=" + docabstract
				+ ", dochtmlcon=" + dochtmlcon + ", detailResources="
				+ detailResources + "]";
	}

}
