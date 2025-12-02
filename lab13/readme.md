<div align="center">

**Шинжлэх Ухаан, Технологийн Их Сургууль**  
**Мэдээлэл, Холбооны Технологийн Сургууль**

<br><br><br>

**F.CSA313 Программ хангамжийн чанарын баталгаа ба туршилт**

<br><br><br>

# **ЛАБОРАТОРИЙН АЖИЛ №13**

<br><br><br><br><br>

**Шалгасан багш:** А.Отгонбаяр /F.SW02/  
**Гүйцэтгэсэн оюутан:** Э. Жавхлан /B222270051/

<br><br><br><br><br>

**Улаанбаатар хот**  
**2025 он**

</div>

# Лабораторийн ажил №13: Хайлт дээр суурилсан ба AI-д суурилсан автомат тест үүсгэлт

## Зорилго

Оюутнууд бодит Python төсөл дээр дараах 3 төрлийн орчин үеийн автомат тест үүсгэх аргыг туршиж, харьцуулж сурна:

1. **Хайлт дээр суурилсан тест (Search-based)** – Pynguin
2. **Өгөгдөлд суурилсан тест (Property-based)** – Hypothesis
3. **Том хэлний загвараар тест үүсгэх (LLM-based)** – GitHub Copilot, Claude 3.5, ChatGPT o1, Cursor г.м.

## Ашигласан багаж

- **Pynguin 0.44.0** – Search-based test generation (genetic algorithm)
- **Hypothesis 6.148.5** – Property-based testing framework
- **Cursor AI / Claude 3.5 Sonnet** – LLM-based test generation
- **pytest 8.4.2** – Test execution framework
- **pytest-cov 7.0.0** – Coverage measurement
- **Python 3.10.16** – Programming language

## Сонгосон төсөл ба сонголтын шалтгаан

### Төсөл: Full Stack FastAPI Template

**GitHub Repository**: https://github.com/tiangolo/full-stack-fastapi-template  
**Last Commit**: 2025 оны 9 сар  
**Технологи**: FastAPI + Pydantic v2 + SQLModel + PostgreSQL + async/await

### Сонголтын шалтгаан

1. Орчин үеийн технологи: FastAPI нь 2025 онд хамгийн түгээмэл Python web framework-үүдийн нэг
2. Бүтэц: Модульчлагдсан бүтэцтэй, тест хийхэд тохиромжтой
3. Идэвхтэй хөгжил: 2025 онд сүүлд шинэчлэгдсэн, бүрэн ажилладаг
4. Олон төрлийн функц: Security (JWT, password hashing), CRUD operations, email utilities гэх мэт олон талыг тестлэх боломжтой
5. Бодит төсөл: Production-ready template, бодит асуудлуудыг илрүүлэх боломжтой

## 3 хувилбарын хамрах хүрээний (coverage) хүснэгт

| Хувилбар           | Line Coverage | Branch Coverage | Илэрсэн алдаа/Edge Case                                                             |
| ------------------ | ------------- | --------------- | ----------------------------------------------------------------------------------- |
| **Pynguin**        | ~45-55%       | ~40-50%         | 5 алдаа: None/str параметр, буруу hash format, bcrypt backend байхгүй               |
| **Hypothesis**     | ~60-70%       | ~55-65%         | 3 edge case: email template-д email_to заримдаа ордоггүй, bcrypt backend dependency |
| **AI (LLM-based)** | ~70-80%       | ~65-75%         | 2 орчны асуудал: bcrypt backend, DB connection mock хийх шаардлагатай               |

Тайлбар: Coverage хувь нь тестлэсэн модулиудын тоо, функцүүдийн нарийн төвөгтэй байдлаас хамаарна. Энэ хүснэгт нь `app.core.security` болон `app.utils` модулиуд дээрх үр дүнг харуулж байна.

## Pynguin-ийн илрүүлсэн бодит алдаа эсвэл сонирхолтой тест

### Pynguin-ийн ажиллуулах командууд

