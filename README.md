#### 2021 KAIST 몰입캠프 겨울학기 1주차 과제
###### KAIST 전산학부 이주현, 부산대학교 정보컴퓨터공학부 이준영
# Pro . dev
## 프로젝트 소개

![main_img](https://user-images.githubusercontent.com/37971925/148037772-2f25ff79-0f50-4652-a902-6f33ca711cc1.png)

## 과제 소개
1주차 과제의 경우 다음과 같이 3개의 탭으로 구성된 어플리케이션을 개발하는 것이다.
3개 탭의 내용은 아래와 같다.
>
* 연락처를 보여주는 화면 (단, 연락처는 JSON 데이터 형식이어야함)
* 20장 내외의 이미지로 구성된 이미지 갤러리를 보여주는 화면
* 자유롭게 구현한 기능을 보여주는 화면
<br>

### 1번탭
***
JSON 데이터를 효과적으로 다룰 수 있는 <U>[ZXing](https://github.com/zxing/zxing)</U> 라이브러리를 사용하여 연락처 데이터를 추가하고 편집할 수 있게 진행했다. *ZXing은 QR코드 읽기/쓰기 기능을 제공해주는 라이브러리*

별도의 Activity나 Fragment를 만들어 주지 않아도  QR 코드를 읽어올 수 있으며, Activity의 경우 onActivityResult() 함수를 Override하여 결과 값을 받아올 수 있다.

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
그리고 연락처는 프로필 카드의 형태로 보여주며, 이를 위해 프로필 카드를 직접 디자인했다. 프로필 카드는 여러 개를 추가할 수 있으며 이를 위해 ViewPager2를 사용했다.

추가로 현재 선택된 프로필 카드의 위치를 시각적으로 사용자에게 알려주기 위해 <U>[Dots Indicator](https://github.com/tommybuonomo/dotsindicator)</U> 라이브러리를 사용했다. 하지만 ViewPager2의 아이템을 삭제해도 Indicator의 인덱스 길이가 변하지 않는 등 여러 문제점이 있어 <U>[Circle Indicator](https://github.com/ongakuer/CircleIndicator)</U> 라이브러리의 CircleIndicator3를 사용하여 문제를 해결했다.

![](https://user-images.githubusercontent.com/37971925/148042318-4d0216e8-b154-4391-b7ec-22f966754810.gif)

*첫번째 탭 작동 예시.gif*
<br>

그리고 프로필 카드의 추가 및 삭제를 위한 드롭다운 메뉴를 추가했다.
![그림1](https://user-images.githubusercontent.com/37971925/148050280-b86e7919-50e3-4f4d-86d8-7e2aa3f525db.png)*드롭다운 및 삭제화면 예시. 프로필 카드를 추가하는 화면도 있지만, 안드로이드 에뮬레이터에서는 QR 코드 인식이 불가능해 따로 첨부하지 못했다.*

추가 혹은 삭제 시 나오는 모달 창의 경우 Dialog 클래스를 상속하여 커스텀 Dialog 클래스를 만들어 사용했다. 그리고 이를 위해 별도의 XML 파일을 만들어 사용했으며, 삭제 시 모달 창을 띄우고 결과에 따라 프로필 카드를 삭제하는 코드를 작성했다.

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
<br>

### 2번탭
***
두번째 탭은 사진을 갤러리로 보여주는 화면이다. 저희는 IT 계열 회사에서 사용한다고 가정하였으므로, 동료 개발자를 비롯한 사우들이 올린 게시물을 보여줄 수 있는 화면으로 구성했다.

화면의 구성은 인스타그램이나 페이스북 등을 참고해서 배치했다. 사진의 경우 GridView를 사용하여 한 열에 3개의 사진이 배치 될 수 있게 했다.

![두번쨰 탭](https://user-images.githubusercontent.com/37971925/148053534-aade4c37-67cd-419d-9c17-435728f30c57.png)

사진 클릭 시 게시글을 볼 수 있는 Fragment를 실행한다. 이때 Fragment를 전환하면서 애니메이션을 추가했는데, 아래와 같은 코드로 Fragment 간의 전환에 애니메이션을 실행할 수 있다.
```kotlin
 activity!!.supportFragmentManager.beginTransaction()
 	.setCustomAnimations(R.anim.fade_in, R.anim.fade_out)
	.replace(R.id.fragment, feedDetailFragment)
	.commitAllowingStateLoss()
```

현재 알파 값을 조정하여 Fade-In과 Fade-Out 효과를 구현했는데, 안드로이드의 경우 XML  파일을 작성하여 애니메이션을 구현할 수 있다.

```XML
<?xml version="1.0" encoding="utf-8"?>
<set xmlns:android="http://schemas.android.com/apk/res/android">
    <alpha
        xmlns:android="http://schemas.android.com/apk/res/android"
        android:interpolator="@android:anim/accelerate_interpolator"
        android:fromAlpha="0.0"
        android:toAlpha="1.0"
        android:duration="500"/>
</set>
```
<br>

### 3번탭
***
 본 앱은 IT 계열 직장인을 대상으로 만들어졌기 때문에 3번째 탭은 프롤젝트 관리형 페이지로 직장인들이 참여하는 프로젝트를 관리하기 용이하도록 제작하였다. 시간 관리 및 세부적인 할 일을 체크하기 편하도록 제작되었다.
 
 ![](https://images.velog.io/images/dhwndudkaps2/post/675bd10b-bb7a-4410-b862-ec439c92ff89/image.png)
 
 #### UI
 
 (1) 언어 비율 차트
  가장 위의 chart에서는 프로젝트의 언어 비율을 확인할 수 있다. 가지고 있는 프로젝트들의 언어를 파악하여 개수에 따라 ratio를 계산하여 반영한다. 어떤 언어가 높은 비중을 차지하고 있는지 빠르게 확인하는 용도이다.
 
(2) 프로젝트 카드
 #### 앞면
  프로젝트를 관리하는 카드의 앞면은 중요 정보를 포함하고 있다. 프로젝트 마감일, 마감일까지의 D-Day, 프로젝트를 담당하는 리더, 리더 외 프로젝트 참여자들, 현재 프로젝트 진행률을 포함하고 있다. 프로젝트에서 사용하는 언어는 카드의 색상을 통해 구분할 수 있다. 파란색은 Python, 노란색은 Kotlin, 빨간색은 그 외의 언어를 의미한다. 
 
 
 #### 뒷면
  카드의 뒷면에서는 세부적인 프로젝트 정보를 확인할 수 있다. 앞면에서 잘린 프로젝트 제목을 다시 한 번 확인할 수 있으며, 프로젝트 담당자의 이름, 이메일, 핸드폰 번호, 프로필 사진을 볼 수 있다. 아래쪽에는 프로젝트를 효과적으로 관리하기 위한 TODO List를 제공하고 있다. 우측에는 프로젝트를 실행할 때 수행해야 하는 일들을 스크롤하여 확인할 수 있다. 일을 마쳤다면 체크 박스를 선택하면 진행률이 왼쪽의 차트에 반영된다. 뒷면에 있는 파이 차트는 해당 프로젝트의 세부 업무 진행 상황을 나타낸다. 전체 업무 중 몇 개의 업무가 마무리 되었는지, 체크 박스를 통해 확인하고 퍼센트로 프로젝트 완성도를 알려준다. 
 
(3) 프로젝트 생성 화면
 ![gif](https://user-images.githubusercontent.com/89647814/148054243-99cb6da0-a70e-452b-8c4e-beb668760337.gif)

  프로젝트를 추가할 수 있는 화면이다. 참여하는 프로젝트의 이름, 사용하는 언어, 프로젝트에 참여하는 다른 사람들, 프로젝트 시작일과 마감일은 dialog를 통해 날짜를 선택할 수 있다. 그 아래에는 프로젝트의 세부 업무를 작성하는 칸이 있다. 마지막으로 프로젝트 담당자의 정보를 입력한 후 ADD 버튼을 클릭할 시 안내 팝업과 함께 프로젝트가 추가되어 세 번째 탭에서 바로 확인할 수 있다.
 
 #### 코드 구현
 
(1) Project Add Fragment
 #### Dialog 구현
 
```kotlin
binding.startDateInput.setOnClickListener {

            var calendar = Calendar.getInstance()
            var year = calendar.get(Calendar.YEAR)
            var month = calendar.get(Calendar.MONTH)
            var day = calendar.get(Calendar.DAY_OF_MONTH)

            var date_listener = object: DatePickerDialog.OnDateSetListener{
                override fun onDateSet(view: DatePicker?, year: Int, month: Int, dayOfMonth: Int) {
                    var startDate = "${year}-${month+1}-${dayOfMonth}"
                    binding.startDateInput.setText(startDate)
                }
            }
            var builder = DatePickerDialog(this.requireContext(), date_listener, year, month, day)
            builder.show()
        }
```
  Start Date, End Date 버튼을 눌렀을 때 날짜를 선택할 수 있는 Dialog가 뜨도록 하는 코드이다. DatePickerDialog를 사용하였다. 
  #### Third Fragment에 입력 값 전달
  
  ```kotlin
  var bundle: Bundle = Bundle()
            var jsonObject = JSONObject()
            jsonObject.put("name", name)
            jsonObject.put("email", email)
            jsonObject.put("phone", phone)
            jsonObject.put("title", projectTitle)
            jsonObject.put("language", language)
            jsonObject.put("startdate", startDate)
            jsonObject.put("enddate", endDate)
            jsonObject.put("participants", participants)
            jsonObject.put("todo1", todo1)
            jsonObject.put("todo2", todo2)
            jsonObject.put("todo3", todo3)
            jsonObject.put("todo4", todo4)
            jsonObject.put("todo5", todo5)

            bundle.putString("project", jsonObject.toString())
            var thirdFragment = ThirdFragment()
            thirdFragment.arguments = bundle

  ```
  해당 Fragment에서 받은 정보는 bundle을 통해 third fragment로 전달된다.
 
(2) Project Adapter
 ```kotlin
 data class ProjectItem (var title: String,
                        var language: String,
                        var leader: String,
                        var status: Int,
                        var start_date: LocalDate,
                        var end_date: LocalDate,
                        var participants: String,
                        var todo : List<String>,
                        var email: String,
                        var phone: String,
) {
    var d_day: Int = ChronoUnit.DAYS.between(LocalDate.now(), end_date).toInt()
}
 ```
  Project adapter에서 프로젝트 카드에 표현하고자 하는 정보를 binding하기 위하여 위와 같이 project item class를 정의하였다. 하나의 카드를 만드는데 필요한 정보들을 정리하였다. 그 후 itemList에서 해당 item의 정보를 view와 binding하였다. 
  
  ```kotlin
  when (item.language.uppercase()) { // 프로젝트 언어별로 색상을 다르게 지정
                "PYTHON" -> {
                    viewpagerBinding.projectItemBeforeFolding.projectTitleBorder
                        .setBackgroundColor(ContextCompat.getColor(context, R.color.python))
                    viewpagerBinding.projectItemBeforeFolding.projectProgress.progressTintList =
                        ColorStateList
                            .valueOf(ContextCompat.getColor(context, R.color.python))
                    viewpagerBinding.projectItemAfterFolding.projectTitleBorder
                        .setBackgroundColor(ContextCompat.getColor(context, R.color.python))
                    setDataToPieChart(chart, 1400, 0, R.color.python)
                    viewpagerBinding.projectItemBeforeFolding.projectItemVerticalBar.verticalBar
                        .setBackgroundColor(ContextCompat.getColor(context, R.color.python))
                }
                "KOTLIN" -> {
                    viewpagerBinding.projectItemBeforeFolding.projectTitleBorder
                        .setBackgroundColor(ContextCompat.getColor(context, R.color.kotlin))
                    viewpagerBinding.projectItemBeforeFolding.projectProgress.progressTintList =
                        ColorStateList
                            .valueOf(ContextCompat.getColor(context, R.color.kotlin))
                    viewpagerBinding.projectItemAfterFolding.projectTitleBorder
                        .setBackgroundColor(ContextCompat.getColor(context, R.color.kotlin))
                    setDataToPieChart(chart, 1400, 0, R.color.kotlin)
                    viewpagerBinding.projectItemBeforeFolding.projectItemVerticalBar.verticalBar
                        .setBackgroundColor(ContextCompat.getColor(context, R.color.kotlin))
                }
                else -> {
                    viewpagerBinding.projectItemBeforeFolding.projectTitleBorder
                        .setBackgroundColor(ContextCompat.getColor(context, R.color.others))
                    viewpagerBinding.projectItemBeforeFolding.projectProgress.progressTintList =
                        ColorStateList
                            .valueOf(ContextCompat.getColor(context, R.color.others))
                    viewpagerBinding.projectItemAfterFolding.projectTitleBorder
                        .setBackgroundColor(ContextCompat.getColor(context, R.color.others))
                    setDataToPieChart(chart, 1400, 0, R.color.others)
                    viewpagerBinding.projectItemBeforeFolding.projectItemVerticalBar.verticalBar
                        .setBackgroundColor(ContextCompat.getColor(context, R.color.others))
                }
            }
  ```
  이 때 언어에 따라 카드의 색상이 달라져야 하기 때문에 bind 할 때 item의 language에 따라서 card border와 pie chart의 색상이 달라지도록 set하였다.
  
  ```kotlin
  fun listen(cb: CheckBox, chart: PieChart) {
            cb.setOnClickListener(object : View.OnClickListener {
                override fun onClick(view: View) {
                    val checked = cb.isChecked
                    when (checked) {
                        true -> {
                            cb.setTextColor(ContextCompat.getColor(context, R.color.gray))
                            checkedNum += 1
                            println("checkedNum: ${checkedNum}")

                            when(language.uppercase()) {
                                "PYTHON" -> {
                                    cb.buttonTintList = ContextCompat.getColorStateList(context, R.color.python)
                                    setDataToPieChart(chart, 1400, checkedNum, R.color.python)
                                }
                                "KOTLIN" -> {
                                    cb.buttonTintList = ContextCompat.getColorStateList(context, R.color.kotlin)
                                    setDataToPieChart(chart, 1400, checkedNum, R.color.kotlin)
                                }
                            }
                        }
                        false -> {
                            cb.setTextColor(ContextCompat.getColor(context, R.color.darknavy))
                            cb.buttonTintList = ContextCompat.getColorStateList(context, R.color.gray)
                            checkedNum -= 1
                            println("checkedNum: ${checkedNum}")
                            when(language.uppercase()) {
                                "PYTHON" -> setDataToPieChart(chart, 1400, checkedNum, R.color.python)
                                "KOTLIN" -> setDataToPieChart(chart, 1400, checkedNum, R.color.kotlin)
                            }
                        }
                    }
                }
  ```
  또한 project adapter에서 프로젝트의 세부 작업 진행 현황을 관리하기 때문에 checkbox를 선택할 때마다 chart를 reload하는 함수가 필요하다. checkbox가 click될 때마다 현재까지 체크된 박스들의 개수와 토탈 개수를 확인하여 퍼센트로 변환하여 차트로 시각화된다. 
 
(3) Third Fragment
 ```kotlin
 override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        requireActivity().window.statusBarColor = ContextCompat.getColor(requireContext(), R.color.lightgray)

        _binding = FragmentThirdBinding.inflate(inflater, container, false)
        fragmentThirdBinding = binding
        projectAdapter = ProjectAdapter(this.requireContext())
        readJsonData()
        projectAdapter.itemList = projectList
        binding.projectList.adapter = projectAdapter
 ```
  마지막으로 세 번째 tap 화면의 기능을 담당하는 third fragment에서는 project.json 파일에 저장된 json data를 읽어 프로젝트 카드들을 view page로 보여준다. third fragment의 view를 생성할 때마다 json 파일에서 data를 읽어온다. 
  ```kotlin
  var item = ProjectItem(title, language, name, status
                , LocalDate.parse(startDate, DateTimeFormatter.ofPattern("yyyy-M-d"))
                , LocalDate.parse(endDate, DateTimeFormatter.ofPattern("yyyy-M-d")), participants, todoList, email, phone)
            projectList.add(item)
            writeJsonData()
  ```
  
  앞에서 project add fragment에서 bundle을 통해 값을 전달 받았기 때문에 전달 받은 값을 projectItem들을 모아둔 projectList에 추가한 뒤 파일에 write한다. 앱이 꺼졌다가 다시 실행되어도 파일에 저장되어 불러올 수 있도록 한다. 

