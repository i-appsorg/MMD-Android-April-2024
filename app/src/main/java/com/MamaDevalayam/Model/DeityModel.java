package com.MamaDevalayam.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;
public class DeityModel {

	@SerializedName("status")
	@Expose
	private Integer status;
	@SerializedName("message")
	@Expose
	private String message;
	@SerializedName("page")
	@Expose
	private Integer page;
	@SerializedName("total_pages")
	@Expose
	private Integer totalPages;
	@SerializedName("data")
	@Expose
	private List<DeityDataModel> data;

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Integer getPage() {
		return page;
	}

	public void setPage(Integer page) {
		this.page = page;
	}

	public Integer getTotalPages() {
		return totalPages;
	}

	public void setTotalPages(Integer totalPages) {
		this.totalPages = totalPages;
	}

	public List<DeityDataModel> getData() {
		return data;
	}

	public void setData(List<DeityDataModel> data) {
		this.data = data;
	}

	@Override
	public String toString() {
		return "DeityModel{" +
				"status=" + status +
				", message='" + message + '\'' +
				", page=" + page +
				", totalPages=" + totalPages +
				", data=" + data +
				'}';
	}
}