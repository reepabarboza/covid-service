DROP TABLE IF EXISTS covid;

CREATE TABLE COVID (
  id INT AUTO_INCREMENT  PRIMARY KEY,
  city VARCHAR(250) DEFAULT NULL,
  province VARCHAR(250) DEFAULT NULL,
  country VARCHAR(250) NOT NULL,
  lastUpdate VARCHAR(250) DEFAULT NULL,
  keyId VARCHAR(250) DEFAULT NULL,
  deaths VARCHAR(250) DEFAULT NULL,
  recovered VARCHAR(250) DEFAULT NULL,
  createdDate DATE NOT NULL
);