## 1. 돈 뿌리기 API Server 프로젝트 구성 

 - java 1.8
 - spring boot
 - jpa
 - MY-SQL



## 2. 돈 뿌리기 Kafka Consumer 프로젝트 구성 

 - java 1.8
 - spring boot, srping boot kafka
 - mybatis
 - MY-SQL



## 3. API 정보

### 3.1 돈 뿌리기 API

	요청 URL : /v1/api/divide
	요청 Type : POST
	요청 request param
	    - header : X-USER-ID : mvmdkiem@naver.com, X-ROOM-ID : T100212
	    - body : 
	    {
	    	"money" : "20000",
	    	"count" : "3"
	    }
	
	    - X-USER-ID : 사용자 id
	    - X-ROOM-ID : 대화방 id 
	    - money : 뿌릴 금액
	    - count : 뿌릴 인원
	
	
	응답값 :
		{
		    "tokken": token_value, 
		    "resultCode": "200",
		    "resultMsg": "정상 처리 되었습니다."
		}
		 - tokken : 신규 발급된 tokken (3자리 수 글자)
		 - resultCode : 200 성공, 500 실패 
		 - resultMsg : response 응답 메세지 
### 3.2 돈 받기 API

	요청 URL : /v1/api/divide
		요청 Type : PUT
		요청 request param
		 - header : X-USER-ID : mvmdkiem@.com, X-ROOM-ID : T100212
		 - body : 
		 		{
				    "tokken": token_value
				}
	
		 - X-USER-ID : 사용자 id
		 - X-ROOM-ID : 대화방 id 
		 - tokken : 요청 tokken
	응답값 : 
		{
		    "resultCode": "200",
		    "resultMsg": "6522원을 줍줍 하였습니다. 현재 당신의 카카오 페이는 총 35057원 입니다."
		}
		 - resultCode : 200 성공, 500 실패 
		 - resultMsg : response 응답 메세지 
### 3.3 조회 API

	 요청 URL : /v1/api/divide
		요청 Type : GET
		요청 request param
		 - header : X-USER-ID : test1@naver.com, X-ROOM-ID : T100212
		 - body : 
		 		{
				    "tokken": token_value
				}
	
		 - X-USER-ID : 사용자 id
		 - X-ROOM-ID : 대화방 id 
		 - tokken : 요청 tokken
	

	응답값 :
		{
		    "giveMoney": "20000",
		    "totalMoney": "20000",
		    "resultCode": "200",
		    "regDate": "2021년 03월 02일 06시 08분 38초",
		    "giveList": [
		        {
		            "money": "5952",
		            "userId": "test2@naver.com"
		        },
		        {
		            "money": "7024",
		            "userId": "test1@naver.com"
		        },
		        {
		            "money": "7024",
		            "userId": "test3@naver.com"
		        }
		    ],
		    "resultMsg": "정상 처리 되었습니다."
		}
	
	 - totalMoney : 뿌린 돈의 총액
	 - giveMoney : 뿌린 돈 중 다른 사람들이 주어간 돈의 총액
	 - regDate : 요청 시간 
	 - giveList : 개개인이 가져간 돈 금액, 가져간 user의 id 목록
	 - resultCode : 200 성공, 500 실패 
	 - resultMsg : response 응답 메세지 

## 4 Kafka 구성 

 - Kafka 설치 경로 : C:\Dev\kafka
 - 주키퍼 (window 실행) : C:\Dev\kafka\bin\windows\zookeeper-server-start.bat C:\Dev\kafka\config\zookeeper.properties 
 - Kafka (window 실행) : C:\Dev\kafka\bin\windows\kafka-server-start.bat C:\Dev\kafka\config\server.properties 
 - Topic 생성 : C:\Dev\kafka\bin\windows\kafka-topics.bat --create --zookeeper localhost:2181 --replication-factor 1 --partitions 1 --topic kakaopay
 - Topic 확인 : C:\Dev\kafka\bin\windows\kafka-topics.bat --list --zookeeper localhost:2181
 - consumer-group 확인 (kakaopay-consumer-group) : kafka-consumer-groups.bat --bootstrap-server localhost:9093,localhost:9094,localhost:9095 --list
 - produce 실행 : C:\Dev\kafka\bin\windows\kafka-console-producer.bat --broker-list localhost:9092 --topic kakaopay
 - consumer 실행 : C:\Dev\kafka\bin\windows\kafka-console-consumer.bat --bootstrap-server localhost:9092 --topic kakaopay --from-beginning
 - topic : kakaopay
 - consumer-group : kakaopay-consumer-group

ex) apache-kafka 관련 정보 : https://kafka.apache.org/downloads (kafka version : 2.6.1)
ex) spring-kafka 관련 정보 : https://docs.spring.io/spring-kafka/reference/html/#update-deps



## 5. MYSQL 구성

### 5.1 대화방 정보 테이블

CREATE TABLE `chatroom` (
	  `_id` int NOT NULL AUTO_INCREMENT,
	  `chat_room_id` varchar(255) NOT NULL,
	  `chat_room_title` varchar(255) NOT NULL,
	  `chat_room_status` varchar(4) NOT NULL,
	  PRIMARY KEY (`_id`)
	) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;



