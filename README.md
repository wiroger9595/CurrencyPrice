# CurrencyPrice

A java project use JPA & UnitTest, base on SpringBoot.

### Environments

- JDK: 1.8
- SpringBoot: 2.7.2

##### application.properties

Change these properties to direct your h2 db or others.

./src/main/resources/application.yml

```properties
spring:
  h2:
    console:
      enabled: true
  datasource:s
    url: jdbc:h2:mem:dbtest
    driver-class-name: org.h2.Driver
    username: root
    password:
```

### H2 Schema

```two table CURRENCY_PRICE and CURRENCY_PRICE_CHINESE_NAME
CREATE TABLE CURRENCY_PRICE(
    ID INTEGER PRIMARY KEY AUTO_INCREMENT,
    CODE VARCHAR(3) NOT NULL DEFAULT '',
    SYMBOL VARCHAR(10) NOT NULL DEFAULT '',
    DESCRIPTION VARCHAR(255),
    RATE DECFLOAT(10) NOT NULL DEFAULT 0,
    CREATE_TIME TIMESTAMP NOT NULL DEFAULT Currency_TIMESTAMP,
    UPDATE_TIME TIMESTAMP NOT NULL DEFAULT Currency_TIMESTAMP,
    IS_SUSPEND VARCHAR(3) NOT NULL DEFAULT ''
);

CREATE TABLE CURRENCY_PRICE_CHINESE_NAME(
    ID INTEGER PRIMARY KEY AUTO_INCREMENT,
    CHINESE_CODE VARCHAR(10) NOT NULL DEFAULT '',
    CODE VARCHAR(3) NOT NULL DEFAULT '',
    RATE DECFLOAT(10) NOT NULL DEFAULT 0
);


CREATE INDEX IDX_CURRENCY_SYMBOL ON CURRENCY_PRICE(SYMBOL);

INSERT INTO CURRENCY VALUES(1, 'USD', '$', 'default', 28.3, '2022-03-28 09:00:00.000', '2022-03-28 09:00:00.000', 'N');
INSERT INTO CURRENCY VALUES(2, 'USA', '@', 'default', 5.432101234, '2022-03-28 09:00:00.000', '2022-03-28 09:00:00.000', 'N');






CREATE SEQUENCE CHRIS_CURRENCY_SEQUENCE START WITH 3 INCREMENT BY 1;
```

### The Script for Postman

Create environment parameter `base_url` before use.

```properties
# import file
{baseDir}/CallCoinDeskAPI/src/main/resources/CoinDesk.postman_collection.json
```

##### API Logic

- api.coindesk
- getAllInfo
- getDataById
- insertData
- updateData
- deleteData
- callCoinDesk

### Feature

- [ ] New API: getCurrencyList
- [ ] AOP manage controller's exceptions
- [ ] LoadingCache(?
- [ ] add ActionHistoryLog
- [ ] H2 in memory while testing
