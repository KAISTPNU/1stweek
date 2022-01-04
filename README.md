#### 2021 KAIST 몰입캠프 겨울학기 1주차 과제
###### KAIST 전산학부 이주현, 부산대학교 정보컴퓨터공학부 이준영
# Pro . dev
## 프로젝트 소개

![main_img](https://user-images.githubusercontent.com/37971925/148037772-2f25ff79-0f50-4652-a902-6f33ca711cc1.png)

## 과제 소개
1주차 과제의 경우 다음과 같이 3개의 탭으로 구성된 어플리케이션을 개발하는 것입니다.
3개 탭의 내용은 아래와 같습니다.
>
* 연락처를 보여주는 화면 (단, 연락처는 JSON 데이터 형식이어야함)
* 20장 내외의 이미지로 구성된 이미지 갤러리를 보여주는 화면
* 자유롭게 구현한 기능을 보여주는 화면

### 1번탭
***
JSON 데이터를 효과적으로 다룰 수 있는 <U>[ZXing](https://github.com/zxing/zxing)</U> 라이브러리를 사용하여 연락처 데이터를 추가하고 편집할 수 있게 진행했습니다.
ZXing은 QR코드 읽기/쓰기 기능을 제공해주는 라이브러리입니다.

별도의 Activity나 Fragment를 만들어 주지 않아도  QR 코드를 읽어올 수 있으며, Activity의 경우 onActivityResult()  함수를 Override하여 결과 값을 받아올 수 있습니다.

```kotlin
fun runQRCodeReader() { // QR 코드 리더 실행 함수 예시
	val integrator = IntentIntegrator(this)
	integrator.setBarcodeImageEnabled(false)
	integrator.setBeepEnabled(false)
	integrator.setPrompt("화면에 QR 코드를 인식시켜주세요")
	integrator.initiateScan()
}

@SuppressLint("MissingSuperCall") //  QR 코드 리더의 결과를 처리하는 함수
override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
	result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data)
	if (result.contents == null) {
		Toast.makeText(applicationContext, "스캔 실패. 다시 시도해주세요.", Toast.LENGTH_SHORT).show()
	}
	else {
		var bundle = Bundle()
		bundle.putString("JSON", result.contents)
	}
}
```
그리고 연락처는 프로필 카드의 형태로 보여주며, 이를 위해 프로필 카드를 직접 디자인했습니다. 프로필 카드는 여러 개를 추가할 수 있으며 이를 위해  ViewPager2를 사용했습니다. 

추가로 현재 선택된 프로필 카드의 위치를 시각적으로 사용자에게 알려주기 위해 <U>[Dots Indicator](https://github.com/tommybuonomo/dotsindicator)</U> 라이브러리를 사용했습니다. 하지만  ViewPager2의 아이템을 삭제해도 Indicator의 인덱스 길이가 변하지 않는 등 여러 문제점이 있어 <U>[Circle Indicator](https://github.com/ongakuer/CircleIndicator)</U> 라이브러리의 CircleIndicator3를 사용하여 문제를 해결했습니다.

![](https://user-images.githubusercontent.com/37971925/148042318-4d0216e8-b154-4391-b7ec-22f966754810.gif)

*첫번째 탭 작동 예시.gif*
<br>

그리고 프로필 카드의 추가 및 삭제를 위한 드롭다운 메뉴를 추가했습니다.
![그림1](https://user-images.githubusercontent.com/37971925/148050280-b86e7919-50e3-4f4d-86d8-7e2aa3f525db.png)
*드롭다운 및 삭제화면 예시. 프로필 카드를 추가하는 화면도 있지만, 안드로이드 에뮬레이터에서는 QR 코드 인식이 불가능해 따로 첨부하지 못했습니다.*

추가 혹은 삭제 시 나오는 모달 창의 경우 Dialog 클래스를 상속하여 커스텀 Dialog 클래스를 만들어 사용했습니다. 그리고 이를 위해 별도의 XML 파일을 만들어 사용했으며, 삭제 시 모달 창을 띄우고 결과에 따라 프로필 카드를 삭제하는 코드를 작성했습니다.

```kotlin
deleteBtn.setOnClickListener(object: View.OnClickListener{
	override fun onClick(v: View?) {
		val dialog = ProfileDeleteDialog(context)
		dialog.setOnOKClickedListener{
		delete(viewholder)
		readJsonData()
	}
	dialog.start("dsf")
	}
})
```
*프로필 카드의 삭제 버튼에 onClickListener를 활용하여 삭제 기능 실행*



