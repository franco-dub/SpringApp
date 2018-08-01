package it.unisalento.se.saw.dto;

import java.util.Date;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import javax.validation.constraints.Pattern;

public class PersonDto {
	@NotNull
    private String firstName;
	@NotNull
    private String lastName;
	@NotNull
    private String email;
	@NotNull
    private String phone;
	@NotNull
	@Past
    private Date dateOfBirth;
	@NotNull
	@Pattern(regexp="[mfMF]")
    private String gender;
	@NotNull
    private String password;
	
	public PersonDto(@NotNull String firstName, @NotNull String lastName, @NotNull String email, @NotNull String phone,
			@NotNull @Past Date dateOfBirth, @NotNull @Pattern(regexp = "[mfMF]") String gender,
			@NotNull String password) {
		super();
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.phone = phone;
		this.dateOfBirth = dateOfBirth;
		this.gender = gender;
		this.password = password;
	}

		protected PersonDto() {}

		public String getFirstName() {
			return firstName;
		}

		public void setFirstName(String firstName) {
			this.firstName = firstName;
		}

		public String getLastName() {
			return lastName;
		}

		public void setLastName(String lastName) {
			this.lastName = lastName;
		}

		public String getEmail() {
			return email;
		}

		public void setEmail(String email) {
			this.email = email;
		}

		public String getPhone() {
			return phone;
		}

		public void setPhone(String phone) {
			this.phone = phone;
		}

		public Date getDateOfBirth() {
			return dateOfBirth;
		}

		public void setDateOfBirth(Date dateOfBirth) {
			this.dateOfBirth = dateOfBirth;
		}

		public String getGender() {
			return gender;
		}

		public void setGender(String gender) {
			this.gender = gender;
		}

		public String getPassword() {
			return password;
		}

		public void setPassword(String password) {
			this.password = password;
		}
		
		
}
