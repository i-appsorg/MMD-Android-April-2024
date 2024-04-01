package com.MamaDevalayam.Model;

import com.google.gson.annotations.SerializedName;
import com.google.gson.annotations.Expose;

	public class GetParmsDataModel {

	@SerializedName("ID")
	@Expose
	private String id;
	@SerializedName("meta_key")
	@Expose
	private String metaKey;
	@SerializedName("meta_value")
	@Expose
	private String metaValue;
	@SerializedName("type")
	@Expose
	private String type;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getMetaKey() {
		return metaKey;
	}

	public void setMetaKey(String metaKey) {
		this.metaKey = metaKey;
	}

	public String getMetaValue() {
		return metaValue;
	}

	public void setMetaValue(String metaValue) {
		this.metaValue = metaValue;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

		@Override
		public String toString() {
			return "GetParmsDataModel{" +
					"id='" + id + '\'' +
					", metaKey='" + metaKey + '\'' +
					", metaValue='" + metaValue + '\'' +
					", type='" + type + '\'' +
					'}';
		}
	}