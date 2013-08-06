package com.tuna.idchk.db.api;

import java.util.Date;

public class IdRecord {
	public Long id;
	public String feedName;
	public String citizenId;
	public String passportId;
	public String name;
	public String surname;
	public Date birthDate;
	public String nationality;
	public String country;
	public String city;
	public GenderEnum gender;
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("IdRecord [id=").append(id).append(", feedName=")
				.append(feedName).append(", citizenId=").append(citizenId)
				.append(", passportId=").append(passportId).append(", name=")
				.append(name).append(", surname=").append(surname)
				.append(", birthDate=").append(birthDate)
				.append(", nationality=").append(nationality)
				.append(", country=").append(country).append(", city=")
				.append(city).append(", gender=").append(gender).append("]");
		return builder.toString();
	}
}
