package com.github.khandroid.http.misc;

import java.util.Date;

import android.os.Parcel;
import android.os.Parcelable;


public class ParcelableCookie implements Parcelable {
	private String name;
	private String value;
	private String domain;
	private String path;
	private int version;
	private Date expiryDate;


	public ParcelableCookie() {
		super();
	}


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public String getValue() {
		return value;
	}


	public void setValue(String value) {
		this.value = value;
	}


	public String getDomain() {
		return domain;
	}


	public void setDomain(String domain) {
		this.domain = domain;
	}


	public String getPath() {
		return path;
	}


	public void setPath(String path) {
		this.path = path;
	}


	public int getVersion() {
		return version;
	}


	public void setVersion(int version) {
		this.version = version;
	}

	
	public Date getExpiryDate() {
		return expiryDate;
	}


	public void setExpiryDate(Date expiryDate) {
		this.expiryDate = expiryDate;
	}


	@Override
	public int describeContents() {
		return 0;
	}


	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeString(name);
		dest.writeString(value);
		dest.writeString(domain);
		dest.writeString(path);
		dest.writeInt(version);
		if (expiryDate != null) {
			dest.writeLong(expiryDate.getTime());
		} else {
			dest.writeLong(-1);
		}
	}

	
	public static final Parcelable.Creator<ParcelableCookie> CREATOR = new Parcelable.Creator<ParcelableCookie>() {
		public ParcelableCookie createFromParcel(Parcel in) {
			return new ParcelableCookie(in);
		}


		public ParcelableCookie[] newArray(int size) {
			return new ParcelableCookie[size];
		}
	};


	private ParcelableCookie(Parcel in) {
		name = in.readString();
		value = in.readString();
		domain = in.readString();
		path = in.readString();
		version = in.readInt();
		long rawExpiryDate = in.readLong();
		if (rawExpiryDate != -1) {
			expiryDate = new Date(in.readLong());
		} else {
			expiryDate = null;
		}
	}
}
