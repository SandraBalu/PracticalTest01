# PracticalTest01

### Project Description
This Android project was developed as part of the **"Elemente de Informatică Mobilă"** practical test (Practical Test 1).  
The app demonstrates fundamental Android concepts such as:
- Activity lifecycle management  
- State saving/restoration using `onSaveInstanceState()`  
- Inter-activity communication using `Intent` and `startActivityForResult()`  
- Use of a **started Service** and **BroadcastReceiver**  
- Background processing via a custom `Thread`  

---

### Functionality Overview
1. **Main Activity**
   - Two non-editable `TextView` fields showing click counts for two buttons.
   - Each button increments its own counter.
   - When the sum of the counters exceeds a defined threshold (5), a background service is started.

2. **Secondary Activity**
   - Displays the total number of button presses.
   - Two buttons: **OK** and **Cancel**, which return a message to the main activity via `Intent`.

3. **Service**
   - Runs in the background, computing arithmetic and geometric means of the two numbers.
   - Sends broadcast messages every 10 seconds with the results and a timestamp.

4. **BroadcastReceiver**
   - Logs all received broadcast messages in Logcat.

---

###  Technologies Used
- **Language:** Kotlin  
- **Minimum API:** 16 (Android 4.1 - Jelly Bean)  
- **Framework:** Android SDK  
- **IDE:** Android Studio  

---

###  License
This project is released under the [MIT License](./LICENSE).

---

### 🧠 Notes
The application was tested on an emulator running Android 11 (API 30).  
All required tasks (A–D) from the exam specification are implemented and functional.