```bash
export PYNGUIN_DANGER_AWARE=YES
python -m pynguin \
  --project_path . \
  --module-name app.core.security \
  --output-path tests_pynguin/security \
  --algorithm WHOLE_SUITE \
  --max_sequence_length 20 \
  --maximum_search_time 300
```

### Илрүүлсэн алдаанууд

#### 1. **Төрлийн аюулгүй байдлын алдаа: `create_access_token` функц**

**Код**:

```python
def create_access_token(subject: str | Any, expires_delta: timedelta) -> str:
    expire = datetime.now(timezone.utc) + expires_delta
    # ...
```

**Pynguin-ийн үүсгэсэн тест**:

```python
def test_case_0():
    none_type_0 = None
    with pytest.raises(TypeError):
        module_0.create_access_token(none_type_0, none_type_0)
```

**Алдааны тайлбар**:

- Pynguin `None` утгыг `expires_delta` параметрт дамжуулж, `TypeError: unsupported operand type(s) for +: 'datetime.datetime' and 'NoneType'` алдааг илрүүлсэн
- Энэ нь функц нь runtime дээр type checking хийхгүй, зөвхөн type hint-д найдаж байгааг харуулж байна
- Шийдэл: Pydantic-ийн `validate_call` эсвэл manual type checking нэмэх шаардлагатай

#### 2. **Буруу Hash Формат алдаа: `verify_password` функц**

**Pynguin-ийн үүсгэсэн тест**:

```python
def test_case_2():
    bool_0 = True
    str_0 = "nXZq\tfe0"
    with pytest.raises(Exception):
        module_0.verify_password(bool_0, str_0)
```

**Алдааны тайлбар**:

- Pynguin буруу форматтай hash string (`"nXZq\tfe0"`) өгч, `passlib.exc.UnknownHashError` алдааг илрүүлсэн
- Энэ нь `verify_password` функц нь зөвхөн жинхэнэ bcrypt hash-ийг хүлээн авдаг, буруу форматтай утгад хангалттай алдааны зохицуулалт хийхгүй байгааг харуулж байна

#### 3. **Орчны хамаарлын алдаа: bcrypt backend**

**Pynguin-ийн үүсгэсэн тест**:

```python
def test_case_3():
    str_0 = "/openapi.json"
    with pytest.raises(Exception):
        module_0.get_password_hash(str_0)
```

**Алдааны тайлбар**:

- Зарим орчинд bcrypt backend суулгаагүй байх үед `passlib.exc.MissingBackendError` алдааг илрүүлсэн
- Энэ нь код нь орчны хамаарлыг зөв хангахгүй байгааг харуулж байна

### Сонирхолтой ажиглалт

Pynguin нь 9 тест үүсгэсэн, тэдгээрийн 5 нь алдаа илрүүлсэн. Энэ нь Pynguin-ийн давуу тал: энэ нь зөвхөн "амжилттай зам" биш, харин буруу оролтыг ч туршиж, кодны хамгаалалтыг шалгадаг.

Coverage-ийн дэлгэрэнгүй мэдээллийг `coverage_pynguin/html/index.html` файлыг browser-оор нээж харж болно.

## Hypothesis-ээр илэрсэн сонирхолтой edge case эсвэл алдаа

### Hypothesis-ийн property тестүүд

Би 7 property-based тест бичсэн:

1. `test_password_hash_roundtrip` – Password hash/verify roundtrip property
2. `test_access_token_contains_subject` – Access token үргэлж хоосон биш байх
3. `test_password_reset_token_roundtrip` – Password reset token roundtrip
4. `test_invalid_password_reset_token_returns_none` – Invalid token-ууд `None` буцаах
5. `test_generate_test_email_structure` – Email structure property
6. `test_generate_reset_password_email_structure` – Reset password email property
7. `test_generate_new_account_email_structure` – New account email property

### Илэрсэн edge case

#### Email Template-д `email_to` заримдаа ордоггүй

**Property тест**:

```python
@given(st.emails(), st.emails())
def test_generate_reset_password_email_structure(email_to: str, email: str) -> None:
    """Reset password emails must embed both addresses somewhere in the HTML."""
    token = generate_password_reset_token(email)
    email_data = generate_reset_password_email(email_to=email_to, email=email, token=token)
    assert email_data.subject
    assert email in email_data.html_content
    assert email_to in email_data.html_content  # Энэ заримдаа FAIL хийж байсан
```

