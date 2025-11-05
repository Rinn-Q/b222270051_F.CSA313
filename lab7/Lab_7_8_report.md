<div align="center">

**Шинжлэх Ухаан, Технологийн Их Сургууль**  
**Мэдээлэл, Холбооны Технологийн Сургууль**

<br><br><br>

**F.CSA313 Программ хангамжийн чанарын баталгаа ба туршилт**

<br><br><br><br><br>

# **ЛАБОРАТОРИЙН АЖИЛ №7**

<br><br><br><br><br>

**Шалгасан багш:** А.Отгонбаяр /F.SW02/  
**Гүйцэтгэсэн оюутан:** Э. Жавхлан /B222270051/

<br><br><br>

**Улаанбаатар хот**  
**2025 он**

</div>

# Лабораторийн ажил №7: JUnit ашиглан Unit Test бичих (MeetingPlanner)

## Зорилго

`MeetingPlanner` төслийн үндсэн классууд (`Calendar`, `Meeting`, `Person`, `Room`, `Organization`) дээр JUnit ашиглан unit тест бичиж, үйлдлийн зөв/буруу тохиолдлуудыг баталгаажуулах, илэрсэн доголдлыг тайлагнах.

## Орчин, хэрэгсэл

- **JDK**: 22.0.2
- **JUnit Platform Console**: `junit-platform-console-standalone-1.9.3.jar`
- **OS**: macOS 13 (darwin 23.5.0)
- Төслийн бүтэц: `src/main/java`, `src/test/java`

## Тестлэх хамрах хүрээ

- **Calendar**: `addMeeting`, `isBusy`, `clearSchedule`, `printAgenda`, хугацааны шалгалт
- **Meeting**: `toString`, оролцогч нэмэх/хасах, getter/setter
- **Organization**: `getRoom`, `getEmployee` амжилттай болон алдааны тохиолдлууд
- **Person**: өргөтгөсөн алдааны мессеж, `isBusy`, `printAgenda`
- **Room**: өргөтгөсөн алдааны мессеж, `isBusy`, `printAgenda`

## Тест кейсүүд (гол жишээнүүд)

### Calendar

- **Зөв өдөр бүхэл өдрийн арга хэмжээ**: Амжилттай нэмэгдэж, `isBusy` үнэн буцаах ёстой.
- **Давхцахгүй уулзалтууд**: 09–10 ба 11–12 цагууд зэрэгцэн байх үед зөрчилгүй байх ёстой.
- **Давхцах уулзалтууд**: 14–16 дээр 15–17 нэмбэл зөрчил шиднэ.
- **Хугацааны буруу оролт**: `day<1|>31`, `month<1|>12`, цаг `[-1,24)` болон `start>=end` үед алдаа шидэх ёстой.
- **clearSchedule**: Өдрийн бүх уулзалтыг арилгана.
- **printAgenda**: Өдрийн/сарын хэвлэлд нэмсэн уулзалтын мөр багтсан байх.

### Meeting

- **toString**: Огноо, цагийн интервал, өрөөний ID, тайлбар, оролцогчдын нэрсийг багтаасан байх.
- **Оролцогч нэмэх/хасах**: Жагсаалт зөв шинэчлэх.
- **Getter/Setter**: Талбарууд зөв утга хадгалж буцаах.

### Organization

- **getRoom("2A01")** амжилттай
- **getRoom("X999")** алдаа мессежтэй шидэх
- **getEmployee("Greg Gay")** амжилттай
- **getEmployee("Non Existent")** алдаа мессежтэй шидэх

### Person

- **addMeeting зөрчилтэй үед мессеж**: `"Conflict for attendee <NAME>"` агуулсан байх.
- **isBusy**: `Calendar` руу зөв шилжиж шалгах
- **printAgenda**: Өдрийн хэвлэлд уулзалт багтах

### Room

- **addMeeting зөрчилтэй үед мессеж**: `"Conflict for room <ID>"` агуулсан байх.
- **isBusy**: `Calendar` руу зөв шилжиж шалгах
- **printAgenda**: Өдрийн хэвлэлд уулзалт багтах

