DROP TABLE IF EXISTS location_feature;
DROP TABLE IF EXISTS feature;
DROP TABLE IF EXISTS review;
DROP TABLE IF EXISTS user;
DROP TABLE IF EXISTS special;
DROP TABLE IF EXISTS location;


CREATE TABLE location (
	location_id BIGINT NOT NULL AUTO_INCREMENT,
	business_name varchar(256) NOT NULL,
	street_address varchar(128) NOT NULL,
	city varchar(60),
	state varchar(40),
	zip varchar(20),
	phone varchar(30),
	PRIMARY KEY (location_id)
	
);

CREATE TABLE special (
	specials_id BIGINT NOT NULL AUTO_INCREMENT,
	location_id BIGINT NULL,
	special varchar(256) NOT NULL,
	price decimal(10,2) NOT NULL,
	day varchar(10),
	hours varchar(40),
	day_int int,
	PRIMARY KEY (specials_id),
	FOREIGN KEY (location_id) REFERENCES location (location_id) ON DELETE CASCADE
	
);

CREATE TABLE user (
	user_id BIGINT NOT NULL AUTO_INCREMENT,
	first_name varchar(60) NOT NULL,
	last_name varchar(60) NOT NULL,
	email varchar(60) NOT NULL,
	phone varchar(30),
	birthday varchar(5),
	PRIMARY KEY (user_id)
	
);

CREATE TABLE review (
	review_id BIGINT NOT NULL AUTO_INCREMENT,
	user_id BIGINT NULL,
	location_id BIGINT NULL,
	review varchar(500),
	PRIMARY KEY (review_id),
	FOREIGN KEY (location_id) REFERENCES location (location_id) ON DELETE CASCADE,
	FOREIGN KEY (user_id) REFERENCES user (user_id) ON DELETE CASCADE
	
);

CREATE TABLE feature (
	feature_id BIGINT NOT NULL AUTO_INCREMENT,
	feature_name varchar(256) NOT NULL,
	PRIMARY KEY (feature_id)
	
);

CREATE TABLE location_feature (
	location_id BIGINT NOT NULL,
	feature_id BIGINT NOT NULL,
	FOREIGN KEY (location_id) REFERENCES location(location_id) ON DELETE CASCADE,
	FOREIGN KEY (feature_id) REFERENCES feature(feature_id) ON DELETE CASCADE
);