package com.MamaDevalayam.Model;

import java.util.List;
import com.google.gson.annotations.SerializedName;
import com.google.gson.annotations.Expose;

public class GetParmsModel {

	@SerializedName("status")
	@Expose
	private Integer status;
	@SerializedName("message")
	@Expose
	private String message;
	@SerializedName("data")
	@Expose
	private List<GetParmsDataModel> data;

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

	public List<GetParmsDataModel> getData() {
		return data;
	}

	public void setData(List<GetParmsDataModel> data) {
		this.data = data;
	}

	@Override
	public String toString() {
		return "GetParmsModel{" +
				"status=" + status +
				", message='" + message + '\'' +
				", data=" + data +
				'}';
	}
}