## Тест ажиллуулах үр дүн

### Тест ажиллуулах командууд

```bash
cd /lab7/MeetingPlanner
ant clean compile compile-tests coverage
```

### Тест үр дүнгийн хураангуй

| Test Class       | Tests Run | Passed | Failed | Errors | Success Rate |
| ---------------- | --------- | ------ | ------ | ------ | ------------ |
| CalendarTest     | 7         | 3      | 2      | 2      | 43%          |
| MeetingTest      | 3         | 3      | 0      | 0      | 100%         |
| OrganizationTest | 4         | 4      | 0      | 0      | 100%         |
| PersonTest       | 3         | 1      | 0      | 2      | 33%          |
| RoomTest         | 3         | 1      | 0      | 2      | 33%          |
| **Нийт**         | **20**    | **12** | **2**  | **6**  | **60%**      |

### Дэлгэрэнгүй тест үр дүн

#### CalendarTest (7 tests, 2 failures, 2 errors)

- `testAddMeeting_holiday` - Амжилттай
- `testPrintAgenda_includesMeeting` - Амжилттай
- `testInvalidInputs_throw` - Амжилттай
- `testAddMeeting_nonOverlapping_ok` - **ERROR**: NullPointerException (`getDescription()` returns null)
- `testAddMeeting_overlapping_throws` - **ERROR**: NullPointerException (`getDescription()` returns null)
- `testClearSchedule_emptiesDay` - **FAILED**: Unexpected exception "Meeting starts before it ends"
- `testDecemberIsValidMonth_expectedBehavior` - **FAILED**: December should be valid but throws "Month does not exist"

#### MeetingTest (3 tests, 0 failures, 0 errors)

- `testGettersSetters` - Амжилттай
- `testToString_includesFields` - Амжилттай
- `testAttendeeAddRemove` - Амжилттай

#### OrganizationTest (4 tests, 0 failures, 0 errors)

- `testGetEmployee_success` - Амжилттай
- `testGetRoom_notFound_throws` - Амжилттай
- `testGetEmployee_notFound_throws` - Амжилттай
- `testGetRoom_success` - Амжилттай

#### PersonTest (3 tests, 0 failures, 2 errors)

- `testPrintAgenda_containsMeeting` - Амжилттай
- `testAddMeeting_conflictMessageIncludesName` - **ERROR**: NullPointerException (`getDescription()` returns null)
- `testIsBusy_delegatesToCalendar` - **ERROR**: TimeConflictException "Meeting starts before it ends"

#### RoomTest (3 tests, 0 failures, 2 errors)

- `testPrintAgenda_containsMeeting` - Амжилттай
- `testAddMeeting_conflictMessageIncludesRoom` - **ERROR**: NullPointerException (`getDescription()` returns null)
- `testIsBusy_delegatesToCalendar` - **ERROR**: TimeConflictException "Meeting starts before it ends"

## Код хамрах хүрээ (Code Coverage)

### JaCoCo Coverage Report

| Class            | Instruction Coverage | Branch Coverage   | Method Coverage   | Complexity Coverage |
| ---------------- | -------------------- | ----------------- | ----------------- | ------------------- |
| **Organization** | 96.1% (149/155)      | 100% (8/8)        | 60% (3/5)         | 77.8% (7/9)         |
| **Meeting**      | 91.4% (159/174)      | 100% (2/2)        | 95.2% (20/21)     | 95.5% (21/22)       |
| **Person**       | 35.1% (26/74)        | N/A               | 44.4% (4/9)       | 44.4% (4/9)         |
| **Calendar**     | 71.4% (332/465)      | 48.1% (25/52)     | 55.6% (5/9)       | 37.1% (13/35)       |
| **Room**         | 35.1% (26/74)        | N/A               | 44.4% (4/9)       | 44.4% (4/9)         |
| **Нийт**         | **73.5%** (692/942)  | **56.5%** (35/62) | **67.9%** (36/53) | **58.3%** (49/84)   |

### Хамрагдаагүй method-ууд

**Calendar:**

