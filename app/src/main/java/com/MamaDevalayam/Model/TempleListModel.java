package com.MamaDevalayam.Model;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class TempleListModel{

	@SerializedName("aa")
	private String aa;

	@SerializedName("data")
	private List<TempleListDataModel> data;

	@SerializedName("page")
	private int page;

	@SerializedName("total_pages")
	private int totalPages;

	@SerializedName("message")
	private String message;

	@SerializedName("status")
	private int status;

	public void setAa(String aa){
		this.aa = aa;
	}

	public String getAa(){
		return aa;
	}

	public void setData(List<TempleListDataModel> data){
		this.data = data;
	}

	public List<TempleListDataModel> getData(){
		return data;
	}

	public void setPage(int page){
		this.page = page;
	}

	public int getPage(){
		return page;
	}

	public void setTotalPages(int totalPages){
		this.totalPages = totalPages;
	}

	public int getTotalPages(){
		return totalPages;
	}

	public void setMessage(String message){
		this.message = message;
	}

	public String getMessage(){
		return message;
	}

	public void setStatus(int status){
		this.status = status;
	}

	public int getStatus(){
		return status;
	}

	@Override
	public String toString() {
		return "TempleListModel{" +
				"aa='" + aa + '\'' +
				", data=" + data +
				", page=" + page +
				", totalPages=" + totalPages +
				", message='" + message + '\'' +
				", status=" + status +
				'}';
	}
}