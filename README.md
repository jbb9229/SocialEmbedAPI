
# SNS Embed?

>최근 기사나 블로그들을 보면 SNS의 게시글을 SNS에서 보는 것처럼 
그대로 가져온 것을 보신적 있으신가요?
프로젝트에 해당 기능을 추가하며 내용을 공유하고자 작성합니다.

![Example](https://images.velog.io/images/jbb9229/post/2e922545-6d6e-4e66-b2d6-1200ed9de0ae/localhost_8080_%20(4).png)

해당 기능은 모든 소셜에서 퍼가기 기능을 통해 HTML 코드를 반환하고 있습니다.
이번 포스트에서는 해당 기능을 소셜의 URL만을 이용하여 
HTML을 반환받는 API Server를 만들어볼 예정입니다.

각 소셜마다 공식 Document를 확인해보면 Embed API를 확인할 수 있습니다.

## Social API List
1. [Facebook](https://developers.facebook.com/docs/plugins/embedded-posts/?locale=ko_KR)
2. [Instagram](https://developers.facebook.com/docs/instagram/embedding/)
3. [Twitter](https://developer.twitter.com/en/docs/twitter-for-websites/embedded-tweets/overview)
4. [TicToc](https://developers.tiktok.com/doc/Embed)
<br/>

## Code Example
> 실제 프로젝트에서는 에디터의 발췌과정에서 URL을 분석하여
발췌 결과물을 소셜 게시물로 표현했지만 편의상 API의 형태로 작성하였습니다.

### Spring Boot Setting

예제 프로젝트에서 사용할 기술은 다음과 같습니다.

1. [Spring Boot Starter Web](https://www.javatpoint.com/spring-boot-starter-web)
	- Spring Boot에서 사용하는 Web Starter Project  입니다
    - Auto Configuration을 지원합니다
    - Spring MVC, REST 및 내장 Tomcat을 지원합니다.


2. [Jackson data-bind](https://github.com/FasterXML/jackson)
	- Jackson은 Java Object를 JSON으로 변환하거나 
      JSON을 Java Object로 변환하는데 사용하는 Java 라이브러리 입니다.

<br/>

해당 모듈의 의존성을 추가해줍니다.
 - build.gradle
```gradle
    implementation 'org.springframework.boot:spring-boot-starter-web'
    compile group: 'com.fasterxml.jackson.core', 
             name: 'jackson-databind', version: '2.8.8'version: '2.10.0'
```
 - pom.xml
```
   <dependency>
       <groupId>org.springframework.boot</groupId>
       <artifactId>spring-boot-starter-web</artifactId>
   </dependency>
   <dependency>
       <groupId>com.fasterxml.jackson.core</groupId>
       <artifactId>jackson-databind</artifactId>
       <version>2.8.8</version>
   </dependency>
```