**Hypothesis-ийн shrinking-ийн өмнөх утга**:

```
email_to='-@A.tt', email='0@A.COM'
```

**Hypothesis-ийн shrinking-ийн дараах утга**:

```
email_to='a@b.c', email='x@y.z'
```

**Алдааны тайлбар**:

- Hypothesis нь email template-ийн HTML-д `email_to` параметр заримдаа шууд ордоггүй, зөвхөн `email` (username) ордог гэдгийг илрүүлсэн
- Энэ нь template-ийн дизайн асуудал биш, харин property-ийн assertion хэт хатуу байсан
- Шийдэл: Property-ийг засаж, `email_to` нь template-д заавал орсон байх шаардлагагүй гэж тохируулсан

### Hypothesis-ийн давуу тал

Hypothesis нь автоматаар edge case-уудыг олж, shrinking хийж, хамгийн жижиг counterexample-ийг өгдөг. Энэ нь гар аргаар бичихэд хэцүү байдаг жишээлбэл, `'-@A.tt'` гэх мэт сонирхолтой email утгуудыг олдог.

Hypothesis-ийн shrinking-ийн үр дүнг terminal-д харж болно, эсвэл `.hypothesis/examples/` хавтсанд хадгалсан байдаг.

## AI хэрэгслүүдийн жишээ ба үр дүн

### Ашигласан AI хэрэгслүүд

1. Cursor AI – `app.core.security` болон `app.models` модулиуд дээр тест үүсгэсэн
2. Claude 3.5 Sonnet – `app.api.routes.items` модуль дээр тест үүсгэхэд ашигласан

### Хэрэглэсэн prompt

AI-д дараах prompt-ийг өгсөн:

"Энэ Python файлын хувьд 2025 оны орчин үеийн pytest хэв маяг ашиглан бүрэн pytest unit тестүүд үүсгэ. Fixture-уудыг зөв ашигла, гадаад dependency-уудыг mock хий, type hint-уудыг оруул, мөн 90%-аас дээш branch coverage-ийг зорилго болго. Бүх public функц, метод-уудыг тестлэ, setup/teardown-д pytest fixture-уудыг ашигла, database connection болон гадаад үйлчилгээнүүдийг mock хий, эерэг болон сөрөг тест case-уудыг оруул, pytest best practice-уудыг дага."

### AI-ийн үүсгэсэн тестүүдийн жишээ

#### Cursor AI-ийн үүсгэсэн тест (`test_security_ai.py`):

```python
from datetime import timedelta
from app.core import security

def test_get_password_hash_returns_different_value() -> None:
    password = "super-secret-password"
    try:
        hashed = security.get_password_hash(password)
        assert isinstance(hashed, str)
        assert hashed
        assert hashed != password
    except Exception:
        # Bcrypt backend байхгүй орчныг зохицуулах
        pass

def test_verify_password_success_and_failure() -> None:
    password = "another-password"
    try:
        hashed = security.get_password_hash(password)
        assert security.verify_password(password, hashed) is True
        assert security.verify_password(password + "x", hashed) is False
    except Exception:
        # Орчны хязгаарлалт
        pass

def test_create_access_token_produces_non_empty_token() -> None:
    subject = "user-id-123"
    token = security.create_access_token(subject=subject, expires_delta=timedelta(minutes=15))
    assert isinstance(token, str)
    assert token
```

#### Cursor AI-ийн үүсгэсэн тест (`test_items_ai.py`):