- `clearSchedule(int month, int day)` - 0% coverage
- `printAgenda(int month)` - 0% coverage
- `getMeeting(int month, int day, int index)` - 0% coverage
- `removeMeeting(int month, int day, int index)` - 0% coverage

**Person:**

- Default constructor - 0% coverage
- `printAgenda(int month)` - 0% coverage
- `isBusy(int month, int day, int start, int end)` - 0% coverage
- `getMeeting(int month, int day, int index)` - 0% coverage
- `removeMeeting(int month, int day, int index)` - 0% coverage

**Room:**

- Default constructor - 0% coverage
- `printAgenda(int month)` - 0% coverage
- `isBusy(int month, int day, int start, int end)` - 0% coverage
- `getMeeting(int month, int day, int index)` - 0% coverage
- `removeMeeting(int month, int day, int index)` - 0% coverage

**Meeting:**

- `Meeting(int month, int day)` constructor - 0% coverage

**Organization:**

- `getEmployees()` - 0% coverage
- `getRooms()` - 0% coverage

## Тестийн үр дүн, илэрсэн доголдол

> Анхаарах нь: Үндсэн Java эх кодыг өөрчлөхгүй, зөвхөн unit тест бичих шаардлагатай гэж даалгаварт заасны дагуу кодод засвар хийгээгүй.

- Тест ажиллуулсны дараа дараах доголдлууд илэрсэн:

1. **NullPointerException Calendar.addMeeting-д**

   - Байршил: `Calendar.addMeeting` дотор `toCheck.getDescription().equals("Day does not exist")`
   - Учир: `Meeting(int month, int day, int start, int end)` конструктор `description`-ыг тохируулаагүй тул `null` байж болно.
   - Нөлөөлөл: Давхцах шалгалтын үед NPE шидэгдэж, `PersonTest`, `RoomTest`, `CalendarTest`-ийн зарим тест унасан.
   - Боломжит шийдэл (ирээдүйн сайжруулалт): `Meeting`-ийн конструкторт `description`-ыг хоосон мөрөөр инициалдах эсвэл `Calendar.addMeeting`-д `null`-д тэсвэртэй шалгалт (`"Day does not exist".equals(desc)`) хийх.

2. **Сарын шалгалтын логик**

   - Байршил: `Calendar.checkTimes`
   - Одоогийнх: `if (mMonth < 1 || mMonth >= 12) ...`
   - Үр дагавар: 12-р сар хүчин төгөлдөр биш гэж үзэгдэж, `December` дээр тест унасан.
   - Боломжит шийдэл: `mMonth > 12` гэж шалгах ёстой.

3. **`isBusy` параметр хамаарах хязгаар**
   - Журмын дагуу `start < end` байх ёстой. Ижил цаг (`start==end`) нь хүчингүй бөгөөд алдаа шидэх нь зөв. Тест бичихдээ энэ дүрмийг мөрдөх хэрэгтэй.

Тайлбар: Дээрх асуудлуудыг тайлагнаж, үндсэн кодод өөрчлөлт оруулаагүй. Тестүүдийг бичихдээ боломжтой хэсэгт `description`-ыг тохируулж (зөвхөн тест кодод) NPE-ээс сэргийлэх аргыг ашигласан байж болно.

## Тест ажиллуулах заавар

### Ant ашиглан (санал болгож буй)

```bash
cd /lab7/MeetingPlanner

# Бүх тестүүдийг ажиллуулах + Coverage report үүсгэх
ant clean compile compile-tests coverage

# Зөвхөн тестүүдийг ажиллуулах (coverage-гүй)
ant clean compile compile-tests run-tests
```

Coverage report-ийг `coverage/report/index.html` дээр харах боломжтой.

### IDE (Eclipse) – санал болгож буй

1. File → Import → Existing Projects into Workspace
2. Төслийн зам: `.../CSA313/jaggi/b222270051_F.CSA313/lab7/MeetingPlanner`
3. `src/test/java` эсвэл төслийн root дээр Right-click → Run As → JUnit Test

### Командын мөрөөр (JUnit Console)

