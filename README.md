# 푸디 관리자 앱 👷‍

## 🤔 프로젝트 설명
> 식단관리를 간편하게!! 내손안에 식단 플래너 푸디(FOODIary)입니다. <br>
> 해당 앱은 푸디에서 사용하는 Back-end 어플리케이션의 관리를 위해 만든 관리자 앱입니다.<br><br>
> 현재 server에 배포를 하지 않아 정상적인 기능 사용을 위해서는 dJango 설치 후 <br>
> local에서 back-end 어플리케이션을 실행해주어야 합니다. (/backend/FooDi_명세서.txt참고)

### 💻 기술스택 
#### ▪️ Client
<p>
<img src="https://img.shields.io/badge/Android-3DDC84?style=for-the-badge&logo=Android&logoColor=white">
<img src="https://img.shields.io/badge/Kotlin-7F52FF?style=for-the-badge&logo=Kotlin&logoColor=white"
<img src="https://img.shields.io/badge/MVVM-0F9D58?style=for-the-badge&logo=&logoColor=white">
</p>

#### ▪️ Server
<p>
<img src="https://img.shields.io/badge/Python-3776AB?style=for-the-badge&logo=Python&logoColor=white">
<img src="https://img.shields.io/badge/dJango-092E20?style=for-the-badge&logo=Django&logoColor=white">
<img src="https://img.shields.io/badge/SQLite-003B57?style=for-the-badge&logo=SQLite&logoColor=white">
</p>
<br>

### 🛠 구현 사항
##### 1️⃣ 음식 검색하기
###### Rest 통신을 통한 서버에 저장된 음식의 정보를 가져오는 기능

##### 2️⃣ 음식 수정하기
###### Rest 통신을 통한 서버에 저장된 음식의 정보를 수정하는 기능

##### 3️⃣ 음식 삭제하기
###### Rest 통신을 통해  서버에 저장된 음식의 정보를 삭제하는 기능

<br>

### 🎥 시연 영상( 푸디 사용자 앱에서 저장한 테스트 음식을 수정/삭제하는 영상입니다. )
<img width="30%" src="https://user-images.githubusercontent.com/65700842/208656557-de82aeef-8fa6-4d91-9d2a-bf678591a3e3.gif">


### 😎 프로젝트 사용기술 설명
##### 1️⃣ Dagger Hilt를 활용하여 의존성을 주입 해주었습니다.
##### 2️⃣ MVVM 디자인 기반으로 프로젝트를 진행 하였습니다.
##### 3️⃣ Coroutine을 통한 비동기 처리를 , RxBinding을 통한 UI event 처리를 하였습니다. (throttleFirst()를 통한 이중 클릭 방지)
##### 4️⃣ Retrofit2를 통해 Rest통신을 하였습니다.
##### 5️⃣ Repository를 사용하여 Data를 관리 하였습니다.


