<div align="center">

**Шинжлэх Ухаан, Технологийн Их Сургууль**  
**Мэдээлэл, Холбооны Технологийн Сургууль**

<br><br><br>

**F.CSA313 Программ хангамжийн чанарын баталгаа ба туршилт**

<br><br><br><br><br>

# **ЛАБОРАТОРИЙН АЖИЛ №7**

<br><br><br><br><br>

**Шалгасан багш:** А.Отгонбаяр /F.SW02/  
**Гүйцэтгэсэн оюутан:** Б. Тэргэл /B222270006/

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

## Тестийн үр дүн, илэрсэн доголдол

> Анхаарах нь: Үндсэн Java эх кодыг өөрчлөхгүй, зөвхөн unit тест бичих шаардлагатай гэж даалгаварт заасны дагуу кодод засвар хийгээгүй.

- Тест ажиллуулсны дараа дараах доголдлууд илэрсэн:

1) **NullPointerException Calendar.addMeeting-д**  
   - Байршил: `Calendar.addMeeting` дотор `toCheck.getDescription().equals("Day does not exist")`  
   - Учир: `Meeting(int month, int day, int start, int end)` конструктор `description`-ыг тохируулаагүй тул `null` байж болно.  
   - Нөлөөлөл: Давхцах шалгалтын үед NPE шидэгдэж, `PersonTest`, `RoomTest`, `CalendarTest`-ийн зарим тест унасан.  
   - Боломжит шийдэл (ирээдүйн сайжруулалт): `Meeting`-ийн конструкторт `description`-ыг хоосон мөрөөр инициалдах эсвэл `Calendar.addMeeting`-д `null`-д тэсвэртэй шалгалт (`"Day does not exist".equals(desc)`) хийх.

2) **Сарын шалгалтын логик**  
   - Байршил: `Calendar.checkTimes`  
   - Одоогийнх: `if (mMonth < 1 || mMonth >= 12) ...`  
   - Үр дагавар: 12-р сар хүчин төгөлдөр биш гэж үзэгдэж, `December` дээр тест унасан.  
   - Боломжит шийдэл: `mMonth > 12` гэж шалгах ёстой.

3) **`isBusy` параметр хамаарах хязгаар**  
   - Журмын дагуу `start < end` байх ёстой. Ижил цаг (`start==end`) нь хүчингүй бөгөөд алдаа шидэх нь зөв. Тест бичихдээ энэ дүрмийг мөрдөх хэрэгтэй.

Тайлбар: Дээрх асуудлуудыг тайлагнаж, үндсэн кодод өөрчлөлт оруулаагүй. Тестүүдийг бичихдээ боломжтой хэсэгт `description`-ыг тохируулж (зөвхөн тест кодод) NPE-ээс сэргийлэх аргыг ашигласан байж болно.

## Тест ажиллуулах заавар

### IDE (Eclipse) – санал болгож буй
1. File → Import → Existing Projects into Workspace  
2. Төслийн зам: `.../CSA313/lab7/MeetingPlanner`  
3. `src/test/java` эсвэл төслийн root дээр Right-click → Run As → JUnit Test

### Командын мөрөөр (JUnit Console)
```bash
cd /Users/tsundgerhenmacbook/Documents/Lessons/CSA313/lab7/MeetingPlanner
curl -L -o lib/junit-platform-console-standalone-1.9.3.jar \
  https://repo1.maven.org/maven2/org/junit/platform/junit-platform-console-standalone/1.9.3/junit-platform-console-standalone-1.9.3.jar

# Компайл хийх
javac -cp "lib/junit-platform-console-standalone-1.9.3.jar" -d bin src/main/java/edu/sc/csce747/MeetingPlanner/*.java
javac -cp "bin:lib/junit-platform-console-standalone-1.9.3.jar" -d bin src/test/java/edu/sc/csce747/MeetingPlanner/*.java

# Тест ажиллуулах
java -jar lib/junit-platform-console-standalone-1.9.3.jar --class-path bin --scan-classpath
```

## Дүгнэлт
- `MeetingPlanner` төслийн гол функционалуудыг хамарсан unit тестүүд бичиж ажиллуулсан.
- Тестээр дараах асуудлууд илэрсэн: `description` null-той холбоотой NPE, 12-р сарын шалгалтын логик.
- Үндсэн эх кодод өөрчлөлт оруулаагүй; илэрсэн доголдлуудыг баримтжуулж, сайжруулалтын зөвлөмж гаргав.

## Хавсралт
- Тестүүд: `src/test/java/edu/sc/csce747/MeetingPlanner/*.java`  
- Ажиллуулах заавар: дээрх хэсэгт хавсаргав