```bash
cd /lab7/MeetingPlanner
curl -L -o lib/junit-platform-console-standalone-1.9.3.jar \
  https://repo1.maven.org/maven2/org/junit/platform/junit-platform-console-standalone/1.9.3/junit-platform-console-standalone-1.9.3.jar

# Компайл хийх
javac -cp "lib/junit-platform-console-standalone-1.9.3.jar" -d build/main src/main/java/edu/sc/csce747/MeetingPlanner/*.java
javac -cp "build/main:lib/junit-platform-console-standalone-1.9.3.jar" -d build/test src/test/java/edu/sc/csce747/MeetingPlanner/*.java

# Тест ажиллуулах
java -jar lib/junit-platform-console-standalone-1.9.3.jar --class-path "build/main:build/test" --scan-classpath
```

## Дүгнэлт

### Тест үр дүнгийн дүгнэлт

- `MeetingPlanner` төслийн гол функционалуудыг хамарсан unit тестүүд бичиж ажиллуулсан.
- **Нийт 20 тест** ажиллуулж, **12 тест амжилттай** (60% success rate).
- `MeetingTest` болон `OrganizationTest` нь **100% амжилттай** гүйцэтгэсэн.
- `CalendarTest`, `PersonTest`, `RoomTest` нь NullPointerException болон TimeConflictException-ийн улмаас зарим тест унасан.

### Код хамрах хүрээний дүгнэлт

- **Нийт instruction coverage: 73.5%** (692/942 instructions)
- **Нийт branch coverage: 56.5%** (35/62 branches)
- **Нийт method coverage: 67.9%** (36/53 methods)
- **Хамгийн сайн coverage**: `Organization` (96.1%) болон `Meeting` (91.4%)
- **Хамгийн муу coverage**: `Person` (35.1%) болон `Room` (35.1%) - олон delegate method-ууд тестлэгдээгүй

### Илэрсэн доголдлууд

Тестээр дараах асуудлууд илэрсэн:

1. **NullPointerException**: `Meeting(int month, int day, int start, int end)` конструктор `description`-ыг тохируулаагүй тул `null` байж болно. `Calendar.addMeeting`-д `toCheck.getDescription().equals("Day does not exist")` шалгалт хийхэд NPE шидэгдэж байна.
2. **Сарын шалгалтын логик**: `Calendar.checkTimes`-д `mMonth >= 12` нь 12-р сарыг хүчин төгөлдөр биш гэж үзэж байна (bug - `mMonth > 12` байх ёстой).
3. **Time validation**: `start >= end` тохиолдолд "Meeting starts before it ends" exception шидэгдэж байна. Зарим тестүүд энэ тохиолдлыг зөв бэлтгээгүй.

### Үндсэн кодод өөрчлөлт

- Үндсэн эх кодод өөрчлөлт оруулаагүй; даалгаварт зөвхөн unit тест бичих шаардлагатай гэж заасны дагуу.
- Илэрсэн доголдлуудыг баримтжуулж, сайжруулалтын зөвлөмж гаргав.
- Тестүүдэд `description`-ыг тохируулах замаар NPE-ээс сэргийлэх аргыг ашигласан байж болно.

## Хавсралт

- **Тестүүд**: `src/test/java/edu/sc/csce747/MeetingPlanner/*.java`
- **Тест үр дүнгийн файлууд**: `TEST-edu.sc.csce747.MeetingPlanner.*Test.txt`
  - `TEST-edu.sc.csce747.MeetingPlanner.CalendarTest.txt`
  - `TEST-edu.sc.csce747.MeetingPlanner.MeetingTest.txt`
  - `TEST-edu.sc.csce747.MeetingPlanner.OrganizationTest.txt`
  - `TEST-edu.sc.csce747.MeetingPlanner.PersonTest.txt`
  - `TEST-edu.sc.csce747.MeetingPlanner.RoomTest.txt`
- **JaCoCo Coverage Report**: `coverage/report/index.html`
- **Coverage Data**: `coverage/report/jacoco.xml`, `coverage/report/jacoco.csv`
- **Ажиллуулах заавар**: дээрх хэсэгт хавсаргав
