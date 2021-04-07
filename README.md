# SysCollector

### Configuration
> 1. 최상위 요소의 hsot에 설치되어 있는 IP 입력
> 2. system-collect 요소에 각 수집 항목의 enable 여부 지정
>> cpuEnabled, memoryEnabled, diskEnabled, networkEnabled, engineEnabled
> 3. disk, network 활성화 시, 수집할 디렉토리명 및 IP Address 기입
>> * dirNameList > dirName
>> * ipAddressNameList > ipAddressName
> 4. process-collect 옵션을 사용하여 프로세스명 혹은 -Dname 옵션에 해당하는 프로세스 정보 수집
>> * process-collect > process
> 5. store를 이용하여 수집된 데이터를 데이터 스토어에 저장
>> * 해당 요소의 class를 지정
>> * com.havving.system.domain.xml.EsStore


### Java Options
> * CLASSPATH 지정 시, Sigar 라이브러리를 지정해 주어야 함
> * -Dlog.root.level 설정으로 루트 로그 레벨 지정 가능 (info, warn 등...)
> * Command Mode 지원
>> * java -jar syscollector-{VERSION}.jar {options} 사용
>> * --help 혹은 -h 명령어를 통해 해당 예약어 및 동작 확인 가능
>> * com.havving.Printer 내에서 소스 코드 확인 가능


### Store Type
> Store 타입은 EsStore, RestStore, JpaStore 세 종류로 가능
> #### 1. EsStore (Elasticsearch Store)
>> * 설치되어 있는 ES에 해당 데이터 별 index를 생성하여 host별 type에 데이터를 저장함
>> * destination-port : 저장할 포트 지정 (http)
>> * master-ip : 저장할 IP
>> * ttl (optional) : 데이터를 유지할 기간
>> * indexPrefix : 각 데이터의 타입명 이전에 부여할 인덱스의 명칭

> #### 2. RestStore (JSON request send)
>> * 특정 웹 페이지로 JSON 형태의 데이터 전달
>> * url : 데이터를 전송할 url

> #### 3. JpaStore (DB Store)
>> * 기 설치되어 있는 DB에 해당 타입의 데이터 저장
>> * hibernate-path : hibernate configuration 파일 경로 지정
>> * ddl 기능을 활성화 하지 않을 경우, db/ddl.sql 파일을 실행시켜야 함
>> * 제공되는 SQL의 경우, MySQL을 기준으로 작성되어 있음