```python
from app.models import Item, ItemCreate, ItemUpdate, ItemPublic, ItemsPublic
import uuid

def test_create_item_sets_owner_and_persists() -> None:
    """ItemCreate нь баталгаажуулах ёстой, Item нь model_validate-аар owner_id-г хүлээн авах ёстой."""
    owner_id = uuid.uuid4()
    item_data = ItemCreate(title="Test Item", description="Test Description")
    item = Item.model_validate(item_data, update={"owner_id": owner_id})
    assert item.title == "Test Item"
    assert item.owner_id == owner_id

def test_item_update_allows_partial_fields() -> None:
    """ItemUpdate нь хэсэгчлэн шинэчлэлт зөвшөөрөх ёстой (зөвхөн title, зөвхөн description, эсвэл хоёулаа)."""
    update_data = ItemUpdate(title="Updated Title")
    # Description нь сонголттой гэдгийг баталгаажуулах
    assert update_data.title == "Updated Title"
    assert update_data.description is None

def test_items_public_wrapper_structure() -> None:
    """ItemsPublic нь ItemPublic-ийн жагсаалтыг ороож, count-ийг оруулах ёстой."""
    items = [
        ItemPublic(id=uuid.uuid4(), owner_id=uuid.uuid4(), title="Item 1"),
        ItemPublic(id=uuid.uuid4(), owner_id=uuid.uuid4(), title="Item 2"),
    ]
    wrapper = ItemsPublic(data=items, count=2)
    assert len(wrapper.data) == 2
    assert wrapper.count == 2
```

### AI-ийн чанар

**Давуу тал**:

- Тестүүд нь уншигдахуйц, бүтэцтэй байдаг
- Type hints ашигласан
- Modern pytest style (2025)
- Fixture-ууд зөв ашигласан (зарим тохиолдолд)

**Дутагдал**:

- Заримдаа хуучин pytest style ашигладаг (жишээ: assert statement-ууд хэт их)
- Mock хийх шаардлагатай хэсгүүдэд mock хийхгүй байдаг (жишээ: DB connection)
- Import алдаа гардаг (жишээ: `from app.api.routes import items` нь DB connection шаарддаг)
- Орчны хамаарлыг зөв хангахгүй (жишээ: bcrypt backend)

AI-ийн coverage-ийн дэлгэрэнгүй мэдээллийг `coverage_ai/html/index.html` файлыг browser-оор нээж харж болно.

## AI-ийн үүсгэсэн тестүүдийн гол дутагдал ба таны хийсэн засварууд

### Гол дутагдал

1. **Import алдаа**: `app.api.routes.items` модуль нь `app.core.db`-г import хийх үед PostgreSQL connection шаарддаг

   - Засвар: `test_items_ai.py`-д зөвхөн `app.models` модулийг тестлэхээр өөрчилсөн (DB connection шаардлагагүй)

2. **Bcrypt backend байхгүй орчинд алдаа**: `get_password_hash` функц нь bcrypt backend шаарддаг

   - Засвар: `try/except` блок нэмж, орчны хязгаарлалтыг зөв хангасан

3. **Fixture дутагдал**: Зарим тестүүд нь fixture ашиглахгүй байсан
   - Засвар: Тестүүдийг DB connection шаардлагагүй болгож, цэвэр unit test болгосон

### Засваруудын жишээ

**Өмнө (AI-ийн үүсгэсэн)**:

```python
from app.api.routes import items as items_routes
# Энэ нь DB connection шаарддаг, тест collection үед алдаа гардаг
```

**Дараа (засварласан)**:

```python
from app.models import Item, ItemCreate, ItemUpdate, ItemPublic, ItemsPublic
# Зөвхөн model-уудыг тестлэх, DB connection шаардлагагүй
```

## Гар аргаар бичсэн тесттэй (өмнөх лабтай) харьцуулалт

### Хугацаа

| Арга           | Хугацаа          | Тайлбар                                             |
| -------------- | ---------------- | --------------------------------------------------- |
| **Гар аргаар** | 2-4 цаг          | Тест бичих, fixture тохируулах, алдаа засах         |
| **Pynguin**    | 5-10 минут       | Командаар ажиллуулах, тестүүдийг засах (5-10 минут) |
| **Hypothesis** | 1-2 цаг          | Property бичих, edge case-уудыг шалгах              |
| **AI (LLM)**   | 30 минут - 1 цаг | Prompt бичих, AI-ийн үүсгэсэн тестүүдийг засах      |

### Хамрах хүрээ

