# Axtrion

## Live Reload & Auto-Test Configuration (IntelliJ IDEA + Maven)

Follow this guide to set up your IntelliJ IDEA development environment so that both your Spring Boot application and your unit tests automatically reload and re-run whenever you modify your code.

---

### 1. General Setup (Required for Both)

First, we need to allow IntelliJ to compile the project automatically in the background while the application is actively running.

1. Open your IDE settings using `Ctrl + Alt + S` (or `Cmd + ,` on Mac).
2. Navigate to **Build, Execution, Deployment** ➡️ **Compiler**.
3. Check the box for **Build project automatically**.
4. Go to **Advanced Settings** (located on the left menu).
5. Under the *Compiler* section, check the box for **Allow auto-make to start even if developed application is currently running**.
   *(Note: If you are using a very old version of IntelliJ, press `Ctrl + Shift + Alt + /`, enter the **Registry...**, and enable `compiler.automake.allow.when.app.running`)*.
6. Click **Apply** and then **OK**.

---

### 2. Application Live Reload (Spring Boot)

To make the Spring Boot server restart automatically when it detects code changes:

1. Ensure the development dependency is included in your `pom.xml` file:

```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-devtools</artifactId>
    <scope>runtime</scope>
    <optional>true</optional>
</dependency>
```

2. Run your application as you normally do. Now, whenever you edit a file and save (`Ctrl + S` / `Cmd + S`), IntelliJ will compile the change in the background, and **Spring Boot DevTools** will restart the application context within a few seconds.

---

### 3. Continuous Testing (Automatic Test Execution)

DevTools does not manage the lifecycle of tests. To make your tests automatically re-run whenever you modify your code, use IntelliJ's built-in feature:

1. Run your tests manually for the first time (Right-click your test folder or class ➡️ **Run 'All Tests'**).
2. In the bottom **Run** window (where you see the green/red results), look at the vertical toolbar on the far left.
3. Click the **Toggle auto-test** 🔄 button (an icon with two circular arrows or a circular arrow with a small blue "play" symbol).
4. **That's it!** Moving forward, whenever you modify either your source code or the test code, IntelliJ will automatically compile the changes and re-run your tests after 3 seconds of typing inactivity.

> 💡 **Pro Tip:** You can adjust the 3-second delay in **Settings** ➡️ **Advanced Settings** ➡️ by modifying the **Auto-test delay (ms)** value to match your preference.
