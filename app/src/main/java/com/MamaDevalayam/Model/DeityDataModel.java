package com.MamaDevalayam.Model;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import org.json.JSONArray;

public class DeityDataModel {

	@SerializedName("deity_name")
	@Expose
	private String deityName;
	@SerializedName("deity_code")
	@Expose
	private String deityCode;
	@SerializedName("sub_deity_code")
	@Expose
	private String subDeityCode;
	@SerializedName("sub_deity_name")
	@Expose
	private String subDeityName;
	@SerializedName("child_deity_code")
	@Expose
	private String childDeityCode;
	@SerializedName("child_deity_name")
	@Expose
	private String childDeityName;

	private boolean isSelected;
	public boolean isSelected() {
		return isSelected;
	}

	public void setSelected(boolean selected) {
		isSelected = selected;
	}

	public String getDeityName() {
		return deityName;
	}

	public void setDeityName(String deityName) {
		this.deityName = deityName;
	}

	public String getDeityCode() {
		return deityCode;
	}

	public void setDeityCode(String deityCode) {
		this.deityCode = deityCode;
	}

	public String getSubDeityCode() {
		return subDeityCode;
	}

	public void setSubDeityCode(String subDeityCode) {
		this.subDeityCode = subDeityCode;
	}

	public String getSubDeityName() {
		return subDeityName;
	}

	public void setSubDeityName(String subDeityName) {
		this.subDeityName = subDeityName;
	}

	public String getChildDeityCode() {
		return childDeityCode;
	}

	public void setChildDeityCode(String childDeityCode) {
		this.childDeityCode = childDeityCode;
	}

	public String getChildDeityName() {
		return childDeityName;
	}

	public void setChildDeityName(String childDeityName) {
		this.childDeityName = childDeityName;
	}

	@Override
	public String toString() {
		return "DeityDataModel{" +
				"deityName='" + deityName + '\'' +
				", deityCode='" + deityCode + '\'' +
				", subDeityCode='" + subDeityCode + '\'' +
				", subDeityName='" + subDeityName + '\'' +
				", childDeityCode='" + childDeityCode + '\'' +
				", childDeityName='" + childDeityName + '\'' +
				'}';
	}
}