### 5.2 대화방 사용자 정보 테이블

CREATE TABLE `chatroommember` (
	  `_id` int NOT NULL AUTO_INCREMENT,
	  `user_id` varchar(255) DEFAULT NULL,
	  `user_name` varchar(10) DEFAULT NULL,
	  `user_money` decimal(15,0) DEFAULT NULL,
	  `chat_room_id` varchar(255) DEFAULT NULL,
	  PRIMARY KEY (`_id`)
	) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8;



### 5.3 돈 뿌리기 정보 테이블

CREATE TABLE `dividemoney` (
	  `_id` int NOT NULL AUTO_INCREMENT,
	  `order_no` int DEFAULT NULL,
	  `count` int DEFAULT NULL,
	  `money` decimal(15,0) DEFAULT NULL,
	  `room_id` varchar(255) DEFAULT NULL,
	  `user_id` varchar(255) DEFAULT NULL,
	  `user_name` varchar(45) DEFAULT NULL,
	  `user_money` decimal(15,0) DEFAULT NULL,
	  `reg_date` datetime DEFAULT NULL,
	  `update_date` datetime DEFAULT NULL,
	  `group_id` varchar(255) DEFAULT NULL,
	  PRIMARY KEY (`_id`)
	) ENGINE=InnoDB AUTO_INCREMENT=64 DEFAULT CHARSET=utf8;



## 6. 평가항목

### 6.1 프로젝트 구성 방법 및 관련된 시스템 아키텍쳐 설계 방법이 적절한가?

 - 아키텍처 설계 방법에 대해서 많은 고민을 하였습니다. 
 - JDK 1.8 및 Spring Boot 2 선택 : 3월 1일 ~ 2일간이라는 촉박한 개발 기간 동안 만들기에 가장 안정적인 버전을 선택하였습니다. 
 - DB 선택 : 처음에는 MongoDB로 선택을 하였으나, CRU 작업이 빈번하게 이루어지는 프로젝트이기 때문에 MY-SQL로 전환하였습니다. 
 - 뿌리기 API  : 
	1. 한번의 생성 요청으로 복수의 Insert 요청이 이루어지기 때문에 Kafka Consumer 서버에서 Mybatis로 Insert 작업을 이루어지도록 처리했습니다. 
	2. 토큰의 자리수를 3자리로 정리하는 요구사항으로 String.length = 3 으로 이해하여 tokken 압축 기술까지 알아 봤으나 문맥을 잘못 이해했다는 사실을 깨달았습니다.
	3. 카카오 페이의 실제 돈 뿌리기 금액의 제한이 200만원이라서 BigInteger나 Long으로 처리할까 고민하였지만 차후 확장성을 고려하여 BigDecimal로 처리 하였습니다. 
	4. 랜덤 금액 생성을 확인해보니 보낸 돈의 9프로이내 ~ 9프로이상으로 이루어지는 것으로 확인하여 그 사이에 랜덤 금액이 생성되도록 처리 하였습니다.

 - 받기 API  : 한번의 수정 요청으로 복수의 조회 및 수정 요청이 이루어지지만, 실시간성 response를 요구하기 때문에 해당 API 서버에서 JPA로 처리되도록 처리했습니다. 
 - 조회 API  : 한번의 조회 요청으로 간편한 인증 절차 이후 처리가 가능했기 때문에 해당 API 서버에서 처리 되도록 JPA로 처리했습니다.



### 6.2 요구사항을 잘 이해하고 구현하였는가?

 - 요구 기한 : 주어진 요구 기한을 맞추도록 노력하였습니다.
 - 뿌리기 API 요구 사항 : 별다른 문제 없이 요구사항들을 구현하였습니다.
 - 받기 API 요구 사항 : 별다른 문제 없이 요구사항들을 구현하였습니다.
 - 조회 API 요구 사항 : 별다른 문제 없이 요구사항들을 구현하였습니다.



### 6.3 작성한 어플리케이션 코드가 간결하고 가독성 좋게 작성되었는가?

- 최대한 간결하고 가독성 있도록 코드를 짜도록 노력하였습니다. 



### 6.4 어플레케이션은 다량의 트래픽에도 무리가 없도록 효율적으로 작성되었는가?

 - API 서버 : 다량의 트래픽을 버틸 수 있는지 JMeta로 테스트 완료 하였으며, 부하가 발생하는 구간은 찾지 못했습니다. 
 - Kafka Consumer 서버 : 다량을 트래픽을 버틸 수 있는지 Jmeta로 API 서버를 호출하여 다량의 돈 뿌리기 이력을 생성하도록 테스트 하였으며, 부하가 발생하는 구간은 찾지 못했습니다. 
 - 서버 메모리 캐쉬나 Redis 연동까지도 생각을 하였으나, 메모리 서버간의 동기화 문제를 개발 기간에 맞추기에는 너무 촉박했기 때문에 구현에서 제외 하였습니다.
 - Spring Cloud로 쿠버 네티스 도커 도입 설정까지도 생각을 했으나, 역시... 개발 기간에 맞추기에 너무 촉박했기 때문에 반려하였습니다.