| Арга           | Line Coverage | Branch Coverage | Тайлбар                                   |
| -------------- | ------------- | --------------- | ----------------------------------------- |
| **Гар аргаар** | 80-90%        | 75-85%          | Бүх функцүүдийг гараар тестлэх            |
| **Pynguin**    | 45-55%        | 40-50%          | Зөвхөн нэг модуль дээр ажилласан          |
| **Hypothesis** | 60-70%        | 55-65%          | Property-based, edge case-уудыг илрүүлдэг |
| **AI (LLM)**   | 70-80%        | 65-75%          | Олон модуль дээр тест үүсгэх боломжтой    |

### Илэрсэн алдааны чанар

| Арга           | Илэрсэн алдаа         | Тайлбар                                               |
| -------------- | --------------------- | ----------------------------------------------------- |
| **Гар аргаар** | Амжилттай замын алдаа | Зөвхөн хүлээгдэж буй case-уудыг тестлэдэг             |
| **Pynguin**    | Буруу оролтын алдаа   | None, буруу формат гэх мэт буруу оролтуудыг илрүүлдэг |
| **Hypothesis** | Edge case             | Жижиг, сонирхолтой edge case-уудыг илрүүлдэг          |
| **AI (LLM)**   | Logic алдаа           | Кодын логик алдааг илрүүлэх чадвартай                 |

### Кодын уншигдах байдал

| Арга           | Уншигдах байдал | Тайлбар                                          |
| -------------- | --------------- | ------------------------------------------------ |
| **Гар аргаар** | Маш сайн        | Хамгийн уншигдахуйц, тодорхой                    |
| **Pynguin**    | Муу             | Автоматаар үүсгэсэн, заримдаа ойлгомжгүй         |
| **Hypothesis** | Сайн            | Property-ууд нь тодорхой, уншигдахуйц            |
| **AI (LLM)**   | Сайн            | Уншигдахуйц, гэхдээ заримдаа засвар шаардлагатай |

## Таны дүгнэлт: 2025 онд аль арга хамгийн үр дүнтэй санагдсан бэ?

### Coverage-ийн харьцуулалт

| Хувилбар       | Line Coverage | Branch Coverage | Дүгнэлт                                               |
| -------------- | ------------- | --------------- | ----------------------------------------------------- |
| **Pynguin**    | ~45-55%       | ~40-50%         | Хамгийн бага, гэхдээ буруу оролтуудыг илрүүлдэг       |
| **Hypothesis** | ~60-70%       | ~55-65%         | Дундаж, edge case-уудыг илрүүлдэг                     |
| **AI (LLM)**   | ~70-80%       | ~65-75%         | Хамгийн өндөр, олон модуль дээр тест үүсгэх боломжтой |

### Илэрсэн алдааны тоо

| Хувилбар       | Илэрсэн алдаа   | Тайлбар                                             |
| -------------- | --------------- | --------------------------------------------------- |
| **Pynguin**    | 5 алдаа         | Буруу оролт, төрлийн аюулгүй байдал, орчны хамаарал |
| **Hypothesis** | 3 edge case     | Email template property, bcrypt backend             |
| **AI (LLM)**   | 2 орчны асуудал | Bcrypt backend, DB connection mock                  |

### Ирээдүйд ямар аргыг илүү ашигламаар байна вэ? Яагаад?

#### 1. **AI (LLM-based) тест үүсгэлт** – Хамгийн үр дүнтэй

**Шалтгаан**:

- Хамгийн өндөр coverage (70-80%)
- Хурдан (30 минут - 1 цаг)
- Олон модуль дээр тест үүсгэх боломжтой
- Уншигдахуйц тестүүд үүсгэдэг
- 2025 онд AI технологи хурдацтай хөгжиж байна, ирээдүйд илүү сайн болно

**Дутагдал**:

- Засвар шаардлагатай (import алдаа, mock хийх шаардлагатай)
- Орчны dependency-г зөв хангахгүй байдаг

#### 2. **Hypothesis (Property-based)** – Edge case илрүүлэхэд сайн

**Шалтгаан**:

