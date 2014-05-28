package com.example.groupdata;

public class GroupClass {
  
	private String userid;
	private String type;
	private String group;
	 public GroupClass() {
		// TODO Auto-generated constructor stub
		 super();
	}
	 public GroupClass(String userid,String type,String group){
		 this.userid=userid;
		 this.type=type;
		 this.group=group;
	 }
	 public void setId(String userid){
		 this.userid=userid;
	 }
	 public void setType(String type){
		 this.type=type;
	 }
	 public void setGroup(String group){
		 this.group=group;
	 }
	 public String getId(){
		 return userid;
	 }
	 public String getType(){
		return type;
	}
	 public String getGroup(){
		return group;
		 
	 }
	 
	 
}

	