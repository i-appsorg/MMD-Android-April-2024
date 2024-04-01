package com.MamaDevalayam.Model;

import com.google.gson.annotations.SerializedName;

public class TempleListDataModel {

	@SerializedName("image")
	private String image;

	@SerializedName("country")
	private String country;

	@SerializedName("like_count")
	private int likeCount;

	@SerializedName("city")
	private String city;

	@SerializedName("following_count")
	private int followingCount;

	@SerializedName("name")
	private String name;

	@SerializedName("logo")
	private String logo;

	@SerializedName("id")
	private String id;

	@SerializedName("state")
	private String state;

	@SerializedName("temple_id")
	private String templeId;

	@SerializedName("zip_code")
	private String zipCode;

	public void setImage(String image){
		this.image = image;
	}

	public String getImage(){
		return image;
	}

	public void setCountry(String country){
		this.country = country;
	}

	public String getCountry(){
		return country;
	}

	public void setLikeCount(int likeCount){
		this.likeCount = likeCount;
	}

	public int getLikeCount(){
		return likeCount;
	}

	public void setCity(String city){
		this.city = city;
	}

	public String getCity(){
		return city;
	}

	public void setFollowingCount(int followingCount){
		this.followingCount = followingCount;
	}

	public int getFollowingCount(){
		return followingCount;
	}

	public void setName(String name){
		this.name = name;
	}

	public String getName(){
		return name;
	}

	public void setLogo(String logo){
		this.logo = logo;
	}

	public String getLogo(){
		return logo;
	}

	public void setId(String id){
		this.id = id;
	}

	public String getId(){
		return id;
	}

	public void setState(String state){
		this.state = state;
	}

	public String getState(){
		return state;
	}

	public void setTempleId(String templeId){
		this.templeId = templeId;
	}

	public String getTempleId(){
		return templeId;
	}

	public void setZipCode(String zipCode){
		this.zipCode = zipCode;
	}

	public String getZipCode(){
		return zipCode;
	}

	@Override
	public String toString() {
		return "DataItem{" +
				"image='" + image + '\'' +
				", country='" + country + '\'' +
				", likeCount=" + likeCount +
				", city='" + city + '\'' +
				", followingCount=" + followingCount +
				", name='" + name + '\'' +
				", logo='" + logo + '\'' +
				", id='" + id + '\'' +
				", state='" + state + '\'' +
				", templeId='" + templeId + '\'' +
				", zipCode='" + zipCode + '\'' +
				'}';
	}
}