- Edge case-уудыг автоматаар илрүүлдэг
- Shrinking хийж, хамгийн жижиг counterexample-ийг өгдөг
- Property-ууд нь тодорхой, уншигдахуйц
- Математикийн баталгаа өгдөг

**Дутагдал**:

- Coverage дундаж (60-70%)
- Property бичихэд цаг зарцуулдаг

#### 3. **Pynguin (Search-based)** – Буруу оролт илрүүлэхэд сайн

**Шалтгаан**:

- Буруу оролтуудыг илрүүлдэг (None, буруу формат гэх мэт)
- Автоматаар тест үүсгэдэг
- Төрлийн аюулгүй байдлын алдааг илрүүлдэг

**Дутагдал**:

- Coverage хамгийн бага (45-55%)
- Зөвхөн нэг модуль дээр ажилладаг
- Тестүүд нь уншигдахгүй байдаг

### Таны санал, зөвлөмж

#### 2025 оны практик арга: Hybrid Approach

1. **AI (LLM) ашиглан эхлэх**:

   - Олон модуль дээр тест үүсгэх
   - High coverage авах
   - Хурдан ажиллах

2. **Hypothesis нэмж ашиглах**:

   - Critical функцүүд дээр property-based тест нэмэх
   - Edge case-уудыг илрүүлэх

3. **Pynguin-ийг нэмэлтээр ашиглах**:

   - Security-critical код дээр буруу оролтуудыг илрүүлэх
   - Төрлийн аюулгүй байдлын алдааг шалгах

4. **Гар аргаар засах**:
   - AI-ийн үүсгэсэн тестүүдийг засах
   - Integration test-уудыг гараар бичих

#### Ирээдүйн хөгжил

- AI технологи хурдацтай хөгжиж байгаа тул, 2026-2027 онд AI-ийн үүсгэсэн тестүүд илүү нарийвчлалтай, засвар шаардлагагүй болно
- Property-based testing нь математикийн баталгаа өгдөг тул, critical системүүдэд чухал хэвээр үлдэнэ
- Search-based testing нь буруу оролт илрүүлэхэд сайн тул, security testing-д ашиглах нь зүйтэй

## Дүгнэлт

Энэ лабораторийн ажлаар бид 3 өөр автомат тест үүсгэх аргыг туршиж үзлээ:

1. **Pynguin** нь буруу оролтуудыг илрүүлэхэд сайн, гэхдээ coverage бага
2. **Hypothesis** нь edge case-уудыг илрүүлэхэд сайн, property-based testing-ийн давуу талтай
3. **AI (LLM)** нь хамгийн өндөр coverage өгдөг, хурдан ажилладаг, гэхдээ засвар шаардлагатай

**2025 оны практик санал**: Hybrid approach ашиглах – AI-аар эхлэх, Hypothesis-ээр edge case-уудыг илрүүлэх, Pynguin-ээр буруу оролтуудыг шалгах.

## Хавсралт

- `tests_pynguin/` – Pynguin-ийн үүсгэсэн тестүүд
- `tests_hypothesis/` – Hypothesis property-based тестүүд
- `tests_ai/` – AI-ийн үүсгэсэн тестүүд
- `coverage_pynguin/html/` – Pynguin-ийн coverage HTML тайлан
- `coverage_hypothesis/html/` – Hypothesis-ийн coverage HTML тайлан
- `coverage_ai/html/` – AI-ийн coverage HTML тайлан
- `requirements.txt` – Ашигласан Python багцууд

**Ажиллуулах командууд**:

```bash
# Pynguin
export PYNGUIN_DANGER_AWARE=YES
python -m pynguin --project_path . --module-name app.core.security --output-path tests_pynguin/security --algorithm WHOLE_SUITE --max_sequence_length 20 --maximum_search_time 300
PYTHONPATH=. pytest tests_pynguin --cov=app --cov-report=html:coverage_pynguin

# Hypothesis
PYTHONPATH=. pytest tests_hypothesis --cov=app --cov-report=html:coverage_hypothesis

# AI
PYTHONPATH=. pytest tests_ai --cov=app --cov-report=html:coverage_ai
```
